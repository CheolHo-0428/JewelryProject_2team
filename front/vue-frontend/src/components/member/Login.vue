<template>
  <div class="outer">
    <p>로그인</p>
    <!-- content-->
    <div id="content">
      <!-- ID -->
      <h3 class="join_title">
        <label for="id">아이디</label>
      </h3>
      <span class="box int_id">
        <input
          autocomplete="off"
          type="text"
          v-model="user.account"
          v-validate="'required'"
          name="account"
          id="id"
          class="int"
          maxlength="20"
        />
      </span>
      <div v-if="errors.has('account')" class="errorMessage" role="alert">
        아이디를 입력해주세요.
      </div>
      <h3 class="join_title"><label for="pswd1">비밀번호</label></h3>

      <span class="box int_pass">
        <input
          autocomplete="off"
          type="password"
          v-model="user.password"
          v-validate="'required'"
          name="password"
          id="pswd1"
          class="int"
          maxlength="20">
      </span>
    <div
        v-if="errors.has('password')"
        class="errorMessage"
        role="alert"
      >
        비밀번호를 입력해 주세요.
      </div>
      <div class="btn_area">
        <button
          :disabled="loading"
          @click="handleLogin()"
          type="button"
          id="btnJoin"
        >
          <span
            v-show="loading"
            class="spinner-border spinner-border-sm"
          ></span>
          <span>로그인</span>
        </button>
      </div>
      <div class="search">
        <router-link to="/searchid">아이디 찾기</router-link> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <router-link to="/searchpw">비밀번호 찾기</router-link>
      </div>
      <div class="form-group">
        <!-- <div v-if="message" class="alert alert-danger" role="alert">
            {{ message }}
          </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import User from '../../models/user'
export default {
  name: 'Login',
  data () {
    return {
      user: new User('', ''),
      loading: false
      //   message: "",
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    }
  },
  created () {
    if (this.loggedIn) {
      this.$router.push('/')
    }
  },
  methods: {
    handleLogin () {
      this.loading = true
      this.$validator.validateAll().then((isValid) => {
        if (!isValid) {
          this.loading = false
          return
        }
        if (this.user.account && this.user.password) {
          this.$store.dispatch('auth/login', this.user).then(
            () => {
              this.$swal.fire({
                position: 'center',
                icon: 'success',
                title: '로그인에 성공하셨습니다.',
                showConfirmButton: true,
                confirmButtonColor: '#a5dc86',
                timer: 1500,
                footer: 'GGULUCK의 쇼핑몰에 오신것을 환영합니다'
              })
              this.$router.push('/')
            },
            (error) => {
              console.log(error)
              this.loading = false
              //   this.message =
              // 에러메세지 알고싶을때 이걸로 확인!
              // (error.response && error.response.data) ||
              // error.message ||
              // error.toString()
              this.$swal.fire({
                icon: 'warning',
                title: '잘못된값을 입력하셨습니다.',
                showConfirmButton: true,
                confirmButtonColor: '#F8BB86',
                footer: '아이디와 비밀번호를 다시 확인해주세요'
              })
            }
          )
        }
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
  outline:none;
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
.search {
  margin-top: 1rem;
  margin: 0 auto;
  width: fit-content;
}
.search a {
  color: black;
}
.errorMessage{
    font-size: 12px;
    line-height: 42px;
    font-weight: bold;
    color: #ff003e;
    vertical-align: middle;
    float: right;
}
</style>
