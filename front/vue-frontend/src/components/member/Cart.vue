<template>
  <div class="outer">
    <p>장바구니</p>
    <div class="box" v-for="(cart, i) in carts" :key="i">
      <div class="check">
        <v-checkbox color="#FBCD6E"></v-checkbox>
      </div>
      <div class="img">
        <div></div>
      </div>
      <div class="content">
        다이아 반지
      </div>
      <div class="count">
        수량
      </div>
      <div class="price">총 가격</div>
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
              <td>10000원</td>
              <td>+</td>
              <td>2500원</td>
            </tr>
          </table>
      </div>
      <div class="total">
        <table>
          <th>총 주문금액</th>
          <tr>
            <td>12500원</td>
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
// import axios from 'axios'

export default {
  data () {
    return {
      carts: [],
      end: 0,
      next: false,
      page: 0,
      prev: false,
      start: 0,
      page_list: [],
      total_pages: 0,
      total_elements: 0,
      searchPage: 0
    }
  },
  created () {
    // this.cart()
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
