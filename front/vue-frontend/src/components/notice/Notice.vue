<template>
<section class="notice">
  <div class="page-title">
    <div class="container">
        <h3>공지사항</h3>
    </div>
  </div>
  <!-- board seach area -->
  <div id="board-search">
      <div class="container">
          <div class="search-window">
              <div class="search-wrap">
                <select name="member" class="op" @change="optionChange($event)">
                  <option value="" selected>-- 선택하세요 --</option>
                  <option value="writer">작성자</option>
                  <option value="title">공지제목</option>
                </select>
                <label for="search" class="blind">공지사항 내용 검색</label>
                <input class="form-control me-2" @keyup.enter="selectData" type="search" v-model="keyword" aria-label="Search" placeholder="검색어를 입력해 주세요.">
                <button type="submit" class="btn btn-dark" @click="selectData">검색</button>
              </div>
          </div>
      </div>
  </div>
  <!-- board list area -->
    <div id="board-list">
        <div class="container">
            <table class="board-table">
                <thead>
                <tr>
                    <th scope="col" class="th-num">번호</th>
                    <th scope="col" class="th-title">제목</th>
                    <th scope="col" class="th-date">등록일</th>
                    <th scope="col" class="th-writer">작성자</th>
                </tr>
                </thead>
                <tbody v-if="!isSearch">
                  <tr v-for="(notice, i) in notices" :key="i">
                    <td class="tdNo" >{{total_elements - (page - 1)*10 - i}}</td>
                    <th class="tdTitle" @click="detail(notice.id)">{{notice.title}}</th>
                    <td class="tdDate" style="text-align:center;">{{notice.created_at.split('T')[0]}} {{notice.created_at.split('T')[1].split('.')[0]}}</td>
                    <td class="tdWriter">{{notice.writer}}</td>
                  </tr>
                </tbody>
                <tbody v-if="isSearch">
                  <tr v-for="(notice, i) in searchedData" :key="i">
                    <td class="tdNo" >{{total_elements - (page - 1)*10 - i}}</td>
                    <th class="tdTitle" @click="detail(notice.id)">{{notice.title}}</th>
                    <td class="tdDate" style="text-align:center;">{{notice.created_at.split('T')[0]}} {{notice.created_at.split('T')[1].split('.')[0]}}</td>
                    <td class="tdWriter">{{notice.writer}}</td>
                  </tr>
                </tbody>
            </table>
            <p v-if="findRole === true"><router-link class="btn btn-dark" to="/regnotice">공지사항 등록</router-link></p>
            <div class="container page">
              <div class="box">
                <a @click="prevPage" class="arrow pageNum" v-if="prev">&laquo;</a>
                <a @click="changePage(p)" v-for="(p, i) in page_list" class="pageNum" :key="i" :class="{'active' : page == p}">{{p}}</a>
                <a @click="nextPage" class="arrow pageNum" v-if="next">&raquo;</a>
              </div>
            </div>
          </div>
        </div>
</section>
</template>

<script>
import axios from 'axios'
const url = 'http://localhost:8000/jewelry/noticeBoard/paging'

