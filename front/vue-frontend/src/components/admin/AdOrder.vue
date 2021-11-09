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
            <div class="d-flex">
              <select name="order" class="op" @change="optionChange($event)">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="memberId">회원번호</option>
                <option value="name">주문자명</option>
                <option value="state">주문상태</option>
              </select>
              <input class="form-control me-2" @keyup.enter="selectDataSet" type="search" aria-label="Search" v-model="keyword">
              <button class="search" type="submit">
                <span class="material-icons-outlined" @click="selectDataSet">search</span>
              </button>
            </div>
          </td>
        </tr>
        <tr>
          <th scope="col">기간</th>
          <td class="date">
            <b-form-datepicker id="example-datepicker" placeholder="Choose a date" class="mb-2" v-model="date1"></b-form-datepicker>
            <span class="range"> &nbsp; ~ &nbsp;</span>
            <b-form-datepicker id="example-datepicker" placeholder="Choose a date" class="mb-2" v-model="date2"></b-form-datepicker>
            <button class="search" type="submit">
              <span class="material-icons-outlined" @click="selectDateTime">search</span>
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <table class="list">
      <colgroup>
        <col width="20%">
        <col width="13%">
        <col width="11%">
        <col width="13%">
        <col width="15%">
        <col width="15%">
        <col width="13%">
      </colgroup>

      <thead>
        <tr>
          <th>주문일</th>
          <th>주문번호</th>
          <th>회원번호</th>
          <th>주문방법</th>
          <th>주문금액</th>
          <th>주문상태</th>
          <th></th>
        </tr>
      </thead>

      <tbody v-if="!isSearch && !isDate">
        <tr v-for="(order, i) in orders" :key="i">
            <td v-if="order.created_at">{{order.created_at.split('T')[0]}} {{order.created_at.split('T')[1].split('.')[0]}}</td>
            <td>{{order.id}}</td>
            <td>{{order.member_id}}</td>
            <td>무통장입금</td>
            <td>{{order.total_price}}원</td>
            <td>{{order.order_product_state}}</td>
            <td class="button">
              <a @click="orderDetail(order.id)">상세보기</a>
            </td>
        </tr>
      </tbody>
      <tbody v-if="isDate || isSearch">
        <tr  v-for="(order, i) in searchedData" :key="i">
            <td v-if="order.created_at">{{order.created_at.split('T')[0]}} {{order.created_at.split('T')[1].split('.')[0]}}</td>
            <td>{{order.id}}</td>
            <td>{{order.member_id}}</td>
            <td>무통장입금</td>
            <td>{{order.total_price}}원</td>
            <td>{{order.order_product_state}}</td>
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
      option: '',
      keyword: '',
      isSearch: false,
      searchedData: [],
      searchPage: 0,
      isMember: false,
      isState: false,
      date1: '',
      date2: '',
      isDate: false,
      isName: false
    }
  },
  methods: {
    optionChange (event) {
      this.option = event.target.value
    },
    orderDetail (id) {
      this.$store.commit('changeOrderId', id)
      this.$router.push('/adorder_')
    },
    changePage (page) {
      if (this.isDate) {
        this.searchPage = page - 1
        this.searchDate()
      } else if (!this.isSearch) {
        this.urlPage = this.originUrl + `?page=${page - 1}`
        this.orderList()
      } else if (this.isMember) {
        this.searchPage = page - 1
        this.searchMember()
      } else if (this.isState) {
        this.searchPage = page - 1
        this.searchState()
      } else if (this.isName) {
        this.searchPage = page - 1
        this.searchName()
      }
    },
    nextPage () {
      if (this.isDate) {
        this.searchPage = this.end
        this.searchDate()
      } else if (!this.isSearch) {
        this.urlPage = this.originUrl + `?page=${this.end}`
        this.orderList()
      } else if (this.isMember) {
        this.searchPage = this.end
        this.searchMember()
      } else if (this.isState) {
        this.searchPage = this.end
        this.searchState()
      } else if (this.isName) {
        this.searchPage = this.end
        this.searchName()
      }
    },
    prevPage () {
      if (this.isDate) {
        this.searchPage = this.start - 2
        this.searchDate()
      } else if (!this.isSearch) {
        this.urlPage = this.originUrl + `?page=${this.start - 2}`
        this.orderList()
      } else if (this.isMember) {
        this.searchPage = this.start - 2
        this.searchMember()
      } else if (this.isState) {
        this.searchPage = this.start - 2
        this.searchState()
      } else if (this.isName) {
        this.searchPage = this.start - 2
        this.searchName()
      }
    },
    orderList () {
      console.log('orderList')
      this.isSearch = false
      return axios.get(this.urlPage)
        .then(res => {
          this.orders = []
          this.orders = res.data.data

          for (let i = 0; i < this.orders.length; i++) {
            if (this.orders[i].order_product_state === 'BEFORE_BANK_TRANSFER') this.orders[i].order_product_state = '입금전'
            else if (this.orders[i].order_product_state === 'READY') this.orders[i].order_product_state = '배송준비중'
            else if (this.orders[i].order_product_state === 'SHIPPING') this.orders[i].order_product_state = '배송중'
            else if (this.orders[i].order_product_state === 'COMPLETE') this.orders[i].order_product_state = '배송완료'
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
    },
    searchState () {
      console.log('state')
      this.isMember = false
      this.isName = false
      this.isState = true
      this.isSearch = true
      if (this.option === 'state') {
        let keyword = ''
        if (this.keyword === '입금전') keyword = 'BEFORE_BANK_TRANSFER'
        else if (this.keyword === '배송준비중') keyword = 'READY'
        else if (this.keyword === '배송중') keyword = 'SHIPPING'
        else if (this.keyword === '배송완료') keyword = 'COMPLETE'

        return axios.get(`http://localhost:8000/jewelry/orderGroup/searchState?state=${keyword}&page=${this.searchPage}`)
          .then(res => {
            this.searchedData = []
            this.searchedData = res.data.data

            for (let i = 0; i < this.searchedData.length; i++) {
              if (this.searchedData[i].order_product_state === 'BEFORE_BANK_TRANSFER') this.searchedData[i].order_product_state = '입금전'
              else if (this.searchedData[i].order_product_state === 'READY') this.searchedData[i].order_product_state = '배송준비중'
              else if (this.searchedData[i].order_product_state === 'SHIPPING') this.searchedData[i].order_product_state = '배송중'
              else if (this.searchedData[i].order_product_state === 'COMPLETE') this.searchedData[i].order_product_state = '배송완료'
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
    searchMember () {
      console.log('member')
      this.isMember = true
      this.isState = false
      this.isName = false
      this.isSearch = true
      return axios.get(`http://localhost:8000/jewelry/orderGroup/${this.keyword}/searchMember?page=${this.searchPage}`)
        .then(res => {
          this.searchedData = []
          this.searchedData = res.data.data

          for (let i = 0; i < this.searchedData.length; i++) {
            if (this.searchedData[i].order_product_state === 'BEFORE_BANK_TRANSFER') this.searchedData[i].order_product_state = '입금전'
            else if (this.searchedData[i].order_product_state === 'READY') this.searchedData[i].order_product_state = '배송준비중'
            else if (this.searchedData[i].order_product_state === 'SHIPPING') this.searchedData[i].order_product_state = '배송중'
            else if (this.searchedData[i].order_product_state === 'COMPLETE') this.searchedData[i].order_product_state = '배송완료'
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
    },
    searchName () {
      console.log('name')
      this.isName = true
      this.isMember = false
      this.isState = false
      this.isSearch = true
      return axios.get(`http://localhost:8000/jewelry/orderGroup/searchName?name=${this.keyword}&page=${this.searchPage}`)
        .then(res => {
          this.searchedData = []
          this.searchedData = res.data.data

          for (let i = 0; i < this.searchedData.length; i++) {
            if (this.searchedData[i].order_product_state === 'BEFORE_BANK_TRANSFER') this.searchedData[i].order_product_state = '입금전'
            else if (this.searchedData[i].order_product_state === 'READY') this.searchedData[i].order_product_state = '배송준비중'
            else if (this.searchedData[i].order_product_state === 'SHIPPING') this.searchedData[i].order_product_state = '배송중'
            else if (this.searchedData[i].order_product_state === 'COMPLETE') this.searchedData[i].order_product_state = '배송완료'
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
    },
    searchDate () {
      console.log('date')
      return axios.get(`http://localhost:8000/jewelry/orderGroup/searchDate?date1=${this.date1}&date2=${this.date2}&page=${this.searchPage}`)
        .then(res => {
          this.searchedData = []
          this.searchedData = res.data.data

          for (let i = 0; i < this.searchedData.length; i++) {
            if (this.searchedData[i].order_product_state === 'BEFORE_BANK_TRANSFER') this.searchedData[i].order_product_state = '입금전'
            else if (this.searchedData[i].order_product_state === 'READY') this.searchedData[i].order_product_state = '배송준비중'
            else if (this.searchedData[i].order_product_state === 'SHIPPING') this.searchedData[i].order_product_state = '배송중'
            else if (this.searchedData[i].order_product_state === 'COMPLETE') this.searchedData[i].order_product_state = '배송완료'
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
    },
    selectDateTime () {
      this.isDate = true
      this.selectData()
    },
    selectDataSet () {
      this.isDate = false
      this.selectData()
    },
    selectData () {
      if (this.isDate) {
        this.isSearch = false
        this.searchPage = 0
        this.searchDate()
      } else if (this.option === 'state') {
        this.isDate = false
        this.searchPage = 0
        this.searchState()
      } else if (this.option === 'memberId') {
        this.isDate = false
        this.searchPage = 0
        this.searchMember()
      } else if (this.option === 'name') {
        this.isDate = false
        this.searchPage = 0
        this.searchName()
      } else {
        this.isSearch = false
        this.isDate = false
        this.keyword = ''
        this.urlPage = 'http://localhost:8000/jewelry/orderGroup/paging'
        this.orderList()
      }
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
.date .range {
  font-weight: 700;
  font-size: 1.1rem;
  vertical-align: middle;
}
.date .range {
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
