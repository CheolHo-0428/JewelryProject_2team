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
          <th></th>
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
              <v-checkbox name="checkbox" v-model="check[i]" :value="cart.item_id" color="#FBCD6E" @change="select(i)"></v-checkbox>
              <label for="checkbox"></label>
            </div>
          </td>
          <td class="img"><div></div></td>
          <td>
            <strong class="itemName">{{names[i]}}</strong>
          </td>
          <td>{{prices[i]}}원</td>
          <td>{{cart.item_count}}</td>
          <td>{{prices[i] * cart.item_count}}원</td>
        </tr>
      </tbody>
    </table>

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
      <v-btn color="#FBEF97" x-large @click="order">구매하기</v-btn>
      <v-btn color="#F4F2E7" x-large to="/">쇼핑 계속하기</v-btn>
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
      order_total: 2500

    }
  },
  created () {
    this.cart()
  },
  methods: {
    select (i) {
      if (this.check[i]) this.product_total += (this.prices[i] * this.carts[i].item_count)
      else this.product_total -= (this.prices[i] * this.carts[i].item_count)
      this.order_total = this.product_total + 2500
    },
    cart () {
      return axios.get('http://localhost:8000/jewelry/cart/selectCart?member_id=' + this.$store.state.auth.user.id)
        .then(async res => {
          this.carts = res.data.data

          for (let i = 0; i < this.carts.length; i++) {
            await axios.get('http://localhost:8000/jewelry/item/' + this.carts[i].item_id + '/itemInfo')
              .then(res => {
                this.names.push(res.data.data.item_response.name)
                this.prices.push(res.data.data.item_response.price)
                this.images.push(res.data.data.item_response.image_file_response_list)
              })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    order () {
      this.$store.commit('resetCart')
      for (let i = 0; i < this.check.length; i++) {
        if (this.check[i]) {
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
  padding: 1rem 0;
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
.img div {
  width: 90px;
  height: 90px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
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
  margin-top: 2rem;
}
.button a,
.button button {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
</style>
