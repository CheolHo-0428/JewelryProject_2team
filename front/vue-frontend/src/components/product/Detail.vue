<template>
  <div>
    <div class="sideBanner"></div>
    <div class="topContent">
      <div class="topBox left">
        <div class="imageBox"></div>
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
        <div class="count">
          <tr>
            <td class="text">수량</td>
            <td>
                <button type ="button" @click="minus">-</button>
                <input type="text" readonly="readonly" :value="`${this.count}`">
                <button type="button" @click="plus">+</button>
            </td>
          </tr>
        </div>
        <div class="totPrice">총 상품금액 - {{totalPrice}}원</div>
        <div class="button">
          <v-btn color="#FBEF97" x-large @click="order">구매하기</v-btn>
          <v-btn v-if="currentUser" color="#F4F2E7" x-large @click="cart">장바구니</v-btn>
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
      id: 0
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  methods: {
    cart () {
      this.$swal.fire({
        icon: 'info',
        title: '장바구니에 넣으시겠습니까?',
        text: 'yes를 누르시면 장바구니 페이지로 이동합니다.',
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
            console.log(response)
          }).catch((error) => {
            console.log(error)
          })
          this.$router.push('/cart')
        }
      })
    },
    order () {
      this.$store.commit('changeCount', this.count)
      this.$store.commit('changeItemId', this.$store.state.item.itemId)
      this.$store.commit('changePrice', this.price)
      this.$store.commit('changeName', this.name)
      this.$store.commit('changeOrderCart', false)
      this.$router.push('/order')
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
      axios.get(`http://localhost:8000/jewelry/item/${this.$store.state.item.itemId}`)
        .then(res => {
          let info = res.data.data
          this.name = info.name
          this.price = info.price
          this.totalPrice = info.price
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
    let floatPosition = parseInt($('.sideBanner').css('top'))

    // scroll 인식
    $(window).scroll(() => {
      // 현재 스크롤 위치
      let currentTop = $(window).scrollTop()
      let bannerTop = currentTop + floatPosition + 'px'

      // 이동 애니메이션
      $('.sideBanner').stop().animate({
        'top': bannerTop
      }, 500)
    }).scroll()
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
.imageBox {
  width: 460px;
  height: 480px;
  margin: 4rem auto;
  background-size: cover;
  background-image: url(https://ifh.cc/g/K3vlNQ.jpg);
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
  border-radius: 2px;
  border: 1.7px solid black;
  margin: 0 0.5rem;
  font-size: 0.9rem;
  font-weight: 700;
  box-shadow: 1px 0.5px 0 rgb(0,0,0,0.5);
}
.count button:active {
  box-shadow: 1px 0px 0 rgb(0,0,0,0.5);
  position: relative;
  top: 0.5px;
}
.count input {
  font-size: 0.9rem;
  font-weight: 700;
  border-radius: 2px;
  outline: none;
  border: 1.7px solid black;
  text-align:center;
}
.totPrice {
  text-align: right;
  font-weight: 700;
  font-size: 1.1rem;
  margin: 2rem 3rem 0 0;
}
.button {
  display: flex;
  justify-content: center;
  margin-top: 3rem;
}
.button button {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 1.8rem 2.6rem !important;
  font-weight: 700;
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
</style>
