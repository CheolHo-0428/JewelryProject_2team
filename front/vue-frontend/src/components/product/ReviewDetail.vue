<template>
<div id="reg">
  <p>리뷰</p>

  <div class="AddWrap">
    <form>
      <table class="tbAdd">
        <colgroup>
          <col width="15%" />
          <col width="*" />
        </colgroup>
        <tr>
          <th>제목</th>
          <td><input type="text" v-model="title" ref="subject" :readonly="!account" /></td>
        </tr>
        <tr v-if="stored_file_name">
          <th>이미지</th>
          <td><img :src="stored_file_name"></td>
          <td style="width:120px;"><a @click="imgfun" v-if="account">이미지 삭제</a></td>
        </tr>
        <tr>
          <th>내용</th>
          <td><textarea v-model="content" :readonly="!account"></textarea></td>
        </tr>
        <tr v-if="(!stored_file_name) && account">
          <th>이미지 업로드</th>
          <td><v-file-input id="file" name="files" label="File input" style="width: 200px;"></v-file-input></td>
        </tr>
      </table>
    </form>
  </div>

  <div class="input">
    <p>댓글</p>

    <table>
      <colgroup>
        <col width="85%">
        <col width="15%">
      </colgroup>

      <tbody>
        <tr>
          <td><textarea v-model="inputReply" class="inputReply"></textarea></td>
          <td><button class="btn2" @click="regReply">등록</button></td>
        </tr>
      </tbody>
    </table>
  </div>

  <div class="reply">
    <ul>
      <li class="reply_list" v-for="(reply, i) in response_list" :key="i">
        <div class="user">
          <div class="user-image">
            <img src="https://ifh.cc/g/50ygP2.png" class="profile">
          </div>
          <div class="user-info">
            <div style="padding-left:5px;width:700px;">
              <span class="name">{{reply.writer}}</span>
              <span class="date" :class="{nDate : reply.writer !== $store.state.auth.user.account}">{{reply.updated_at.split('T')[0]}} {{reply.updated_at.split('T')[1].split('.')[0]}}</span> &nbsp;&nbsp;&nbsp;
              <a @click="changeModify(i)" v-if="modify[i] && (reply.writer === $store.state.auth.user.account)">수정</a>
              <a @click="saveReply(i)" v-if="!modify[i]">적용</a>
              <a @click="removeReply(reply.id)" v-if="modify[i] && (reply.writer === $store.state.auth.user.account)">삭제</a>
              <a @click="changeModify(i)" v-if="!modify[i]">취소</a>
            </div>
          </div>
        </div>
        <div class="reply_cont">
          <v-textarea v-model="replyContent[i]" @input="reply.content = replyContent[i]" :readonly="modify[i]" :class="{'mod' : modify[i]}" auto-grow rows="1" row-height="18"></v-textarea>
        </div>
      </li>
    </ul>
  </div>

  <div class="btnWrap">
    <router-link to="/detail" class="btn" v-if="!account">목록</router-link>
    <a @click="inputPwd" class="btn" v-if="!account">리뷰관리</a>
    <a @click="mod" class="btn" v-if="account">수정</a>
    <a @click="remove" class="btn" v-if="account">삭제</a>
    <a @click="review" class="btn" v-if="account">취소</a>
  </div>
