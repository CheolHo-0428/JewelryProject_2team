<template>
<div id="reg">
  <p>공지사항</p>

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
        <tr>
          <th>내용</th>
          <td><textarea v-model="content"></textarea></td>
        </tr>
      </table>
    </form>
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
      id: 0
    }
  },
  methods: {
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
        title: '해당내용이 지워집니다.',
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

          location.href = '/notice'
        }
      })
    },
    notice () {
      return axios.get(this.$store.state.notice.allNotices)
        .then(res => {
          let notices = res.data.data
          let index = notices.findIndex(i => i.id === this.$store.state.notice.noticeId)
          console.log(index)
          this.title = res.data.data[index].title
          this.content = res.data.data[index].content
          this.id = res.data.data[index].id
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.notice()
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
.tbAdd td textarea{width:100%; min-height:300px; padding:10px; box-sizing:border-box;}
.btnWrap{text-align:center; margin:30px 0;}
.btnWrap a{margin:0 10px;}
table{width:100%; border-collapse:collapse;}
a{text-decoration:none;}
.btn{padding:10px; background:#34445c; color:#fff;}
</style>
