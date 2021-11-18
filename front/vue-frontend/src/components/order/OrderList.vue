<template>
  <div class="outer">
    <p class="top">주문내역</p>
    <p class="text">주문내역을 클릭하시면 해당 주문에 대한 상세내역을 확인하실 수 있습니다.</p>
    <table>
      <colgroup>
      <col width="150"/><col width="110"/><col width="440"/><col width="140"/><col width="150"/>
      </colgroup>

      <thead>
        <tr>
          <th>주문일자</th>
          <th>주문번호</th>
          <th>주문상품</th>
          <th>주문금액</th>
          <th>주문처리상태</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="(order, i) in orders" :key="i">
            <td class="tdDate">{{order.created_at.split('T')[0]}}</td>
            <td class="tdNo" @click="detail(order.id)">{{order.id}}</td>
            <td class="tdTitle" @click="detail(order.id)" v-if="productNameList[i]">{{productNameList[i]}} 포함 총 {{productInfo[i].count}}개의 상품</td>
            <td class="tdPay">{{order.total_price}}</td>
            <td class="tdState">{{order.order_product_state}}</td>
        </tr>
      </tbody>
    </table>

    <!-- pagination -->
    <div class="page">
      <div class="box">
        <a @click="prevPage" class="arrow pageNum" v-if="prev">&laquo;</a>
        <a @click="changePage(p)" v-for="(p, i) in page_list" class="pageNum" :key="i" :class="{'active' : page == p}">{{p}}</a>
        <a @click="nextPage" class="arrow pageNum" v-if="next">&raquo;</a>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      searchPage: 0,
      urlPage: `http://localhost:8000/jewelry/orderGroup/${this.$store.state.auth.user.id}/searchMember`,
      originUrl: `http://localhost:8000/jewelry/orderGroup/${this.$store.state.auth.user.id}/searchMember`,
      orders: [],
      end: 0,
      next: false,
      page: 0,
      prev: false,
      start: 0,
      page_list: [],
      total_pages: 0,
      total_elements: 0,
      productInfo: [],
      productNameList: []
    }
  },
  methods: {
    detail (id) {
      this.$store.commit('changeOrderId', id)
      this.$router.push('/orderdetail')
    },
    changePage (page) {
      this.urlPage = this.originUrl + `?page=${page - 1}`
      this.orderList()
    },
    nextPage () {
      this.urlPage = this.originUrl + `?page=${this.end}`
      this.orderList()
    },
    prevPage () {
      this.urlPage = this.originUrl + `?page=${this.start - 2}`
      this.orderList()
    },
    orderList () {
      return axios.get(this.urlPage)
        .then(res => {
          this.orders = []
          this.orders = res.data.data

          this.member_id = res.data.data[0].member_id

          for (let i = 0; i < this.orders.length; i++) {
            if (this.orders[i].order_product_state === 'BEFORE_BANK_TRANSFER') this.orders[i].order_product_state = '입금전'
            else if (this.orders[i].order_product_state === 'READY') this.orders[i].order_product_state = '배송준비중'
            else if (this.orders[i].order_product_state === 'SHIPPING') this.orders[i].order_product_state = '배송중'
            else if (this.orders[i].order_product_state === 'COMPLETE') this.orders[i].order_product_state = '배송완료'
          }

          this.page = res.data.pagination.current_page + 1
          this.total_pages = res.data.pagination.total_pages
          this.total_elements = res.data.pagination.total_elements

          let tmpEnd = parseInt(Math.ceil(this.page / 5.0) * 5)
          this.start = tmpEnd - 4
          this.prev = this.start > 1
          this.next = this.total_pages > tmpEnd
          this.end = this.total_pages > tmpEnd ? tmpEnd : this.total_pages

          this.page_list.length = 0
          for (let i = this.start; i <= this.end; i++) {
            this.page_list.push(i)
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    product () {
      return axios.get(`http://localhost:8000/jewelry/member/${this.$store.state.auth.user.id}/orderGroupInfo`)
        .then(res => {
          let orderGroup = res.data.data.member_response.order_group_list

          for (let i = 0; i < orderGroup.length; i++) {
            this.productInfo.push(
              {id: res.data.data.member_response.order_group_list[i].order_detail_response_list[0].item_id, count: res.data.data.member_response.order_group_list[i].order_detail_response_list.length}
            )
          }
          this.productName()
        })
        .catch(err => {
          console.log(err)
        })
    },
    async productName () {
      for (let i = 0; i < this.productInfo.length; i++) {
        const id = this.productInfo[i].id
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
    this.orderList()
    this.product()
  }
}
</script>

<style scoped>
.outer {
  width: 900px;
  margin: 4rem auto;
  min-height: 50vh;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 0.2rem;
}
p.text {
  font-size: 0.8rem;
  margin-bottom: 2rem;
}
th, td {
  border-bottom: 1px solid #333333;
  padding: 1rem;
  text-align: center;
}
thead {
  background-color:#e7e7e7;
}
.tdNo,
.tdTitle {
  cursor: pointer;
}

.page {
  display: inline-block;
  width: 900px;
  margin-top: 2rem;
}
.box {
  margin: 0 auto;
  width: fit-content;
}
.page a {
  color: black;
  float: left;
  padding: 4px 12px;
  text-decoration: none;
  border-radius:50%;
  margin: 0 0.5rem;
}
.page a.active {
  background-color: silver;
  color: white;
}
.page a:hover:not(.active) {
  background-color: silver;
  color:white;
}
.pageNum {
  cursor: pointer;
}
</style>
