import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import notice from './notice'
import { auth } from './auth.module'
import item from './item'
import member from './member'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    notice,
    auth,
    item,
    member
  },
  plugins: [
    createPersistedState({
      paths: ['notice', 'item', 'auth', 'member']
    })
  ],
  state: {
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
    ],
    detail_version: 0
  },
  mutations: {
    changeVersion (state, index) {
      state.detail_version = index
    }
  },
  actions: {
  }
})
