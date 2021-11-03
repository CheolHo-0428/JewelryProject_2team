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
      <p>All Products</p>
    </div>

    <div class="boxs">
      <div class="box">
        <div v-for="(list, i) in ring_response_list" :key="i" class="list">
          <div class="img" @click="change(list.id)"></div>
          <div class="product">
            <p class="name">{{list.name}}</p>
            <p class="price">{{list.price}}원</p>
          </div>
        </div>
      </div>
      <div class="box">
        <div v-for="(list, i) in earrings_response_list" :key="i" class="list">
          <div class="img" @click="change(list.id)"></div>
          <div class="product">
            <p class="name">{{list.name}}</p>
            <p class="price">{{list.price}}원</p>
          </div>
        </div>
      </div>
      <div class="box">
        <div v-for="(list, i) in bracelet_response_list" :key="i" class="list">
          <div class="img" @click="change(list.id)"></div>
          <div class="product">
            <p class="name">{{list.name}}</p>
            <p class="price">{{list.price}}원</p>
          </div>
        </div>
      </div>
      <div class="box">
        <div v-for="(list, i) in necklace_response_list" :key="i" class="list">
          <div class="img" @click="change(list.id)"></div>
          <div class="product">
            <p class="name">{{list.name}}</p>
            <p class="price">{{list.price}}원</p>
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
      response_list: [],
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
    all () {
      axios.get('http://localhost:8000/jewelry/item/')
        .then(res => {
          this.response_list = res.data.data
        })
        .catch(err => {
          console.log(err)
        })
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
    this.all()
    this.ring()
    this.earrings()
    this.bracelet()
    this.necklace()
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
}

.boxs {
  margin-bottom: 4rem !important;
  text-align: center;
  width: fit-content;
  max-width: 1340px;
  min-width: 1110px;
  margin: 0 auto;
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
