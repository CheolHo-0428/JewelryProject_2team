<template>
  <div class="outer">
    <p>회원가입</p>
    <!-- content-->
    <div id="content">
      <!-- NAME -->
      <form name="form" @submit.prevent="handleRegister">
        <div v-if="!successful">
          <h3 class="join_title"><label for="name">이름</label></h3>
          <span class="box int_name">
            <input
              placeholder="이름은 3~20자 사이로 입력해주세요"
              v-model="user.name"
              v-validate="'required'"
              type="text"
              name="name"
              id="name"
              class="int"
              minlength="3"
              maxlength="20"
              autocomplete="no"
            />
          </span>
          <div v-if="submitted && errors.has('name')" class="errorMessage">
            <!-- {{ errors.first("name") }} -->
            이름은 3~20자 사이로 입력해주세요
          </div>
          <span>{{ errors[0] }}</span>
        </div>
        <!-- ID -->
        <div v-if="!successful">
          <h3 class="join_title">
            <label for="id">아이디</label>
          </h3>
          <span class="box int_id">
            <input
              placeholder="아이디는 3~20자 사이로 입력해주세요"
              v-model="user.account"
              v-validate="'required'"
              type="text"
              name="account"
              id="id"
              class="int"
              minlength="3"
              maxlength="20"
              autocomplete="off"
            />
          </span>
          <div v-if="submitted && errors.has('account')" class="errorMessage">
            아이디는 3~20자 사이로 입력해주세요
          </div>
        </div>

        <!-- PW1 -->
        <div v-if="!successful">
          <h3 class="join_title"><label for="pswd1">비밀번호</label></h3>
          <span class="box int_pass">
            <input
              autocomplete="off"
              ref="password"
              placeholder="비밀번호는 6~20자로 입력하여 주세요"
              v-model="user.password"
              v-validate="'required|'"
              minlength="6"
              maxlength="20"
              name="password"
              type="password"
              id="pswd1"
              class="int"
            />
          </span>
          <div v-if="submitted && errors.has('password')" class="errorMessage">
            비밀번호는 6~20자로 입력하여 주세요.
          </div>
        </div>

        <!-- PW2 -->
        <div v-if="!successful">
          <h3 class="join_title"><label for="pswd2">비밀번호 확인</label></h3>
          <span class="box int_pass_check">
            <input
              autocomplete="off"
              type="password"
              id="pswd2"
              v-validate="'required|confirmed:password'"
              name="confirm_password"
              class="int"
              minlength="6"
              maxlength="20"
              placeholder="비밀번호 확인은 필수입니다."
            />
          </span>
          <div v-if="errors.has('confirm_password')" class="errorMessage">
            위의 비밀번호와 일치하지 않습니다.
          </div>
        </div>

        <!-- EMAIL -->
        <div v-if="!successful">
          <h3 class="join_title">
            <label for="email">이메일</label>
          </h3>
          <span class="box int_email">
            <input
              autocomplete="off"
              v-model="user.email"
              v-validate="'required'"
              maxlength="30"
              type="email"
              id="email"
              name="email"
              class="int"
              placeholder="이메일 형식에 맞추어 입력해주세요"
            />
          </span>
          <div
            v-if="submitted && errors.has('email')"
            class="errorMessage"
            role="alert"
          >
            이메일형식에 맞게 입력해주세요 (ex)test@naver.com
          </div>
        </div>

        <!-- MOBILE -->
        <div v-if="!successful">
          <h3 class="join_title"><label for="phoneNo">휴대전화</label></h3>
          <div class="phone">
            <div class="w_33">
              <select
                class="form-select ht51"
                id="mobile"
                v-validate="'required'"
                v-model="phone1"
                name="phone1"
              >
                <option class="option_disable" disabled value="">
                  휴대폰번호를 고르세요
                </option>
                <option value="010">010</option>
                <option value="011">011</option>
                <option value="016">016</option>
                <option value="017">017</option>
                <option value="019">019</option>
              </select>
            </div>
            <div class="box int_mobile">
              <input
                autocomplete="no"
                v-model="phone2"
                v-validate="'numeric'"
                maxlength="4"
                name="phone"
                type="tel"
                class="int phone"
              />
            </div>
            <div class="box int_mobile">
              <input
                autocomplete="no"
                v-model="phone3"
                v-validate="'numeric'"
                name="phone"
                maxlength="4"
                type="tel"
                class="int phone"
              />
            </div>
          </div>
          <div class="error_div">
            <div
              v-if="submitted && errors.has('phone1')"
              class="errorMessage clear"
              role="alert"
            >
              휴대폰 번호를 골라주세요
            </div>
            <div
              v-if="submitted && errors.has('phone')"
              class="errorMessage clear"
              role="alert"
            >
              번호를 숫자로 입력해주세요
            </div>
          </div>
        </div>
        <!-- ADDRESS -->
        <div v-if="!successful">
          <h3 class="join_title"><label for="phoneNo">주소</label></h3>
          <div class="addr">
            <div class="box addr1">
              <input
                :disabled="'disabled'"
                name="postCode"
                type="text"
                id="addr"
                class="int"
                maxlength="8"
                placeholder="우편번호 입력"
                v-model="user.postCode"
              />
            </div>
            <div class="form-group btn_area">
              <button @click="showApi" type="button" id="btnAddr">
                <span>우편번호 검색</span>
              </button>
            </div>
            <span class="box addr2">
              <input
                name="address"
                :disabled="'disabled'"
                type="tel"
                id="addr"
                class="int"
                maxlength="16"
                placeholder="주소 입력"
                v-model="user.address"
              />
            </span>
            <span class="box addr3">
              <input
                autocomplete="off"
                name="detailAddress"
                type="tel"
                id="addr"
                class="int"
                maxlength="16"
                placeholder="상세 주소 입력"
                v-validate="'required'"
                v-model="user.detailAddress"
              />
            </span>
            <div
              v-if="submitted && errors.has('detailAddress')"
              class="errorMessage clear"
              role="alert"
            >
              상세주소를 입력해주세요
            </div>
          </div>
        </div>
        <div class="form-group btn_area">
          <button id="btnJoin" value="가입하기">
            <span>가입하기</span>
          </button>
        </div>

        <!-- JOIN BTN-->
      </form>
    </div>
  </div>
