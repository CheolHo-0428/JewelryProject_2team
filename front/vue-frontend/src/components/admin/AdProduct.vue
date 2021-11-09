<template>
  <div class="outer">
    <p>상품목록</p>
    <p class="add"><router-link class="btn btn-dark" to="/regproduct">상품등록</router-link></p>
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
              <select name="product" class="op" @change="optionChange($event)">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="name">상품명</option>
                <option value="id">상품번호</option>
                <option value="category_id">상품분류</option>
                <option value="status">등록상태</option>
              </select>
              <input class="form-control me-2" type="search" v-model="keyword" aria-label="Search">
              <div class="search" @click="selectData">
                <span class="material-icons-outlined">search</span>
              </div>
            </div>
          </td>
        </tr>
        <!-- <tr>
          <th scope="col">상품분류</th>
          <td>
            <div class="d-flex">
              <select name="product" class="op" @change="optionChange($event)">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="1">팔찌</option>
                <option value="2">귀걸이</option>
                <option value="3">목걸이</option>
                <option value="4">반지</option>
              </select>
              <input class="form-control me-2" type="search" v-model="search" aria-label="Search">
              <button class="search" type="submit">
                <span class="material-icons-outlined">search</span>
              </button>
            </div>
          </td>
        </tr> -->
      </tbody>
    </table>

    <table class="list">
      <colgroup>
        <col width="2%">
        <col width="13%">
        <col width="13%">
        <col width="5%">
        <col width="17%">
        <col width="13%">
        <col width="9%">
        <col width="12%">
        <col width="12%">
      </colgroup>

      <thead>
        <tr>
          <th>#</th>
          <th>상품번호</th>
          <th>상품분류</th>
          <th>이미지</th>
          <th>상품명</th>
          <th>상품가격</th>
          <th>재고</th>
          <th>등록상태</th>
          <th></th>
        </tr>
      </thead>

      <tbody v-if="!isSearch">
        <tr v-for="(item, i) in items" :key="i">
            <td>{{ total_elements - (page -1)*8 - i }}</td>
            <td>gguluck-{{ item.id }}-21Y11M</td>
            <td v-if="item.category_id === 1">BRACELET</td>
            <td v-else-if="item.category_id === 2">EARRINGS</td>
            <td v-else-if="item.category_id === 3">NECKLACE</td>
            <td v-else-if="item.category_id === 4">RING</td>
            <td v-else>OTHER</td>
            <td class="img"><div></div></td>
            <td>{{ item.name }}</td>
            <td>{{ item.price }}</td>
            <td>{{ item.stock }}</td>
            <td>{{ item.status }}</td>
            <td class="button"><a @click="detail(item.id)">상세보기</a></td>
        </tr>
      </tbody>
      <tbody v-if="isSearch">
        <tr v-for="(item, i) in searchedData" :key="i">
            <td>{{ total_elements - (page -1)*8 - i }}</td>
            <td>gguluck-{{ item.id }}-21Y11M</td>
            <td v-if="item.category_id === 1">BRACELET</td>
            <td v-else-if="item.category_id === 2">EARRINGS</td>
            <td v-else-if="item.category_id === 3">NECKLACE</td>
            <td v-else-if="item.category_id === 4">RING</td>
            <td v-else>OTHER</td>
            <td class="img"><div></div></td>
            <td>{{ item.name }}</td>
            <td>{{ item.price }}</td>
            <td>{{ item.stock }}</td>
            <td>{{ item.status }}</td>
            <td class="button"><a @click="detail(item.id)">상세보기</a></td>
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
const url = 'http://localhost:8000/jewelry/item/paging'

