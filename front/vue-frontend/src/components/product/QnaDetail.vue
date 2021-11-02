<template>
<div id="reg">
  <p>Q&A</p>

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
          <td><input type="file" id="file" name="files" /></td>
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
    <a @click="List" class="btn" v-if="!account">목록</a>
    <a @click="inputPwd" class="btn" v-if="!account">Q&A관리</a>
    <a @click="mod" class="btn" v-if="account">수정</a>
    <a @click="remove" class="btn" v-if="account">삭제</a>
    <a @click="account = false" class="btn" v-if="account">취소</a>
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
          this.$router.push('/detail')
        }
      })
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
      if (!this.title) {
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
            frm.append('writer', 'testUser')
            frm.append('file', photoFile.files[0])
            frm.append('delete_check', 'YES')
            frm.append('password', this.password)
            frm.append('item_id', this.$store.state.item.itemId)

            axios.put('http://localhost:8000/jewelry/qnaBoard/updateImg', frm, {
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
              'http://localhost:8000/jewelry/qnaBoard/update',
              {
                title: this.title,
                content: this.content,
                id: this.id,
                writer: 'testUser',
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
            frm.append('writer', 'testUser')
            frm.append('delete_check', 'NO')
            frm.append('password', this.password)
            frm.append('file', photoFile.files[0])
            frm.append('item_id', this.$store.state.item.itemId)

            axios.put('http://localhost:8000/jewelry/qnaBoard/updateImg', frm, {
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
              'http://localhost:8000/jewelry/qnaBoard/update',
              {
                title: this.title,
                content: this.content,
                id: this.id,
                writer: 'testUser',
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
          title: 'Q&A가 수정되었습니다.',
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
        title: '해당 Q&A가 삭제됩니다.',
        text: '목록으로 이동하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(
            `http://localhost:8000/jewelry/qnaBoard/${this.$store.state.item.qnaId}`,
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
        'http://localhost:8000/jewelry/qnaBoardReply/update',
        {
          content: this.response_list[i].content,
          id: this.response_list[i].id,
          writer: 'testUser',
          qna_board_id: this.$store.state.item.qnaId
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
        this.qna()
      })
    },
    changeModify (i) {
      this.$set(this.modify, i, !this.modify[i])
    },
    regReply () {
      axios({
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        url: 'http://localhost:8000/jewelry/qnaBoardReply/reg',
        data: JSON.stringify({
          content: this.inputReply,
          writer: 'testUser',
          qna_board_id: this.id
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
          this.qna()
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
            `http://localhost:8000/jewelry/qnaBoardReply/${id}`,
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
        } else {
          this.modify = []
          this.replyContent = []
        }
      })
      await this.qna()
    },
    async qna () {
      await axios.get(`http://localhost:8000/jewelry/qnaBoard/${this.$store.state.item.qnaId}`)
        .then(res => {
          let qna = res.data.data

          this.title = qna.title
          this.content = qna.content
          this.stored_file_name = qna.stored_file_name
          this.id = qna.id
          this.password = qna.password
        })
        .catch(err => {
          console.log(err)
        })
      await axios.get(`http://localhost:8000/jewelry/qnaBoard/${this.$store.state.item.qnaId}/replyInfo`)
        .then(res => {
          this.response_list = res.data.data.qna_board_response.qna_board_reply_response_list
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
    this.qna()
  }
}
</script>

<style scoped>
p {
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
input {
  outline: none;
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
  outline: none;
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
.tbAdd td textarea{width:100%; min-height:150px; padding:10px; box-sizing:border-box;}
.btnWrap{text-align:center; margin:30px 0;}
.btnWrap a{margin:0 10px;}
table{width:100%; border-collapse:collapse;}
a{text-decoration:none;}
.btn{padding:10px; background:#34445c; color:#fff;}
</style>
