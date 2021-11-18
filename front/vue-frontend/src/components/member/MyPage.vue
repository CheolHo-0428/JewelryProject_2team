<template>
  <div class="outer">
    <p class="top">마이페이지</p>
    <div class="boxs">
      <div @click="orderList">
        <p>ORDER</p>
        <p>주문내역조회</p>
        <p class="material-icons-outlined">list_alt</p>
        <p>
          주문하신 상품 내역을 <br />
          확인하실 수 있습니다
        </p>
      </div>
      <div @click="mypage(account)">
        <p>MODIFY</p>
        <p>회원정보수정</p>
        <p class="material-icons-outlined">settings</p>
        <p>
          고객님의 개인정보를 수정하고 <br />
          관리할 수 있습니다
        </p>
      </div>
      <div @click="cart">
        <p>CART</p>
        <p>장바구니</p>
        <p class="material-icons-outlined">shopping_cart</p>
        <p>
          장바구니에 담아두신 상품의 <br />
          목록을 확인합니다
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import User from '../../models/user'
export default {
  data () {
    return {
      user: new User('', '', '', '', '', '', '', '', ''),
      urlPage: this.$store.state.member.memberUrl,
      account: this.$store.state.auth.user.account
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  methods: {
    cart () {
      this.$router.push('/cart')
    },
    orderList () {
      this.$router.push('/orderlist')
    },
    mypage (account) {
      this.$store.commit('mypage', {account: account, urlPage: this.urlPage})
      this.$router.push('/modify')
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 6rem auto;
  width: 52rem;
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
}
.boxs {
  display: flex;
  width: 48rem;
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
