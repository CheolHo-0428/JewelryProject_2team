<template>
  <div class="outer">
    <p>회원목록</p>

    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="80%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">조건검색</th>
          <td>
            <form class="d-flex">
              <select name="member" class="op">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="name">고객명</option>
                <option value="num">회원번호</option>
              </select>
              <input class="form-control me-2" type="search" aria-label="Search">
              <button class="search" type="submit">
                <span class="material-icons-outlined">search</span>
              </button>
            </form>
          </td>
        </tr>
        <tr>
          <th scope="col">가입일</th>
          <td class="date">
            <b-form-datepicker id="example-datepicker" placeholder="Choose a date" class="mb-2"></b-form-datepicker>
            <span> &nbsp; ~ &nbsp;</span>
            <b-form-datepicker id="example-datepicker" placeholder="Choose a date" class="mb-2"></b-form-datepicker>
          </td>
        </tr>
      </tbody>
    </table>

    <table class="list">
      <colgroup>
        <col width="14%">
        <col width="10%">
        <col width="16%">
        <col width="16%">
        <col width="20%">
        <col width="12%">
        <col width="12%">
      </colgroup>

      <thead>
        <tr>
          <th>회원번호</th>
          <th>이름</th>
          <th>아이디</th>
          <th>전화번호</th>
          <th>이메일</th>
          <th></th>
          <th></th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="i in 15" :key="i">
            <td>1392184</td>
            <td>{{account}}</td>
            <td>happy</td>
            <td>010-0000-1111</td>
            <td>aaa@naver.com</td>
            <td class="button"><a href="/admember_">상세보기</a></td>
            <td class="button remove"><a @click="remove">회원삭제</a></td>
        </tr>
      </tbody>
    </table>

    <!-- pagination -->
    <div class="page">
      <div class="box">
        <a href="#" class="arrow">&laquo;</a>
        <a href="#" class="active">1</a>
        <a href="#">2</a>
        <a href="#">3</a>
        <a href="#">4</a>
        <a href="#">5</a>
        <a href="#">6</a>
        <a href="#">7</a>
        <a href="#">8</a>
        <a href="#" class="arrow">&raquo;</a>
      </div>
    </div>

  </div>
</template>

<script>
// import axios from 'axios'
// const url = 'http://localhost:8000/api/1'

export default {
  methods: {
    remove () {
      this.$swal.fire({
        icon: 'warning',
        title: '회원 삭제시 되돌릴 수 없습니다.',
        text: '회원목록에서 삭제하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$swal.fire({
            icon: 'success',
            title: '회원이 삭제되었습니다.',
            confirmButtonColor: '#CEF6CE'
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.outer {
  width: 950px;
  margin: 4rem auto;
}
.table {
  width: 950px;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  background-color: #fefff2;
  margin-bottom: 4rem;
}
.table th,
.date span {
  font-weight: 700;
  font-size: 1.1rem;
  vertical-align: middle;
}
.date span {
  padding-top: 4px;
}
input {
  width: 100% !important;
  font-size: 0.8rem;
}
tr {
  border-bottom: 1.5px solid gray;
}
th {
  text-align: center;
  padding: 0.6rem 0;
  font-size: 0.85rem;
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
.form-control {
  margin-left: 1rem;
  width: 14rem !important;
}
.form-control:focus {
  box-shadow: none;
  border: 1px solid black;
}
.material-icons-outlined {
  vertical-align: middle;
  margin-left: 1rem;
}
#example-datepicker__outer_ {
  margin: 0 !important;
  width: fit-content !important;
  text-align: center;
}
.date {
  display: flex;
}

table {
  width: 950px;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}
.list th,
.list td {
  border-bottom: 1px solid #333333;
  padding: 0.6rem;
  text-align: center;
}
.list thead {
  background-color:#fefff2;
}

.button a {
  text-decoration: none;
  color: black;
  border: 1px solid black;
  border-radius: 10px;
  padding: 0.2rem 0.4rem;
  font-size: 0.8rem;
  font-weight: 700;
  background-color: #fefff2;
  box-shadow: 1px 0.5px 0 rgb(0,0,0,0.5);
}
.remove a {
  background-color: #eeeff0 !important;
  cursor: pointer;
}
.button a:active {
  box-shadow: 1px 0px 0 rgb(0,0,0,0.5);
  position: relative;
  top: 0.5px;
}

.page {
  display: inline-block;
  width: 950px;
  margin-top: 2rem;
}
.box {
  margin: 0 auto;
  width: fit-content;
}
.page a {
  color: black;
  float: left;
  padding: 4px 12px;
  text-decoration: none;
  border-radius:50%;
  margin: 0 0.5rem;
}
.page a.active {
  background-color: #fde8b9;
  color: white;
}
.page a:hover:not(.active) {
  background-color: silver;
}
</style>
