<template>
<section class="notice">
  <div class="page-title">
        <div class="container">
            <h3>상품목록</h3>
        </div>
    </div>

    <!-- board seach area -->
    <div id="board-search">
        <div class="container">
            <div class="search-window">
                    <div class="search-wrap">
                        <select name="product" class="op" @change="optionChange($event)">
                          <option value="" selected>-- 선택하세요 --</option>
                          <option value="name">상품명</option>
                          <option value="id">상품번호</option>
                          <option value="category_id">상품분류</option>
                          <option value="status">등록상태</option>
                        </select>
                        <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." v-model="keyword">
                        <button type="submit" class="btn btn-dark" @click="selectData">검색</button>
                    </div>
            </div>
        </div>
    </div>

  <!-- board list area -->
    <div id="board-list">
        <div class="container">
            <table class="board-table">
                  <colgroup>
                    <col width="6%">
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
                    <tr class="tr_1 trHead">
                      <th>번호</th>
                      <th>상품번호</th>
                      <th>상품분류</th>
                      <th>이미지</th>
                      <th>상품명</th>
                      <th>상품가격</th>
                      <th>재고</th>
                      <th>등록상태</th>
                      <th>상세보기</th>
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
                        <td class="img"><img :src="stored_thumbnail[i]" /></td>
                        <td>{{ item.name }}</td>
                        <td>{{ item.price }}</td>
                        <td>{{ item.stock }}</td>
                        <td v-if="item.status=='REGISTERED'" style="color:blue">{{ item.status }}</td>
                        <td v-if="item.status=='UNREGISTERED'" style="color:red">{{ item.status }}</td>
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
                        <td class="img"><img :src="stored_thumbnail[i]" /></td>
                        <td>{{ item.name }}</td>
                        <td>{{ item.price }}</td>
                        <td>{{ item.stock }}</td>
                        <td>{{ item.status }}</td>
                        <td class="button"><a @click="detail(item.id)">상세보기</a></td>
                    </tr>
                  </tbody>
            </table>
          <div class="container page">
            <router-link class="btn btn-dark reg" to="/regproduct"  style="float: right;border-radius: 0;color: white;height: 40px;line-height: 29px;">상품등록</router-link>
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
const url = 'http://localhost:8000/jewelry/item/paging'

