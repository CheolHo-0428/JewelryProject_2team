<template>
  <div class="outer">
    <div class="button">
      <router-link to="/regreview" v-if='findRole == true' class="btn btn-dark">리뷰작성</router-link>
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
  margin: 3rem 0
}

.profile {
  border-radius: 50%;
  width: 3rem;
  height: 3rem
}

ul {
  list-style: none
}

.user {
  display: flex
}

.user-info {
  height: fit-content;
  margin: auto 1rem
}

.review_list {
  margin-bottom: 3rem;
  cursor: pointer
}

.title {
  margin-top: 1rem
}

.img {
  height: fit-content;
  margin: auto 0
}

.img {
  width: 16%
}

.img img {
  width: 150px;
  height: 150px;
  background-size: cover;
  margin: 1rem
}

.img div {
  width: 90px;
  background-size: cover;
  margin-left: 10px
}

.date,
.writer {
  margin: 10px 0
}

.date {
  margin-left: 1.2rem;
  font-size: .8rem;
  color: #636363
}

.button {
  display: flex;
  justify-content: right;
  margin: 2rem 2rem 0 0
}

.btn {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 700;
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
  padding: .4rem 1.2rem;
  font-size: 1rem
}

.btn-dark:focus,
.btn-dark:hover {
  background: #000;
  color: #fff
}

.name {
  font-weight: 700
}
</style>
