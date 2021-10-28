import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import notice from './notice'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    notice
  },
  plugins: [
    createPersistedState({
      paths: ['notice']
    })
  ],
  state: {
    count: 1,
    totalPrice: 10000,
    slides: [
      {
        src: 'http://www.rurugold.com/design/rurugold/2018wiz/14K_Necklace_pc.jpg'
      },
      {
        src: 'http://www.rurugold.com/design/rurugold/2018wiz/ANKLET-pc.jpg'
      },
      {
        src: 'http://www.rurugold.com/design/rurugold/2018wiz/main02.jpg'
      }
    ]
  },
  mutations: {
    plus (state) {
      state.count++
      state.totalPrice = 10000 * state.count
    },
    minus (state) {
      if (state.count !== 1) state.count--
      this.totalPrice = 10000 * state.count
    }
  },
  actions: {
  }
})
