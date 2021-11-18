<template>
<section class="notice">
  <div class="page-title">
        <div class="container">
            <h3 v-if="!isResign">회원목록</h3>
            <h3 v-if="isResign">탈퇴회원</h3>
        </div>
    </div>

    <!-- board seach area -->
    <div id="board-search">
        <div class="container">
            <div class="search-window">
                    <div class="search-wrap">
                      <select name="admember" class="op" @change="optionChange($event)">
                        <option value="" selected>-- 선택하세요 --</option>
                        <option value="name">고객명</option>
                        <option value="account">회원아이디</option>
                      </select>
                      <input v-if="!isResign" placeholder="검색어를 입력해주세요" class="form-control me-2" type="search" @keyup.enter="selectData" v-model="keyword" aria-label="Search">
                      <input v-if="isResign" placeholder="검색어를 입력해주세요" class="form-control me-2" type="search" @keyup.enter="resignSelectData" v-model="keyword" aria-label="Search">
                      <button v-if="!isResign" type="submit" class="btn btn-dark" @click="selectData">검색</button>
                      <button v-if="isResign" type="submit" class="btn btn-dark" @click="resignSelectData">검색</button>
                    </div>
            </div>
        </div>
    </div>

  <!-- board list area -->
    <div id="board-list">
        <div class="container">
            <table class="board-table">
                <thead>
                <tr class="tr_title trHead">
                    <th scope="col" class="th-num">번호</th>
                    <th v-if="!isResign" scope="col">이름</th>
                    <th v-if="!isResign" scope="col">아이디</th>
                    <th v-if="isResign" scope="col" class="th-name">이름</th>
                    <th v-if="isResign" scope="col" class="th-id">아이디</th>
                    <th v-if="!isResign" scope="col">전화번호</th>
                    <th v-if="isResign" scope="col" class="th-tel">전화번호</th>
                    <th v-if="isResign" scope="col" class="th-address">주소</th>
                    <th scope="col" class="th-email">이메일</th>
                    <th v-if="!isResign" scope="col" class="th-detail">상세보기</th>
                </tr>
                </thead>
                <tbody v-if="!isSearch && !isResign">
                  <tr v-for="(member, i) in admembers" :key="i">
                    <td>{{total_elements - (page - 1)*10 - i}}</td>
                    <td>{{member.name}}</td>
                    <td>{{member.account}}</td>
                    <td>{{member.phone}}</td>
                    <td>{{member.email}}</td>
                    <td class="button"><a @click="admemberDetail(member.id)">상세보기</a></td>
                  </tr>
                </tbody>
                <tbody v-if="!isSearch && isResign">
                  <tr v-for="(member, i) in resignAdmembers" :key="i">
                    <td class="tdNo" >{{resignTotal_elements - (resignPage - 1)*10 - i}}</td>
                    <td class="tdName">{{member.name}}</td>
                    <td class="tdAccont">{{member.account}}</td>
                    <td class="tdPhone">{{member.phone}}</td>
                    <td>{{member.address}}</td>
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
                    <td>{{member.address}}</td>
                    <td class="tdEmail">{{member.email}}</td>
                  </tr>
                </tbody>
            </table>
            <button v-if="isResign" class="memberButton" @click="admember()">회원목록</button>
            <button v-if="!isResign" class="memberButton" @click="resignAdmember()">탈퇴회원</button>
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
    </div>
