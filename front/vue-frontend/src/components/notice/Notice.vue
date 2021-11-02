<template>
  <div class="outer">
    <p>공지사항</p>
    <p  v-if="findRole === true" class="add"><router-link class="btn btn-dark" to="/regnotice">공지사항 등록</router-link></p>
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
              <select name="member" class="op" @change="optionChange($event)">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="writer">작성자</option>
                <option value="title">공지제목</option>
              </select>
              <input class="form-control me-2" type="search" v-model="search" aria-label="Search">
              <div class="search">
                <span class="material-icons-outlined">search</span>
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <table class="list">
      <colgroup>
      <col width="100"/><col width="420"/><col width="140"/><col width="130"/>
      </colgroup>

      <thead>
        <tr>
          <th>#</th>
          <th>TITLE</th>
          <th style="text-align:center;">DATE</th>
          <th style="text-align:center;">WRITER</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="(notice, i) in selectData" :key="i">
          <td class="tdNo">{{total_elements - (page - 1)*10 - i}}</td>
          <td class="tdTitle" @click="detail(notice.id)">{{notice.title}}</td>
          <td class="tdDate" style="text-align:center;">{{notice.created_at.split('T')[0]}} {{notice.created_at.split('T')[1].split('.')[0]}}</td>
          <td class="tdWriter">{{notice.writer}}</td>
        </tr>
      </tbody>
    </table>

    <!-- pagination -->
    <div class="page" v-if="!isSearch">
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
      search: '',
      option: '',
      searchedData: [],
      isSearch: false,
      total_elements: 0
    }
  },
  methods: {
    detail (id) {
      this.$store.commit('noticeDetail', {id: id, urlPage: this.urlPage})
      this.$router.push('/notice_')
    },
    changePage (page) {
      this.urlPage = url + `?page=${page - 1}`
      this.$store.commit('noticeDetail', {id: 0, urlPage: this.urlPage})
      this.notice()
    },
    nextPage () {
      this.urlPage = url + `?page=${this.end}`
      this.$store.commit('noticeDetail', {id: 0, urlPage: this.urlPage})
      this.notice()
    },
    prevPage () {
      this.urlPage = url + `?page=${this.start - 2}`
      this.$store.commit('noticeDetail', {id: 0, urlPage: this.urlPage})
      this.notice()
    },
    notice () {
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
    sortedTitle () {
      this.searchedData = this.allNotices.filter(data => {
        return data.title.toLowerCase().includes(this.search.toLowerCase())
      })
      this.isSearch = true
      return this.searchedData
    },
    sortedWriter () {
      this.searchedData = this.allNotices.filter(data => {
        return data.writer.toLowerCase().includes(this.search.toLowerCase())
      })
      this.isSearch = true
      return this.searchedData
    }
  },
  created () {
    this.notice()
    this.noticeAll()
  },
  computed: {
    selectData () {
      if (this.search && this.option === 'title') {
        return this.sortedTitle()
      } else if (this.search && this.option === 'writer') {
        return this.sortedWriter()
      } else {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.isSearch = false
        return this.notices
      }
    },
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
.outer {
  width: 750px;
  margin: 4rem auto;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.table {
  width: 750px;
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
.table tr {
  border-bottom: 1.5px solid gray;
}
.table th {
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
  width: 13rem !important;
}
.form-control:focus {
  box-shadow: none;
  border: 1px solid black;
}
.material-icons-outlined {
  vertical-align: middle;
  margin-left: -2.8rem;
}

.list th {
  border-bottom: 1px solid #333333;
  padding: 1rem;
}
.list td {
  border-bottom: 1px solid #333333;
  padding: 0.3rem;
}
.list thead {
  background-color:#fefff2;
}
.toNo, .tdDate , .tdWriter {
  text-align:center;
}
.tdTitle {
  cursor: pointer;
}
.tdTitle:hover {
  font-size: 1.02rem;
}
.page {
  display: inline-block;
  width: 750px;
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
.pageNum {
  cursor: pointer;
}

.add {
  margin: 0;
  width: 750px;
  height: 50px;
}
.btn-dark {
  float: right;
}
.btn-dark:focus {
  box-shadow: none;
}
.btn-dark:hover {
  background-color: gray;
}
</style>