export default {
  data () {
    return {
      urlPage: this.$store.state.item.itemPageUrl,
      items: [],
      allItems: [],
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
      isId: false,
      isCategoryId: false,
      isStatus: false,
      total_elements: 0,
      searchPage: 0
    }
  },
  methods: {
    remove () {
      this.$swal.fire({
        icon: 'warning',
        title: '상품 삭제시 되돌릴 수 없습니다.',
        text: '상품목록에서 삭제하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$swal.fire({
            icon: 'success',
            title: '상품이 삭제되었습니다.',
            confirmButtonColor: '#CEF6CE'
          })
        }
      })
    },
    detail (id) {
      this.$store.commit('itemDetail', {id: id, urlPage: this.urlPage})
      this.$router.push('/adproduct_')
    },
    changePage (page) {
      window.scrollTo(0, 0)
      if (!this.isSearch) {
        this.urlPage = url + `?page=${page - 1}`
        this.$store.commit('itemDetail', {id: 0, urlPage: this.urlPage})
        this.item()
      } else if (!this.isId && !this.isCategoryId && !this.isStatus) {
        this.searchPage = page - 1
        this.searchName()
      } else if (!this.isName && !this.isCategoryId && !this.isStatus) {
        this.searchPage = page - 1
        this.searchId()
      } else if (!this.isName && !this.isId && !this.isStatus) {
        this.searchPage = page - 1
        this.searchCategoryId()
      } else if (!this.isName && !this.isId && !this.isCategoryId) {
        this.searchPage = page - 1
        this.searchStatus()
      }
    },
    nextPage () {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${this.end}`
        this.$store.commit('itemDetail', {id: 0, urlPage: this.urlPage})
        this.item()
      } else if (!this.isId && !this.isCategoryId && !this.isStatus) {
        this.searchPage = this.end
        this.searchName()
      } else if (!this.isName && !this.isCategoryId && !this.isStatus) {
        this.searchPage = this.end
        this.searchId()
      } else if (!this.isName && !this.isId && !this.isStatus) {
        this.searchPage = this.end
        this.searchCategoryId()
      } else if (!this.isName && !this.isId && !this.isCategoryId) {
        this.searchPage = this.end
        this.searchStatus()
      }
    },
    prevPage () {
      if (!this.isSearch) {
        this.urlPage = url + `?page=${this.start - 2}`
        this.$store.commit('itemDetail', {id: 0, urlPage: this.urlPage})
        this.item()
      } else if (!this.isId && !this.isCategoryId && !this.isStatus) {
        this.searchPage = this.start - 2
        this.searchName()
      } else if (!this.isName && !this.isCategoryId && !this.isStatus) {
        this.searchPage = this.start - 2
        this.searchId()
      } else if (!this.isName && !this.isId && !this.isStatus) {
        this.searchPage = this.start - 2
        this.searchCategoryId()
      } else if (!this.isName && !this.isId && !this.isCategoryId) {
        this.searchPage = this.start - 2
        this.searchStatus()
      }
    },
    item () {
      this.isSearch = false
      this.isName = false
      this.isId = false
      this.isCategoryId = false
      this.isStatus = false
      return axios.get(this.urlPage)
        .then(res => {
          this.items = res.data.data
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
    itemAll () {
      axios.get('http://localhost:8000/jewelry/item/')
        .then(res => {
          this.allItems = res.data.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    optionChange (event) {
      this.option = event.target.value
    },
    searchName () {
      this.isSearch = true
      this.isName = true
      this.isId = false
      this.isCategoryId = false
      this.isStatus = false
      return axios.get(`http://localhost:8000/jewelry/item/search?keyword=${this.keyword}&page=${this.searchPage}`)
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
    searchId () {
      this.isSearch = true
      this.isName = false
      this.isId = true
      this.isCategoryId = false
      this.isStatus = false
      return axios.get(`http://localhost:8000/jewelry/item/searchId?keyword=${this.keyword}&page=${this.searchPage}`)
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
    searchCategoryId () {
      this.isSearch = true
      this.isName = false
      this.isId = false
      this.isCategoryId = true
      this.isStatus = false
      return axios.get(`http://localhost:8000/jewelry/item/searchCategoryId?keyword=${this.keyword}&page=${this.searchPage}`)
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
    searchStatus () {
      this.isSearch = true
      this.isName = false
      this.isId = false
      this.isCategoryId = false
      this.isStatus = true
      return axios.get(`http://localhost:8000/jewelry/item/searchStatus?keyword=${this.keyword}&page=${this.searchPage}`)
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
      if (this.option === 'id') {
        this.searchId()
      } else if (this.option === 'name') {
        this.searchName()
      } else if (this.option === 'category_id') {
        this.searchCategoryId()
      } else if (this.option === 'status') {
        this.searchStatus()
      } else {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.isSearch = false
        this.keyword = ''
        this.item()
      }
    }
  },
  created () {
    this.item()
    this.itemAll()
  },
  mounted () {
    window.scrollTo(0, 0)
  },
  computed: {
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
  cursor: pointer;
}

table {
  width: 950px;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}
.img div {
  width: 100px;
  height: 100px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  /* margin-left: 35px; */
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
  cursor: pointer;
}
.remove a {
  background-color: #eeeff0 !important;
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
.pageNum {
  cursor: pointer;
}
.add {
  margin: 0;
  width: 950px;
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
