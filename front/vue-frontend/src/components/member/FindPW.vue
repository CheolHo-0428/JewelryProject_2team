<template>
  <div class="outer">
    <div class="material-icons">edit</div>
    <div class="text">
      <p>새 비밀번호 입력</p>
      <p class="input"><input type="password"></p>
      <p>비밀번호 확인</p>
      <p class="input"><input type="password"></p>
    </div>

    <div class="button">
      <button type="button" class="btn btn-secondary" @click="login">저장</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    login () {
      location.href = '/login'
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
.text p{
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

</style>
