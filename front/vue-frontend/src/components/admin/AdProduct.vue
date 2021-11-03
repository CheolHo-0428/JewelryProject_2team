<template>
  <div class="outer">
    <p>상품목록</p>
    <p class="add"><a class="btn btn-dark" href="/regproduct">상품 등록</a></p>
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
              </select>
              <input class="form-control me-2" type="search" v-model="search" aria-label="Search">
              <button class="search" type="submit">
                <span class="material-icons-outlined">search</span>
              </button>
            </div>
          </td>
        </tr>
        <tr>
          <th scope="col">상품분류</th>
          <td>
            <form class="d-flex">
              <select name="product" class="op" @change="optionChange($event)">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="ring">반지</option>
                <option value="earrings">귀걸이</option>
                <option value="bracelet">팔찌</option>
                <option value="necklace">목걸이</option>
              </select>
              <input class="form-control me-2" type="search" v-model="search" aria-label="Search">
              <button class="search" type="submit">
                <span class="material-icons-outlined">search</span>
              </button>
            </form>
          </td>
        </tr>
      </tbody>
    </table>

    <table class="list">
      <colgroup>
        <col width="2%">
        <col width="13%">
        <col width="18%">
        <col width="21%">
        <col width="13%">
        <col width="9%">
        <col width="12%">
        <col width="12%">
      </colgroup>

      <thead>
        <tr>
          <th>#</th>
          <th>상품번호</th>
          <th>이미지</th>
          <th>상품명</th>
          <th>상품가격</th>
          <th>재고</th>
          <th></th>
          <th></th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="(item, i) in selectData" :key="i">
            <td>{{ total_elements - (page -1)*10 - i }}</td>
            <td>{{ item.id }}</td>
            <td class="img"><div></div></td>
            <td>{{ item.name }}</td>
            <td>{{ item.price }}</td>
            <td>{{ item.stock }}</td>
            <td class="button"><a href="/adproduct_">상세보기</a></td>
            <td class="button remove"><a @click="remove">상품삭제</a></td>
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
// import axios from 'axios'
// const url = 'http://localhost:8000/jewelry/item/paging'

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
      search: '',
      option: '',
      searchedData: [],
      isSearch: false,
      total_elements: 0
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
    // prevPage () {
    //   this.urlPage
    // },
    optionChange (event) {
      this.option = event.target.value
    },
    sortedName () {
      this.searchedData = this.allItems.filter(data => {
        return data.name.toLowerCase().includes(this.search.toLowerCase())
      })
      this.isSearch = true
      return this.searchedData
    },
    sortedId () {
      this.searchedData = this.allItems.filter(data => {
        return data.id.toLowerCase().includes(this.search.toLowerCase())
      })
      this.isSearch = true
      return this.searchedData
    }
  },
  computed: {
    selectData () {
      if (this.search && this.option === 'name') {
        return this.sortedName()
      } else if (this.search && this.option === 'id') {
        return this.sortedId()
      } else {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.isSearch = false
        return this.items
      }
    }
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
  margin-left: 35px;
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
