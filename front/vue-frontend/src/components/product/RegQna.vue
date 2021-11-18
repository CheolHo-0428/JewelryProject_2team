<template>
  <div class="outer">
    <p class="top">Q&A 등록</p>

    <table class="table">
      <colgroup>
        <col width="25%">
        <col width="*">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">Q&A 제목</th>
          <td><input type="text" v-model="title"></td>
        </tr>
        <tr>
          <th scope="col">Q&A 내용</th>
          <td><textarea type="text" placeholder="Q&A를 작성해주세요." v-model="content"></textarea></td>
        </tr>
        <tr>
          <th scope="col">이미지</th>
          <td class="img" colspan="3">
            <v-file-input id="file" name="files" label="File input" style="width: 200px;"></v-file-input>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="private" style="display:flex;">
      <p>비밀번호 설정</p>
      <span class="material-icons-outlined">lock</span>
      <div>
        <input type="password" id="pwd" v-model="password" autocomplete="new-password">
      </div>
    </div>

    <div class="button">
      <a @click="detail" class="btn">취소</a>
      <a @click="save" :class="{disabled : !password}" class="btn">Q&A등록</a>
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
      password: ''
    }
  },
  methods: {
    detail () {
      this.$swal.fire({
        icon: 'warning',
        title: 'Q&A가 등록되지 않습니다.',
        text: '취소 하시겠습니까?',
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
    save () {
      if (!this.title) {
        this.$swal.fire({
          icon: 'info',
          title: '제목을 적어주세요.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        let frm = new FormData()
        let photoFile = document.getElementById('file')
        frm.append('title', this.title)
        frm.append('content', this.content)
        frm.append('writer', this.$store.state.auth.user.account)
        frm.append('file', photoFile.files[0])
        frm.append('password', this.password)
        frm.append('item', this.$store.state.item.itemId)
        if (photoFile.files[0]) {
          axios.post('http://localhost:8000/jewelry/qnaBoard/regImg', frm, {
            headers: {
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
            headers: { 'Content-Type': 'application/json' },
            url: 'http://localhost:8000/jewelry/qnaBoard/reg',
            data: JSON.stringify({
              title: this.title,
              content: this.content,
              writer: this.$store.state.auth.user.account,
              item_id: this.$store.state.item.itemId
            })
          }).then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
          })
        }
        this.$swal.fire({
          icon: 'success',
          title: 'Q&A가 등록되었습니다.',
          confirmButtonColor: '#CEF6CE'
        }).then(() => {
          this.$router.push('/detail')
        })
      }
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem auto;
  width: 50rem;
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}

.table {
  width: 50rem;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  margin-bottom: 5rem;
}
input {
  text-align: center;
  width: 100% !important;
  font-size: 0.8rem;
}
tr {
  border-bottom: 1.5px solid gray;
  border-right: 1px solid black;
}
th {
  padding: 0.6rem 0;
  font-size: 0.85rem;
  background-color: silver;
  border-right: 1px solid black;
  border-left: 1px solid black;
  vertical-align: middle;
}
textarea {
  width: 100%;
  min-height: 7rem;
  font-size: 0.8rem;
}

.check {
  margin-top: 0 !important;
  padding-top: 0 !important;
  padding-left: 6px;
}
#pwd {
  border: 1px solid black;
  width: 200px !important;
  margin-left: 20px;
  border-radius: 4px;
}

.button {
  margin-bottom: 2rem;
}
.btn {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 400;
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
.btn {
  background: #000;
  color: #fff;
  padding: 0.4rem 1.2rem;
  font-size: 1rem;
  margin: 0 0.6rem;
}
.disabled {
  pointer-events: none;
  cursor: default;
  background-color: silver;
}
</style>
