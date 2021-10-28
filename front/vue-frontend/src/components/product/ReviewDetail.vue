<template>
<div id="reg">
  <p>리뷰</p>

  <div class="AddWrap">
    <form>
      <table class="tbAdd">
        <colgroup>
          <col width="15%" />
          <col width="*" />
        </colgroup>
        <tr>
          <th>제목</th>
          <td><input type="text" v-model="subject" ref="subject" readonly /></td>
        </tr>
        <tr>
          <th>사진</th>
          <td><div class="img"></div></td>
        </tr>
        <tr>
          <th>내용</th>
          <td><textarea v-model="cont" ref="cont" readonly></textarea></td>
        </tr>
      </table>
    </form>
  </div>

  <div class="btnWrap">
    <a @click="List" class="btn">목록</a>
  </div>
</div>
</template>

<script>
export default {
  data () {
    return {
      subject: '제목입니다.',
      cont: '리뷰 내용'
    }
  },
  methods: {
    List () {
      location.href = '/detail'
    }
  }
}
</script>

<style scoped>
p {
  font-weight: 700;
  font-size: 1.5rem;
  margin: 3rem 0;
}
#reg {
  width: 800px;
  margin: 0 auto;
  text-align: center;
  color: #2c3e50;
}
.img {
  width: 200px;
  height: 200px;
  margin: 1rem 0;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
}
input {
  outline: none;
}
.tbAdd{border-top:1px solid #888;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0;}
.tbAdd td{padding:10px 10px; box-sizing:border-box;}
.tbAdd td input{width:100%; min-height:30px; box-sizing:border-box; padding:0 10px;}
.tbAdd td textarea{width:100%; min-height:200px; padding:10px; box-sizing:border-box;}
.btnWrap{text-align:center; margin:30px 0;}
.btnWrap a{margin:0 10px;}
table{width:100%; border-collapse:collapse;}
a{text-decoration:none;}
.btn{padding:10px; background:#34445c; color:#fff;}
</style>
