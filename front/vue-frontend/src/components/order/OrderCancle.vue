<template>
  <div class="outer">
    <p>주문취소</p>
    <div class="check material-icons">done_all</div>
    <div class="text">
      <p>고객님의 주문이 취소 되었습니다.</p>
      <p>주문내역에 관한 안내는 <a href="/orderlist">주문목록조회</a>를 통하여 확인 가능합니다.</p>
      <p>주문번호 : <input type="text" value="23313232123" readonly></p>
      <p>취소일자 : <input type="text" value="2021-10-23" readonly></p>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.outer {
  margin: 3rem auto;
  width: 52rem;
  text-align: center;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 0rem;
}
.check {
  border: 0.4rem solid rgb(182, 182, 182);
  border-radius: 50px;
  padding: 1rem;
  font-size: 3rem;
  color: rgb(255, 73, 73);
  margin: 2rem 0;
}
.text p:nth-child(1) {
  font-size: 1.3rem;
  margin-bottom: 0.3rem;
}
.text p:nth-child(2) {
  font-size: 0.7rem;
  color: #747272;
  margin-bottom: 1.5rem;
}
.text p:nth-child(3),
.text p:nth-child(4),
.text p:nth-child(3) input,
.text p:nth-child(4) input  {
  font-size: 0.7rem;
  color: #747272;
  text-align: center;
}
.text p:nth-child(3) input,
.text p:nth-child(4) input  {
  width: 8rem;
}
.text p:nth-child(2) a {
  color: black;
}

</style>
