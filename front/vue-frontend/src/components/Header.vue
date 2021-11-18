<template>
  <div class="header">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid headerTop">
        <router-link to="/" class="navbar-brand">GGULUCK</router-link>
        <form class="d-flex">
            <input class="form-control me-2" @keypress.enter="search" type="search" v-model="keyword" aria-label="Search"/>
            <div class="search" @click="search">
              <span class="material-icons-outlined">search</span>
            </div>
          </form>
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li v-if="!currentUser" class="signup">
              <router-link to="/signup" class="nav-link top">SIGNUP</router-link>
            </li>
            <li v-if="!currentUser" class="login">
              <router-link to="/login" class="nav-link top">LOGIN</router-link>
            </li>
            <li v-if="currentUser" class="mypage">
              <router-link to="/mypage" class="nav-link"><span class="material-icons-outlined">perm_identity</span></router-link>
            </li>
            <li v-if="currentUser && findRole" class="adminpage">
              <router-link to="/adminpage" class="nav-link"><span class="material-icons-outlined">manage_accounts</span></router-link>
            </li>
            <li v-if="currentUser" class="name">
              <router-link to="/mypage" class="nav-link top"
                >{{ currentUser.account }}님</router-link
              >
            </li>
            <li v-if="currentUser" class="logout top">
              <a class="nav-link" href @click.prevent="logOut">LOGOUT</a>
            </li>
          </ul>
      </div>
    </nav>
    <nav class="headerB navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
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
              <router-link to="/earrings" class="nav-link"
                >EARRINGS</router-link
              >
            </li>
            <li class="nav-item">
              <router-link to="/ring" class="nav-link">RING</router-link>
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
          </ul>
        </div>
      </div>
    </nav>
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
      this.$router.push('/searcheditem').catch(() => {
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
.headerTop .navbar-nav,
.headerTop form {
  margin-left: 2.4rem;
}
.top {
  margin-top: 4px;
}
.material-icons-outlined {
  font-size: 30px !important;
}
a {
  cursor: pointer;
}
.headerB .container-fluid {
  padding: 1vh 2rem !important;
}
.container-fluid {
  padding: 2vh 2rem;
  width: fit-content;
  margin: 0 auto;
}
.headerTop .container-fluid {
  padding: 3vh 2rem 0 !important;
}
.navbar-brand {
  font-size: 2rem;
  font-family: "Bad Script", cursive;
  font-weight: 700;
  text-decoration-line: underline;
  text-underline-position: under;
}
.form-control {
  font-size: 0.9rem;
  min-width: 12rem;
  width: 16rem;
  height: 2.6rem;
  border-radius: 2rem;
  border: 2px solid rgba(146, 146, 146, 0.55);
  outline: none;
}
.form-control:focus {
  box-shadow: none;
  border: 1px solid #ced4da;
}
.nav-link {
  font-size: 0.9rem;
  font-family: 'Barlow', sans-serif;
}
.nav-link:hover {
  transition: .3s;
}
.nav-item {
  padding: 0.3rem 2rem 0.1rem;
}
.nav-item:hover {
  background-color: rgba(245, 245, 245, 0.918);
  border-radius: 4px;
  transition: .3s;
}
.notice {
  margin-left: 1rem;
}
.signup::after {
  content: "|";
  float: right;
  display: block;
  margin: -1.8rem 0 0 4rem;
  font-size: 0.8rem;
  color: rgba(0, 0, 0, 0.55);
}
.search {
  padding: 0.6rem 0.4rem 0 0.1rem;
  outline: none;
  border: none;
  background-color: #f8f9fa;
  cursor: pointer;
}
.search span {
  font-size: 1.7rem;
}
</style>
