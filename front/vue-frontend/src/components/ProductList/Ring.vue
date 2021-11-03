<template>
  <div>
    <div class="category">
      <p>Ring</p>
    </div>

    <div class="boxs">
      <div v-for="(list, i) in response_list" :key="i" class="list">
        <div class="img" @click="change(list.id)"></div>
        <div class="product">
          <p class="name">{{list.name}}</p>
          <p class="price">{{list.price}}원</p>
        </div>
      </div>
    </div>

    <!-- pagination -->
    <div class="outer">
      <div class="page">
        <div class="box">
          <a @click="prevPage" class="arrow pageNum" v-if="prev">&laquo;</a>
          <a @click="changePage(p)" v-for="i in 4" class="pageNum" :key="i" :class="{'active' : page + 1 == i}">{{i}}</a>
          <a @click="nextPage" class="arrow pageNum" v-if="next">&raquo;</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      response_list: [],
      end: 0,
      next: false,
      page: 0,
      prev: false,
      start: 0,
      page_list: [],
      total_pages: 0,
      total_elements: 0
    }
  },
  methods: {
    change (id) {
      this.$store.commit('itemDetail', {id: id})
      this.$store.commit('changeVersion', 0)
      this.$router.push('/detail')
    },
    ring () {
      axios.get('http://localhost:8000/jewelry/category/4/itemInfo')
        .then(res => {
          this.response_list = res.data.data.category_response.item_response_list
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.ring()
  }
}
</script>

<style scoped>
.category {
  text-align: center;
  margin-top: 4rem;
}
.category p {
  font-weight: 700;
  font-size: 1.4rem;
  margin-bottom: 1rem;
  text-decoration-line: underline;
}

.boxs {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 4rem !important;
  text-align: center;
  width: fit-content;
  max-width: 1340px;
  min-width: 1110px;
  margin: 0 auto;
}
.list {
  width: 25%;
}
.img {
  width: 230px;
  height: 230px;
  margin: 2rem 3rem 1rem;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  cursor: pointer;
}
.img:hover {
  transform:scale(1.01);
  transition: 0.2s;
}
.product p {
  margin-bottom: 0;
}
.name {
  font-weight: 700;
  cursor: pointer;
}

.outer {
  width: 750px;
  margin: 4rem auto;
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
</style>
