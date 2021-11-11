<template>
  <div class="box">
    <div v-for="(s, i) in stored_file_name" :key="i" class="img"><img :src="stored_file_name[i]" /></div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      stored_file_name: []
    }
  },
  methods: {
    content () {
      this.stored_file_name = []
      axios.get(`http://localhost:8000/jewelry/item/${this.$store.state.item.itemId}/itemInfo`)
        .then(res => {
          let info = res.data.data.item_response
          let imageList = info.image_file_response_list

          for (let i = 0; i < imageList.length; i++) {
            if (imageList[i].delegate_thumbnail === 'NO') {
              this.stored_file_name.push(imageList[i].stored_file_name)
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created () {
    this.content()
  }
}
</script>

<style scoped>
.box {
  width: 56rem;
  margin-top: 7rem;
}
img {
  width: 960px;
  height: 500px;
  background-size: cover;
  margin-bottom: 5rem;
}
.box div:last-child img {
  width: 960px;
  height: 890px;
}
</style>
