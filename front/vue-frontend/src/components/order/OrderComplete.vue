<template>
  <div class="outer">
    <p>주문완료</p>
    <div class="check material-icons">done_all</div>
    <div class="text">
      <p>고객님의 주문이 완료 되었습니다.</p>
      <p>주문내역 및 배송에 관한 안내는 <a href="/orderdetail">주문조회</a>를 통하여 확인 가능합니다.</p>
      <p>주문번호 : <input type="text" value="23313232123" readonly></p>
      <p>주문일자 : <input type="text" value="2021-10-23" readonly></p>
    </div>
    <form>
      <p>
        <label>최종 결제금액</label>
        <span><input class="pay" type="text" value="12500원" readonly></span>
      </p>
      <p>
        <label>결제수단</label>
        <span class="info">
          <p>무통장입금</p>
          <p>입금자 : <input type="text" readonly value="홍길동">, 계좌번호 : <input type="text" readonly value="국민은행000-00000000-00"> </p>
        </span>
      </p>
    </form>
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

label{
  width:140px;
  display: table-cell;
  border-right: 1px dashed #c6c6c6;
  padding: 10px 5px;
  font-size: 1rem;
}
form{
  display:table;
  border-collapse: collapse;
  margin: 5rem auto;
  width: fit-content;
}
span{
  display: table-cell;
  padding: 5px 0px 5px 20px;
  vertical-align: middle;
}
input{
  width:100px;
  height:20px;
  margin:2px;
  outline: none;
}
.pay {
  font-size: 1rem;
}
.info p {
  font-size: 0.8rem;
  width: fit-content;
}
.info input:first-child {
  width: 3rem;
}
.info input:last-child {
  width: 12rem
}
</style>
