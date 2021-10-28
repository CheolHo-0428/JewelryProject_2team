<template>
  <div class="outer">
    <p class="top">주문내역</p>
    <p class="text">주문번호를 클릭하시면 해당 주문에 대한 상세내역을 확인하실 수 있습니다.</p>
    <table>
      <colgroup>
      <col width="150"/><col width="100"/><col width="450"/><col width="140"/><col width="150"/>
      </colgroup>

      <thead>
        <tr>
          <th>주문일자</th>
          <th>주문번호</th>
          <th>주문상품</th>
          <th>주문금액</th>
          <th>주문처리상태</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="i in 4" :key="i">
            <td class="tdDate">2021-10-23</td>
            <td class="tdNo" @click="detail">324382492</td>
            <td class="tdTitle">다이아 반지 외 2개</td>
            <td class="tdPay">12500원</td>
            <td class="tdState">배송완료</td>
        </tr>
      </tbody>
    </table>

    <!-- pagination -->
    <div class="page">
      <div class="box">
        <a href="#" class="arrow">&laquo;</a>
        <a href="#" class="active">1</a>
        <a href="#" class="arrow">&raquo;</a>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  methods: {
    detail () {
      location.href = '/orderdetail'
    }
  }
}
</script>

<style scoped>
.outer {
  width: 900px;
  margin: 4rem auto;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 0.2rem;
}
p.text {
  font-size: 0.8rem;
  margin-bottom: 2rem;
}
th, td {
  border-bottom: 1px solid #333333;
  padding: 1rem;
  text-align: center;
}
thead {
  background-color:#fefff2;
}
.tdNo{
  cursor: pointer;
}
.page {
  display: inline-block;
  width: 900px;
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
