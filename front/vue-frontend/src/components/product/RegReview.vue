<template>
  <div class="outer">
    <p class="top">리뷰등록</p>

    <table class="table">
      <colgroup>
        <col width="25%">
        <col width="*">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">리뷰제목</th>
          <td><input type="text"></td>
        </tr>
        <tr>
          <th scope="col">리뷰내용</th>
          <td><textarea type="text" placeholder="리뷰를 작성해주세요."></textarea></td>
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
      <v-btn color="#D1CFC4" x-large @click="detail">취소</v-btn>
      <v-btn color="#FBEF97" x-large @click="save">리뷰등록</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    detail () {
      this.$swal.fire({
        icon: 'warning',
        title: '리뷰가 등록되지 않습니다.',
        text: '취소 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          location.href = '/detail'
        }
      })
    },
    save () {
      this.$swal.fire({
        icon: 'success',
        title: '리뷰가 등록되었습니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        location.href = '/detail'
      })
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem auto;
  width: 50rem;
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}

.table {
  width: 50rem;
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
textarea {
  width: 100%;
  min-height: 7rem;
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
