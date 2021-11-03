<template>
  <div class="outer">
    <p>아이디 찾기</p>
    <!-- content-->
    <div id="content">
      <div>
        <h3 class="join_title">
          <label for="name">이름</label>
        </h3>
        <span class="box int_id">
          <input
            autocomplete="off"
            v-validate="'required'"
            type="text"
            id="name"
            class="int"
            name="name"
            v-model="user.name"
            minlength="3"
            maxlength="20"
          />
        </span>
        <div v-if="errors.has('name')" class="errorMessage" role="alert">
          이름은 3~20자 사이로 입력해주세요
        </div>
      </div>
      <div>
        <h3 class="join_title"><label for="email">이메일</label></h3>
        <span class="box int_pass">
          <input
            autocomplete="off"
            type="email"
            id="email"
            class="int"
            v-validate="'required'"
            name="email"
            v-model="user.email"
            maxlength="30"
          />
        </span>
        <div v-if="errors.has('email')" class="errorMessage" role="alert">
          이메일을 형식에 맞게 입력해주세요
        </div>
      </div>

      <div class="btn_area">
        <button type="button" id="btnJoin" @click="search">
          <span>아이디 찾기</span>
        </button>
      </div>
      <div class="search">
        <router-link to="/searchpw">비밀번호 찾기</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import User from '../../models/user'
export default {
  name: 'SearchID',
  data () {
    return {
      user: new User('', '')
    }
  },
  methods: {
    search () {
      this.$validator.validateAll().then((isValid) => {
        if (!isValid) {
          this.$swal.fire({
            icon: 'warning',
            title: '입력사항이 입력되지 않거나 <br> 양식에 맞지 않습니다.',
            showConfirmButton: true,
            confirmButtonColor: '#F8BB86',
            footer: '아래 빨간색으로 체크된 잘못된 부분을 확인해주세요'
          })
        }
        this.$store.dispatch('auth/findId', this.user).then(
          (data) => {
            if (data.result_code === 'OK') {
              this.$swal.fire({
                position: 'center',
                icon: 'success',
                title: '아이디찾기에 성공하셨습니다.',
                showConfirmButton: true,
                confirmButtonColor: '#a5dc86',
                timer: 1500,
                footer: '잊어버린아이디를 찾으셨네요!'
              })
              this.$router.push({
                name: 'findId',
                query: { account: data.data.account }
              })
            } else if (isValid && data.result_code === 'ERROR') {
              this.$swal.fire({
                position: 'center',
                icon: 'warning',
                title: '입력에 맞는 아이디가 없습니다.',
                showConfirmButton: true,
                confirmButtonColor: '#F8BB86',
                footer: '제대로 아이디를 입력했는지 확인해주세요.'
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
              showConfirmButton: true,
              confirmButtonColor: '#F27474',
              footer: this.message
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
.errorMessage {
  font-size: 12px;
  line-height: 42px;
  font-weight: bold;
  color: #ff003e;
  vertical-align: middle;
  float: right;
}
</style>
