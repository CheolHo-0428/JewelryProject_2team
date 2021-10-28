<template>
  <div class="outer">
    <div class="box" v-for="i in 5" :key="i">
      <div class="num">1</div>
      <div class="content" @click="detail">
        Q&A 제목
      </div>
      <div class="writer">작성자 : 홍길동</div>
      <div class="date">2021-10-20</div>
    </div>

    <div class="button">
      <v-btn color="#F4F2E7" href="/regqna">Q&A등록</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    detail () {
      location.href = './qna_'
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 3rem 0;
}
.box {
  display: flex;
  margin: 2rem;
  border-bottom: 1px solid black;;
  width: 50rem;
}
.num, .content {
  height: 40px;
}
.writer,
.date {
  width : 12%;
}
.num {
  width: 10%;
  text-align: center;
}
.content {
  width: 66%;
  word-break: break-all;
  padding-top: 10px;
  overflow: hidden;
  text-align: center;
  cursor: pointer;
}
.content:hover {
  font-size: 1.02rem;
}
.writer,
.date {
  text-align: center;
  font-size: 0.7rem;
  padding-top: 10px;
}

.button {
  display: flex;
  justify-content: right;
  margin: 2rem 3.8rem 0 0;
}
.button a {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
</style>
