<template>
  <div class="outer">
    <p class="top">회원상세</p>

    <p class="group">회원 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">이름</th>
          <td><input type="text" readonly value="홍길동"></td>
          <th scope="col">아이디</th>
          <td><input type="text" readonly value="happy"></td>
        </tr>
        <tr>
          <th scope="col">가입일</th>
          <td><input type="text" readonly value="2021-10-25"></td>
          <th scope="col">전화번호</th>
          <td><input type="text" value="010-0000-1111" readonly></td>
        </tr>
        <tr>
          <th scope="col">우편번호</th>
          <td><input type="text" readonly value="81000"></td>
          <th scope="col">주소</th>
          <td><input type="text" value="서울시 송파구 ㅇㅇ아파트 105동403호" readonly></td>
        </tr>
        <tr>
          <th scope="col">이메일</th>
          <td><input type="text" value="aaa@naver.com" readonly></td>
          <th scope="col">총 주문액</th>
          <td><input type="text" readonly value="143000원"></td>
        </tr>
        <tr>
          <th scope="col">관리자 메모</th>
          <td colspan="3"><textarea type="text">관리자 메모사항</textarea></td>
        </tr>
      </tbody>
    </table>

    <div class="button">
      <v-btn color="#D1CFC4" x-large @click="list">회원목록</v-btn>
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
          location.href = '/admember'
        }
      })
    },
    save () {
      this.$swal.fire({
        icon: 'success',
        title: '회원정보가 수정되었습니다.',
        text: '목록으로 이동합니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        location.href = '/admember'
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
textarea {
  width: 100%;
  min-height: 5rem;
  font-size: 0.8rem;
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