export default {
  data () {
    return {
      urlPage: this.$store.state.notice.noticePageUrl,
      notices: [],
      allNotices: [],
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
      isWriter: false,
      total_elements: 0,
      searchPage: 0
    }
  },
  methods: {
    detail (id) {
      this.$store.commit('noticeDetail', {id: id, urlPage: this.urlPage})
      this.$router.push('/notice_')
    },
    changePage (page) {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${page - 1}`
        this.$store.commit('noticeDetail', {id: 0, urlPage: this.urlPage})
        this.notice()
      } else if (!this.isWriter) {
        this.searchPage = page - 1
        this.searchTitle()
      } else {
        this.searchPage = page - 1
        this.serchWriter()
      }
    },
    nextPage () {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${this.end}`
        this.$store.commit('noticeDetail', {id: 0, urlPage: this.urlPage})
        this.notice()
      } else if (!this.isWriter) {
        this.searchPage = this.end
        this.searchTitle()
      } else {
        this.searchPage = this.end
        this.serchWriter()
      }
    },
    prevPage () {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${this.start - 2}`
        this.$store.commit('noticeDetail', {id: 0, urlPage: this.urlPage})
        this.notice()
      } else if (!this.isWriter) {
        this.searchPage = this.start - 2
        this.searchTitle()
      } else {
        this.searchPage = this.start - 2
        this.serchWriter()
      }
    },
    notice () {
      this.isSearch = false
      this.isWriter = false
      return axios.get(this.urlPage)
        .then(res => {
          this.notices = res.data.data

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
    noticeAll () {
      axios.get('http://localhost:8000/jewelry/noticeBoard/')
        .then(res => {
          this.allNotices = res.data.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    optionChange (event) {
      this.option = event.target.value
    },
    searchTitle () {
      this.isSearch = true
      this.isWriter = false
      return axios.get(`http://localhost:8000/jewelry/noticeBoard/searchTitle?keyword=${this.keyword}&page=${this.searchPage}`)
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
    serchWriter () {
      this.isSearch = true
      this.isWriter = true
      return axios.get(`http://localhost:8000/jewelry/noticeBoard/searchWriter?keyword=${this.keyword}&page=${this.searchPage}`)
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
      if (this.option === 'title') {
        this.searchTitle()
      } else if (this.option === 'writer') {
        this.serchWriter()
      } else {
        this.isSearch = false
        this.keyword = ''
        this.notice()
      }
    }
  },
  created () {
    this.notice()
    this.noticeAll()
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    findRole () {
      if (this.currentUser) {
        return this.currentUser.roles.includes('ROLE_ADMIN')
      }
      return false
    }
  },
  mounted () {
    window.scrollTo(0, 0)
  }
}
</script>

<style scoped>
p {
  font-weight: 400;
  font-size: 28px;
  text-align: center;
  color: #333;
  margin-top: 15px
}
a.btn.btn-dark {
  height: 40px;
  line-height: 38px;
  width: 108px;
  padding: 0
}
.table {
  width: 750px;
  background-color: #fefff2;
  border-collapse: collapse;
  border-spacing: 0;
  margin-bottom: 4rem
}
.table th {
  font-weight: 700;
  font-size: 1.1rem;
  vertical-align: middle
}
input {
  font-size: .8rem
}
.table th {
  text-align: center;
  padding: .6rem 0;
  font-size: .85rem
}
.op {
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
  width: 13rem !important
}
.form-control:focus {
  box-shadow: none;
  border: 1px solid #000
}

.list th {
  border-bottom: 1px solid #333;
  padding: 1rem
}
.list td {
  border-bottom: 1px solid #333;
  padding: .3rem
}
.list thead {
  background-color: #fefff2
}

.tdDate,
.tdWriter {
  text-align: center
}
.tdTitle {
  cursor: pointer
}
.tdTitle:hover {
  font-size: 1.02rem
}
.page {
  display: inline-block;
  width: 750px;
  margin-top: 3rem
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
.pageNum {
  cursor: pointer
}

.btn-dark {
  float: right
}
.btn-dark:focus {
  box-shadow: none
}
.btn-dark:hover {
  background-color: gray
}

select {
  position: absolute;
  height: 40px;
  width: 100%;
  font-size: 14px;
  padding: 7px 14px;
  border: 1px solid #ccc
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
  color: #333;
  display: inline-block;
  line-height: 1.4;
  word-break: break-all;
  vertical-align: middle
}
.board-table a:hover {
  text-decoration: underline
}
.board-table th {
  text-align: center
}
.board-table .th-num {
  width: 100px;
  text-align: center
}
.board-table .th-date {
  width: 200px
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
  background: #373737;
  border-color: #373737;
  color: #fff
}
.btn-dark {
  background: #555;
  color: #fff
}
.btn-dark:focus,
.btn-dark:hover {
  background: #373737;
  border-color: #373737;
  color: #fff
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
.container .page {
  margin-top: 2rem
}
.blind {
  position: absolute;
  overflow: hidden;
  clip: rect(0 0 0 0);
  margin: -1px;
  width: 1px;
  height: 1px
}
</style>
