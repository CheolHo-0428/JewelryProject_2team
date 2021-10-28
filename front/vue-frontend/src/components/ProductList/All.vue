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
      <div v-for="i in 14" :key="i">
        <div class="img" @click="change"></div>
        <div class="product">
          <p class="name">다이아 반지</p>
          <p class="price">10000 원</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  methods: {
    change () {
      location.href = '/detail'
    }
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
