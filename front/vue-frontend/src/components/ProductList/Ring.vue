<template>
  <div>
    <div class="category">
      <p>Ring</p>
    </div>

    <div class="boxs">
      <div v-for="(list, i) in response_list" :key="i" class="list" :style="computedStyledObject">
        <div class="img" @click="change(list.id)"><img :src="stored_file_name[i]" /></div>
        <div class="product">
          <p class="name" @click="change(list.id)" :class="{unreg : status[i] === 'UNREGISTERED'}">{{list.name}} <span v-if="status[i] === 'UNREGISTERED'" style="color:#DF0101;font-size:0.95rem;">&nbsp; 일시품절</span></p>
          <p class="price" :class="{unreg : status[i] === 'UNREGISTERED'}">{{list.price}}원</p>
        </div>
      </div>
    </div>

    <!-- pagination -->
    <div class="outer">
      <div class="page">
        <div class="box">
          <a @click="prevPage" class="arrow pageNum" v-if="prev">&laquo;</a>
          <a @click="changePage(p)" v-for="(p, i) in page_list" class="pageNum" :key="i" :class="{'active' : page == p}">{{p}}</a>
          <a @click="nextPage" class="arrow pageNum" v-if="next">&raquo;</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
const url = 'http://localhost:8000/jewelry/category/4/itemPagingInfo'

export default {
  data () {
    return {
      urlPage: 'http://localhost:8000/jewelry/category/4/itemPagingInfo',
      response_list: [],
      end: 0,
      next: false,
      page: 0,
      prev: false,
      start: 0,
      page_list: [],
      total_pages: 0,
      total_elements: 0,
      current_elements: 0,
      stored_file_name: [],
      status: []
    }
  },
  methods: {
    change (id) {
      this.$store.commit('itemDetail', {id: id})
      this.$store.commit('changeVersion', 0)
      this.$router.push('/detail')
    },
    changePage (page) {
      this.urlPage = url + `?page=${page - 1}`
      this.ring()
    },
    nextPage () {
      this.urlPage = url + `?page=${this.end}`
      this.ring()
    },
    prevPage () {
      this.urlPage = url + `?page=${this.start - 2}`
      this.ring()
    },
    ring () {
      this.stored_file_name = []
      this.status = []
      return axios.get(this.urlPage)
        .then(res => {
          this.response_list = res.data.data.category_response.item_response_list

          for (let i = 0; i < this.response_list.length; i++) {
            if (this.response_list[i].image_file_response_list.length !== 0) {
              let tmp = res.data.data.category_response.item_response_list[i].image_file_response_list.findIndex(
                (i) => i.delegate_thumbnail === 'YES'
              )
              if (tmp === -1) this.stored_file_name.push(this.response_list[i].image_file_response_list[0].stored_file_name)
              else this.stored_file_name.push(this.response_list[i].image_file_response_list[tmp].stored_file_name)
            } else {
              this.stored_file_name.push(null)
            }
            this.status.push(this.response_list[i].status)
          }

          this.page = res.data.pagination.current_page + 1
          this.total_pages = res.data.pagination.total_pages
          this.total_elements = res.data.pagination.total_elements
          this.current_elements = res.data.pagination.current_elements

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
    }
  },
  created () {
    this.ring()
  },
  computed: {
    computedStyledObject () {
      return {
        width: this.current_elements === 1 ? '100%' : this.current_elements === 2 ? '33%' : this.current_elements === 3 ? '33%' : '25%'
      }
    }
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

.unreg {
  color: #747272;
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
img {
  width: 230px;
  height: 230px;
  margin: 2rem auto 1rem;
  background-size: cover;
  cursor: pointer;
}
img:hover {
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
  background-color: #555;
  color: white;
}
.page a:hover:not(.active) {
  background-color: #555;
  color:white;
}
.pageNum {
  cursor: pointer;
}
</style>
