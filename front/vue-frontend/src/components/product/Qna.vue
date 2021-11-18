<template>
  <div class="outer">
    <div class="button">
      <router-link to="/regqna" v-if='findRole == true' class="btn btn-dark">Q&A작성</router-link>
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
  margin: 2rem 2rem 0 0;
}
.btn {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 700;
  background: transparent;
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
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -ms-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}
.btn-dark {
  background: #555;
  color: #fff;
  padding: 0.4rem 1.2rem;
  font-size: 1rem;
}
.btn-dark:hover, .btn-dark:focus {
  background: #000;
  color: #fff;
}
.name {
  font-weight: 700;
}
</style>
