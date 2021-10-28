<template>
  <div>
    <HEADER></HEADER>
    <!-- <p v-for="(empl , index) in employees" :key="index">
      <span>{{empl.id}} </span>
      <span>{{empl.first_name}} </span>
      <span>{{empl.last_name}} </span>
      <span>{{empl.email}} </span>
    </p> -->
    <router-view></router-view>
    <FOOTER></FOOTER>
  </div>
</template>

<script>
import HEADER from './components/Header.vue'
import FOOTER from './components/Footer.vue'

// import axios from 'axios'
// const url = 'http://localhost:8060/api/employees'

export default {
  components: {
    HEADER,
    FOOTER
  },
  data () {
    return {
      employees: []
    }
  }
  // methods: {
  //   emp () {
  //     return axios.get(url)
  //       .then(res => {
  //         this.employees = res.data
  //         console.log(res.data)
  //       })
  //       .catch(err => {
  //         console.log(err)
  //       })
  //   }
  // },
  // created () {
  //   this.emp()
  // }
}
</script>

<style scoped>
</style>
