<template>
<div class="outer">
    <p>회원가입</p>
    <!-- content-->
    <div id="content">

        <!-- NAME -->
        <div>
            <h3 class="join_title"><label for="name">이름</label></h3>
            <span class="box int_name">
                <input type="text" id="name" class="int" maxlength="20">
            </span>
        </div>

        <!-- ID -->
        <div>
            <h3 class="join_title">
                <label for="id">아이디</label>
            </h3>
            <span class="box int_id">
                <input type="text" id="id" class="int" maxlength="20">
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
            <h3 class="join_title"><label for="pswd2">비밀번호 재확인</label></h3>
            <span class="box int_pass_check">
                <input type="text" id="pswd2" class="int" maxlength="20">
            </span>
        </div>

        <!-- EMAIL -->
        <div>
            <h3 class="join_title"><label for="email">본인확인 이메일<span class="optional">(선택)</span></label></h3>
            <span class="box int_email">
                <input type="text" id="email" class="int" maxlength="100" placeholder="선택입력">
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
                    <input type="text" id="mobile" class="int phone"/>
                </div>
                <div class="box int_mobile">
                    <input type="text" id="mobile" class="int phone"/>
                </div>
            </div>
        </div>

        <!-- ADDRESS -->
        <div>
            <h3 class="join_title"><label for="phoneNo">주소</label></h3>
            <span class="box addr">
                <input type="tel" id="addr" class="int" maxlength="8" placeholder="우편번호 입력">
            </span> <br>
            <span class="box addr">
                <input type="tel" id="addr" class="int" maxlength="16" placeholder="주소 입력">
            </span>
        </div>

        <!-- JOIN BTN-->
        <div class="btn_area">
            <button type="button" id="btnJoin">
                <span>가입하기</span>
            </button>
        </div>
    </div>

</div>
</template>

<script>
export default {

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
    font-family: Dotum,'돋움',Helvetica,sans-serif;
}
#btnJoin:hover {
  background-color: black;
}
</style>
