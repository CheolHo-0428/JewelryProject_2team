<template>
  <div class="outer">
    <p>장바구니</p>
    <!-- <div class="box"> -->
    <div class="box" v-for="(cart, i) in carts" :key="i">
      <!-- <div class="check">
        <v-checkbox name="checkbox" v-model="check" color="#FBCD6E"></v-checkbox>
        <label for="checkbox"></label>
      </div> -->
      <div class="img">
        <div></div>
      </div>
      <!-- <div v-for="(name,i) in names" :key="i"  class="content" >
        {{name}}
      </div>       -->
      <div class="content" >
        {{names[i]}}
      </div>
      <div class="count">
        {{cart.item_count}}
      </div>
      <div class="price">
        {{prices[i]}}
      </div>
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
              <td>{{product_total}}</td>
              <td>+</td>
              <td>{{delivery}}</td>
            </tr>
          </table>
      </div>
      <div class="total">
        <table>
          <th>총 주문금액</th>
          <tr>
            <td>{{order_total}}</td>
          </tr>
        </table>
      </div>
    </div>

    <div class="button">
      <v-btn color="#FBEF97" x-large href="/order">구매하기</v-btn>
      <v-btn color="#F4F2E7" x-large href="/">쇼핑 계속하기</v-btn>
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
      product_total: '',
      delivery: '',
      order_total: ''

    }
  },
  created () {
    this.cart()
  },
  methods: {
    cart () {
      return axios.get('http://localhost:8000/jewelry/cart/selectCart?member_id=' + this.$store.state.auth.user.id)
        .then(res => {
          this.carts = res.data.data
          console.log(res.data.data)
          console.log('길이' + this.carts.length)
          for (let i = 0; i < this.carts.length; i++) {
            axios.get('http://localhost:8000/jewelry/item/' + this.carts[i].item_id + '/itemInfo')
              .then(res => {
                this.names[i] = res.data.data.item_response.name
                this.prices[i] = res.data.data.item_response.price
                this.images[i] = res.data.data.item_response.image_file_response_list

                console.log(res.data.data.item_response.name)
                console.log(res.data.data.item_response.price)
                console.log(res.data.data.item_response.image_file_response_list)
                console.log('이름' + this.names)
                console.log('가격' + this.prices)
              })
          }
        })
        .catch(err => {
          console.log(err)
        }).finally(() => {
          console.log('여기서실행')
        })
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
.box, .bottom {
  display: flex;
  margin: 3rem;
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
.img {
  width: 26%;
}
.img div {
  width: 90px;
  height: 90px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  margin-left: 45px;
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
.calc,
.total {
  width: 50%;
  font-weight: 700;
}
table {
  width: 100%;
}
th {
  padding-bottom: 0.7rem;
}
td {
  color: #747272;
}

.button {
  display: flex;
  justify-content: center;
  margin-top: 2rem;
}
.button a {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
</style>
