<template>
  <div class="box">
    <div v-for="i in 5" :key="i" class="img"></div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.box {
  width: 56rem;
  margin: 7rem 0 0 14rem;
}
.img {
  width: 400px;
  height: 400px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  margin-bottom: 5rem;
}
</style>
