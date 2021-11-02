<template>
  <div>
    <div class="category">
      <p>Earrings</p>
    </div>

    <div class="boxs">
      <div v-for="(list, i) in response_list" :key="i">
        <div class="img" @click="change(list.id)"></div>
        <div class="product">
          <p class="name">{{list.name}}</p>
          <p class="price">{{list.price}}원</p>
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
      response_list: []
    }
  },
  methods: {
    change (id) {
      this.$store.commit('itemDetail', {id: id})
      this.$router.push('/detail')
    },
    earrings () {
      axios.get('http://localhost:8000/jewelry/category/2/itemInfo')
        .then(res => {
          this.response_list = res.data.data.category_response.item_response_list
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.earrings()
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
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 4rem !important;
  text-align: center;
  width: fit-content;
  max-width: 1340px;
  margin: 0 auto;
}
.img {
  width: 230px;
  height: 230px;
  margin: 2rem 3rem 1rem;
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
