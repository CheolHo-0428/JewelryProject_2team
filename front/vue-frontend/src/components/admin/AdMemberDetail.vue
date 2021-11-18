<template>
  <div class="outer">
    <p class="top">회원 상세정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">이름</th>
          <td><input type="text" readonly v-model="name"></td>
          <th scope="col">아이디</th>
          <td><input type="text" readonly v-model="account"></td>
        </tr>
        <tr>
          <th scope="col">가입일</th>
          <td><input type="text" readonly v-model="created_at"></td>
          <th scope="col">전화번호</th>
          <td><input type="text" v-model="phone" readonly></td>
        </tr>
        <tr>
          <th scope="col">우편번호</th>
          <td><input type="text" readonly v-model="post_code"></td>
          <th scope="col">주소</th>
          <td><input type="text" v-model="address" readonly></td>
        </tr>
        <tr>
          <th scope="col">이메일</th>
          <td><input type="text" v-model="email" readonly></td>
          <th scope="col">권한</th>
          <td><input type="text" readonly v-model="role"></td>
        </tr>
        <tr>
          <th scope="col">관리자 메모</th>
          <td colspan="3"><textarea type="text" v-model="admin_message" placeholder="메모를 입력하여 주세요"></textarea></td>
        </tr>
      </tbody>
    </table>
    <div class="button">
      <v-btn color="#000" style="color:white"  x-large @click="list">회원목록</v-btn>
      <v-btn color="#000" style="color:white"  x-large @click="mod">저장</v-btn>
      <v-btn color="#000" style="color:white"  x-large @click="remove">삭제</v-btn>
    </div>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  data () {
    return {
      id: 0,
      name: '',
      account: '',
      created_at: '',
      phone: '',
      post_code: '',
      address: '',
      email: '',
      role: '',
      admin_message: ''
    }
  },
  created () {
    this.detail()
  },
  methods: {
    list () {
      this.$swal.fire({
        icon: 'warning',
        title: '수정사항이 저장되지 않습니다.',
        text: '목록으로 돌아가시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push('/admember')
        }
      })
    },
    save () {
      this.$swal.fire({
        icon: 'success',
        title: '회원정보가 수정되었습니다.',
        text: '목록으로 이동합니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        this.$router.push('/admember')
      })
    },
    mod () {
      axios
        .put('http://localhost:8000/jewelry/member/update', {
          account: this.account,
          admin_message: this.admin_message
        })
        .then((res) => {
          console.log(res)
        })
        .catch((error) => {
          console.log(error)
        })
      this.save()
    },
    remove () {
      this.$swal
        .fire({
          icon: 'warning',
          title: '해당유저가 삭제됩니다.',
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
              .delete(`http://localhost:8000/jewelry/auth/${this.id}`, {
                data: {
                  id: this.id
                }
              })
              .then(function (response) {
                console.log(response)
              })
              .catch(function (error) {
                console.log(error)
              })
            await this.$store.commit('admemberDetail', {id: 0, urlPage: 'http://localhost:8000/jewelry/member/paging'})
            await this.$router.push('/admember')
          }
        })
    },
    async detail () {
      await axios
        .get(this.$store.state.member.allAdMembers)
        .then((res) => {
          let admembers = res.data.data
          let index = admembers.findIndex(
            (i) => i.id === this.$store.state.member.id
          )
          this.id = res.data.data[index].id
          this.account = res.data.data[index].account
          this.name = res.data.data[index].name
          this.phone = res.data.data[index].phone
          this.post_code = res.data.data[index].post_code
          this.address = res.data.data[index].address
          this.created_at = res.data.data[index].created_at.split('T')[0] + ' ' + res.data.data[index].created_at.split('T')[1].split('.')[0]
          this.email = res.data.data[index].email
          this.admin_message = res.data.data[index].admin_message
          console.log(this.id)
          if (this.account === 'admin') {
            this.role = '관리자'
          } else {
            this.role = '일반유저'
          }
        })
        .catch((err) => {
          console.log(err)
        })
      await axios
        .get(`http://localhost:8000/jewelry/member/${this.id}/orderGroupInfo`)
        .then((res) => {
          this.response_list = res.data.data.member_response.order_group_list
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem auto;
  width: 53rem;
  text-align: center
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem
}
.table>:not(:first-child) {
  border: 1px solid gray
}
.table {
  width: 850px;
  margin-bottom: 5rem;
  border: 1px solid gray
}
input {
  text-align: center;
  width: 100% !important;
  font-size: .8rem;
  outline: 0
}
th {
  padding: .6rem 0;
  font-size: .85rem;
  background-color: #e7e7e7;
  vertical-align: middle
}
textarea {
  width: 100%;
  min-height: 5rem;
  font-size: .8rem;
  color: #000
}
textarea::placeholder {
  text-align: center;
  color: #e7e7e7;
  font-size: 30px;
  padding-top: 20px;
  font-weight: 700
}
.button button {
  border: 1px solid #000;
  margin: 0 .6rem;
  padding: .5rem 2rem;
  font-weight: 700
}
.button {
  margin-bottom: 2rem
}
</style>
