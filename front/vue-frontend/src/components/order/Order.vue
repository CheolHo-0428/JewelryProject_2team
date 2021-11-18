<template>
  <div class="outer">
    <p>주문/결제</p>
    <table class="info" v-if="!this.$store.state.order.isCart">
      <colgroup>
        <col style="width:19%">
        <col style="width:33%">
        <col style="width:18%">
        <col style="width:12%">
        <col style="width:18%">
      </colgroup>
      <thead>
        <tr>
          <th scope="col">이미지</th>
          <th scope="col">상품정보</th>
          <th scope="col">판매가</th>
          <th scope="col">수량</th>
          <th scope="col">합계</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="img"><img :src="thumbnail" /></td>
          <td>
            <strong class="itemName">{{this.$store.state.order.name}}</strong>
          </td>
          <td>{{this.$store.state.order.price}}원</td>
          <td>{{this.$store.state.order.count}}</td>
          <td>{{this.$store.state.order.count * this.$store.state.order.price}}원</td>
        </tr>
      </tbody>
    </table>

    <table class="info" v-if="this.$store.state.order.isCart">
      <colgroup>
        <col style="width:19%">
        <col style="width:33%">
        <col style="width:18%">
        <col style="width:12%">
        <col style="width:18%">
      </colgroup>
      <thead>
        <tr>
          <th scope="col">이미지</th>
          <th scope="col">상품정보</th>
          <th scope="col">판매가</th>
          <th scope="col">수량</th>
          <th scope="col">합계</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(c, i) in $store.state.order.cname" :key="i">
          <td class="img"><img :src="stored_thumbnail[i]" /></td>
          <td>
            <strong class="itemName">{{$store.state.order.cname[i]}}</strong>
          </td>
          <td>{{$store.state.order.cprice[i]}}원</td>
          <td>{{$store.state.order.ccount[i]}}</td>
          <td>{{$store.state.order.ccount[i] * $store.state.order.cprice[i]}}원</td>
        </tr>
      </tbody>
    </table>

    <p class="group">주문 정보</p>
    <form>
      <p>
        <label for="name">수령인<span class="essential">*</span></label>
        <span>
          <input
            v-model="recipient"
            v-validate="'required'"
            type="text"
            id="name"
            class="name recipient"
            minlength="3"
            maxlength="20"
            autocomplete="no"
          />
        </span>
      </p>
      <p>
        <label>휴대폰<span class="essential">*</span></label>
        <span class="phone">
          <input class="phone1" v-model="phone1" type="text" v-validate="'required'"> - <input v-model="phone2" type="text" v-validate="'required'" class="phone2"> - <input v-model="phone3" class="phone3" type="text" v-validate="'required'">
        </span>
      </p>
      <p>
        <label>배송주소<span class="essential">*</span></label>
        <span class="addr">
          <input class="post" type="text" placeholder="우편번호 입력" v-model="postCode" :disabled="'disabled'">
          <a style="font-size:18px;" class="btn btn-secondary" @click="showApi">우편번호 검색</a> <br>
          <input type="text" class="text2 basic" v-model="address" placeholder="주소 입력" :disabled="'disabled'"><br>
          <input type="text" class="text2 detail" autocomplete="off" placeholder="상세 주소 입력" v-validate="'required'" v-model="detailAddress">
        </span>
      </p>
      <p>
        <label>예금자명<span class="essential">*</span></label>
        <span><input class="name depositor" type="text" v-model="depositor" v-validate="'required'"></span>
      </p>
      <p>
        <label>배송메시지</label>
        <span><input class="request" type="text" placeholder="요청사항을 입력하세요." v-model="request"></span>
      </p>
      <p>
        <label>결제방식</label>
        <span>
          <select id="pay_method" class="bank"  @change="optionChangePay()">
            <option value = "무통장입금" selected>무통장입금</option>
            <option value = "카드">카드</option>
          </select>
        </span>
        <!-- <span><input class="pay" type="text" value="무통장입금" readonly></span> -->
      </p>
      <p class="pay_bank">
        <label>결제은행<span class="essential">*</span></label>
        <span>
          <select name="bank" class="bank" @change="optionChange($event)">
            <option value="" selected>-- 선택하세요 --</option>
            <option value="IBK">기업은행000-00000000-00</option>
            <option value="WOORI">우리은행111-11111111-11</option>
          </select>
        </span>
      </p>
    </form>

    <div class="bottom" v-if="!this.$store.state.order.isCart">
      <div class="calc">
          <table>
            <colgroup>
              <col width="45%"/><col width="10%"/><col width="45%"/>
            </colgroup>
            <th>총 상품금액</th>
            <th>+</th>
            <th>배송비</th>
            <tr>
              <td>{{this.$store.state.order.count * this.$store.state.order.price}}원</td>
              <td>+</td>
              <td>2500원</td>
            </tr>
          </table>
      </div>
      <div class="total">
        <table>
          <th>총 주문금액</th>
          <tr>
            <td>{{this.$store.state.order.count * this.$store.state.order.price + 2500}}원</td>
          </tr>
        </table>
      </div>
    </div>

    <div class="bottom" v-if="this.$store.state.order.isCart">
      <div class="calc">
          <table>
            <colgroup>
              <col width="45%"/><col width="10%"/><col width="45%"/>
            </colgroup>
            <th>총 상품금액</th>
            <th>+</th>
            <th>배송비</th>
            <tr>
              <td>{{this.totalCart}}원</td>
              <td>+</td>
              <td>2500원</td>
            </tr>
          </table>
      </div>
      <div class="total">
        <table>
          <th>총 주문금액</th>
          <tr>
            <td>{{this.totalCart + 2500}}원</td>
          </tr>
        </table>
      </div>
    </div>

    <div class="button">
      <a class="btn2 btn-dark" @click="order">구매하기</a>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      phone1: '',
      phone2: '',
      phone3: '',
      recipient: '',
      depositor: '',
      postCode: '',
      address: '',
      detailAddress: '',
      request: '',
      option: '',
      totalCart: 0,
      totalCount: 0,
      stored_thumbnail: [],
      thumbnail: '',
      cardOk: false
    }
  },
  methods: {
    nCart () {
      if (!this.$store.state.order.isCart) {
        this.thumbnail = ''
        axios.get('http://localhost:8000/jewelry/item/' + this.$store.state.order.itemId + '/itemInfo')
          .then(res => {
            let tmp = res.data.data.item_response.image_file_response_list.findIndex(
              (i) => i.delegate_thumbnail === 'YES'
            )
            if (res.data.data.item_response.image_file_response_list.length !== 0) {
              if (tmp === -1) this.thumbnail = res.data.data.item_response.image_file_response_list[0].stored_file_name
              else this.thumbnail = res.data.data.item_response.image_file_response_list[tmp].stored_file_name
            }
          })
      }
    },
    async cart () {
      this.stored_thumbnail = []
      let n = this.$store.state.order.ccount.length
      for (let i = 0; i < n; i++) {
        this.totalCart += this.$store.state.order.ccount[i] * this.$store.state.order.cprice[i]
        this.totalCount += this.$store.state.order.ccount[i]

        await axios.get('http://localhost:8000/jewelry/item/' + this.$store.state.order.citemId[i] + '/itemInfo')
          .then(res => {
            let tmp = res.data.data.item_response.image_file_response_list.findIndex(
              (i) => i.delegate_thumbnail === 'YES'
            )
            if (res.data.data.item_response.image_file_response_list.length !== 0) {
              if (tmp === -1) this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[0].stored_file_name)
              else this.stored_thumbnail.push(res.data.data.item_response.image_file_response_list[tmp].stored_file_name)
            }
          })
      }
    },
    member () {
      return axios.get(`http://localhost:8000/jewelry/auth/mypage?account=${this.$store.state.auth.user.account}`)
        .then(res => {
          this.phone1 = res.data.data.phone.substring(0, 3)
          this.phone2 = res.data.data.phone.substring(4, 8)
          this.phone3 = res.data.data.phone.substring(9, 13)
          this.depositor = res.data.data.name
          this.recipient = res.data.data.name
          this.postCode = res.data.data.post_code
          this.address = res.data.data.address
          this.detailAddress = res.data.data.detail_address
        })
        .catch(err => {
          console.log(err)
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
          this.postCode = data.zonecode
          this.address = fullRoadAddr
          this.detailAddress = ''
        }
      }).open()
    },
    optionChange (event) {
      this.option = event.target.value
    },
    optionChangePay () {
      const payMethod = $('#pay_method option:selected').val()
      if (payMethod === '카드') {
        $('.pay_bank').hide()
        this.cardOk = 'true'
      } else if (payMethod === '무통장입금') {
        $('.pay_bank').show()
        this.cardOk = 'false'
      }
    },
    order () {
      this.$validator.validate().then((isValid) => {
        if (!this.cardOk && isValid && this.option !== '') {
          if (!this.$store.state.order.isCart) {
            axios({
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              url: 'http://localhost:8000/jewelry/orderGroup/reg',
              data: JSON.stringify({
                resipient: this.recipient,
                depositor: this.depositor,
                post_code: this.postCode,
                address: this.address,
                detail_address: this.detailAddress,
                delivery_message: this.request,
                pay_account: this.option,
                phone: this.phone1 + '-' + this.phone2 + '-' + this.phone3,
                total_price: this.$store.state.order.count * this.$store.state.order.price + 2500,
                total_count: this.$store.state.order.count,
                member_id: this.$store.state.auth.user.id
              })
            }).then(res => {
              console.log(res)
              axios
                .put('http://localhost:8000/jewelry/item/update/stockminus', {
                  id: this.$store.state.order.itemId,
                  stock: this.$store.state.order.count
                })
                .then((res) => {
                  console.log(res)
                })
                .catch((error) => {
                  console.log(error)
                })
              this.$router.push('/order_')
            }).catch(error => {
              console.log(error)
            })
          } else {
            axios({
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              url: 'http://localhost:8000/jewelry/orderGroup/reg',
              data: JSON.stringify({
                resipient: this.recipient,
                depositor: this.depositor,
                post_code: this.postCode,
                address: this.address,
                detail_address: this.detailAddress,
                delivery_message: this.request,
                pay_account: this.option,
                phone: this.phone1 + '-' + this.phone2 + '-' + this.phone3,
                total_price: this.totalCart + 2500,
                total_count: this.totalCount,
                member_id: this.$store.state.auth.user.id
              })
            }).then(res => {
              console.log(res)
              let n = this.$store.state.order.cartId.length
              for (let i = 0; i < n; i++) {
                axios
                  .put('http://localhost:8000/jewelry/item/update/stockminus', {
                    id: this.$store.state.order.citemId[i],
                    stock: this.$store.state.order.ccount[i]
                  })
                  .then((res) => {
                    console.log(res)
                  })
                  .catch((error) => {
                    console.log(error)
                  })
              }
              this.$router.push('/order_')
            }).catch(error => {
              console.log(error)
            })
          }
        } else if (this.cardOk && isValid) {
          if (!this.$store.state.order.isCart) {
            axios({
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              url: 'http://localhost:8000/jewelry/orderGroup/reg',
              data: JSON.stringify({
                resipient: this.recipient,
                depositor: this.depositor,
                post_code: this.postCode,
                address: this.address,
                detail_address: this.detailAddress,
                delivery_message: this.request,
                pay_account: null,
                phone: this.phone1 + '-' + this.phone2 + '-' + this.phone3,
                total_price: this.$store.state.order.count * this.$store.state.order.price + 2500,
                total_count: this.$store.state.order.count,
                member_id: this.$store.state.auth.user.id
              })
            }).then(res => {
              console.log(res)
              axios
                .put('http://localhost:8000/jewelry/item/update/stockminus', {
                  id: this.$store.state.order.itemId,
                  stock: this.$store.state.order.count
                })
                .then((res) => {
                  console.log(res)
                })
                .catch((error) => {
                  console.log(error)
                })
              this.$router.push('/order_')
            }).catch(error => {
              console.log(error)
            })
          } else {
            axios({
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              url: 'http://localhost:8000/jewelry/orderGroup/reg',
              data: JSON.stringify({
                resipient: this.recipient,
                depositor: this.depositor,
                post_code: this.postCode,
                address: this.address,
                detail_address: this.detailAddress,
                delivery_message: this.request,
                pay_account: null,
                phone: this.phone1 + '-' + this.phone2 + '-' + this.phone3,
                total_price: this.totalCart + 2500,
                total_count: this.totalCount,
                member_id: this.$store.state.auth.user.id
              })
            }).then(res => {
              console.log(res)
              let n = this.$store.state.order.cartId.length
              for (let i = 0; i < n; i++) {
                axios
                  .put('http://localhost:8000/jewelry/item/update/stockminus', {
                    id: this.$store.state.order.citemId[i],
                    stock: this.$store.state.order.ccount[i]
                  })
                  .then((res) => {
                    console.log(res)
                  })
                  .catch((error) => {
                    console.log(error)
                  })
              }
              this.$router.push('/order_')
            }).catch(error => {
              console.log(error)
            })
          }
        } else {
          this.$swal.fire({
            icon: 'warning',
            title: '입력사항이 입력되지 않았습니다.',
            showConfirmButton: true,
            confirmButtonColor: '#F8BB86',
            footer: '필수입력란을 모두 입력해주세요.'
          })
          this.successful = false
        }
      })
    }
  },
  created () {
    this.cart()
    this.member()
    this.nCart()
  }
}
</script>

