<template>
  <div class="outer">
    <p>회원목록</p>

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
              <select name="admember" class="op" @change="optionChange($event)">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="name">고객명</option>
                <option value="account">회원아이디</option>
              </select>
              <input class="form-control me-2" type="search" @keyup.enter="selectData" v-model="keyword" aria-label="Search">
              <div class="search" @click="selectData">
                <span class="material-icons-outlined">search</span>
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <table class="list">
      <colgroup>
        <col width="14%">
        <col width="10%">
        <col width="16%">
        <col width="16%">
        <col width="32%">
        <col width="12%">
      </colgroup>

      <thead>
        <tr>
          <th>회원번호</th>
          <th>이름</th>
          <th>아이디</th>
          <th>전화번호</th>
          <th>이메일</th>
          <th>상세보기</th>
        </tr>
      </thead>
      <tbody v-if="!isSearch">
        <tr v-for="(member, i) in admembers" :key="i">
          <td class="tdNo" >{{total_elements - (page - 1)*10 - i}}</td>
          <td class="tdName">{{member.name}}</td>
          <td class="tdAccont">{{member.account}}</td>
          <td class="tdPhone">{{member.phone}}</td>
          <td class="tdEmail">{{member.email}}</td>
          <td class="button"><a @click="admemberDetail(member.id)">상세보기</a></td>
        </tr>
      </tbody>
      <tbody v-if="isSearch">
        <tr v-for="(member, i) in searchedData" :key="i">
          <td class="tdNo" >{{total_elements - (page - 1)*10 - i}}</td>
          <td class="tdName">{{member.name}}</td>
          <td class="tdAccont">{{member.account}}</td>
          <td class="tdPhone">{{member.phone}}</td>
          <td class="tdEmail">{{member.email}}</td>
          <td class="button"><a @click="admemberDetail(member.id)">상세보기</a></td>
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
const url = 'http://localhost:8000/jewelry/member/paging'

export default {
  data () {
    return {
      urlPage: this.$store.state.member.admemberPageUrl,
      admembers: [],
      allAdMembers: [],
      end: 0,
      next: false,
      page: 0,
      prev: false,
      start: 0,
      page_list: [],
      total_pages: 0,
      keyword: '',
      option: '',
      searchedData: [],
      isSearch: false,
      isName: false,
      total_elements: 0,
      searchPage: 0
      // created1: '',
      // created2: ''
    }
  },
  methods: {
    admemberDetail (id) {
      this.$store.commit('admemberDetail', {id: id, urlPage: this.urlPage})
      this.$router.push('/admember_')
    },
    changePage (page) {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${page - 1}`
        this.$store.commit('admemberDetail', {id: 0, urlPage: this.urlPage})
        this.admember()
      } else if (!this.isName) {
        this.searchPage = page - 1
        this.searchAccount()
      } else {
        this.searchPage = page - 1
        this.searchName()
      }
    },
    nextPage () {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${this.end}`
        this.$store.commit('admemberDetail', {id: 0, urlPage: this.urlPage})
        this.admember()
      } else if (!this.isName) {
        this.searchPage = this.end
        this.searchAccount()
      } else {
        this.searchPage = this.end
        this.searchName()
      }
    },
    prevPage () {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${this.start - 2}`
        this.$store.commit('admemberDetail', {id: 0, urlPage: this.urlPage})
        this.admember()
      } else if (!this.isName) {
        this.searchPage = this.start - 2
        this.searchAccount()
      } else {
        this.searchPage = this.start - 2
        this.searchName()
      }
    },
    admember () {
      this.isSearch = false
      this.isName = false
      return axios.get(this.urlPage)
        .then(res => {
          this.admembers = res.data.data
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
    admemberAll () {
      axios.get('http://localhost:8000/jewelry/member/')
        .then(res => {
          this.allAdMembers = res.data.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    optionChange (event) {
      this.option = event.target.value
    },
    searchAccount () {
      this.isSearch = true
      this.isName = false
      return axios.get(`http://localhost:8000/jewelry/member/searchAccount?keyword=${this.keyword}&page=${this.searchPage}`)
        .then(res => {
          this.searchedData = []
          this.searchedData = res.data.data
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
      this.isSearch = true
      this.isName = true
      return axios.get(`http://localhost:8000/jewelry/member/searchName?keyword=${this.keyword}&page=${this.searchPage}`)
        .then(res => {
          this.searchedData = []
          this.searchedData = res.data.data

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
    selectData () {
      if (this.option === 'account') {
        this.searchAccount()
      } else if (this.option === 'name') {
        this.searchName()
      } else {
        this.isSearch = false
        this.keyword = ''
        this.admember()
      }
    }
  },
  created () {
    this.admember()
    this.admemberAll()
  },
  mounted () {
    window.scrollTo(0, 0)
  }
}
</script>

<style scoped>
.outer {
  width: 950px;
  margin: 4rem auto;
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
  cursor:pointer;
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
.remove a {
  background-color: #eeeff0 !important;
  cursor: pointer;
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
