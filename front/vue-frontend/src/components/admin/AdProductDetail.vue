<template>
  <div class="outer">
    <p class="top">상품상세</p>

    <p class="group">상품 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">상품명</th>
          <td><input type="text" value="~~목걸이"></td>
          <th scope="col">상품코드</th>
          <td><input type="text" value="ab1234"></td>
        </tr>
        <tr>
          <th scope="col">상품분류</th>
          <td><input type="text" value="목걸이"></td>
          <th scope="col">상품가격</th>
          <td><input type="text" value="30000원"></td>
        </tr>
        <tr>
          <th scope="col">재고량</th>
          <td><input type="text" value="23"></td>
          <th scope="col">등록날짜</th>
          <td><input type="text" value="2021-10-25"></td>
        </tr>
        <tr>
          <th scope="col">이미지</th>
          <td class="img" colspan="3">
            <div class="imgBox">
              <div v-for="i in 3" :key="i"></div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="button">
      <v-btn color="#D1CFC4" x-large @click="list">상품목록</v-btn>
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
          location.href = '/adproduct'
        }
      })
    },
    save () {
      this.$swal.fire({
        icon: 'success',
        title: '상품정보가 수정되었습니다.',
        text: '목록으로 이동합니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        location.href = '/adproduct'
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
.img {
  overflow-x: scroll;
}
.imgBox {
  display: flex;
}
.imgBox div {
  width: 100px;
  height: 100px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  margin-left: 35px;
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
