<template>
  <div class="outer">
    <p class="top">주문상세내역</p>

    <p class="group">주문 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주문번호</th>
          <td><input type="text" readonly :value="orderGroupInfo.id"></td>
          <th scope="col">주문일자</th>
          <td><input type="text" readonly v-if="orderGroupInfo.created_at" :value="orderGroupInfo.created_at.split('T')[0]"></td>
        </tr>
        <tr>
          <th scope="col">수취인</th>
            <td><input type="text" readonly :value="orderGroupInfo.depositor"></td>
            <th scope="col">주문처리상태</th>
            <td><input type="text" :value="orderGroupInfo.order_product_state" readonly></td>
          </tr>
      </tbody>
    </table>

    <p class="group">배송지 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="80%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주소</th>
          <td><input v-if="orderGroupInfo.address" type="text" readonly :value="orderGroupInfo.address + ' ' + orderGroupInfo.detail_address"></td>
        </tr>
        <tr>
          <th scope="col">요청사항</th>
          <td><input type="text" :value="orderGroupInfo.delivery_message" readonly></td>
        </tr>
      </tbody>
    </table>

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
          <td class="img"><div></div></td>
          <td>
            <strong class="itemName" v-if="productNameList[i]">{{productNameList[i]}}</strong>
          </td>
          <td>{{order.order_price}}원</td>
          <td>{{order.order_count}}</td>
          <td>{{order.order_count * order.order_price}}원</td>
        </tr>
      </tbody>
    </table>

    <p class="group">결제 정보</p>
    <table class="table">
      <colgroup>
        <col width="20%">
        <col width="30%">
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주문방법</th>
          <td><input type="text" readonly value="무통장입금"></td>
          <th scope="col">결제계좌</th>
          <td><input type="text" readonly :value="bank"></td>
        </tr>
        <tr>
          <th scope="col">총 주문금액</th>
            <td><input type="text" readonly :value="orderGroupInfo.total_price +'원'"></td>
            <th scope="col">예금자명</th>
            <td><input type="text" :value="orderGroupInfo.depositor" readonly></td>
          </tr>
      </tbody>
    </table>

    <div class="button">
      <v-btn color="#FBEF97" x-large to="/orderlist">주문목록</v-btn>
      <v-btn color="#D1CFC4" x-large @click="ordercancle">주문취소</v-btn>
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
      detailId: []
    }
  },
  methods: {
    ordercancle () {
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
          await this.$router.push('/orderlist')
        }
      })
    },
    orderDetail () {
      return axios.get(`http://localhost:8000/jewelry/orderGroup/${this.$store.state.order.orderId}/orderDetailInfo`)
        .then(res => {
          this.orderGroupInfo = res.data.data.order_group_response
          this.orderDetailInfo = res.data.data.order_group_response.order_detail_response_list

          for (let i = 0; i < this.orderDetailInfo.length; i++) {
            this.detailId.push(this.orderDetailInfo[i].id)
          }

          if (this.orderGroupInfo.order_product_state === 'BEFORE_BANK_TRANSFER') this.orderGroupInfo.order_product_state = '입금전'
          else if (this.orderGroupInfo.order_product_state === 'READY') this.orderGroupInfo.order_product_state = '배송준비중'
          else if (this.orderGroupInfo.order_product_state === 'SHIPPING') this.orderGroupInfo.order_product_state = '배송중'
          else if (this.orderGroupInfo.order_product_state === 'COMPLETE') this.orderGroupInfo.order_product_state = '배송완료'

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
  margin: 3rem auto;
  width: 53rem;
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}

.group {
  float: left;
  font-weight: 700;
}
.table {
  width: 850px;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  background-color: #fefff2;
  margin-bottom: 5rem;
}
input {
  text-align: center;
  width: 100% !important;
  font-size: 0.8rem;
  outline: none;
}
.table tr {
  border-bottom: 1.5px solid gray;
}
.table th {
  padding: 0.6rem 0;
  font-size: 0.85rem;
}

.info {
  margin: 1rem 0 3rem;
  border: 2px solid black;
  border-left: none;
  border-right: none;
  width: 850px;
}
.info thead {
  border-bottom: 1px solid black;
  background-color: #fefff2;
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
.img {
  width: 26%;
}
.img div {
  width: 90px;
  height: 90px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  margin-left: 60px;
}
.count,
.price {
  width: 15%;
  padding-top: 30px;
}
.content {
  width: 38%;
  padding-top: 30px;
}

.button button,
.button a {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}

</style>
