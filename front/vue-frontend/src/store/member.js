const member = {
  state: {
    account: '',
    memberUrl: 'http://localhost:8000/jewelry/auth/mypage'
  },
  mutations: {
    mypage (state, ob) {
      state.account = ob.account
    }
  }
}
export default member