export default {
  data () {
    return {
      urlPage: 'http://localhost:8000/jewelry/item/paging',
      items: [],
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
      searchPage: 0,
      stored_thumbnail: []
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
        .then(async res => {
          this.items = res.data.data
          this.page = res.data.pagination.current_page + 1
          this.total_pages = res.data.pagination.total_pages
          this.total_elements = res.data.pagination.total_elements
          console.log(res.data.pagination)
          let tmpEnd = parseInt(Math.ceil(this.page / 5.0) * 5)
          this.start = tmpEnd - 4
          this.prev = this.start > 1
          this.next = this.total_pages > tmpEnd
          this.end = this.total_pages > tmpEnd ? tmpEnd : this.total_pages

          this.page_list.length = 0
          for (let i = this.start; i <= this.end; i++) {
            this.page_list.push(i)
          }
          this.stored_thumbnail = []
          for (let i = 0; i < this.items.length; i++) {
            await axios.get('http://localhost:8000/jewelry/item/' + this.items[i].id + '/itemInfo')
              .then(res => {
                let tmp = res.data.data.item_response.image_file_response_list.findIndex(
                  (i) => i.delegate_thumbnail === 'YES'
                )
                if (res.data.data.item_response.image_file_response_list.length !== 0) {
                  if (tmp === -1) this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[0].stored_file_name)
                  else this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[tmp].stored_file_name)
                } else {
                  this.stored_thumbnail.push(null)
                }
              })
          }
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
        .then(async res => {
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
          this.stored_thumbnail = []
          for (let i = 0; i < this.searchedData.length; i++) {
            await axios.get('http://localhost:8000/jewelry/item/' + this.searchedData[i].id + '/itemInfo')
              .then(res => {
                let tmp = res.data.data.item_response.image_file_response_list.findIndex(
                  (i) => i.delegate_thumbnail === 'YES'
                )
                if (res.data.data.item_response.image_file_response_list.length !== 0) {
                  if (tmp === -1) this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[0].stored_file_name)
                  else this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[tmp].stored_file_name)
                } else {
                  this.stored_thumbnail.push(null)
                }
              })
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
        .then(async res => {
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
          this.stored_thumbnail = []
          for (let i = 0; i < this.searchedData.length; i++) {
            await axios.get('http://localhost:8000/jewelry/item/' + this.searchedData[i].id + '/itemInfo')
              .then(res => {
                let tmp = res.data.data.item_response.image_file_response_list.findIndex(
                  (i) => i.delegate_thumbnail === 'YES'
                )
                if (res.data.data.item_response.image_file_response_list.length !== 0) {
                  if (tmp === -1) this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[0].stored_file_name)
                  else this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[tmp].stored_file_name)
                } else {
                  this.stored_thumbnail.push(null)
                }
              })
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
        .then(async res => {
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
          this.stored_thumbnail = []
          for (let i = 0; i < this.searchedData.length; i++) {
            await axios.get('http://localhost:8000/jewelry/item/' + this.searchedData[i].id + '/itemInfo')
              .then(res => {
                let tmp = res.data.data.item_response.image_file_response_list.findIndex(
                  (i) => i.delegate_thumbnail === 'YES'
                )
                if (res.data.data.item_response.image_file_response_list.length !== 0) {
                  if (tmp === -1) this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[0].stored_file_name)
                  else this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[tmp].stored_file_name)
                } else {
                  this.stored_thumbnail.push(null)
                }
              })
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
        .then(async res => {
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
          this.stored_thumbnail = []
          for (let i = 0; i < this.searchedData.length; i++) {
            await axios.get('http://localhost:8000/jewelry/item/' + this.searchedData[i].id + '/itemInfo')
              .then(res => {
                let tmp = res.data.data.item_response.image_file_response_list.findIndex(
                  (i) => i.delegate_thumbnail === 'YES'
                )
                if (res.data.data.item_response.image_file_response_list.length !== 0) {
                  if (tmp === -1) this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[0].stored_file_name)
                  else this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[tmp].stored_file_name)
                } else {
                  this.stored_thumbnail.push(null)
                }
              })
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
  },
  mounted () {
    window.scrollTo(0, 0)
  },
  computed: {
  }
}
</script>

<style scoped>
.table {
  width: 950px;
  border-top: .2rem solid #000;
  border-bottom: .2rem solid #000;
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
  left: -118px;
  border: 1px solid #ccc;
  color: gray;
  width: fit-content;
  font-size: .8rem;
  padding: .2rem;
  background-color: #fff;
  text-align: center
}
table {
  width: 950px
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem
}
img {
  width: 100px;
  height: 100px;
  background-size: cover
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
.button a {
  text-decoration: none;
  color: #000;
  border: 1px solid #000;
  border-radius: 10px;
  padding: .2rem .4rem;
  font-size: .8rem;
  font-weight: 550;
  background-color: #555;
  box-shadow: 1px .5px 0 rgb(0, 0, 0, .5);
  cursor: pointer
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
.pageNum {
  cursor: pointer
}
.reg {
  float: right;
  border-radius: 0;
  color: #fff;
  height: 40px;
  line-height: 29px
}
.btn-dark {
  float: right;
  line-height: 37px;
  height: 40px
}
.btn-dark:focus {
  box-shadow: none
}
.btn-dark:hover {
  background-color: gray
}
.button a:hover {
  color: #fff
}
table {
  border-collapse: collapse;
  border-spacing: 0
}
section.notice {
  padding: 80px 0
}
.page-title {
  margin-bottom: 40px
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
  border: 1px solid #ccc
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
.board-table tr:hover:not(.trHead) {
  background-color: #e7e7e7
}
.board-table th {
  text-align: center
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
  color: #fff;
  width: 108px
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
.container.page {
  margin-top: 2rem
}
</style>