</div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      title: '',
      content: '',
      stored_file_name: '',
      id: 0,
      deleteImg: false,
      response_list: [],
      modify: [],
      replyContent: [],
      inputReply: '',
      password: '',
      account: false
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  methods: {
    imgfun () {
      this.deleteImg = true
      this.stored_file_name = false
    },
    async inputPwd () {
      const { value: pwd } = await this.$swal.fire({
        title: 'Enter your password',
        input: 'password',
        inputLabel: 'Password',
        inputPlaceholder: 'Enter your password',
        inputAttributes: {
          maxlength: 10,
          autocapitalize: 'off',
          autocorrect: 'off'
        }
      })
      if (pwd === this.password) {
        this.account = true
      } else {
        this.$swal.fire({
          icon: 'error',
          title: '비밀번호가 틀렸습니다.',
          confirmButtonColor: '#F27474'
        })
      }
    },
    mod () {
      console.log(this.title)
      if (this.title === '') {
        this.$swal.fire({
          icon: 'info',
          title: '제목을 적어주세요.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        if (this.deleteImg) {
          let frm = new FormData()
          let photoFile = document.getElementById('file')
          if (photoFile.files[0]) {
            frm.append('title', this.title)
            frm.append('content', this.content)
            frm.append('id', this.id)
            frm.append('writer', this.$store.state.auth.user.account)
            frm.append('file', photoFile.files[0])
            frm.append('delete_check', 'YES')
            frm.append('password', this.password)
            frm.append('item_id', this.$store.state.item.itemId)

            axios.put('http://localhost:8000/jewelry/reviewBoard/updateImg', frm, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }).then((response) => {
              console.log(response)
            }).catch((error) => {
              console.log(error)
            })
          } else {
            axios.put(
              'http://localhost:8000/jewelry/reviewBoard/update',
              {
                title: this.title,
                content: this.content,
                id: this.id,
                writer: this.$store.state.auth.user.account,
                delete_check: 'YES',
                password: this.password,
                item_id: this.$store.state.item.itemId
              }
            ).then(res => {
              console.log(res)
            }).catch(error => {
              console.log(error)
            })
          }
        } else {
          if (document.getElementById('file') && document.getElementById('file').files[0]) {
            let frm = new FormData()
            let photoFile = document.getElementById('file')

            frm.append('title', this.title)
            frm.append('content', this.content)
            frm.append('id', this.id)
            frm.append('writer', this.$store.state.auth.user.account)
            frm.append('delete_check', 'NO')
            frm.append('file', photoFile.files[0])
            frm.append('password', this.password)
            frm.append('item_id', this.$store.state.item.itemId)

            axios.put('http://localhost:8000/jewelry/reviewBoard/updateImg', frm, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }).then((response) => {
              console.log(response)
            }).catch((error) => {
              console.log(error)
            })
          } else {
            axios.put(
              'http://localhost:8000/jewelry/reviewBoard/update',
              {
                title: this.title,
                content: this.content,
                id: this.id,
                writer: this.$store.state.auth.user.account,
                password: this.password,
                item_id: this.$store.state.item.itemId
              }
            ).then(res => {
              console.log(res)
            }).catch(error => {
              console.log(error)
            })
          }
        }
        this.$swal.fire({
          icon: 'success',
          title: '리뷰가 수정되었습니다.',
          text: '목록으로 이동합니다.',
          confirmButtonColor: '#CEF6CE'
        }).then(() => {
          this.$router.push('/detail')
        })
      }
    },
    remove () {
      this.$swal.fire({
        icon: 'warning',
        title: '해당 리뷰가 삭제됩니다.',
        text: '목록으로 이동하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(
            `http://localhost:8000/jewelry/reviewBoard/${this.$store.state.item.reviewId}`,
            {
              data: {
                id: this.id
              }
            }
          ).then(function (response) {
            console.log(response)
          }).catch(function (error) {
            console.log(error)
          })
          this.$router.push('/detail')
        }
      }).catch(err => {
        console.log(err)
      })
    },
    saveReply (i) {
      axios.put(
        'http://localhost:8000/jewelry/reviewBoardReply/update',
        {
          content: this.response_list[i].content,
          id: this.response_list[i].id,
          writer: this.$store.state.auth.user.account,
          review_board_id: this.$store.state.item.reviewId
        }
      ).then(res => {
        console.log(res)
      }).catch(error => {
        console.log(error)
      })

      this.$swal.fire({
        icon: 'success',
        title: '댓글이 수정되었습니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        this.modify = []
        this.replyContent = []
        this.review()
      })
    },
    changeModify (i) {
      this.$set(this.modify, i, !this.modify[i])
    },
    regReply () {
      axios({
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        url: 'http://localhost:8000/jewelry/reviewBoardReply/reg',
        data: JSON.stringify({
          content: this.inputReply,
          writer: this.$store.state.auth.user.account,
          review_board_id: this.id
        })
      }).then(res => {
        console.log(res)
        this.$swal.fire({
          icon: 'success',
          title: '댓글이 등록되었습니다.',
          confirmButtonColor: '#CEF6CE'
        }).then(() => {
          this.modify = []
          this.replyContent = []
          this.review()
          this.inputReply = ''
        }).catch(error => {
          console.log(error)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    async removeReply (id) {
      await this.$swal.fire({
        icon: 'warning',
        title: '해당댓글이 삭제됩니다.',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(
            `http://localhost:8000/jewelry/reviewBoardReply/${id}`,
            {
              data: {
                id: id
              }
            }
          ).then(function (response) {
            console.log(response)
          }).catch(function (error) {
            console.log(error)
          })
        } else {
          this.modify = []
          this.replyContent = []
        }
      })
      await this.review()
    },
    async review () {
      await axios.get(`http://localhost:8000/jewelry/reviewBoard/${this.$store.state.item.reviewId}`)
        .then(res => {
          let review = res.data.data

          this.title = review.title
          this.content = review.content
          this.stored_file_name = review.stored_file_name
          this.id = review.id
          this.password = review.password

          this.account = false
        })
        .catch(err => {
          console.log(err)
        })
      await axios.get(`http://localhost:8000/jewelry/reviewBoard/${this.$store.state.item.reviewId}/replyInfo`)
        .then(res => {
          this.response_list = res.data.data.review_board_response.review_board_reply_response_list
          this.modify = []
          this.replyContent = []
          for (let i = 0; i < this.response_list.length; i++) {
            this.modify.push(true)
            this.replyContent.push(this.response_list[i].content)
          }
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
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin: 3rem 0
}

#reg {
  width: 800px;
  margin: 0 auto;
  text-align: center
}

.img {
  width: 200px;
  height: 200px;
  margin: 1rem 0;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg)
}

input {
  outline: 0
}

.inputReply {
  min-height: 90px;
  border: 1px solid #000;
  border-radius: 10px
}

.input {
  margin-bottom: 2rem
}

.input p {
  margin-top: 2.5rem;
  text-align: left;
  cursor: pointer;
  font-weight: 700
}

.reply {
  margin-bottom: 5rem
}

.reply input {
  width: 100%;
  height: 100%
}

textarea {
  width: 100%;
  outline: 0
}

.mod {
  outline: 0
}

.table td {
  padding: .5rem;
  vertical-align: middle
}

a {
  text-decoration: none;
  background: #fff;
  color: #000;
  padding: .2rem .4rem;
  font-size: .8rem;
  font-weight: 700;
  margin-left: .3rem;
  cursor: pointer
}

.tbAdd {
  border-top: 1px solid #888
}

.tbAdd td,
.tbAdd th {
  border-bottom: 1px solid #eee;
  padding: 5px 0
}

.tbAdd td {
  padding: 10px 10px;
  box-sizing: border-box
}

.tbAdd td input {
  width: 100%;
  min-height: 30px;
  box-sizing: border-box;
  padding: 0 10px
}

.tbAdd td textarea {
  width: 100%;
  min-height: 200px;
  padding: 10px;
  box-sizing: border-box
}

.btnWrap {
  text-align: center;
  margin: 30px 0;
  padding-bottom: 30px
}

.btnWrap a {
  margin: 0 10px
}

table {
  width: 100%;
  border-collapse: collapse
}

a {
  text-decoration: none
}

.btn {
  padding: 10px;
  background: #555;
  color: #fff;
  border-radius: 0
}

.btn:hover {
  background-color: #000;
  color: #fff
}

.btn2 {
  color: #000;
  font-weight: 700
}

.profile {
  border-radius: 50%;
  width: 3rem;
  height: 3rem
}

ul {
  list-style: none;
  padding-left: 0 !important
}

li {
  width: 48rem
}

.user {
  display: flex
}

.user-info {
  height: fit-content;
  margin: auto 1rem
}

.reply_list {
  margin-bottom: 3rem;
  cursor: pointer
}

.reply_cont {
  margin-top: 1rem
}

.date {
  margin-left: 40%;
  font-size: .8rem;
  color: #636363
}

.nDate {
  margin-left: 57%
}

.name {
  font-weight: 700;
  width: 160px;
  display: inline-block;
  text-align: left
}
</style>
