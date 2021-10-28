<template>
  <div class="outer">
    <p class="top">상품등록</p>

    <p class="group">상품 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">상품명</th>
          <td><input type="text"></td>
          <th scope="col">상품코드</th>
          <td><input type="text"></td>
        </tr>
        <tr>
          <th scope="col">상품분류</th>
          <td>
            <select name="product" class="op">
              <option value="" selected>-- 선택하세요 --</option>
              <option value="ring">반지</option>
              <option value="earrings">귀걸이</option>
              <option value="bracelet">팔찌</option>
              <option value="necklace">목걸이</option>
            </select>
          </td>
          <th scope="col">상품가격</th>
          <td><input type="text"></td>
        </tr>
        <tr>
          <th scope="col">재고량</th>
          <td><input type="text"></td>
        </tr>
        <tr>
          <th scope="col">이미지</th>
          <td class="img" colspan="3">
            <input type="file" multiple>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="button">
      <v-btn color="#D1CFC4" x-large @click="list">상품목록</v-btn>
      <v-btn color="#FBEF97" x-large @click="save">등록</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    list () {
      this.$swal.fire({
        icon: 'warning',
        title: '상품이 등록되지 않습니다.',
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
        title: '상품이 등록되었습니다.',
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
.op {
  border: 1px solid black;
  width: fit-content;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.2rem;
  background-color: white;
  text-align: center;
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
