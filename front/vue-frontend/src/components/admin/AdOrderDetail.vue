<template>
  <div class="outer">
    <p class="top">주문상세</p>

    <p class="group">주문상품 정보</p> <br>
    <table class="info">
      <colgroup>
        <col style="width:19%">
        <col style="width:38%">
        <col style="width:14%">
        <col style="width:11%">
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
        <tr v-for="(order, i) in orderDetailInfo" :key="i">
          <td class="img"><img :src="stored_thumbnail[i]" /></td>
          <td>
            <strong class="itemName" v-if="productNameList[i]">{{productNameList[i]}}</strong>
          </td>
          <td>{{order.order_price}}원</td>
          <td>{{order.order_count}}</td>
          <td>{{order.order_count * order.order_price}}원</td>
        </tr>
      </tbody>
    </table>

    <p class="group">주문 정보<span class="modifyText">( * 수정가능 )</span></p>
    <table class="table t2">
      <colgroup>
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">회원번호</th>
          <td><input type="text" :value="orderGroupInfo.member_id" style="outline:none;" readonly></td>
          <th scope="col">주문일자</th>
          <td><input type="text" v-if="orderGroupInfo.created_at" :value="orderGroupInfo.created_at.split('T')[0]" style="outline:none;" readonly></td>
        </tr>
        <tr v-if="orderGroupInfo.pay_method === 'BANK_TRANSFER'">
          <th scope="col">결제방법</th>
          <td><input type="text" value="무통장입금" style="outline:none;" readonly></td>
          <th scope="col">결제계좌</th>
          <td><input type="text" :value="this.bank" style="outline:none;" readonly></td>
        </tr>
        <tr v-if="orderGroupInfo.pay_method === 'CARD'">
          <th scope="col">결제방법</th>
          <td><input type="text" value="카드" style="outline:none; text-align:center;" readonly></td>
          <!-- <th scope="col">결제계좌</th>
          <td><input type="text" :value="this.bank" style="outline:none;" readonly></td> -->
        </tr>
        <tr>
          <th scope="col">입금자명</th>
          <td><input type="text" :value="orderGroupInfo.depositor" style="outline:none;" readonly></td>
          <th scope="col">전화번호*</th>
          <td><input type="text" v-model="phone"></td>
        </tr>
        <tr>
          <th scope="col">총결제금액</th>
          <td><input type="text" :value="orderGroupInfo.total_price + '원'" style="outline:none;" readonly></td>
          <th scope="col">주문처리상태*</th>
          <td>
            <select name="order" class="op" @change="optionChange($event)">
              <option value="BEFORE_BANK_TRANSFER" :selected="orderGroupInfo.order_product_state === 'order_product_state'">입금전</option>
              <option value="READY" :selected="orderGroupInfo.order_product_state === 'READY'">배송준비중</option>
              <option value="SHIPPING" :selected="orderGroupInfo.order_product_state === 'SHIPPING'">배송중</option>
              <option value="COMPLETE" :selected="orderGroupInfo.order_product_state === 'COMPLETE'">배송완료</option>
              <option value="CARD" :selected="orderGroupInfo.order_product_state === 'CARD'">카드결제</option>
            </select>
          </td>
        </tr>
      </tbody>
    </table>

    <p class="group">배송 정보<span class="modifyText">( 수정가능 )</span></p>
    <table class="table t3">
      <colgroup>
        <col width="10%">
        <col width="20%">
        <col width="15%">
        <col width="20%">
        <col width="15%">
        <col width="20%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">수령인</th>
          <td><input type="text" v-model="recipient"></td>
          <th scope="col">우편번호</th>
          <td><input type="text" v-model="postCode"></td>
          <th scope="col">주소변경</th>
          <td class="changeBtn"><a @click="showApi">click</a></td>
        </tr>
        <tr>
          <th scope="col">주소</th>
          <td colspan="3"><input type="text" v-model="address"></td>
          <th scope="col">상세주소</th>
          <td><input type="text" v-model="detailAddress"></td>
        </tr>
        <tr>
          <th scope="col">요청사항</th>
          <td colspan="5"><input type="text" v-model="request"></td>
        </tr>
      </tbody>
    </table>

    <div class="button">
      <v-btn color="#000" style="color:white;" x-large @click="list">주문목록</v-btn>
      <v-btn color="#000" style="color:white;" x-large @click="cancle">주문취소</v-btn>
      <v-btn color="#000" style="color:white;" x-large @click="save">저장</v-btn>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      orderGroupInfo: {},
      orderDetailInfo: [],
      bank: '',
      productNameList: [],
      detailId: [],
      postCode: 0,
      address: '',
      detailAddress: '',
      phone: '',
      recipient: '',
      request: '',
      option: '',
      stored_thumbnail: [],
      orderCount: [],
      itemId: []
    }
  },
  methods: {
    optionChange (event) {
      this.option = event.target.value
    },
    cancle () {
      this.$swal.fire({
        icon: 'warning',
        title: '주문 취소시 되돌릴 수 없습니다.',
        text: '주문을 취소하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then(async (result) => {
        if (result.isConfirmed) {
          for (let i = 0; i < this.detailId.length; i++) {
            await axios
              .delete(`http://localhost:8000/jewelry/orderDetail/${this.detailId[i]}`, {
              })
              .then(function (response) {
                console.log(response)
              })
              .catch(function (error) {
                console.log(error)
              })
            axios
              .put('http://localhost:8000/jewelry/item/update/stockplus', {
                id: this.itemId[i],
                stock: this.orderCount[i]
              })
              .then((res) => {
                console.log(res)
              })
              .catch((error) => {
                console.log(error)
              })
          }
          await axios
            .delete(`http://localhost:8000/jewelry/orderGroup/${this.orderGroupInfo.id}`, {
            })
            .then(function (response) {
              console.log(response)
            })
            .catch(function (error) {
              console.log(error)
            })
          await this.$router.push('/adorder')
        }
      })
    },
    list () {
      this.$swal.fire({
        icon: 'warning',
        title: '수정사항이 저장되지 않습니다.',
        text: '목록으로 돌아가시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push('/adorder')
        }
      })
    },
    save () {
      axios
        .put('http://localhost:8000/jewelry/orderGroup/update', {
          delivery_message: this.request,
          resipient: this.recipient,
          order_product_state: this.option,
          post_code: this.postCode,
          address: this.address,
          detail_address: this.detailAddress,
          phone: this.phone,
          id: this.orderGroupInfo.id
        })
        .then((res) => {
          console.log(res)
          this.$swal.fire({
            icon: 'success',
            title: '주문정보가 수정되었습니다.',
            text: '목록으로 이동합니다.',
            confirmButtonColor: '#CEF6CE'
          }).then(() => {
            this.$router.push('/adorder')
          })
        })
        .catch((error) => {
          console.log(error)
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
    orderDetail () {
      this.stored_thumbnail = []
      return axios.get(`http://localhost:8000/jewelry/orderGroup/${this.$store.state.order.orderId}/orderDetailInfo`)
        .then(async res => {
          this.orderGroupInfo = res.data.data.order_group_response
          this.orderDetailInfo = res.data.data.order_group_response.order_detail_response_list

          this.postCode = this.orderGroupInfo.post_code
          this.address = this.orderGroupInfo.address
          this.detailAddress = this.orderGroupInfo.detail_address
          this.phone = this.orderGroupInfo.phone
          this.recipient = this.orderGroupInfo.resipient
          this.request = this.orderGroupInfo.delivery_message
          this.option = this.orderGroupInfo.order_product_state

          for (let i = 0; i < this.orderDetailInfo.length; i++) {
            this.detailId.push(this.orderDetailInfo[i].id)
            this.itemId.push(this.orderDetailInfo[i].item_id)
            this.orderCount.push(this.orderDetailInfo[i].order_count)

            await axios.get('http://localhost:8000/jewelry/item/' + this.orderDetailInfo[i].item_id + '/itemInfo')
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

          if (this.orderGroupInfo.pay_account === 'IBK') this.bank = '기업은행000-00000000-00'
          else this.bank = '우리은행111-11111111-11'

          this.productName()
        })
        .catch(err => {
          console.log(err)
        })
    },
    async productName () {
      for (let i = 0; i < this.orderDetailInfo.length; i++) {
        const id = this.orderDetailInfo[i].item_id
        await axios.get(`http://localhost:8000/jewelry/item/${id}`)
          .then(res => {
            this.productNameList.push(res.data.data.name)
          })
          .catch(err => {
            console.log(err)
          })
      }
    }
  },
  created () {
    this.orderDetail()
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem auto;
  width: 53rem;
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}

.modifyText {
  font-size: 0.7rem;
}

.group {
  float: left;
  margin-bottom: 2rem;
  font-weight: 700;
}
.boxs {
  width: 53rem;
  margin-bottom: 4rem;
}
.box {
  display: flex;
  margin: 2rem auto;
  border-bottom: 0.15rem solid black;;
}
.img, .content {
  height: 100px;
}
.table > :not(:first-child) {
  border: 1px solid gray;
}
.img {
  width: 20%;
}
img {
  width: 90px;
  height: 90px;
  background-size: cover;
}
.count,
.price {
  width: 16%;
  padding-top: 30px;
}
.content {
  width: 16%;
  padding-top: 30px;
}
.op {
  border: 1px solid gray;
  width: fit-content;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.2rem 0.8rem;
  background-color: white;
  text-align: center;
  color:gray;
}

.table {
  width: 850px;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  margin-bottom: 5rem;
}
input {
  text-align: center;
  width: 100% !important;
  font-size: 0.8rem;
}

.table tr {
  border-bottom: 1.5px solid gray;
  border-right: 1px solid black;
}
.table th {
  padding: 0.6rem 0;
  font-size: 0.85rem;
  background-color: #e7e7e7;
  border-right: 1px solid black;
  border-left: 1px solid black;
  vertical-align: middle;
}

.changeBtn a {
  text-decoration: none;
  color: black;
  border: 1px solid black;
  border-radius: 10px;
  padding: 0.2rem 1.5rem;
  font-size: 0.8rem;
  font-weight: 700;
  background-color: #555;
  box-shadow: 1px 0.5px 0 rgb(0,0,0,0.5);
  cursor: pointer;
}
.changeBtn a:active {
  box-shadow: 1px 0px 0 rgb(0,0,0,0.5);
  position: relative;
  top: 0.5px;
}

.info {
  margin: 1rem 0 3rem;
  border: 2px solid black;
  border-left: none;
  border-right: none;
  width: 850px;
}
.info thead {
  border-bottom: 1px solid #e7e7e7;
  background-color: #e7e7e7;
}
.info thead th {
  padding: 1rem 0;
  vertical-align: middle;
}
.info tbody td {
  padding: 1rem 0;
  vertical-align: middle;
}
.info tbody tr {
  border-bottom: 0.5px solid black;
}

.button button {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
.button {
  margin-bottom: 2rem;
}

</style>
