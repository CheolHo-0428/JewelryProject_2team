<template>
  <div class="header">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">GGULUCK</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="/">HOME</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/ring">RING</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/earrings">EARRINGS</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/bracelet">BRACELET</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/necklace">NECKLACE</a>
            </li>
            <li class="nav-item notice">
              <a class="nav-link" href="/notice">NOTICE</a>
            </li>
            <li class="nav-item signup">
              <a class="nav-link" href="/signup">SIGNUP</a>
            </li>
            <li class="nav-item login">
              <a class="nav-link" href="/login">LOGIN</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/mypage">MYPAGE</a>
            </li>
          </ul>
          <form class="d-flex">
            <input class="form-control me-2" type="search" placeholder="제품명 입력" aria-label="Search">
            <button class="search" type="submit">
              <span class="material-icons-outlined">search</span></button>
          </form>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.container-fluid {
  padding : 5vh 2rem;
  width: fit-content;
  margin: 0 auto;
}
.navbar-brand {
  padding-right: 4rem;
  font-size: 2rem;
  font-family: 'Bad Script', cursive;
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
.login::after{
  content: "|";
  float: right;
  display: block;
  margin: -1.8rem 0 0 4rem;
  font-size: 0.8rem;
  color: rgba(0,0,0,.55);;
}
.search {
  padding: 0.2rem 0.4rem 0 0.1rem;
  font-size: 0.7rem;
  outline: none;
  border: none;
  background-color: #f8f9fa;
  vertical-align: middle;
}
</style>