<style scoped>
.phone1{
  width:180px;
  height:43px;
  color:gray;
}
.phone2{
  width:180px;
  height:43px;
  color:gray;
}
.phone3{
  width:180px;
  height:43px;
  color:gray;
}
.recipient {
  width:602px;
  height:43px;
  color:gray;
}
.outer {
  margin: 3rem auto;
  width: 52rem;
  text-align: center;
}
p {
  font-weight: 700;
  font-size: 1.5rem;
}

.info {
  margin-top: 3rem;
  border-left: none;
  border-right: none;
}
thead {
  border-bottom: 0.5px solid black;
  border-top: 0.5px solid black;
}
thead th {
  padding: 1rem 0;
  vertical-align: middle;
}
tbody td {
  padding: 1rem 0;
  vertical-align: middle;
}
.info tr {
  border-bottom: 0.5px solid black;
}

.bottom {
  display: flex;
  margin: 3rem;
  border-bottom: 1px solid black;
}
img {
  width: 90px;
  height: 90px;
  background-size: cover;
  margin: 0 auto;
}

.bottom {
  margin-top: 5rem;
  border-top: 1px solid black;
  padding: 36px 0;
}
.calc {
  width: 60%;
  font-weight: 700;
}
.total {
  width: 40%;
  font-weight: 700;
}
table {
  width: 100%;
}
.bottom th {
  padding-bottom: 0.7rem;
}
.bottom td {
  color: #747272;
}
.button {
  display: flex;
  justify-content: center;
  margin-top: 2rem;
}
.btn2 {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 700;
  background: transparent;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  border: 1px solid transparent;
  text-transform: uppercase;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -ms-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}
