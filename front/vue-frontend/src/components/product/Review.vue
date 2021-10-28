<template>
  <div class="outer">
    <div class="box" v-for="i in 5" :key="i">
      <div class="img">
        <div></div>
      </div>
      <div class="content" @click="detail">
        리뷰 제목
      </div>
      <div class="innerBox">
        <div class="writer">작성자 : 홍길동</div>
        <div class="date">2021-10-20</div>
      </div>
    </div>

    <div class="button">
      <v-btn color="#F4F2E7" href="/regreview">리뷰작성</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    detail () {
      location.href = './review_'
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem 0;
}
.box {
  display: flex;
  margin: 2rem;
  border-bottom: 1px solid black;;
}
.img, .content {
  height: 100px;
}
.innerBox {
  width : 24%;
}
.img {
  width: 25%;
}
.img div {
  width: 90px;
  height: 90px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  margin-left: 45px;
}
.content {
  width: 51%;
  word-break: break-all;
  padding-top: 10px;
  overflow: hidden;
  cursor: pointer;
}
.content:hover {
  font-size: 1.02rem;
}
.innerBox div {
  height: 20%;
  text-align: center;
  font-size: 0.7rem;
}
.writer {
  margin-top: 30px;
}

.button {
  display: flex;
  justify-content: right;
  margin: 2rem 2rem 0 0;
}
.button a {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
</style>
