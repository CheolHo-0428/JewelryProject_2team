<template>
<div class="outer">
    <p>회원정보</p>
    <!-- content-->
    <div id="content">
        <!-- ID -->
        <div>
            <h3 class="join_title">
                <label for="id">아이디</label>
            </h3>
            <span class="box int_id">
                <input class="read" id="id" type="text" readonly value="happy">
                <span class="step_url">@naver.com</span>
            </span>
        </div>

        <!-- PW1 -->
        <div>
            <h3 class="join_title"><label for="pswd1">비밀번호</label></h3>
            <span class="box int_pass">
                <input type="text" id="pswd1" class="int" maxlength="20">
            </span>
        </div>

        <!-- PW2 -->
        <div>
            <h3 class="join_title"><label for="pswd2">비밀번호 확인</label></h3>
            <span class="box int_pass_check">
                <input type="text" id="pswd2" class="int" maxlength="20">
            </span>
        </div>

        <!-- NAME -->
        <div>
            <h3 class="join_title"><label for="name">이름</label></h3>
            <span class="box int_name">
                <input type="text" id="name" class="int read" readonly value="홍길동">
            </span>
        </div>

        <!-- EMAIL -->
        <div>
            <h3 class="join_title"><label for="email">이메일</label></h3>
            <span class="box int_email">
                <input type="text" id="email" class="int" value="happy@naver.com">
            </span>
        </div>

        <!-- MOBILE -->
        <div>
            <h3 class="join_title"><label for="phoneNo">휴대전화</label></h3>
            <div class="phone">
                <div class="box int_mobile">
                    <select id="mobile">
                        <option value="010" selected>010</option>
                        <option value="011">011</option>
                        <option value="016">016</option>
                        <option value="017">017</option>
                        <option value="019">019</option>
                    </select>
                </div>
                <div class="box int_mobile">
                    <input type="text" id="mobile" class="int phone" value="0000"/>
                </div>
                <div class="box int_mobile">
                    <input type="text" id="mobile" class="int phone" value="1111"/>
                </div>
            </div>
        </div>

        <!-- ADDRESS -->
        <div>
            <h3 class="join_title"><label for="phoneNo">주소</label></h3>
            <span class="box addr">
                <input type="tel" id="addr" class="int" value="80111">
            </span> <br>
            <span class="box addr">
                <input type="tel" id="addr" class="int" value="서울시 송파구 ㅇㅇ아파트 101동101호">
            </span>
        </div>

        <!-- BTN-->
        <div class="button">
          <button type="button" class="btn btn-light remove" @click="remove">회원탈퇴</button>
          <button type="button" class="btn btn-light" @click="mypage">취소</button>
          <button type="button" class="btn btn-secondary" @click="modify">회원정보수정</button>
        </div>
    </div>

</div>
</template>

<script>
export default {
  methods: {
    mypage () {
      location.href = '/mypage'
    },
    modify () {
      this.$swal.fire({
        icon: 'success',
        title: '회원정보가 수정되었습니다.',
        text: '마이페이지로 이동합니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        location.href = '/mypage'
      })
    },
    remove () {
      this.$swal.fire({
        title: '정말 탈퇴 하시겠습니까?',
        text: '탈퇴시 다시 되돌릴 수 없습니다.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#FE2E2E',
        cancelButtonColor: '#BDBDBD'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$swal.fire({
            icon: 'success',
            title: '탈퇴 되었습니다.',
            confirmButtonColor: '#CEF6CE'
          })
          location.href = '/'
        }
      })
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
    width: 33% !important;
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
    font-family: Dotum,'돋움',Helvetica,sans-serif;
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
.button {
  display: flex;
  justify-content: center;
  margin: 2rem 0;
}
.button button {
  border: 1px solid black;
  border-radius: 0;
  margin: 0 0.6rem;
  padding: 0.6vw 1.2vw;
  font-size: 1.2vw;
  font-weight: 700;
}
.button button:focus {
  box-shadow: none;
}
.remove {
  margin-right: 18vw !important;
}
</style>
