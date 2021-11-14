<template>
  <div class="top">
    <div class="category">
      <p>Searched Items {{this.$store.state.item.searchedItem}}</p>
    </div>

    <div class="boxs">
      <div v-for="(list, i) in searchedData" :key="i" class="list" :style="computedStyledObject">
        <div class="img" @click="change(list.id)"><img :src="stored_thumbnail[i]" /></div>
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
          <a @click="changePage(p)" v-for="(p, i) in page_list" class="pageNum" :key="i" :class="{'active' : page == p}">{{p}}</a>
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
      searchedData: [],
      end: 0,
      next: false,
      page: 0,
      prev: false,
      start: 0,
      page_list: [],
      total_pages: 0,
      total_elements: 0,
      current_elements: 0,
      searchPage: 0,
      stored_thumbnail: []
    }
  },
  methods: {
    change (id) {
      this.$store.commit('itemDetail', {id: id})
      this.$store.commit('changeVersion', 0)
      this.$router.push('/detail')
    },
    changePage (page) {
      this.searchPage = page - 1
      this.serchItem()
    },
    nextPage () {
      this.searchPage = this.end
      this.serchItem()
    },
    prevPage () {
      this.searchPage = this.start - 2
      this.serchItem()
    },
    serchItem () {
      this.stored_thumbnail = []
      return axios.get(`http://localhost:8000/jewelry/item/search?keyword=${this.$store.state.item.searchedItem}&page=${this.searchPage}`)
        .then(async res => {
          this.searchedData = []
          this.searchedData = res.data.data

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
    }
  },
  created () {
    this.serchItem()
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
.top {
  min-height: 50vh;
}
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

.material-icons-outlined {
  vertical-align: middle;
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
