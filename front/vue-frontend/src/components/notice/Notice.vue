<template>
  <div class="outer">
    <p>공지사항</p>
    <p class="add"><a class="btn btn-dark" href="/regnotice">공지사항 등록</a></p>

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
                <option value="title">공지번호</option>
                <option value="id">공지제목</option>
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
        <tr v-for="(notice, i) in sortedData" :key="i">
          <td class="tdNo">{{notice.id}}</td>
          <td class="tdTitle" @click="detail(notice.id)">{{notice.title}}</td>
          <td class="tdDate" style="text-align:center;">{{createdDay[start + i -1]}} {{createdTime[start + i -1]}}</td>
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
      createdDay: [],
      createdTime: [],
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
      isSearch: false
    }
  },
  methods: {
    detail (id) {
      this.$store.commit('noticeDetail', {id: id, urlPage: this.urlPage})
      location.href = '/notice_'
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
          for (let i = 0; i < res.data.data.length; i++) {
            this.createdDay.push(res.data.data[i].created_at.split('T')[0])
            this.createdTime.push(res.data.data[i].created_at.split('T')[1].split('.')[0])
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    optionChange (event) {
      this.option = event.target.value
    }
  },
  created () {
    this.notice()
    this.noticeAll()
  },
  computed: {
    sortedData () {
      if (this.search) {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.searchedData = this.allNotices.filter(data => {
          return data.title.toLowerCase().includes(this.search.toLowerCase())
        }
        )
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.isSearch = true
        return this.searchedData
      } else {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.isSearch = false
        return this.notices
      }
    }
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
