<template>
  <div class="outer">
    <p class="top">주문상세내역</p>

    <p class="group">주문 정보</p>
    <table class="table1">
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
    <table class="table1">
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

    <p class="group">결제 정보</p>
    <table v-if="this.orderGroupInfo.pay_method === 'BANK_TRANSFER'" class="table1 t2">
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
    <table v-if="this.orderGroupInfo.pay_method === 'CARD'" class="table1 t2">
      <colgroup>
        <col width="20%">
        <col width="30%">
        <col width="20%">
        <col width="30%">
      </colgroup>
      <tbody>
        <tr>
          <th scope="col">주문방법</th>
          <td><input type="text" readonly value="카드"></td>
          <!-- <th scope="col">결제계좌</th>
          <td><input type="text" readonly :value="bank"></td> -->
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
      <router-link to="/orderlist" class="btn btn-dark">주문목록</router-link>
      <a v-if="orderGroupInfo.order_product_state === '입금전'" @click="ordercancle" class="btn btn-dark btn2">주문취소</a>
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
      stored_thumbnail: [],
      orderCount: [],
      itemId: []
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
          await this.$router.push('/orderlist')
        }
      })
    },
    orderDetail () {
      this.stored_thumbnail = []
      return axios.get(`http://localhost:8000/jewelry/orderGroup/${this.$store.state.order.orderId}/orderDetailInfo`)
        .then(async res => {
          this.orderGroupInfo = res.data.data.order_group_response
          this.orderDetailInfo = res.data.data.order_group_response.order_detail_response_list

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
.table1 {
  width: 850px;
  background-color: #fff;
  margin-bottom: 5rem;
}
input {
  text-align: center;
  width: 100% !important;
  font-size: 0.8rem;
  outline: none;
}
.table1 tr {
  border-bottom: 0.5px solid #000;
}
.table1 th {
  padding: 0.6rem 0;
  font-size: 0.85rem;
}
.table1 tbody {
  border-top: 1.4px solid black;
  border-bottom: 1.2px solid black;
}
.t2 tbody {
  border-bottom: 1.3px solid black !important;
}

.info {
  margin: 1rem 0 3rem;
  border: 1.3px solid #000;
  border-left: none;
  border-right: none;
  width: 850px;
}
.info thead {
  border-bottom: 0.5px solid #000;
  border-top: 1.2px solid #000;
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
  border-bottom: 0.5px solid #000;
}

.boxs {
  width: 53rem;
  margin-bottom: 4rem;
}
.box {
  display: flex;
  margin: 2rem auto;
  border-bottom: 0.5px solid #000;
}
.img, .content {
  height: 100px;
}
.img {
  width: 26%;
}
img {
  width: 90px;
  height: 90px;
  background-size: cover;
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

.btn {
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
.btn {
  background: #000;
  color: #fff;
  padding: 0.8rem 0;
  width: 180px;
  margin: 0 1rem;
  font-size: 1.2rem;
}
.btn2 {
  background: #fff;
  color: #000;
  border-color: #000;
  padding: 0.8rem 0;
  width: 180px;
  margin: 0 1rem;
  font-size: 1.2rem;
}
</style>
