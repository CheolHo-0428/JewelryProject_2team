<template>
<div id="reg">
  <p>공지사항 등록</p>

  <div class="AddWrap">
    <form>
      <table class="tbAdd">
        <colgroup>
          <col width="15%" />
          <col width="*" />
        </colgroup>
        <tr>
          <th>제목</th>
          <td><input type="text" v-model="title" /></td>
        </tr>
        <tr>
          <th>내용</th>
          <td><textarea v-model="content"></textarea></td>
        </tr>
        <tr>
          <th>이미지 업로드</th>
          <td><v-file-input id="file" name="files" label="File input" style="width: 200px;"></v-file-input></td>
        </tr>
      </table>
    </form>
  </div>

  <div class="btnWrap">
    <a @click="List" class="btn">목록</a>
    <a @click="fnAddProc" class="btnAdd btn">등록</a>
  </div>
</div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      title: '',
      content: ''
    }
  },
  methods: {
    List () {
      this.$swal.fire({
        icon: 'warning',
        title: '해당내용이 지워집니다.',
        text: '목록으로 이동하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push('/notice')
        }
      })
    },
    fnAddProc () {
      if (!this.title) {
        this.$swal.fire({
          icon: 'info',
          title: '제목을 적어주세요.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        console.log(this.$store.state.auth.user.token)
        let frm = new FormData()
        let photoFile = document.getElementById('file')
        frm.append('title', this.title)
        frm.append('content', this.content)
        frm.append('writer', this.$store.state.auth.user.account)
        frm.append('file', photoFile.files[0])
        if (photoFile.files[0]) {
          axios.post('http://localhost:8000/jewelry/noticeBoard/regImg', frm, {
            headers: {
              'Authorization': 'Bearer ' + this.$store.state.auth.user.token,
              'Content-Type': 'multipart/form-data'
            }
          }).then((response) => {
            console.log(response)
          }).catch((error) => {
            console.log(error)
          })
        } else {
          axios({
            method: 'POST',
            headers: { 'Authorization': 'Bearer ' + this.$store.state.auth.user.token, 'Content-Type': 'application/json' },
            url: 'http://localhost:8000/jewelry/noticeBoard/reg',
            data: JSON.stringify({
              title: this.title,
              content: this.content,
              writer: this.$store.state.auth.user.account
            })
          }).then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
          })
        }
        this.$swal.fire({
          icon: 'success',
          title: '공지사항이 등록되었습니다.',
          text: '목록으로 이동합니다.',
          confirmButtonColor: '#CEF6CE'
        }).then(() => {
          this.$router.push('/notice')
        })
      }
    }
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
.tbAdd{border-top:1px solid #888;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0;}
.tbAdd td{padding:10px 10px; box-sizing:border-box;}
.tbAdd td input{width:100%; min-height:30px; box-sizing:border-box; padding:0 10px;}
.tbAdd td textarea{width:100%; min-height:340px; padding:10px; box-sizing:border-box;}
.btnWrap{text-align:center; margin:30px 0;}
.btnWrap a{margin:0 10px;}
table{width:100%; border-collapse:collapse;}
a{text-decoration:none;}
.btn{padding:10px; background:#000; color:#fff; border-radius: 0;}
</style>
