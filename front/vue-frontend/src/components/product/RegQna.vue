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
        <input type="password" id="pwd" v-model="password">
      </div>
    </div>

    <div class="button">
      <v-btn color="#D1CFC4" x-large @click="detail">취소</v-btn>
      <v-btn color="#FBEF97" x-large @click="save">Q&A 등록</v-btn>
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
        frm.append('writer', 'testUser')
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
              writer: 'testUser',
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
  background-color: #fefff2;
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

.button button {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
.button {
  margin-bottom: 2rem;
}

</style>
