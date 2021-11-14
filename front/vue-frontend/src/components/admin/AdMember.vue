<template>
  <div class="outer">
    <p v-if="!isResign">회원목록</p>
    <p v-if="isResign">탈퇴회원</p>

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
              <input v-if="!isResign" class="form-control me-2" type="search" @keyup.enter="selectData" v-model="keyword" aria-label="Search">
              <div v-if="!isResign" class="search" @click="selectData">
                <span class="material-icons-outlined">search</span>
              </div>
              <input v-if="isResign" class="form-control me-2" type="search" @keyup.enter="resignSelectData" v-model="keyword" aria-label="Search">
              <div v-if="isResign" class="search" @click="resignSelectData">
                <span class="material-icons-outlined">search</span>
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td style="text-align:right"><button class="memberButton" @click="admember()">회원목록</button></td>
          <td><button class="memberButton" @click="resignAdmember()">탈퇴회원</button></td>
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
          <th v-if="isResign">주소</th>
          <th>이메일</th>
          <th v-if="!isResign">상세보기</th>
        </tr>
      </thead>
      <tbody v-if="!isSearch && !isResign">
        <tr v-for="(member, i) in admembers" :key="i">
          <td class="tdNo" >{{total_elements - (page - 1)*10 - i}}</td>
          <td class="tdName">{{member.name}}</td>
          <td class="tdAccont">{{member.account}}</td>
          <td class="tdPhone">{{member.phone}}</td>
          <td class="tdEmail">{{member.email}}</td>
          <td class="button"><a @click="admemberDetail(member.id)">상세보기</a></td>
        </tr>
      </tbody>
      <tbody v-if="!isSearch && isResign">
        <tr v-for="(member, i) in resignAdmembers" :key="i">
          <td class="tdNo" >{{resignTotal_elements - (resignPage - 1)*10 - i}}</td>
          <td class="tdName">{{member.name}}</td>
          <td class="tdAccont">{{member.account}}</td>
          <td class="tdPhone">{{member.phone}}</td>
          <td>{{member.address}}{{member.detail_address}}</td>
          <td class="tdEmail">{{member.email}}</td>
        </tr>
      </tbody>
      <tbody v-if="isSearch && !isResign">
        <tr v-for="(member, i) in searchedData" :key="i">
          <td class="tdNo" >{{total_elements - (page - 1)*10 - i}}</td>
          <td class="tdName">{{member.name}}</td>
          <td class="tdAccont">{{member.account}}</td>
          <td class="tdPhone">{{member.phone}}</td>
          <td class="tdEmail">{{member.email}}</td>
          <td class="button"><a @click="admemberDetail(member.id)">상세보기</a></td>
        </tr>
      </tbody>
      <tbody v-if="isSearch && isResign">
        <tr v-for="(member, i) in resignSearchedData" :key="i">
          <td class="tdNo" >{{resignTotal_elements - (resignPage - 1)*10 - i}}</td>
          <td class="tdName">{{member.name}}</td>
          <td class="tdAccont">{{member.account}}</td>
          <td class="tdPhone">{{member.phone}}</td>
          <td>{{member.address}}{{member.detail_address}}</td>
          <td class="tdEmail">{{member.email}}</td>
        </tr>
      </tbody>
    </table>

    <!-- pagination -->
    <div v-if="!isResign" class="page">
      <div class="box">
        <a @click="prevPage" class="arrow pageNum" v-if="prev">&laquo;</a>
        <a @click="changePage(p)" v-for="(p, i) in page_list" class="pageNum" :key="i" :class="{'active' : page == p}">{{p}}</a>
        <a @click="nextPage" class="arrow pageNum" v-if="next">&raquo;</a>
      </div>
    </div>
    <div v-if="isResign" class="page">
      <div class="box">
        <a @click="resignPrevPage" class="arrow pageNum" v-if="resignPrev">&laquo;</a>
        <a @click="resignChangePage(p)" v-for="(p, i) in resign_page_list" class="pageNum" :key="i" :class="{'active' : resignPage == p}">{{p}}</a>
        <a @click="resignNextPage" class="arrow pageNum" v-if="resignNext">&raquo;</a>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
