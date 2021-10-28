<template>
  <div class="outer">
    <p class="top">주문상세</p>

    <p class="group">주문상품 정보</p> <br>
    <div class="boxs">
      <div class="box" v-for="i in 2" :key="i">
        <div class="img">
          <div></div>
        </div>
        <div class="content">
          상품코드
        </div>
        <div class="content">
          상품명
        </div>
        <div class="price">
          가격
        </div>
        <div class="count">
          수량
        </div>
        <div class="price">총 가격</div>
      </div>
    </div>

    <p class="group">주문 정보</p>
    <table class="table t2">
      <colgroup>
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주문번호</th>
          <td><input type="text" value="abc123" readonly></td>
          <th scope="col">주문일자</th>
          <td><input type="text" value="2021-10-25" readonly></td>
        </tr>
        <tr>
          <th scope="col">결제방법</th>
          <td><input type="text" value="무통장입금" readonly></td>
          <th scope="col">결제계좌</th>
          <td><input type="text" value="국민은행000-00000000-00" readonly></td>
        </tr>
        <tr>
          <th scope="col">입금인</th>
          <td><input type="text" value="홍길동" readonly></td>
          <th scope="col">주문처리상태</th>
          <td>
            <select name="order" class="op">
              <option value="1" selected>주문완료</option>
              <option value="2">결제확인</option>
              <option value="3">배송완료</option>
              <option value="4">주문취소</option>
            </select>
          </td>
        </tr>
      </tbody>
    </table>

    <p class="group">주문자 정보</p>
    <table class="table t3">
      <colgroup>
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주문자명</th>
          <td><input type="text" value="홍길동" readonly></td>
          <th scope="col">전화번호</th>
          <td><input type="text" value="010-0000-1111"></td>
        </tr>
        <tr>
          <th scope="col">이메일</th>
          <td><input type="text" value="aaa@naver.com"></td>
          <th scope="col">주소</th>
          <td><input type="text" value="서울시 송파구 ㅇㅇ아파트 101동101호"></td>
        </tr>
        <tr>
          <th scope="col">요청사항</th>
          <td colspan="3"><input type="text" value="문앞에 놔주세요."></td>
        </tr>
      </tbody>
    </table>

    <div class="button">
      <v-btn color="#D1CFC4" x-large @click="list">주문목록</v-btn>
      <v-btn color="#FBEF97" x-large @click="save">저장</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    list () {
      this.$swal.fire({
        icon: 'warning',
        title: '수정사항이 저장되지 않습니다.',
        text: '목록으로 돌아가시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          location.href = '/adorder'
        }
      })
    },
    save () {
      this.$swal.fire({
        icon: 'success',
        title: '주문정보가 수정되었습니다.',
        text: '목록으로 이동합니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        location.href = '/adorder'
      })
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem auto;
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
  margin-bottom: 2rem;
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
  width: 20%;
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
  width: 16%;
  padding-top: 30px;
}
.content {
  width: 16%;
  padding-top: 30px;
}
.op {
  border: 1px solid black;
  width: fit-content;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.2rem;
  background-color: white;
  text-align: center;
}

.table {
  width: 850px;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  margin-bottom: 5rem;
}
input {
  text-align: center;
  width: 100% !important;
  font-size: 0.8rem;
}
.t2 input {
  outline: none;
}
tr {
  border-bottom: 1.5px solid gray;
  border-right: 1px solid black;
}
th {
  padding: 0.6rem 0;
  font-size: 0.85rem;
  background-color: #fefff2;
  border-right: 1px solid black;
  border-left: 1px solid black;
  vertical-align: middle;
}

.button button {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
.button {
  margin-bottom: 2rem;
}

</style>