.btn2 {
  background: #000;
  color: #fff;
  padding: 0.8rem 0;
  width: 180px;
  font-size: 1.2rem;
  text-decoration: none;
}

.group {
  font-size: 24px;
  margin: 4rem auto 1rem;
  border-bottom: 1px solid black;
  width: fit-content;
  margin-bottom: 30px;
}
label{
  width:100px;
  display: table-cell;
  padding: 10px 5px;
  font-size: 1rem;
  vertical-align: middle;
}
form{
  display:table;
  border-collapse: collapse;
  margin: 0 auto;
  width: 46rem;
}
span{
  display: table-cell;
  padding: 5px 0px 5px 20px;
  vertical-align: middle;
}
input{
  width:100px;
  height:20px;
  margin:2px;
}
.depositor {
  width:602px;
  height:43px;
  color:gray;
  border:1px solid #e7e7e7;
}
.essential {
  padding: 0;
  display: inline;
  color: red;
}
.post {
  width: 300px;
  border: 1px solid gray;
  height: 43px;
  color:gray;
}
.text2{
  font-size: 0.8rem;
}
.text2.detail {
    border: 1px solid gray;
    /* padding: 0.8rem 0; */
    width: 602px;
    /* margin-left: -15rem; */
    /* font-size: 0.9rem; */
    height: 43px;
    color: gray;
}
.text2.basic {
    width: 602px;
    color: gray;
    border: 1px solid gray;
    height: 43px;
}
.name,
.phone input,
.request {
  padding: 0.8rem 0;
  border: 1px solid gray;
  font-size: 0.8rem;
  text-align: center;
}
.addr a {
  cursor: pointer;
  width: 300px;
  float: right;
  height: 46px;
  line-height: 30px;
}
.addr input {
  padding-left: 15px;
  font-size: 1rem;
}
.request {
  font-size: 0.8rem;
  padding: 1rem 0.2rem;
  height: 43px;
  width: 602px;
}
.pay {
  font-size: 0.9rem;
  outline: none;
  color: #747272;
}
.bank {
  border: 1px solid gray;
  font-size: 0.8rem;
  padding: 0.2rem;
  text-align: center;
  width: 602px;
  height: 43px;
  color: gray;
}
</style>
