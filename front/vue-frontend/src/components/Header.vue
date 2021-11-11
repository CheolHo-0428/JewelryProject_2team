<template>
  <div class="header">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand">GGULUCK</router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link to="/" class="nav-link" aria-current="page"
                >HOME</router-link
              >
            </li>
            <li class="nav-item">
              <router-link to="/ring" class="nav-link">RING</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/earrings" class="nav-link"
                >EARRINGS</router-link
              >
            </li>
            <li class="nav-item">
              <router-link to="/bracelet" class="nav-link"
                >BRACELET</router-link
              >
            </li>
            <li class="nav-item">
              <router-link to="/necklace" class="nav-link"
                >NECKLACE</router-link
              >
            </li>
            <li class="nav-item notice">
              <a class="nav-link" @click="notice">NOTICE</a>
            </li>
            <li v-if="!currentUser" class="nav-item signup">
              <router-link to="/signup" class="nav-link">SIGNUP</router-link>
            </li>
            <li v-if="!currentUser" class="nav-item login">
              <router-link to="/login" class="nav-link">LOGIN</router-link>
            </li>
            <li v-if="currentUser" class="nav-item mypage">
              <router-link to="/mypage" class="nav-link">MYPAGE</router-link>
            </li>
            <li v-if="currentUser && findRole" class="nav-item adminpage">
              <router-link to="/adminpage" class="nav-link">MANAGEMENT</router-link>
            </li>
            <li v-if="currentUser" class="nav-item name">
              <router-link to="/mypage" class="nav-link"
                >{{ currentUser.account }}님</router-link
              >
            </li>
            <li v-if="currentUser" class="nav-item logout">
              <a class="nav-link" href @click.prevent="logOut">LOGOUT</a>
            </li>
          </ul>
          <form class="d-flex">
            <input class="form-control me-2" @keypress.enter="search" type="search" placeholder="제품명 입력" v-model="keyword" aria-label="Search"/>
            <div class="search" @click="search">
              <span class="material-icons-outlined">search</span>
            </div>
          </form>
        </div>
      </div>
    </nav>
    <!-- <button @click="requestPay">결제하기</button> -->
  </div>
</template>
<script>
export default {
  data () {
    return {
      keyword: ''
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    findRole () {
      if (this.currentUser) {
        return this.currentUser.roles.includes('ROLE_ADMIN')
      }
      return false
    }
  },
  methods: {
    // requestPay: function () {
    //   var IMP = window.IMP // 생략 가능
    //   IMP.init('{imp73794280}') // 예: imp00000000
    //   // IMP.request_pay(param, callback) 결제창 호출
    //   IMP.request_pay({ // param
    //     pg: 'html5_inicis',
    //     pay_method: 'card',
    //     merchant_uid: 'ORD20180131-0000011',
    //     name: '노르웨이 회전 의자',
    //     amount: 64900,
    //     buyer_email: 'gildong@gmail.com',
    //     buyer_name: '홍길동',
    //     buyer_tel: '010-4242-4242',
    //     buyer_addr: '서울특별시 강남구 신사동',
    //     buyer_postcode: '01181'
    //   }, rsp => { // callback
    //     if (rsp.success) {
    //       // 결제 성공 시 로직,
    //     } else {
    //       // 결제 실패 시 로직,
    //     }
    //   })
    // },
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$swal.fire({
        position: 'center',
        icon: 'success',
        title: '로그아웃에 성공하셨습니다',
        showConfirmButton: false,
        timer: 1500,
        footer: 'GGULUCK의 쇼핑몰을 이용하실 고객은 로그인을 부탁드립니다.'
      })
      this.$router.push('/login')
    },
    search () {
      this.$store.commit('searchItem', this.keyword)
      this.$router.push('/searchedItem').catch(() => {
        this.$router.go()
      })
      this.keyword = ''
    },
    notice () {
      this.$store.commit('noticeReset')
      this.$router.push('/notice')
    }
  }
}
</script>

<style scoped>
a {
  cursor: pointer;
}
.container-fluid {
  padding: 5vh 2rem;
  width: fit-content;
  margin: 0 auto;
}
.navbar-brand {
  padding-right: 4rem;
  font-size: 2rem;
  font-family: "Bad Script", cursive;
  font-weight: 700;
  text-decoration-line: underline;
  text-underline-position: under;
}
.form-control {
  font-size: 0.7rem;
  min-width: 12rem;
  height: 1.8rem;
  border-radius: 2rem;
  border: none;
  outline: none;
}
.form-control:focus {
  box-shadow: none;
  border: 1px solid #ced4da;
}
.navbar-nav {
  margin-right: 5rem !important;
}
.nav-link {
  font-size: 0.9rem;
}
.notice {
  padding: 0 1.2rem;
}
ul.navbar-nav {
  margin-right: 2rem !important;
}
.signup::after,
.login::after {
  content: "|";
  float: right;
  display: block;
  margin: -1.8rem 0 0 4rem;
  font-size: 0.8rem;
  color: rgba(0, 0, 0, 0.55);
}
.search {
  padding: 0.2rem 0.4rem 0 0.1rem;
  font-size: 0.7rem;
  outline: none;
  border: none;
  background-color: #f8f9fa;
  vertical-align: middle;
  cursor: pointer;
}
</style>