</section>
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
        window.scrollTo(0, 0)
        this.searchAccount()
      } else if (this.option === 'name') {
        window.scrollTo(0, 0)
        this.searchName()
      } else if (!this.isResign) {
        this.isSearch = false
        this.isResign = false
        this.keyword = ''
        window.scrollTo(0, 0)
        this.admember()
      } else {
        this.isSearch = false
        this.keyword = ''
        window.scrollTo(0, 0)
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
.pageNum {
  cursor: pointer
}
.table {
  width: 950px;
  background-color: #fefff2;
  margin-bottom: 4rem
}
.table th {
  font-weight: 700;
  font-size: 1.1rem;
  vertical-align: middle
}
input {
  width: 100% !important;
  font-size: .8rem
}
th {
  text-align: center;
  padding: .6rem 0;
  font-size: .85rem
}
.op {
  position: absolute;
  height: 40px;
  left: -107px;
  border: 1px solid #ccc;
  color: gray;
  width: fit-content;
  font-size: .8rem;
  padding: .2rem .4rem;
  background-color: #fff;
  text-align: center
}
.form-control {
  margin-left: 1rem;
  width: 14rem !important
}
.form-control:focus {
  box-shadow: none;
  border: 1px solid #000
}
table {
  width: 950px
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem
}
.list td,
.list th {
  border-bottom: 1px solid #333;
  padding: .6rem;
  text-align: center
}
.list thead {
  background-color: #fefff2
}
.button a:hover {
  color: #fff
}
.button a {
  cursor: pointer;
  text-decoration: none;
  color: #fff;
  border: 1px solid #000;
  border-radius: 10px;
  padding: .2rem .4rem;
  font-size: .8rem;
  font-weight: 550;
  background-color: #555;
  box-shadow: 1px .5px 0 rgb(0, 0, 0, .5)
}
.button a:active {
  box-shadow: 1px 0 0 rgb(0, 0, 0, .5);
  position: relative;
  top: .5px
}
.page {
  display: inline-block;
  width: 950px;
  margin-top: 2rem
}
.box {
  margin: 0 auto;
  width: fit-content
}
.page a {
  color: #000;
  float: left;
  padding: 4px 12px;
  text-decoration: none;
  border-radius: 50%;
  margin: 0 .5rem
}
.page a.active {
  background-color: #555;
  color: #fff
}
.page a:hover:not(.active) {
  background-color: #555;
  color: #fff
}
.memberButton {
  margin-top: 15px;
  float: right;
  background: #555;
  color: #fff;
  border: none;
  position: relative;
  height: 40px;
  font-size: 1em;
  padding: 0 2em;
  cursor: pointer;
  transition: .8s ease all;
  outline: 0
}
.memberButton:hover {
  background: #000;
  color: #fff
}
table {
  border-collapse: collapse;
  border-spacing: 0
}
section.notice {
  padding: 80px 0
}
.page-title h3 {
  font-size: 28px;
  color: #333;
  font-weight: 400;
  text-align: left
}
#board-search .search-window {
  padding: 15px 0;
  float: right
}
#board-search .search-window .search-wrap {
  position: relative;
  padding-right: 124px;
  margin: 0 auto;
  width: 100%;
  max-width: 564px
}
#board-search .search-window .search-wrap input {
  height: 40px;
  width: 100%;
  font-size: 14px;
  padding: 7px 14px;
  border: 1px solid #ccc;
  border-radius: 0 !important
}
#board-search .search-window .search-wrap input:focus {
  border-color: #333;
  outline: 0;
  border-width: 1px
}
#board-search .search-window .search-wrap .btn {
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  width: 108px;
  padding: 0;
  font-size: 16px
}
.board-table {
  font-size: 13px;
  width: 100%;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc
}
.board-table a {
  color: #fff;
  display: inline-block;
  line-height: 1.4;
  word-break: break-all;
  vertical-align: middle
}
.board-table th {
  text-align: center
}
.board-table tr:hover:not(.trHead) {
  background: #e7e7e7
}
.board-table .th-num {
  width: 50px;
  text-align: center
}
.board-table .th-name {
  width: 80px
}
.board-table .th-id {
  width: 100px
}
.board-table .th-tel {
  width: 150px
}
.board-table td,
.board-table th {
  padding: 14px 0
}
.board-table tbody td {
  border-top: 1px solid #e7e7e7;
  text-align: center
}
.board-table tbody th {
  padding-left: 28px;
  padding-right: 14px;
  border-top: 1px solid #e7e7e7;
  text-align: left
}
.btn {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 400;
  background: 0 0;
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
  -webkit-transition: all .3s;
  -moz-transition: all .3s;
  -ms-transition: all .3s;
  -o-transition: all .3s;
  transition: all .3s
}
.btn-dark {
  background: #555;
  color: #fff
}
.btn-dark:focus,
.btn-dark:hover {
  background: #000;
  border-color: #000;
  color: #fff;
  box-shadow: none
}
* {
  list-style: none;
  text-decoration: none;
  padding: 0;
  margin: 0;
  box-sizing: border-box
}
.container {
  width: 900px;
  margin: 0 auto
}
</style>
