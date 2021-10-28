<template>
  <div class="outer">
    <p>주문/결제</p>
    <div class="box" v-for="i in 2" :key="i">
      <div class="img">
        <div></div>
      </div>
      <div class="content">
        다이아 반지
      </div>
      <div class="count">
        수량
      </div>
      <div class="price">총 가격</div>
    </div>

    <p class="group">주문 정보</p>
    <form>
      <p>
        <label>이름</label>
        <span><input class="name" type="text" value="홍길동" readonly></span>
      </p>
      <p>
        <label>휴대폰</label>
        <span class="phone">
          <input value="010" type="text">- <input value="0000" type="text">- <input value="1111" type="text">
          <a class="btn btn-secondary">수정</a>
        </span>
      </p>
      <p>
        <label>배송주소</label>
        <span class="addr">
          <input type="text" value="(08123)">
          <a class="btn btn-secondary">우편번호 검색</a> <br>
          <input type="text" class="text2" value="서울시 송파구 ㅇㅇㅇ아파트"><br>
          <input type="text" class="text2" value="101동101호">
        </span>
      </p>
      <p>
        <label>수령인</label>
        <span><input class="name" type="text" value="홍길동"></span>
      </p>
      <p>
        <label>요청사항</label>
        <span><input class="request" type="text" placeholder="요청사항을 입력하세요."></span>
      </p>
      <p>
        <label>결제방식</label>
        <span><input class="pay" type="text" value="무통장입금" readonly></span>
      </p>
      <p>
        <label>결제은행</label>
        <span>
          <select name="bank" class="bank">
            <option value="" selected>-- 선택하세요 --</option>
            <option value="국민">국민은행000-00000000-00</option>
            <option value="신한">신한은행111-11111111-11</option>
          </select>
        </span>
      </p>
    </form>

    <div class="bottom">
      <div class="calc">
          <table>
            <colgroup>
              <col width="80"/><col width="10"/><col width="70"/>
            </colgroup>
            <th>총 상품금액</th>
            <th>+</th>
            <th>배송비</th>
            <tr>
              <td>10000원</td>
              <td>+</td>
              <td>2500원</td>
            </tr>
          </table>
      </div>
      <div class="total">
        <table>
          <th>총 주문금액</th>
          <tr>
            <td>12500원</td>
          </tr>
        </table>
      </div>
    </div>

    <div class="button">
      <v-btn color="#FBEF97" x-large @click="order" href="/order_">결제하기</v-btn>
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
}
.box, .bottom {
  display: flex;
  margin: 3rem;
  border-bottom: 1px solid black;;
}
.img, .content {
  height: 100px;
}
.img {
  width: 26%;
}
.img div {
  width: 90px;
  height: 90px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  margin-left: 45px;
}
.count,
.price {
  width: 15%;
  padding-top: 30px;
}
.content {
  width: 38%;
  padding-top: 30px;
}

.bottom {
  margin-top: 5rem;
  border-top: 1px solid black;
  padding: 36px 0;
}
.calc,
.total {
  width: 50%;
  font-weight: 700;
}
table {
  width: 100%;
}
th {
  padding-bottom: 0.7rem;
}
td {
  color: #747272;
}
.button {
  display: flex;
  justify-content: center;
  margin-top: 2rem;
}
.button a {
  border: 1px solid black;
  padding: 0.5rem 2rem;
  font-weight: 700;
}

.group {
  font-size: 1rem;
  margin: 4rem auto 1rem;
  border-bottom: 1px solid black;
  width: fit-content;
}
label{
  width:100px;
  display: table-cell;
  border-right: 1px dashed #c6c6c6;
  padding: 10px 5px;
  font-size: 1rem;
}
form{
  display:table;
  border-collapse: collapse;
  margin: 0 auto;
  width: 46rem;
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
}
.text2{
  width:260px;
}
.name {
  font-size: 1rem;
}
.phone input {
  font-size: 1rem;
  width: 2.8rem;
}
.phone a,
.addr a {
  font-size: 0.7rem;
  padding: 0.1rem 0.4rem;
}
.addr input {
  font-size: 1rem;
}
.request {
  width: 30rem;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.8rem 0.2rem;
  border: 1px solid black;
}
.pay {
  font-size: 0.9rem;
  outline: none;
  color: #747272;
}
.bank {
  border: 1px solid black;
  width: 12rem;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.2rem;
}

</style>