</template>

<script>
import User from '../../models/user'

export default {
  name: 'Register',
  data () {
    return {
      user: new User('', '', '', '', '', '', '', ''),
      submitted: false,
      successful: false,
      message: '',
      phone1: '',
      phone2: '',
      phone3: ''
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    }
  },
  mounted () {
    if (this.loggedIn) {
      this.$router.push('/')
    }
  },
  methods: {
    handleRegister () {
      this.message = ''
      this.submitted = true
      this.$validator.validate().then((isValid) => {
        if (isValid) {
          this.user.phone = this.phone1 + '-' + this.phone2 + '-' + this.phone3
          this.$store.dispatch('auth/register', this.user).then(
            (data) => {
              this.successful = true
              this.$swal.fire({
                position: 'center',
                icon: 'success',
                title: '회원가입에 성공하셨습니다.',
                timer: 3000,
                showConfirmButton: true,
                confirmButtonColor: '#a5dc86',
                footer: 'GGULUCK의 쇼핑몰에 오신것을 환영합니다!'
              })
              this.$router.push('/')
            },
            (error) => {
              this.message =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString()
              if (
                this.message ===
                'could not execute statement; SQL [n/a]; constraint [CLINUT.UKD7QQV98P5470L9VJP4J6YTOMD]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement'
              ) {
                this.$swal.fire({
                  position: 'center',
                  icon: 'success',
                  title: '회원가입에 성공하셨습니다.',
                  showConfirmButton: true,
                  confirmButtonColor: '#a5dc86',
                  timer: 3000,
                  footer: 'GGULUCK의 쇼핑몰에 오신것을 환영합니다!'
                })
                this.$router.push('/')
              } else {
                this.$swal.fire({
                  icon: 'error',
                  title: '회원가입에 실패하셨습니다',
                  showConfirmButton: true,
                  confirmButtonColor: '#F27474',
                  footer: this.message
                })
              }
              this.successful = false
            }
          )
        } else {
          this.$swal.fire({
            icon: 'warning',
            title: '입력사항이 입력되지 않거나<br> 양식에 맞지 않습니다.',
            showConfirmButton: true,
            confirmButtonColor: '#F8BB86',
            footer: '아래 빨간색으로 체크된 잘못된 부분을 확인해주세요'
          })
          this.successful = false
        }
      })
    },
    showApi () {
      new window.daum.Postcode({
        oncomplete: (data) => {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
          // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          let fullRoadAddr = data.roadAddress
          // 도로명 주소 변수
          let extraRoadAddr = ''
          // 도로명 조합형 주소 변수
          // 법정동명이 있을 경우 추가한다. (법정리는 제외)
          // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
          if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname
          } // 건물명이 있고, 공동주택일 경우 추가한다.
          if (data.buildingName !== '' && data.apartment === 'Y') {
            extraRoadAddr +=
              extraRoadAddr !== ''
                ? ', ' + data.buildingName
                : data.buildingName
          }
          // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
          if (extraRoadAddr !== '') {
            extraRoadAddr = ' (' + extraRoadAddr + ')'
          }
          if (fullRoadAddr !== '') {
            fullRoadAddr += extraRoadAddr
          }
          this.user.postCode = data.zonecode
          this.user.address = fullRoadAddr
        }
      }).open()
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 5rem 0 0 28%;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
}
#content {
  width: 45vw;
  margin-top: 3rem;
}
h3 {
  margin: 19px 0 8px;
  font-size: 14px;
  font-weight: 700;
}
.box {
  display: block;
  width: 100%;
  height: 51px;
  border: solid 1px #dadada;
  padding: 10px 14px 10px 14px;
  box-sizing: border-box;
  background: #fff;
  position: relative;
}
.int_mobile {
  width: 33.3% !important;
}
.phone {
  display: flex;
}
.int {
  display: block;
  position: relative;
  width: 100%;
  height: 29px;
  border: none;
  background: #fff;
  font-size: 15px;
}
input {
  font-family: Dotum, "돋움", Helvetica, sans-serif;
  outline: none;
}
select {
  width: 100% !important;
}
.box.int_id {
  padding-right: 110px;
}
.box.int_pass {
  padding-right: 40px;
}
.box.int_pass_check {
  padding-right: 40px;
}
.step_url {
  /*@naver.com*/
  position: absolute;
  top: 16px;
  right: 13px;
  font-size: 15px;
  color: #8e8e8e;
}

