<template>
  <div class="outer">
    <div class="button">
      <v-btn color="#F4F2E7" to="/regreview">리뷰작성</v-btn>
    </div>
    <div class="box" v-for="(list, i) in response_list" :key="i">
      <div class="img" v-if="list.stored_file_name">
        <div><img :src="list.stored_file_name"></div>
      </div>
      <div class="img" v-if="!list.stored_file_name">
        <div></div>
      </div>
      <div class="content" @click="detail(list.id)">
        {{list.title}}
      </div>
      <div class="innerBox">
        <div class="writer">작성자 : {{list.writer}}</div>
        <div class="date">{{list.created_at.split('T')[0]}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      response_list: []
    }
  },
  methods: {
    detail (id) {
      this.$store.commit('reviewDetail', {id: id})
      this.$router.push('/review_')
    },
    review () {
      axios.get(`http://localhost:8000/jewelry/item/${this.$store.state.item.itemId}/itemInfo`)
        .then(res => {
          this.response_list = res.data.data.item_response.review_board_response_list
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.review()
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem 0;
}
.box {
  display: flex;
  margin: 2rem;
  border-bottom: 1px solid black;;
}
.img, .content {
  height: 100px;
}
.innerBox {
  width : 24%;
}
.img {
  width: 25%;
}
.img div,
.img img {
  width: 90px;
  height: 90px;
  background-size: cover;
  margin-left: 20px;
}
.content {
  width: 51%;
  word-break: break-all;
  padding-top: 10px;
  overflow: hidden;
  cursor: pointer;
}
.content:hover {
  font-size: 1.02rem;
}
.innerBox div {
  height: 20%;
  text-align: center;
  font-size: 0.7rem;
}
.writer {
  margin-top: 30px;
}

.button {
  display: flex;
  justify-content: right;
  margin: 2rem 2rem 0 0;
}
.button a {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
</style>