const url = 'http://localhost:8000/jewelry/member/paging'
const resignUrl = 'http://localhost:8000/jewelry/resignMember/paging'

export default {
  data () {
    return {
      urlPage: 'http://localhost:8000/jewelry/member/paging',
      resignUrlPage: 'http://localhost:8000/jewelry/resignMember/paging',
      admembers: [],
      resignAdmembers: [],
      allResignAdmembers: [],
      allAdMembers: [],
      resignEnd: 0,
      end: 0,
      next: false,
      resignNext: false,
      page: 0,
      resignPage: 0,
      prev: false,
      resignPrev: false,
      start: 0,
      resignStart: 0,
      page_list: [],
      resign_page_list: [],
      total_pages: 0,
      resignTotal_pages: 0,
      keyword: '',
      option: '',
      searchedData: [],
      resignSearchedData: [],
      isSearch: false,
      isName: false,
      isResign: false,
      total_elements: 0,
      resignTotal_elements: 0,
      searchPage: 0,
      resignSearchPage: 0
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
        if (!this.isResign) {
          this.admember()
        } else {
          this.resignAdmember()
        }
      } else if (!this.isName) {
        if (!this.isResign) {
          this.searchPage = page - 1
          this.searchAccount()
        } else {
          this.resignSearchPage = page - 1
          this.resignSearchAccount()
        }
      } else {
        if (!this.isResign) {
          this.searchPage = page - 1
          this.searchName()
        } else {
          this.resignSearchPage = page - 1
          this.resignSearchName()
        }
      }
    },
    resignChangePage (resignPage) {
      if (!this.isSearch) {
        this.resignUrlPage = resignUrl + `?page=${resignPage - 1}`
        this.resignAdmember()
        console.log('3')
      } else if (!this.isName) {
        this.resignSearchPage = resignPage - 1
        this.resignSearchAccount()
        console.log('1')
      } else {
        this.resignSearchPage = resignPage - 1
        this.resignSearchName()
        console.log('2')
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
    resignNextPage () {
      this.isResign = true
      if (!this.isSearch) {
        this.urlPage = resignUrl + `?page=${this.resignEnd}`
        this.resignAdmember()
      } else if (!this.isName) {
        this.searchPage = this.resignEnd
        this.resignSearchAccount()
      } else {
        this.searchPage = this.resignEnd
        this.resignSearchName()
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
    resignPrevPage () {
      this.isResign = true
      if (!this.isSearch) {
        this.urlPage = url + `?page=${this.resignStart - 2}`
        // this.$store.commit('admemberDetail', {id: 0, urlPage: this.urlPage})
        this.resignAdmember()
      } else if (!this.isName) {
        this.searchPage = this.resignStart - 2
        this.resignSearchAccount()
      } else {
        this.searchPage = this.resignStart - 2
        this.resignSearchName()
      }
    },
    admember () {
      this.isSearch = false
      this.isName = false
      this.isResign = false
      return axios.get(this.urlPage)
        .then(res => {
          this.admembers = res.data.data
          console.log(res.data.pagination)
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
    resignAdmember () {
      this.isSearch = false
      this.isName = false
      this.isResign = true
      return axios.get(this.resignUrlPage)
        .then(res => {
          this.resignAdmembers = res.data.data
          this.resignPage = res.data.pagination.current_page + 1
          this.resignTotal_pages = res.data.pagination.total_pages
          this.resignTotal_elements = res.data.pagination.total_elements
          let tmpEnd = parseInt(Math.ceil(this.resignPage / 5.0) * 5)
          this.resignStart = tmpEnd - 4
          this.resignPrev = this.resignStart > 1
          this.resignNext = this.resignTotal_pages > tmpEnd
          this.resignEnd = this.resignTotal_pages > tmpEnd ? tmpEnd : this.resignTotal_pages
          this.resign_page_list.length = 0
          for (let i = this.resignStart; i <= this.resignEnd; i++) {
            this.resign_page_list.push(i)
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
    resignAdmemberAll () {
      axios.get('http://localhost:8000/jewelry/resignMember/')
        .then(res => {
          this.allResignAdmembers = res.data.data
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
    resignSearchAccount () {
      this.isSearch = true
      this.isName = false
      this.isResign = true
      return axios.get(`http://localhost:8000/jewelry/resignMember/searchAccount?keyword=${this.keyword}&page=${this.resignSearchPage}`)
        .then(res => {
          this.resignSearchedData = []
          this.resignSearchedData = res.data.data
          this.resignPage = res.data.pagination.current_page + 1
          this.resignTotal_pages = res.data.pagination.total_pages
          this.resignTotal_elements = res.data.pagination.total_elements
          let tmpEnd = parseInt(Math.ceil(this.resignPage / 5.0) * 5)
          this.resignStart = tmpEnd - 4
          this.resignPrev = this.resignStart > 1
          this.resignNext = this.resignTotal_pages > tmpEnd
          this.resignEnd = this.resignTotal_pages > tmpEnd ? tmpEnd : this.resignTotal_pages
          this.resign_page_list.length = 0
          for (let i = this.resignStart; i <= this.resignEnd; i++) {
            this.resign_page_list.push(i)
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
    resignSearchName () {
      this.isSearch = true
      this.isName = true
      this.isResign = true
      return axios.get(`http://localhost:8000/jewelry/resignMember/searchName?keyword=${this.keyword}&page=${this.resignSearchPage}`)
        .then(res => {
          this.resignSearchedData = []
          this.resignSearchedData = res.data.data
          this.resignPage = res.data.pagination.current_page + 1
          this.resignTotal_pages = res.data.pagination.total_pages
          this.resignTotal_elements = res.data.pagination.total_elements
          let tmpEnd = parseInt(Math.ceil(this.page / 5.0) * 5)
          this.resignStart = tmpEnd - 4
          this.resignPrev = this.resignStart > 1
          this.resignNext = this.resignTotal_pages > tmpEnd
          this.resignEnd = this.resignTotal_pages > tmpEnd ? tmpEnd : this.resignTotal_pages
          this.resign_page_list.length = 0
          for (let i = this.resignStart; i <= this.resignEnd; i++) {
            this.resign_page_list.push(i)
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
      } else if (!this.isResign) {
        this.isSearch = false
        this.isResign = false
        this.keyword = ''
        this.admember()
      } else {
        this.isSearch = false
        this.keyword = ''
        this.resignAdmember()
      }
    },
    resignSelectData () {
      if (this.option === 'account') {
        this.resignSearchAccount()
      } else if (this.option === 'name') {
        this.resignSearchName()
      } else if (!this.isResign) {
        this.isSearch = false
        this.isResign = false
        this.keyword = ''
        this.admember()
      } else {
        this.isSearch = false
        this.keyword = ''
        this.resignAdmember()
      }
    }
  },
  created () {
    this.admember()
    this.admemberAll()
    // this.resignAdmemberAll()
  },
  mounted () {
    window.scrollTo(0, 0)
  }
}
</script>

<style scoped>
.pageNum{
  cursor:pointer;
}
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
.memberButton{
  background:black;
  color:#fff;
  border:none;
  position:relative;
  height:30px;
  font-size:1em;
  padding:0 2em;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.memberButton:hover{
  background:#fff;
  color:black;
}
.memberButton:before,.memberButton:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: gray;
  transition:400ms ease all;
}
.memberButton:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.memberButton:hover:before,.memberButton:hover:after{
  width:100%;
  transition:800ms ease all;
}
</style>
