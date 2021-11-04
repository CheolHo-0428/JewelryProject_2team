<template>
  <div class="outer">
    <div class="button">
      <v-btn v-if='findRole == true' color="#F4F2E7" to="/regreview">리뷰작성</v-btn>
    </div>
    <ul>
      <li class="review_list" v-for="(list, i) in response_list" :key="i" @click="detail(list.id)">
        <div class="user">
          <div class="user-image">
            <img src="https://ifh.cc/g/50ygP2.png" class="profile">
          </div>
          <div class="user-info">
            <div style="padding-left:5px;">
              <span class="name">{{list.writer}}</span>
              <span class="date">{{list.created_at.split('T')[0]}}</span>
            </div>
          </div>
        </div>
        <div class="img" v-if="list.stored_file_name">
          <div><img :src="list.stored_file_name"></div>
        </div>
        <div class="title">
          <p>{{list.title}}</p>
        </div>
      </li>
    </ul>
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

.profile {
  border-radius: 50%;
  width: 3rem;
  height: 3rem;
}
ul {
  list-style: none;
}
.user {
  display: flex;
}
.user-info {
  height: fit-content;
  margin: auto 1rem;
}
.review_list {
  margin-bottom: 3rem;
  cursor: pointer;
}
.title {
  margin-top: 1rem;
}

.box {
  display: flex;
  margin: 0.6rem;
  border: 1.5px solid black;
  border-radius: 2px;
}
.img, .content {
  height: fit-content;
  margin: auto 0;
}
.innerBox {
  width : 24%;
  height: fit-content;
  margin: auto 0;
}
.img {
  width: 16%;
}
.img img {
  width: 150px;
  height: 150px;
  background-size: cover;
  margin: 1rem;
}
.img div {
  width: 90px;
  background-size: cover;
  margin-left: 10px;
}
.content {
  width: 51%;
  word-break: break-all;
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
.writer,
.date {
  margin: 10px 0;
}
.date {
  margin-left: 1.2rem;
  font-size: 0.8rem;
  color: rgb(99, 99, 99);
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
