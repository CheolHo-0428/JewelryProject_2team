import AuthService from '../services/auth.service'

const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null }

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login ({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          commit('loginSuccess', user)
          return Promise.resolve(user)
        },
        error => {
          commit('loginFailure')
          return Promise.reject(error)
        }
      )
    },
    logout ({ commit }) {
      AuthService.logout()
      commit('logout')
    },
    register ({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess')
          return Promise.resolve(response.data)
        },
        error => {
          commit('registerFailure')
          return Promise.reject(error)
        }
      )
    },
    // 네비게이션가드설정을 안함.
    findId ({ commit }, user) {
      return AuthService.findId(user).then(
        user => {
          commit('findIdSuccess')
          return Promise.resolve(user)
        },
        error => {
          commit('findIdFailure')
          return Promise.reject(error)
        }
      )
    },
    findPw ({ commit }, user) {
      return AuthService.findPw(user).then(
        user => {
          commit('findPwSuccess')
          return Promise.resolve(user)
        },
        error => {
          commit('findPwFailure')
          return Promise.reject(error)
        }
      )
    },
    changePw ({ commit }, user) {
      return AuthService.changePw(user).then(
        user => {
          commit('changePwSuccess', user)
          return Promise.resolve(user)
        },
        error => {
          commit('changePwFailure')
          return Promise.reject(error)
        }
      )
    }
  },
  mutations: {
    loginSuccess (state, user) {
      state.status.loggedIn = true
      state.user = user
    },
    loginFailure (state) {
      state.status.loggedIn = false
      state.user = null
    },
    logout (state) {
      state.status.loggedIn = false
      state.user = null
    },
    registerSuccess (state) {
      state.status.loggedIn = false
    },
    registerFailure (state) {
      state.status.loggedIn = false
    },
    findIdSuccess (state, user) {
      state.status.loggedIn = false
      state.user = user
    },
    findIdFailure (state) {
      state.status.loggedIn = false
      state.user = null
    },
    findPwSuccess (state, user) {
      state.status.loggedIn = false
      state.user = user
    },
    findPwFailure (state) {
      state.status.loggedIn = false
      state.user = null
    },
    changePwSuccess (state) {
      state.status.loggedIn = false
    },
    changePwFailure (state) {
      state.status.loggedIn = false
    }
  }
}
