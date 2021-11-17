<template>
  <div>
    <footer class="text-center text-lg-start bg-light text-muted">
      <!-- Section: Links  -->
      <section class="footer2">
        <div class="container text-center text-md-start mt-5">
          <div class="row mt-3">
            <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
              <h6 class="text-uppercase fw-bold mb-4">
                <i class="fas fa-gem me-3"></i>GGULUCK
              </h6>
              <p class="explane">
                악세사리를 판매하는 쇼핑몰 입니다 :)
              </p>
            </div>

            <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
              <h6 class="text-uppercase fw-bold mb-4">
                Category
              </h6>
              <p>
                <router-link to="/ring" class="text-reset explane">Ring</router-link>
                <router-link to="/earrings" class="c1 text-reset explane">Earrings</router-link>
              </p>
              <p>
                <router-link to="/bracelet" class="text-reset explane">Bracelet</router-link>
                <router-link to="/necklace" class="c2 text-reset explane">Necklace</router-link>
              </p>
            </div>

            <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
              <h6 class="text-uppercase fw-bold mb-4">
                Member
              </h6>
              <p class="member" style="min-width:13rem;">
                <router-link to="https://github.com/CheolHo-0428" target="_blank" class="mem text-reset explane"><i class="fab fa-github"></i> 조철호</router-link>
                &nbsp;<router-link to="https://github.com/Lee-YongJun" target="_blank" class="mem text-reset explane"><i class="fab fa-github"></i> 이용준</router-link>
                &nbsp;<router-link to="https://github.com/eunnjin" target="_blank" class="mem text-reset explane"><i class="fab fa-github"></i> 박은진</router-link>
              </p>
              <h6 class="text-uppercase fw-bold mb-4">
                TEAM
              </h6>
              <p>
                <router-link to="https://github.com/CheolHo-0428/JewelryProject_2team" target="_blank" class="mem text-reset explane"><i class="fab fa-github"></i> JewelryProject</router-link>
              </p>
            </div>

            <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
              <h6 class="text-uppercase fw-bold mb-4">
                Contact
              </h6>
              <p class="explane"><i class="fas fa-home me-3"></i>서울특별시 송파구 중대로 135</p>
              <p class="explane">
                <i class="fas fa-envelope me-3"></i>
                info@example.com
              </p>
              <p class="explane"><i class="fas fa-phone me-3"></i> + 01 234 567 88</p>
            </div>
          </div>
        </div>
      </section>
      <!-- Copyright -->
      <div class="text-center p-4 footer3" style="background-color: rgba(0, 0, 0, 0.05);">
        Copyright ©
        <router-link class="text-reset fw-bold" to="/">gguluck</router-link>
      </div>
    </footer>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.footer2 {
  margin: -1.4rem 0rem -0.2rem 0rem;
}
.footer2 .container {
  margin-top: 1rem !important;
}
.member {
  margin-bottom: 0.7rem !important;
}
.mem {
  text-decoration: none;
}
.c1 {
  padding-left: 1.9rem;
}
.c2 {
  padding-left: 0.7rem;
}
.footer3 {
  padding: 0.8rem !important;
  font-size: 0.8rem;
}
.explane {
  font-size: 0.8rem;
}
.mx-auto P {
  margin-bottom: 0.4rem;
}
.mb-4 {
  margin-bottom: 0.2rem !important;
}
h6 {
  font-size: 0.9rem;
}
.mt-3 {
  margin-top: 0.4rem !important;
}
</style>
