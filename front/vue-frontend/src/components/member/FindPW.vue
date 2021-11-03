<template>
  <div class="outer">
    <div class="material-icons">edit</div>
    <div class="text">
      <p>새 비밀번호 입력</p>
      <p class="input">
        <input
          ref="password"
          v-validate="'required'"
          name="password"
          type="password"
          autocomplete="off"
          v-model="user.password"
          minlength="6"
          maxlength="20"
        />
      </p>
      <div v-if="errors.has('password')" class="errorMessage">
        비밀번호는 6~20자로 입력하여 주세요.
      </div>
      <br />
      <p>비밀번호 확인</p>
      <p class="input">
        <input
          name="confirm_password"
          type="password"
          autocomplete="off"
          v-validate="'required|confirmed:password'"
          minlength="6"
          maxlength="20"
        />
      </p>
      <div v-if="errors.has('confirm_password')" class="errorMessage">
        위의 비밀번호와 일치하지 않습니다.
      </div>
    </div>

    <div class="button">
      <button type="submit" class="btn btn-secondary" @click="login">
        저장
      </button>
    </div>
  </div>
</template>

<script>
import User from '../../models/user'
export default {
  name: 'FINDPW',
  data () {
    return {
      user: new User('', '', '', '', '', '', '', '', '', '')
    }
  },
  methods: {
    login () {
      this.$validator.validateAll().then((isValid) => {
        if (!isValid) {
          this.$swal.fire({
            icon: 'warning',
            title: '유효성에 맞지않습니다',
            showConfirmButton: true,
            confirmButtonColor: '#F8BB86',
            footer: ' 다시한번 유효성에 맞춰 기입해주세요 '
          })
        } else {
          this.user.account = this.$route.query.account
          this.user.password = this.user.password
          this.$store.dispatch('auth/changePw', this.user).then(
            () => {
              this.$swal.fire({
                position: 'center',
                icon: 'success',
                title: '비밀번호수정에 성공하셨습니다.',
                showConfirmButton: true,
                confirmButtonColor: '#a5dc86',
                timer: 1500,
                footer: '로그인페이지로 이동합니다.'
              })
              this.$router.push('/login')
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
                icon: 'error',
                title: '에러가 발생하였습니다.',
                showConfirmButton: true,
                confirmButtonColor: '#F27474',
                footer: this.message
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
  margin: 3rem auto;
  width: 52rem;
  text-align: center;
}
.material-icons {
  border: 0.4rem solid rgb(182, 182, 182);
  border-radius: 50px;
  padding: 1rem;
  font-size: 3rem;
  margin: 2rem 0;
}
.text {
  width: 450px;
  margin: 0 auto;
}
.text p {
  font-size: 1.3rem;
  margin-bottom: 2rem;
  font-weight: 700;
}
input {
  text-align: center;
  width: 100%;
}
.input {
  border: 1px solid black;
}

.button {
  display: flex;
  justify-content: center;
  margin: 2rem 0;
}
.button button {
  border: 1px solid black;
  border-radius: 0;
  margin: 0 0.6rem;
  padding: 0.6vw 1.2vw;
  font-size: 1.2rem;
  font-weight: 700;
}
.button button:focus {
  box-shadow: none;
}
.errorMessage {
  font-size: 16px;
  line-height: 0px;
  font-weight: bold;
  color: #ff003e;
  text-align: center;
}
</style>
