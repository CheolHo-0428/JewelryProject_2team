<template>
  <div class="outer">
    <p>매출관리</p>

    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="80%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">분석방법</th>
          <td>
            <form class="d-flex">
              <select name="member" class="op">
                <option value="" selected>-- 선택하세요 --</option>
                <option value="1">년별</option>
                <option value="2">월별</option>
                <option value="3">일별</option>
                <option value="4">요일별</option>
              </select>
              <button class="search" type="submit">
                <span class="material-icons-outlined">search</span>
              </button>
            </form>
          </td>
        </tr>
        <tr>
          <th scope="col">기간</th>
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
        <col width="25%">
        <col width="25%">
        <col width="25%">
        <col width="25%">
      </colgroup>

      <thead>
        <tr>
          <th>요일</th>
          <th>주문건수</th>
          <th>매출액</th>
          <th>비율</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="day in days" :key="day">
            <td>{{day}}</td>
            <td>3</td>
            <td>100000원</td>
            <td>11%</td>
        </tr>
      </tbody>
    </table>

  </div>
</template>

<script>
export default {
  data () {
    return {
      days: ['월', '화', '수', '목', '금', '토', '일']
    }
  }
}
</script>

<style scoped>
.outer {
  width: 900px;
  margin: 4rem auto;
  margin-bottom: 8rem;
}
.table {
  width: 900px;
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
  width: 900px;
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
}
.button a:active {
  box-shadow: 1px 0px 0 rgb(0,0,0,0.5);
  position: relative;
  top: 0.5px;
}

</style>
