<template>
  <div class="outer">
    <p class="top">주문상세내역</p>

    <p class="group">주문 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주문번호</th>
          <td><input type="text" readonly value="4392840342"></td>
          <th scope="col">주문일자</th>
          <td><input type="text" readonly value="2021-10-23"></td>
        </tr>
        <tr>
          <th scope="col">주문자</th>
            <td><input type="text" readonly value="홍길동"></td>
            <th scope="col">주문처리상태</th>
            <td><input type="text" value="배송완료" readonly></td>
          </tr>
      </tbody>
    </table>

    <p class="group">배송지 정보</p>
    <table class="table">
      <colgroup>
        <col width="15%">
        <col width="20%">
        <col width="20%">
        <col width="45%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">수취인</th>
          <td><input type="text" readonly value="홍길동"></td>
          <th scope="col">주소</th>
          <td><input type="text" readonly value="서울시 송파구 ㅇㅇ아파트 101동101호"></td>
        </tr>
        <tr>
          <th scope="col">연락처</th>
            <td><input type="text" readonly value="010-0000-1111"></td>
            <th scope="col">요청사항</th>
            <td><input type="text" value="문앞에 놔주세요!" readonly></td>
          </tr>
      </tbody>
    </table>

    <p class="group">주문상품 정보</p> <br>
    <div class="boxs">
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
    </div>

    <p class="group">결제 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주문방법</th>
          <td><input type="text" readonly value="무통장입금"></td>
          <th scope="col">결제계좌</th>
          <td><input type="text" readonly value="국민은행000-00000000-00"></td>
        </tr>
        <tr>
          <th scope="col">총 주문금액</th>
            <td><input type="text" readonly value="12500원"></td>
            <th scope="col">입금날짜</th>
            <td><input type="text" value="2021-10-23" readonly></td>
          </tr>
      </tbody>
    </table>

    <div class="button">
      <v-btn color="#FBEF97" x-large href="/orderlist">주문목록</v-btn>
      <v-btn color="#D1CFC4" x-large @click="ordercancle">주문취소</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    ordercancle () {
      this.$swal.fire({
        icon: 'warning',
        title: '주문 취소시 되돌릴 수 없습니다.',
        text: '주문을 취소하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          location.href = '/orderlist'
        }
      })
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 3rem auto;
  width: 53rem;
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}

.group {
  float: left;
}
.table {
  width: 850px;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  background-color: #fefff2;
  margin-bottom: 5rem;
}
input {
  text-align: center;
  width: 100% !important;
  font-size: 0.8rem;
}
tr {
  border-bottom: 1.5px solid gray;
}
th {
  padding: 0.6rem 0;
  font-size: 0.85rem;
}

.boxs {
  width: 53rem;
  margin-bottom: 4rem;
}
.box {
  display: flex;
  margin: 2rem auto;
  border-bottom: 0.15rem solid black;;
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

.button button,
.button a {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}

</style>
