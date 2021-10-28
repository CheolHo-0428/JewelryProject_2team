<template>
    <div class="outer">
        <p>아이디 찾기</p>
        <!-- content-->
        <div id="content">
            <div>
                <h3 class="join_title">
                    <label for="name">이름</label>
                </h3>
                <span class="box int_id">
                    <input type="text" id="name" class="int">
                </span>
            </div>
            <div>
                <h3 class="join_title"><label for="email">이메일</label></h3>
                <span class="box int_pass">
                    <input type="text" id="email" class="int">
                </span>
            </div>

            <div class="btn_area">
                <button type="button" id="btnJoin" @click="search">
                    <span>아이디 찾기</span>
                </button>
            </div>
            <div class="search">
                <a href="/searchpw">비밀번호 찾기</a>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  methods: {
    search () {
      location.href = '/findid'
    }
  }
}
</script>

<style scoped>
.outer {
    margin: 5rem 0 3rem;
}
p {
    font-weight: 700;
    font-size: 1.5rem;
    margin: 0 auto;
    width: 660px;
}
#content {
    width: 660px;
    margin-top: 3rem;
    margin: 0 auto;
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
    width: 100%;
}

/* 버튼 */
.btn_area {
    margin: 30px 0 20px;
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
.search {
    margin-top: 1rem;
    margin: 0 auto;
    width: fit-content;
}
.search a {
    color: black;
}
</style>