/* 버튼 */
.btn_area {
  margin: 30px 0 91px;
}
#btnJoin {
  width: 100%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: #fff;
  background-color: #b4b9be;
  font-size: 20px;
  font-weight: 700;
  font-family: Dotum, "돋움", Helvetica, sans-serif;
}
#btnJoin:hover {
  background-color: black;
}
.errorMessage {
  font-size: 12px;
  line-height: 42px;
  font-weight: bold;
  color: #ff003e;
  vertical-align: middle;
  float: right;
}
input::placeholder {
  color: rgb(201, 196, 196);
  font-size: 15px;
  font-weight: bold;
}
.ht51 {
  height: 51px;
}
.w_33 {
  width: 33.3%;
}
.option_disable {
  color: rgb(201, 196, 196);
  font-weight: bold;
}
.form-select {
  color: black;
  font-weight: bold;
}
.int_email,
.int_id,
.phone,
.int_name {
  color: black;
  font-weight: bold;
}
.clear {
  clear: both;
}
#btnAddr {
  width: 50%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: #fff;
  background-color: #b4b9be;
  font-weight: 700;
  font-family: Dotum, "돋움", Helvetica, sans-serif;
  font-size: 14px;
  height: 51px;
  line-height: 1px;
  border-radius: 5px;
}
#btnAddr:hover {
  background-color: black;
}
.addr {
  width: 100%;
}
.addr1 {
  color: black;
  font-weight: bold;
  width: 50%;
  float: left;
  height: 51px;
  border-radius: 5px;
}
.addr2 {
  color: black;
  font-weight: bold;
  margin-top: -85px;
  margin-bottom: 7px;
}
.addr3 {
  color: black;
  font-weight: bold;
}
.form-select {
  outline: none !important;
  box-shadow: none;
  border-color: #b4b9be;
}
</style>
