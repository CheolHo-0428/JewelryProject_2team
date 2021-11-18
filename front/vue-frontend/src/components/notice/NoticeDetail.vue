<template>
  <div id="reg">
    <p class="top">공지사항</p>

    <div class="AddWrap">
      <form>
        <table class="tbAdd">
          <colgroup>
            <col width="15%" />
            <col width="*" />
          </colgroup>
          <tr>
            <th>제목</th>
            <td>
              <input
                :disabled="findRole === false"
                type="text"
                v-model="title"
              />
            </td>
          </tr>
          <tr v-if="stored_file_name">
            <th>이미지</th>
            <td><img :src="stored_file_name" /></td>
            <td v-show="findRole === true" style="width: 120px">
              <a @click="imgfun">이미지 삭제</a>
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td colspan="2">
              <textarea
                :disabled="findRole === false"
                v-model="content"
              ></textarea>
            </td>
          </tr>
          <tr v-if="!stored_file_name">
            <th>이미지 업로드</th>
            <td>
              <v-file-input v-show="findRole === true" id="file" name="files" label="File input" style="width: 200px;"></v-file-input>
            </td>
          </tr>
        </table>
      </form>
    </div>

    <div class="input">
      <p>댓글</p>

      <table>
        <colgroup>
          <col width="85%" />
          <col width="15%" />
        </colgroup>

        <tbody>
          <tr>
            <td>
              <textarea v-model="inputReply" class="inputReply"></textarea>
            </td>
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
      <router-link to="/notice" class="btn">목록</router-link>
      <a v-if="findRole === true" v-show="writer === $store.state.auth.user.account" @click="mod" class="btn">수정</a>
      <a v-if="findRole === true" v-show="writer === $store.state.auth.user.account" @click="remove" class="btn">삭제</a>
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
      id: 0,
      stored_file_name: '',
      response_list: [],
      modify: [],
      inputReply: '',
      deleteImg: false,
      replyContent: [],
      password: '',
      writer: ''
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    findRole () {
      if (this.currentUser) {
        return this.currentUser.roles.includes('ROLE_ADMIN')
      }
      return false
    }
  },
  methods: {
    imgfun () {
      this.deleteImg = true
      this.stored_file_name = false
    },
    mod () {
      console.log(this.replyContent)
      if (!this.title) {
        this.$swal.fire({
          icon: 'info',
          title: '제목을 적어주세요.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        console.log(this.deleteImg)
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
            axios
              .put('http://localhost:8000/jewelry/noticeBoard/updateImg', frm, {
                headers: {
                  'Authorization': 'Bearer ' + this.$store.state.auth.user.token,
                  'Content-Type': 'multipart/form-data'
                }
              })
              .then((response) => {
                console.log(response)
              })
              .catch((error) => {
                console.log(error)
              })
          } else {
            axios
              .put('http://localhost:8000/jewelry/noticeBoard/update', {
                title: this.title,
                content: this.content,
                id: this.id,
                writer: this.$store.state.auth.user.account,
                delete_check: 'YES'
              }, {
                headers: {
                  'Authorization': 'Bearer ' + this.$store.state.auth.user.token
                }
              })
              .then((res) => {
                console.log(res)
              })
              .catch((error) => {
                console.log(error)
              })
          }
        } else {
          console.log('3')
          if (
            document.getElementById('file') &&
            document.getElementById('file').files[0]
          ) {
            let frm = new FormData()
            let photoFile = document.getElementById('file')
            frm.append('title', this.title)
            frm.append('content', this.content)
            frm.append('id', this.id)
            frm.append('writer', this.$store.state.auth.user.account)
            frm.append('delete_check', 'NO')
            frm.append('file', photoFile.files[0])
            axios
              .put('http://localhost:8000/jewelry/noticeBoard/updateImg', frm, {
                headers: {
                  'Authorization': 'Bearer ' + this.$store.state.auth.user.token,
                  'Content-Type': 'multipart/form-data'
                }
              })
              .then((response) => {
                console.log(response)
              })
              .catch((error) => {
                console.log(error)
              })
          } else {
            console.log('4')
            axios
              .put('http://localhost:8000/jewelry/noticeBoard/update', {
                title: this.title,
                content: this.content,
                id: this.id,
                writer: this.$store.state.auth.user.account
              }, {
                headers: {
                  'Authorization': 'Bearer ' + this.$store.state.auth.user.token
                }
              })
              .then((res) => {
                console.log(res)
              })
              .catch((error) => {
                console.log(error)
              })
          }
        }
        this.$swal
          .fire({
            icon: 'success',
            title: '공지사항이 수정되었습니다.',
            text: '목록으로 이동합니다.',
            confirmButtonColor: '#CEF6CE'
          })
          .then(() => {
            this.$router.push('/notice')
          })
      }
    },
    remove () {
      this.$swal
        .fire({
          icon: 'warning',
          title: '해당글이 삭제됩니다.',
          text: '목록으로 이동하시겠습니까?',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          confirmButtonColor: '#FE9A2E',
          cancelButtonColor: '#BDBDBD',
          cancelButtonText: 'No'
        })
        .then(async (result) => {
          if (result.isConfirmed) {
            await axios
              .delete(`http://localhost:8000/jewelry/noticeBoard/${this.id}`, {
                data: {
                  id: this.id
                },
                headers: {
                  'Authorization': 'Bearer ' + this.$store.state.auth.user.token
                }
              })
              .then(function (response) {
                console.log(response)
              })
              .catch(function (error) {
                console.log(error)
              })
            await this.$store.commit('noticeDetail', {id: 0, urlPage: 'http://localhost:8000/jewelry/noticeBoard/paging'})
            await this.$router.push('/notice')
          }
        })
    },
    saveReply (i) {
      axios
        .put('http://localhost:8000/jewelry/noticeBoardReply/update', {
          content: this.response_list[i].content,
          id: this.response_list[i].id,
          writer: this.$store.state.auth.user.account
        })
        .then((res) => {
          console.log(res)
        })
        .catch((error) => {
          console.log(error)
        })
      this.$swal
        .fire({
          icon: 'success',
          title: '댓글이 수정되었습니다.',
          confirmButtonColor: '#CEF6CE'
        })
        .then(() => {
          this.modify = []
          this.replyContent = []
          this.notice()
        })
    },
    changeModify (i) {
      this.$set(this.modify, i, !this.modify[i])
    },
    async notice () {
      await axios
        .get(this.$store.state.notice.allNotices)
        .then((res) => {
          let notices = res.data.data
          let index = notices.findIndex(
            (i) => i.id === this.$store.state.notice.noticeId
          )
          this.title = res.data.data[index].title
          this.content = res.data.data[index].content
          this.id = res.data.data[index].id
          this.stored_file_name = res.data.data[index].stored_file_name
          this.writer = res.data.data[index].writer
        })
        .catch((err) => {
          console.log(err)
        })
      await axios
        .get(`http://localhost:8000/jewelry/noticeBoard/${this.id}/replyInfo`)
        .then((res) => {
          this.response_list = res.data.data.notice_board_response.notice_board_reply_response_list
          for (let i = 0; i < this.response_list.length; i++) {
            this.modify.push(true)
            this.replyContent.push(this.response_list[i].content)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    regReply () {
      if (this.inputReply === '') {
        this.$swal.fire({
          icon: 'warning',
          title: '댓글내용을 입력하고 등록해주세요',
          confirmButtonColor: '#FE9A2E'
        })
      } else {
        axios({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          url: 'http://localhost:8000/jewelry/noticeBoardReply/reg',
          data: JSON.stringify({
            content: this.inputReply,
            writer: this.$store.state.auth.user.account,
            notice_board_id: this.id
          })
        })
          .then((res) => {
            console.log(res)
            this.$swal
              .fire({
                icon: 'success',
                title: '댓글이 등록되었습니다.',
                confirmButtonColor: '#CEF6CE'
              })
              .then(() => {
                this.modify = []
                this.replyContent = []
                this.notice()
                this.inputReply = ''
              })
              .catch((error) => {
                console.log(error)
              })
          })
          .catch((error) => {
            console.log(error)
          })
      }
    },
    async removeReply (id) {
      await this.$swal
        .fire({
          icon: 'warning',
          title: '해당댓글이 삭제됩니다.',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          confirmButtonColor: '#FE9A2E',
          cancelButtonColor: '#BDBDBD',
          cancelButtonText: 'No'
        })
        .then((result) => {
          if (result.isConfirmed) {
            axios
              .delete(`http://localhost:8000/jewelry/noticeBoardReply/${id}`, {
                data: {
                  id: id
                }
              })
              .then(async (response) => {
                console.log(response)
                this.modify = []
                this.replyContent = []
                await this.notice()
              })
              .catch(function (error) {
                console.log(error)
              })
          } else {
            this.modify = []
            this.replyContent = []
            this.notice()
          }
        })
    }
  },
  created () {
    this.notice()
  }
}
</script>

<style scoped>
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin: 3rem 0;
}
#reg {
  width: 800px;
  margin: 0 auto;
  text-align: center;
  color: #2c3e50;
}
img {
  width: 14rem;
}
.inputReply {
  min-height: 90px;
  border: 1px solid black;
  border-radius: 10px;
}
.input {
  margin-bottom: 2rem;
}
.input p {
  margin-top: 2.5rem;
  text-align: left;
  cursor: pointer;
  font-weight: 700;
}
.reply {
  margin-bottom: 5rem;
}
.reply input {
  width: 100%;
  height: 100%;
}
textarea {
  width: 100%;
}
.mod {
  outline: none;
}
.table td {
  padding: 0.5rem;
  vertical-align: middle;
}
a {
  text-decoration: none;
  background: #fff;
  color: #000;
  padding: 0.2rem 0.4rem;
  font-size: 0.8rem;
  font-weight: 700;
  margin-left: 0.3rem;
  cursor: pointer;
}
.tbAdd {
  border-top: 1px solid #888;
}
.tbAdd th,
.tbAdd td {
  border-bottom: 2px solid #eee;
  padding: 5px 0;
}
.tbAdd td {
  padding: 10px 10px;
  box-sizing: border-box;
}
.tbAdd td input {
  width: 100%;
  min-height: 30px;
  box-sizing: border-box;
  padding: 0 10px;
}
.tbAdd td textarea {
  width: 100%;
  min-height: 300px;
  padding: 10px;
  box-sizing: border-box;
}
.btnWrap {
  text-align: center;
  padding-bottom: 50px;
}
.btnWrap a {
  margin: 0 10px;
}
table {
  width: 100%;
  border-collapse: collapse;
}
a {
  text-decoration: none;
}
.btn{padding:10px; background:#000; color:#fff; border-radius: 0;}

.btn2 {
  color: #000;
  font-weight: 700;
}

.profile {
  border-radius: 50%;
  width: 3rem;
  height: 3rem;
}
ul {
  list-style: none;
  padding-left: 0 !important;
}
li {
  width: 48rem;
}
.user {
  display: flex;
}
.user-info {
  height: fit-content;
  margin: auto 1rem;
}
.reply_list {
  margin-bottom: 3rem;
  cursor: pointer;
}
.reply_cont {
  margin-top: 1rem;
}
.date {
  margin-left: 40%;
  font-size: 0.8rem;
  color: rgb(99, 99, 99);
}
.nDate {
  margin-left: 57%;
}
.name {
  font-weight: 700;
  width: 160px;
  display: inline-block;
  text-align: left;
}
</style>
