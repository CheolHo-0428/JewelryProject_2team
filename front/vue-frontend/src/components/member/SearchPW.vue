<template>
  <div class="outer">
    <p>비밀번호 찾기</p>
    <!-- content-->
    <div id="content">
      <div>
        <h3 class="join_title">
          <label for="name">이름</label>
        </h3>
        <span class="box int_id">
          <input
            v-validate="'required'"
            name="name"
            v-model="user.name"
            type="text"
            id="name"
            class="int"
          />
        </span>
        <div v-if="errors.has('name')" class="errorMessage" role="alert">
          이름을 입력해주세요
        </div>
      </div>
      <div>
        <h3 class="join_title"><label for="account">아이디</label></h3>
        <span class="box int_pass">
          <input
            v-validate="'required'"
            name="account"
            v-model="user.account"
            type="text"
            id="id"
            class="int"
          />
        </span>
        <div v-if="errors.has('account')" class="errorMessage" role="alert">
          아이디를 입력해주세요
        </div>
      </div>
      <div>
        <h3 class="join_title"><label for="email">이메일</label></h3>
        <span class="box int_pass">
          <input
            v-validate="'required'"
            name="email"
            v-model="user.email"
            type="email"
            id="email"
            class="int"
          />
        </span>
        <div v-if="errors.has('email')" class="errorMessage" role="alert">
          이메일을 입력해주세요
        </div>
      </div>

      <div class="btn_area">
        <button type="button" id="btnJoin" @click="find">
          <span>비밀번호 찾기</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import User from '../../models/user'
export default {
  name: 'SearchPW',
  data () {
    return {
      user: new User('', '', '')
    }
  },
  methods: {
    find () {
      this.$validator.validateAll().then((isValid) => {
        if (!isValid) {
          this.$swal.fire({
            icon: 'warning',
            title: '입력사항이 입력되지 않거나 <br> 양식에 맞지 않습니다.',
            text: '아래 빨간색으로 체크된 잘못된 부분을 확인해주세요',
            confirmButtonColor: '#F8BB86'
          })
        }
        this.$store.dispatch('auth/findPw', this.user).then(
          (data) => {
            if (data.result_code === 'OK') {
              this.$swal.fire({
                position: 'center',
                icon: 'success',
                title: '비밀번호변경페이지로 이동합니다',
                showConfirmButton: false,
                timer: 1500,
                footer: '원하시는 비밀번호로 설정해주세요'
              })
              this.$router.push({
                name: 'findPw',
                query: { account: data.data.account }
              })
            } else if (isValid && data.result_code === 'ERROR') {
              this.$swal.fire({
                position: 'center',
                icon: 'warning',
                title: '입력에 맞는 정보가 없습니다.',
                footer: '제대로 정보를 입력했는지 확인해주세요.',
                confirmButtonColor: '#F8BB86'
              })
            }
          },
          (error) => {
            this.message =
              // 에러메세지 알고싶을때 이걸로 확인!
              (error.response && error.response.data) ||
              error.message ||
              error.toString()
            this.$swal.fire({
              icon: 'error',
              title: '에러가 발견되었습니다.',
              text: this.message,
              confirmButtonColor: '#F27474'
            })
          }
        )
      })
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 5rem 0 3rem;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin: 0 auto;
  width: 660px;
}
#content {
  width: 660px;
  margin-top: 3rem;
  margin: 0 auto;
}
h3 {
  margin: 19px 0 8px;
  font-size: 14px;
  font-weight: 700;
}
.box {
  display: block;
  width: 100%;
  height: 51px;
  border: solid 1px #dadada;
  padding: 10px 14px 10px 14px;
  box-sizing: border-box;
  background: #fff;
  position: relative;
}
.int {
  display: block;
  position: relative;
  width: 100%;
  height: 29px;
  border: none;
  background: #fff;
  font-size: 15px;
}
input {
  font-family: Dotum, "돋움", Helvetica, sans-serif;
  width: 100%;
}

/* 버튼 */
.btn_area {
  margin: 30px 0 20px;
}
#btnJoin {
  width: 100%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: #fff;
  background-color: #b4b9be;
  font-size: 20px;
  font-weight: 700;
  font-family: Dotum, "돋움", Helvetica, sans-serif;
}
#btnJoin:hover {
  background-color: black;
}
.errorMessage {
  font-size: 12px;
  line-height: 42px;
  font-weight: bold;
  color: #ff003e;
  vertical-align: middle;
  float: right;
}
</style>
