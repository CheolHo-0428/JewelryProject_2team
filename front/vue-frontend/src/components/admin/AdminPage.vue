<template>
  <div class="outer">
    <p class="top">관리페이지</p>
    <div class="boxs">
      <div @click="admember">
        <p>MEMBER</p>
        <p>회원관리</p>
        <p class="material-icons-outlined">manage_accounts</p>
        <p>회원 목록과 정보를 <br> 확인하실 수 있습니다</p>
      </div>
      <div @click="adproduct">
        <p>PRODUCT</p>
        <p>상품관리</p>
        <p class="material-icons-outlined">inventory_2</p>
        <p>상품재고를 확인하고 <br> 관리할 수 있습니다</p>
      </div>
      <div @click="adorder">
        <p>ORDER</p>
        <p>주문관리</p>
        <p class="material-icons-outlined">list_alt</p>
        <p>고객들의 주문을 확인하고 <br> 관리할 수 있습니다</p>
      </div>
      <!-- <div @click="adsales">
        <p>SALES</p>
        <p>매출관리</p>
        <p class="material-icons-outlined">insights</p>
        <p>원하시는 날짜의 매출을 <br> 확인하실 수 있습니다</p>
      </div> -->
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    admember () {
      this.$router.push('/admember')
    },
    adproduct () {
      this.$router.push('/adproduct')
    },
    adorder () {
      this.$router.push('/adorder')
    },
    adsales () {
      this.$router.push('/adsales')
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 6rem auto;
  width: 62rem;
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
}
.boxs {
  display: flex;
  width: 62rem;
  margin: 6rem auto;
  margin-bottom: 12rem;
}
.boxs div {
  width: 33%;
  padding: 0 2.2rem;
  cursor: pointer;
}
.boxs div p {
  margin-bottom: 0;
}
.boxs div p:nth-child(1) {
  font-size: 1.3rem;
}
.boxs div p:nth-child(2) {
  font-size: 1rem;
  color: rgb(255, 73, 73);
}
.boxs div p:nth-child(3) {
  font-size: 3rem;
}
.boxs div p:nth-child(4) {
  font-size: 0.8rem;
}

</style>
