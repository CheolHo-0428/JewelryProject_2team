<template>
  <div class="outer">
    <div class="button">
      <v-btn  v-if='findRole == true' color="#F4F2E7" to="/regqna">Q&A등록</v-btn>
    </div>
    <div class="box" v-for="(list, i) in response_list" :key="i">
      <div class="num">{{response_list.length - i}}</div>
      <div class="content" @click="detail(list.id)">
        {{list.title}}
      </div>
      <div class="writer">작성자 : {{list.writer}}</div>
      <div class="date">{{list.created_at.split('T')[0]}}</div>
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
      this.$store.commit('qnaDetail', {id: id})
      this.$router.push('/qna_')
    },
    qna () {
      axios.get(`http://localhost:8000/jewelry/item/${this.$store.state.item.itemId}/itemInfo`)
        .then(res => {
          this.response_list = res.data.data.item_response.qna_board_response_list
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.qna()
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    findRole () {
      if (this.currentUser) {
        return this.currentUser.roles.includes('ROLE_ADMIN') || this.currentUser.roles.includes('ROLE_USER')
      }
      return false
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 3rem 0;
}
.box {
  display: flex;
  margin: 2rem;
  border-bottom: 1px solid black;;
  width: 50rem;
}
.num, .content {
  height: 40px;
}
.writer,
.date {
  width : 12%;
}
.num {
  width: 10%;
  text-align: center;
}
.content {
  width: 66%;
  word-break: break-all;
  padding-top: 10px;
  overflow: hidden;
  text-align: center;
  cursor: pointer;
}
.content:hover {
  font-size: 1.02rem;
}
.writer,
.date {
  text-align: center;
  font-size: 0.7rem;
  padding-top: 10px;
}

.button {
  display: flex;
  justify-content: right;
  margin: 2rem 3.8rem 0 0;
}
.button a {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
</style>
