<template>
  <div class="outer">
    <div class="material-icons">search</div>
    <div class="text">
      <p>고객님의 아이디 찾기 결과입니다.</p>
      <p>{{ $route.query.account }}</p>
    </div>

    <div class="button">
      <button type="button" class="btn btn-light" @click="searchpw">
        비밀번호 찾기
      </button>
      <button type="button" class="btn btn-secondary" @click="login">
        로그인하러 가기
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FINDID',
  methods: {
    searchpw () {
      this.$router.push('/searchpw')
    },
    login () {
      this.$router.push('/login')
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
.text p {
  font-size: 1.3rem;
  margin-bottom: 2rem;
  font-weight: 700;
}
input {
  text-align: center;
  outline: none;
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
  font-size: 0.8vw;
  font-weight: 700;
}
.button button:focus {
  box-shadow: none;
}
</style>
