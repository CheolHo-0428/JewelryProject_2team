<template>
  <div>
    <div class="topContent">
      <div class="topBox left">
        <div class="imageBox"><img :src="stored_thumbnail" /></div>
      </div>
      <div class="topBox right">
        <div class="name">{{this.name}}</div>
        <div class="price">PRICE - {{this.price}}원</div>
        <hr>
        <div class="trans">
          <p><span class="info">국내·해외배송 </span><span class="info2">국내배송</span></p>
          <p><span class="info">배송 방법 </span><span class="info2">택배</span></p>
          <p><span class="info">배송비 </span><span class="info2">2500원</span></p>
        </div>
        <hr>
        <div class="count" :class="{unreg : status === 'UNREGISTERED'}">
          <tr>
            <td class="text">수량</td>
            <td>
                <button type ="button" @click="minus" :class="{disabled:status === 'UNREGISTERED'}">-</button>
                <input type="text" readonly="readonly" :value="`${this.count}`">
                <button type="button" @click="plus" :class="{disabled:status === 'UNREGISTERED'}">+</button>
            </td>
          </tr>
        </div>
        <div class="totPrice" :class="{unreg : status === 'UNREGISTERED'}">총 상품금액 - {{totalPrice}}원</div>
        <div class="totPrice" v-if="status === 'UNREGISTERED'" style="color:#DF0101;">일시품절</div>
        <div class="button">
          <a class="btn btn-dark" @click="order" :class="{disabled:status === 'UNREGISTERED'}">BUY IT NOW</a>
          <a v-if="currentUser" class="btn btn-dark btn2" @click="cart" :class="{disabled:status === 'UNREGISTERED'}">ADD TO CART</a>
        </div>
      </div>
    </div>

    <div class="tab">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" @click="changeVersion(0)" :class="{'active' : $store.state.detail_version == 0}">IMAGE</a>
        </li>
        <li class="nav-item">
          <a class="nav-link"  @click="changeVersion(1)" :class="{'active' : $store.state.detail_version  == 1}">REVIEW</a>
        </li>
        <li class="nav-item">
          <a class="nav-link"  @click="changeVersion(2)" :class="{'active' : $store.state.detail_version  == 2}">Q&A</a>
        </li>
      </ul>
      <div class="tab-content">
        <CONTENT v-if="$store.state.detail_version  == 0"></CONTENT>
        <REVIEW v-if="$store.state.detail_version  == 1"></REVIEW>
        <QNA v-if="$store.state.detail_version  == 2"></QNA>
      </div>
    </div>
  </div>
</template>

<script>
import CONTENT from './Content'
import REVIEW from './Review'
import QNA from './Qna'
import axios from 'axios'

