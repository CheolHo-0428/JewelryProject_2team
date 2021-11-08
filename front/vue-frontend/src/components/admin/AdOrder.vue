<template>
  <div class="outer">
    <p>주문목록</p>

    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="80%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">조건검색</th>
          <td>
            <form class="d-flex">
              <select name="order" class="op">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="memberId">주문자번호</option>
                <option value="num">주문번호</option>
              </select>
              <input class="form-control me-2" type="search" aria-label="Search">
              <button class="search" type="submit">
                <span class="material-icons-outlined">search</span>
              </button>
            </form>
          </td>
        </tr>
        <tr>
          <th scope="col">기간</th>
          <td class="date">
            <b-form-datepicker id="example-datepicker" placeholder="Choose a date" class="mb-2"></b-form-datepicker>
            <span> &nbsp; ~ &nbsp;</span>
            <b-form-datepicker id="example-datepicker" placeholder="Choose a date" class="mb-2"></b-form-datepicker>
          </td>
        </tr>
      </tbody>
    </table>

    <table class="list">
      <colgroup>
        <col width="20%">
        <col width="13%">
        <col width="16%">
        <col width="15%">
        <col width="17%">
        <col width="19%">
      </colgroup>

      <thead>
        <tr>
          <th>주문일</th>
          <th>주문번호</th>
          <th>주문자번호</th>
          <th>주문방법</th>
          <th>주문금액</th>
          <th></th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="(order, i) in orders" :key="i">
            <td>{{order.created_at.split('T')[0]}} {{order.created_at.split('T')[1].split('.')[0]}}</td>
            <td>{{order.id}}</td>
            <td>{{order.member_id}}</td>
            <td>무통장입금</td>
            <td>{{order.total_price}}원</td>
            <td class="button">
              <a @click="orderDetail(order.id)">상세보기</a>
            </td>
        </tr>
      </tbody>
    </table>

    <!-- pagination -->
    <div class="page">
      <div class="box">
        <a @click="prevPage" class="arrow pageNum" v-if="prev">&laquo;</a>
        <a @click="changePage(p)" v-for="(p, i) in page_list" class="pageNum" :key="i" :class="{'active' : page == p}">{{p}}</a>
        <a @click="nextPage" class="arrow pageNum" v-if="next">&raquo;</a>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      urlPage: `http://localhost:8000/jewelry/orderGroup/paging`,
      originUrl: `http://localhost:8000/jewelry/orderGroup/paging`,
      end: 0,
      next: false,
      page: 0,
      prev: false,
      start: 0,
      page_list: [],
      total_pages: 0,
      total_elements: 0,
      orders: [],
      bank: ''
    }
  },
  methods: {
    orderDetail (id) {
      this.$store.commit('changeOrderId', id)
      this.$router.push('/adorder_')
    },
    changePage (page) {
      this.urlPage = this.originUrl + `?page=${page - 1}`
      this.orderList()
    },
    nextPage () {
      this.urlPage = this.originUrl + `?page=${this.end}`
      this.orderList()
    },
    prevPage () {
      this.urlPage = this.originUrl + `?page=${this.start - 2}`
      this.orderList()
    },
    orderList () {
      return axios.get(this.urlPage)
        .then(res => {
          this.orders = []
          this.orders = res.data.data

          for (let i = 0; i < this.orders.length; i++) {
            if (this.orders[i].pay_account === 'IBK') this.bank = '기업은행000-00000000-00'
            else this.bank = '우리은행111-11111111-11'
          }

          this.page = res.data.pagination.current_page + 1
          this.total_pages = res.data.pagination.total_pages
          this.total_elements = res.data.pagination.total_elements

          let tmpEnd = parseInt(Math.ceil(this.page / 5.0) * 5)
          this.start = tmpEnd - 4
          this.prev = this.start > 1
          this.next = this.total_pages > tmpEnd
          this.end = this.total_pages > tmpEnd ? tmpEnd : this.total_pages

          this.page_list.length = 0
          for (let i = this.start; i <= this.end; i++) {
            this.page_list.push(i)
          }
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.orderList()
  }
}
</script>

<style scoped>
.outer {
  width: 950px;
  margin: 4rem auto;
}
a {
  cursor: pointer;
}
.table {
  width: 950px;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  background-color: #fefff2;
  margin-bottom: 4rem;
}
.table th,
.date span {
  font-weight: 700;
  font-size: 1.1rem;
  vertical-align: middle;
}
.date span {
  padding-top: 4px;
}
input {
  width: 100% !important;
  font-size: 0.8rem;
}
tr {
  border-bottom: 1.5px solid gray;
}
th {
  text-align: center;
  padding: 0.6rem 0;
  font-size: 0.85rem;
}
.op {
  border: 1px solid black;
  width: fit-content;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.2rem;
  background-color: white;
  text-align: center;
}
.state .op {
  margin: 0 auto;
}
.form-control {
  margin-left: 1rem;
  width: 14rem !important;
}
.form-control:focus {
  box-shadow: none;
  border: 1px solid black;
}
.material-icons-outlined {
  vertical-align: middle;
  margin-left: 1rem;
}
#example-datepicker__outer_ {
  margin: 0 !important;
  width: fit-content !important;
  text-align: center;
}
.date {
  display: flex;
}

table {
  width: 950px;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}
.list th,
.list td {
  border-bottom: 1px solid #333333;
  padding: 0.6rem;
  text-align: center;
}
.list thead {
  background-color:#fefff2;
}

.button a {
  text-decoration: none;
  color: black;
  border: 1px solid black;
  border-radius: 10px;
  padding: 0.2rem 0.4rem;
  font-size: 0.8rem;
  font-weight: 700;
  background-color: #fefff2;
  box-shadow: 1px 0.5px 0 rgb(0,0,0,0.5);
}
.button a:active {
  box-shadow: 1px 0px 0 rgb(0,0,0,0.5);
  position: relative;
  top: 0.5px;
}

.page {
  display: inline-block;
  width: 950px;
  margin-top: 2rem;
}
.box {
  margin: 0 auto;
  width: fit-content;
}
.page a {
  color: black;
  float: left;
  padding: 4px 12px;
  text-decoration: none;
  border-radius:50%;
  margin: 0 0.5rem;
}
.page a.active {
  background-color: #fde8b9;
  color: white;
}
.page a:hover:not(.active) {
  background-color: silver;
}
</style>
