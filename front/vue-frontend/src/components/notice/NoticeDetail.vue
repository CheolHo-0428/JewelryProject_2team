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
          <td><input type="text" v-model="title"/></td>
        </tr>
        <tr v-if="stored_file_name">
          <th>이미지</th>
          <td><img :src="stored_file_name"></td>
          <td style="width:120px;"><a @click="imgfun">이미지 삭제</a></td>
        </tr>
        <tr>
          <th>내용</th>
          <td><textarea v-model="content"></textarea></td>
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
          <td><v-btn color="#F4F2E7" x-large class="v_btn" @click="regReply" >등록</v-btn></td>
        </tr>
      </tbody>
    </table>
  </div>

  <div class="reply">
    <table class="table">
      <colgroup>
        <col width="53%">
        <col width="12%">
        <col width="20%">
        <col width="15%">
      </colgroup>

      <tbody>
        <tr v-for="(reply, i) in response_list" :key="i">
          <td class="cont"><v-textarea v-model="replyContent[i]" @input="reply.content = replyContent[i]" :readonly="modify[i]" :class="{'mod' : modify[i]}" auto-grow rows="1" row-height="18"></v-textarea></td>
          <td class="s">{{reply.writer}}</td>
          <td class="s">{{reply.updated_at.split('T')[0]}} {{reply.updated_at.split('T')[1].split('.')[0]}}</td>
          <td><a @click="changeModify(i)" v-if="modify[i]">수정</a><a @click="saveReply(i)" v-if="!modify[i]">적용</a>
              <a @click="removeReply(reply.id)" v-if="modify[i]">삭제</a><a @click="changeModify(i)" v-if="!modify[i]">취소</a>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

  <div class="btnWrap">
    <a @click="List" class="btn">목록</a>
    <a @click="mod" class="btn">수정</a>
    <a @click="remove" class="btn">삭제</a>
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
      replyContent: []
    }
  },
  methods: {
    imgfun () {
      this.deleteImg = true
      this.stored_file_name = false
    },
    List () {
      this.$swal.fire({
        icon: 'warning',
        title: '원본으로 유지됩니다.',
        text: '목록으로 이동하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          location.href = '/notice'
        }
      })
    },
    mod () {
      if (!this.title) {
        this.$swal.fire({
          icon: 'info',
          title: '제목을 적어주세요.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        if (this.deleteImg) {
          axios.put(
            'http://localhost:8000/jewelry/noticeBoard/update',
            {
              title: this.title,
              content: this.content,
              id: this.id,
              writer: 'testUser',
              delete_check: 'YES'
            }
          ).then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
          })
        } else {
          axios.put(
            'http://localhost:8000/jewelry/noticeBoard/update',
            {
              title: this.title,
              content: this.content,
              id: this.id,
              writer: 'testUser'
            }
          ).then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
          })
        }
        this.$swal.fire({
          icon: 'success',
          title: '공지사항이 수정되었습니다.',
          text: '목록으로 이동합니다.',
          confirmButtonColor: '#CEF6CE'
        }).then(() => {
          location.href = '/notice'
        })
      }
    },
    remove () {
      this.$swal.fire({
        icon: 'warning',
        title: '해당글이 삭제됩니다.',
        text: '목록으로 이동하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(
            `http://localhost:8000/jewelry/noticeBoard/${this.id}`,
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
          this.$store.commit('noticeDetail', {id: 0, url: 'http://localhost:8000/jewelry/noticeBoard/paging'})
          location.href = '/notice'
        }
      })
    },
    saveReply (i) {
      axios.put(
        'http://localhost:8000/jewelry/noticeBoardReply/update',
        {
          content: this.response_list[i].content,
          id: this.response_list[i].id,
          writer: 'testUser'
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
        this.notice()
      })
    },
    changeModify (i) {
      this.$set(this.modify, i, !this.modify[i])
    },
    async notice () {
      await axios.get(this.$store.state.notice.allNotices)
        .then(res => {
          let notices = res.data.data
          let index = notices.findIndex(i => i.id === this.$store.state.notice.noticeId)

          this.title = res.data.data[index].title
          this.content = res.data.data[index].content
          this.id = res.data.data[index].id
          this.stored_file_name = res.data.data[index].stored_file_name
        })
        .catch(err => {
          console.log(err)
        })
      await axios.get(`http://localhost:8000/jewelry/noticeBoard/${this.id}/replyInfo`)
        .then(res => {
          this.response_list = res.data.data.notice_board_response.notice_board_reply_response_list
          for (let i = 0; i < this.response_list.length; i++) {
            this.modify.push(true)
            this.replyContent.push(this.response_list[i].content)
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    regReply () {
      axios({
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        url: 'http://localhost:8000/jewelry/noticeBoardReply/reg',
        data: JSON.stringify({
          content: this.inputReply,
          writer: 'testUser',
          notice_board_id: this.id
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
          this.notice()
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
            `http://localhost:8000/jewelry/noticeBoardReply/${id}`,
            {
              data: {
                id: id
              }
            }
          ).then(function (response) {
            console.log(response)
            this.modify = []
            this.replyContent = []
          }).catch(function (error) {
            console.log(error)
          })
        }
      })
      await this.notice()
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
  min-height: 120px;
  border: 1px solid black;
  border-radius: 10px;
}
.input {
  margin-bottom: 2rem;
}
.v_btn {
  height: 70px !important;
  font-weight: 700;
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
.cont {
  padding-left: 2rem !important;
}

.table td {
  padding: 0.5rem;
  vertical-align: middle;
}
.s {
  font-size: small;
}
a {
  text-decoration: none;
  color: black;
  border: 1px solid black;
  border-radius: 10px;
  padding: 0.2rem 0.4rem;
  font-size: 0.8rem;
  font-weight: 700;
  background-color: #fefff2;
  box-shadow: 1px 0.5px 0 rgb(0,0,0,0.5);
  margin-left: 0.3rem;
  cursor: pointer;
}
a:active {
  box-shadow: 1px 0px 0 rgb(0,0,0,0.5);
  position: relative;
  top: 0.5px;
}

.tbAdd{border-top:1px solid #888;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0;}
.tbAdd td{padding:10px 10px; box-sizing:border-box;}
.tbAdd td input{width:100%; min-height:30px; box-sizing:border-box; padding:0 10px;}
.tbAdd td textarea{width:100%; min-height:250px; padding:10px; box-sizing:border-box;}
.btnWrap{text-align:center; margin:30px 0;}
.btnWrap a{margin:0 10px;}
table{width:100%; border-collapse:collapse;}
a{text-decoration:none;}
.btn{padding:10px; background:#34445c; color:#fff;}
</style>
