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
          <td><input type="text" v-model="title" ref="subject" /></td>
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
      stored_file_name: '',
      id: 0,
      deleteImg: false
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
            'http://localhost:8000/jewelry/reviewBoard/update',
            {
              title: this.title,
              content: this.content,
              id: this.id,
              writer: 'testUser',
              delete_check: 'YES',
              item_id: this.$store.state.item.itemId
            }
          ).then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
          })
        } else {
          axios.put(
            'http://localhost:8000/jewelry/reviewBoard/update',
            {
              title: this.title,
              content: this.content,
              id: this.id,
              writer: 'testUser',
              item_id: this.$store.state.item.itemId
            }
          ).then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
          })
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
    review () {
      axios.get(`http://localhost:8000/jewelry/reviewBoard/${this.$store.state.item.reviewId}`)
        .then(res => {
          let review = res.data.data

          this.title = review.title
          this.content = review.content
          this.stored_file_name = review.stored_file_name
          this.id = review.id
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
  margin: 3rem 0;
}
#reg {
  width: 800px;
  margin: 0 auto;
  text-align: center;
  color: #2c3e50;
}
.img {
  width: 200px;
  height: 200px;
  margin: 1rem 0;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
}
input {
  outline: none;
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
.tbAdd td textarea{width:100%; min-height:200px; padding:10px; box-sizing:border-box;}
.btnWrap{text-align:center; margin:30px 0;}
.btnWrap a{margin:0 10px;}
table{width:100%; border-collapse:collapse;}
a{text-decoration:none;}
.btn{padding:10px; background:#34445c; color:#fff;}
</style>