export default {
  components: {
    CONTENT, REVIEW, QNA
  },
  data () {
    return {
      name: '',
      price: 0,
      count: 1,
      totalPrice: 0,
      id: 0,
      stored_thumbnail: '',
      status: '',
      stock: 0
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  methods: {
    cart () {
      if (this.count > this.stock) {
        this.$swal.fire({
          icon: 'warning',
          title: '상품의 재고량보다 많습니다.',
          text: `해당상품 남은 재고 : ${this.stock}`,
          confirmButtonColor: '#FE9A2E'
        })
      } else {
        this.$swal.fire({
          icon: 'info',
          title: '장바구니에 넣으시겠습니까?',
          text: '장바구니 페이지로 이동합니다.',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          confirmButtonColor: '#9de0f6',
          cancelButtonColor: '#BDBDBD',
          cancelButtonText: 'No'
        }).then((result) => {
          if (result.isConfirmed) {
            axios.post('http://localhost:8000/jewelry/cart/reg', {
              item_count: this.count,
              member_id: this.$store.state.auth.user.id,
              item_id: this.$store.state.item.itemId
            }).then((response) => {
              this.$router.push('/cart')
              console.log(response)
            }).catch((error) => {
              console.log(error)
            })
          }
        })
      }
    },
    order () {
      if (this.count > this.stock) {
        this.$swal.fire({
          icon: 'warning',
          title: '상품의 재고량보다 많습니다.',
          text: `해당상품 남은 재고 : ${this.stock}`,
          confirmButtonColor: '#FE9A2E'
        })
      } else {
        this.$store.commit('changeCount', this.count)
        this.$store.commit('changeItemId', this.$store.state.item.itemId)
        this.$store.commit('changePrice', this.price)
        this.$store.commit('changeName', this.name)
        this.$store.commit('changeOrderCart', false)
        this.$router.push('/order')
      }
    },
    plus () {
      this.count++
      this.totalPrice = this.price * this.count
    },
    minus () {
      if (this.count !== 1) this.count--
      this.totalPrice = this.price * this.count
    },
    changeVersion (index) {
      this.$store.commit('changeVersion', index)
    },
    detail () {
      axios.get(`http://localhost:8000/jewelry/item/${this.$store.state.item.itemId}/itemInfo`)
        .then(res => {
          let info = res.data.data.item_response
          this.name = info.name
          this.price = info.price
          this.totalPrice = info.price
          this.status = info.status
          this.stock = info.stock

          let imageList = info.image_file_response_list

          let tmp = imageList.findIndex(
            (i) => i.delegate_thumbnail === 'YES'
          )
          if (tmp === -1) this.stored_thumbnail = imageList[0].stored_file_name
          else this.stored_thumbnail = imageList[tmp].stored_file_name
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.detail()
  },
  mounted () {
    window.scrollTo(0, 0)
  }
}
</script>

<style scoped>
.sideBanner {
  position: absolute;
  width: 280px;
  height: 400px;
  top: 170px;
  background-color: #ffd95d;
  margin-left: 20px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/AyICP7.jpg);
}

.unreg {
  color: #747272;
}
.unreg button {
  border-color: #747272 !important;
}
.unreg input {
  border-color: #747272 !important;
  color: #747272;
}

.topContent {
  margin: 4rem 0 3rem;
  display: flex;
  justify-content: center;
}
.topBox {
  margin: 0 1rem;
  width: 33rem;
  padding-bottom: 2rem;
}
img {
  width: 460px;
  height: 480px;
  margin: 4rem auto;
  background-size: cover;
}
.name {
  font-weight: 700;
  font-size: 1.5rem;
  margin: 4rem 0 1rem 2rem;
}
.price{
  text-align: right;
  font-weight: 700;
  font-size: 1.2rem;
  margin-right: 2rem;
}
.trans {
  margin: 2rem 0 2rem 2rem;
  font-size: 0.9rem;
  color: #9da2a7;
}
.info {
  color: black;
  width: 8rem;
  display: inline-block;
}
.count {
  margin: 1.8rem 0 0 2em;
}
.count .text {
  padding-right: 2rem;
  font-size: 0.9rem;
}
.count button {
outline: none;
    background-color: white;
    padding: 0 0.5rem;
    border: 1px solid black;
    margin: 0 0.5rem;
    font-weight: 700;
    height: 30px;
    line-height: -35;
    width: 35px;
    border-radius: 43px;
    -webkit-box-shadow: 0.5px 0.5px 0 rgb(0 0 0 / 50%);
    box-shadow: 0.5px 0.5px 0 rgb(0 0 0 / 50%);
}
.count button:active {
  box-shadow: 0.5px 0px 0 rgb(0,0,0,0.5);
  position: relative;
  top: 0.5px;
}
.count input {
  font-size: 0.9rem;
  outline: none;
  border: 1px solid black;
  text-align:center;
  width: 311px;
  height: 30px;
  border-radius: 2px;
}
.totPrice {
  text-align: right;
  font-weight: 700;
  font-size: 1.1rem;
  margin: 2rem 2rem 0 0;
}
.button {
  display: flex;
  justify-content: center;
  margin-top: 3rem;
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
.btn-dark {
  background: #000;
  color: #fff;
  padding: 1rem 4rem;
  margin: 0 1rem;
  font-size: 1.2rem;
}
.btn2 {
  background: #fff;
  color: #000;
  border: #000 1px solid;
  padding: 1rem 4rem;
  margin: 0 1rem;
  font-size: 1.2rem;
}

.tab {
  width: 56rem;
  margin: 0 auto;
}
.nav-item {
  width: 33%;
}
.nav-link {
  color: #b4b9be;
  cursor: pointer;
  transition: .5s;
  font-weight: 700;
}

.disabled {
  pointer-events: none;
  cursor: default;
  background-color: silver;
}
</style>
