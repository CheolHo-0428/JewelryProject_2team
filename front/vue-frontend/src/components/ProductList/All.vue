<template>
  <div>
    <v-carousel
      cycle
      height="360"
      hide-delimiter-background
      :show-arrows=false
      delimiter-icon="mdi-minus"
    >
      <v-carousel-item
        v-for="(item,i) in $store.state.slides"
        :key="i"
        :src="item.src"
        reverse-transition="fade-transition"
        transition="fade-transition"
      ></v-carousel-item>
    </v-carousel>

    <div class="category">
      <p>New Items</p>
    </div>

    <div class="boxs">
      <p class="cat">RING</p>
      <div class="box">
        <div v-for="i in 4" :key="i" class="list">
          <div class="img" @click="change(ring_response_list[i-1].id)"></div>
          <div class="product">
            <p class="name" v-if="ring_response_list[i-1]">{{ring_response_list[i-1].name}}</p>
            <p class="price" v-if="ring_response_list[i-1]">{{ring_response_list[i-1].price}}원</p>
          </div>
        </div>
      </div>
      <p class="cat">EARRINGS</p>
      <div class="box">
        <div v-for="i in 4" :key="i" class="list">
          <div class="img" @click="change(earrings_response_list[i-1].id)"></div>
          <div class="product">
            <p class="name" v-if="earrings_response_list[i-1]">{{earrings_response_list[i-1].name}}</p>
            <p class="price" v-if="earrings_response_list[i-1]">{{earrings_response_list[i-1].price}}원</p>
          </div>
        </div>
      </div>
      <p class="cat">BRACELET</p>
      <div class="box">
        <div v-for="i in 4" :key="i" class="list">
          <div class="img" @click="change(bracelet_response_list[i-1].id)"></div>
          <div class="product">
            <p class="name" v-if="bracelet_response_list[i-1]">{{bracelet_response_list[i-1].name}}</p>
            <p class="price" v-if="bracelet_response_list[i-1]">{{bracelet_response_list[i-1].price}}원</p>
          </div>
        </div>
      </div>
      <p class="cat">NECKLACE</p>
      <div class="box">
        <div v-for="i in 4" :key="i" class="list">
          <div class="img" @click="change(necklace_response_list[i-1].id)"></div>
          <div class="product">
            <p class="name" v-if="necklace_response_list[i-1]">{{necklace_response_list[i-1].name}}</p>
            <p class="price" v-if="necklace_response_list[i-1]">{{necklace_response_list[i-1].price}}원</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      ring_response_list: [],
      earrings_response_list: [],
      bracelet_response_list: [],
      necklace_response_list: []
    }
  },
  methods: {
    change (id) {
      this.$store.commit('itemDetail', {id: id})
      this.$store.commit('changeVersion', 0)
      this.$router.push('/detail')
    },
    ring () {
      axios.get('http://localhost:8000/jewelry/category/4/itemInfo')
        .then(res => {
          this.ring_response_list = res.data.data.category_response.item_response_list
        })
        .catch(err => {
          console.log(err)
        })
    },
    earrings () {
      axios.get('http://localhost:8000/jewelry/category/2/itemInfo')
        .then(res => {
          this.earrings_response_list = res.data.data.category_response.item_response_list
        })
        .catch(err => {
          console.log(err)
        })
    },
    bracelet () {
      axios.get('http://localhost:8000/jewelry/category/1/itemInfo')
        .then(res => {
          this.bracelet_response_list = res.data.data.category_response.item_response_list
        })
        .catch(err => {
          console.log(err)
        })
    },
    necklace () {
      axios.get('http://localhost:8000/jewelry/category/3/itemInfo')
        .then(res => {
          this.necklace_response_list = res.data.data.category_response.item_response_list
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.ring()
    this.earrings()
    this.bracelet()
    this.necklace()
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

.category {
  text-align: center;
  margin-top: 4rem;
}
.category p {
  font-weight: 700;
  font-size: 1.4rem;
  margin-bottom: 1rem;
  text-decoration-line: underline;
}

.boxs {
  margin-bottom: 4rem !important;
  text-align: center;
  width: fit-content;
  max-width: 1340px;
  min-width: 1110px;
  margin: 0 auto;
}
.cat {
  text-align: left;
  font-family: 'Open Sans Condensed', sans-serif;
  font-weight: 700;
  margin: 3rem 0 -1rem;
  font-size: larger;
  text-decoration-line: underline;
  text-decoration-color: rgb(255, 245, 188);
}
.box {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
.list {
  width: 25%;
}
.img {
  width: 230px;
  height: 230px;
  margin: 2rem auto 1rem;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  cursor: pointer;
}
.img:hover {
  transform:scale(1.01);
  transition: 0.2s;
}
.product p {
  margin-bottom: 0;
}
.name {
  font-weight: 700;
  cursor: pointer;
}
</style>
