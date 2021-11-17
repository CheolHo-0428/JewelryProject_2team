<template>
  <div class="outer">
    <p>장바구니</p>
    <table border="1" class="info">
      <colgroup>
        <col style="width:8%">
        <col style="width:15%">
        <col style="width:32%">
        <col style="width:17%">
        <col style="width:10%">
        <col style="width:28%">
      </colgroup>
      <thead>
        <tr>
          <th><v-checkbox class="selectAll" color="#555" @change="selectAll()"></v-checkbox></th>
          <th scope="col">이미지</th>
          <th scope="col">상품정보</th>
          <th scope="col">판매가</th>
          <th scope="col">수량</th>
          <th scope="col">합계</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(cart, i) in carts" :key="i">
          <td>
            <div class="check">
              <v-checkbox name="checkbox" v-model="check[i]" :value="cart.item_id" color="#555" @change="select(i)"></v-checkbox>
              <label for="checkbox"></label>
            </div>
          </td>
          <td class="img"><img :src="stored_thumbnail[i]" /></td>
          <td>
            <strong class="itemName">{{names[i]}}</strong>
          </td>
          <td>{{prices[i]}}원</td>
          <td>{{cart.item_count}}
            <span class="countBtn"><button @click="countminus(i)">▼</button><button @click="cart.item_count++">▲</button></span>
          </td>
          <td>{{prices[i] * cart.item_count}}원</td>
        </tr>
      </tbody>
    </table>
    <div class="button remove">
      <a @click="remove" class="btn btn-dark">선택상품 삭제</a>
    </div>

    <div class="bottom">
      <div class="calc">
          <table>
            <colgroup>
              <col width="80"/><col width="10"/><col width="70"/>
            </colgroup>
            <th>총 상품금액</th>
            <th>+</th>
            <th>배송비</th>
            <tr>
              <td>{{product_total}}원</td>
              <td>+</td>
              <td>2500원</td>
            </tr>
          </table>
      </div>
      <div class="total">
        <table>
          <th>총 주문금액</th>
          <tr>
            <td>{{order_total}}원</td>
          </tr>
        </table>
      </div>
    </div>

    <div class="button">
      <a class="btn btn-dark btn2" @click="order">구매하기</a>
      <router-link to="/" class="btn btn-dark btn2">쇼핑 계속하기</router-link>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      check: [],
      carts: [],
      items: [],
      prices: [],
      names: [],
      images: [],
      product_total: 0,
      order_total: 2500,
      stored_thumbnail: [],
      allCheck: false
    }
  },
  created () {
    this.cart()
  },
  methods: {
    countminus (i) {
      if (this.carts[i].item_count > 1) {
        this.carts[i].item_count--
      }
    },
    select (i) {
      if (this.check[i]) this.product_total += (this.prices[i] * this.carts[i].item_count)
      else this.product_total -= (this.prices[i] * this.carts[i].item_count)
      this.order_total = this.product_total + 2500
    },
    selectAll () {
      for (let i = 0; i < this.carts.length; i++) {
        if (!this.allCheck) {
          this.check[i] = this.carts[i].item_id
          this.product_total += (this.prices[i] * this.carts[i].item_count)
        } else {
          this.check[i] = null
          this.product_total -= (this.prices[i] * this.carts[i].item_count)
        }
        this.order_total = this.product_total + 2500
      }
      this.allCheck = !this.allCheck
    },
    cart () {
      this.stored_thumbnail = []
      this.names = []
      this.prices = []
      this.images = []
      this.check = []
      return axios.get('http://localhost:8000/jewelry/cart/selectCart?member_id=' + this.$store.state.auth.user.id)
        .then(async res => {
          this.carts = res.data.data

          for (let i = 0; i < this.carts.length; i++) {
            await axios.get('http://localhost:8000/jewelry/item/' + this.carts[i].item_id + '/itemInfo')
              .then(res => {
                this.names.push(res.data.data.item_response.name)
                this.prices.push(res.data.data.item_response.price)
                this.images.push(res.data.data.item_response.image_file_response_list)

                let tmp = res.data.data.item_response.image_file_response_list.findIndex(
                  (i) => i.delegate_thumbnail === 'YES'
                )
                if (res.data.data.item_response.image_file_response_list.length !== 0) {
                  if (tmp === -1) this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[0].stored_file_name)
                  else this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[tmp].stored_file_name)
                }
              })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    order () {
      let count = 0
      this.$store.commit('resetCart')
      for (let i = 0; i < this.check.length; i++) {
        if (this.check[i]) {
          count++
        }
      }
      if (count === 0) {
        this.$swal.fire({
          icon: 'warning',
          title: '구매할 상품을 선택하세요.',
          confirmButtonColor: '#FE9A2E'
        })
      } else {
        for (let i = 0; i < this.check.length; i++) {
          if (this.check[i]) {
            count++
            this.$store.commit('removeCartList', this.carts[i].id)
            this.$store.commit('cchangeCount', this.carts[i].item_count)
            this.$store.commit('cchangeItemId', this.carts[i].item_id)
            this.$store.commit('cchangePrice', this.prices[i])
            this.$store.commit('cchangeName', this.names[i])
            // console.log(this.carts[i].item_count, this.carts[i].item_id, this.prices[i], this.names[i])
          }
        }
        this.$store.commit('changeOrderCart', true)
        this.$router.push('/order')
      }
    },
    async remove () {
      this.product_total = 0
      for (let i = 0; i < this.check.length; i++) {
        if (this.check[i]) {
          await axios
            .delete(`http://localhost:8000/jewelry/cart/${this.carts[i].id}`, {
              data: {
                id: this.carts[i].id
              }
            })
            .then(function (response) {
              console.log(response)
            })
            .catch(function (error) {
              console.log(error)
            })
        }
      }
      this.cart()
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 3rem auto;
  width: 52rem;
  text-align: center;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
}

.info {
  margin-top: 3rem;
  border-left: none;
  border-right: none;
}
.info thead {
  border-bottom: 1px solid black;
}
.info thead th {
  padding: 0.1rem 0;
  vertical-align: middle;
}
.info tbody td {
  padding: 0.4rem 0;
  vertical-align: middle;
}
.info tr {
  border-bottom: 0.5px solid black;
}

.box, .bottom {
  display: flex;
  border-bottom: 1px solid black;;
}
.img, .content {
  height: 100px;
}
.check {
  width: 5%;
  text-align: center;
  padding-top: 15px;
  padding-left: 5px;
}
.check checkbox {
  width: 18px;
  height: 18px;
}
.selectAll {
  margin-left: 5px;
}

img {
  width: 90px;
  height: 90px;
  background-size: cover;
  margin: 0 auto;
}
.count,
.price {
  width: 15%;
  padding-top: 30px;
}
.content {
  width: 38%;
  padding-top: 30px;
}
.countBtn {
  color: black;
}

.bottom {
  margin-top: 5rem;
  border-top: 1px solid black;
  padding: 36px 0;
}
.calc {
  width: 60%;
  font-weight: 700;
}
.total {
  width: 40%;
  font-weight: 700;
}
table {
  width: 100%;
}
th {
  padding-bottom: 1rem;
}
td {
  color: #747272;
}

.button {
  display: flex;
  justify-content: center;
  padding: 2rem 0;
}
.btn {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 400;
  background: transparent;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  border: 1px solid transparent;
  text-transform: uppercase;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -ms-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}
.btn-dark {
  background: #fff;
  border-color: #000;
  color: #000;
  padding: 0.4rem 1.2rem;
  font-size: 1.1rem;
}
.btn-dark:hover, .btn-dark:focus {
  background: #000;
  border-color: #fff;
  color: #fff;
}
.button.remove {
  justify-content: right;
}
.btn2 {
  background: #000;
  color: #fff;
  padding: 0.8rem 0;
  width: 180px;
  margin: 0 1rem;
  font-size: 1.2rem;
}
.btn2:hover {
  background: #fff;
  border-color: #000;
  color: #000;
}
</style>
