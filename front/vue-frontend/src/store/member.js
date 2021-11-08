const member = {
  state: {
    id: 0,
    account: '',
    memberUrl: 'http://localhost:8000/jewelry/auth/mypage',
    admemberPageUrl: 'http://localhost:8000/jewelry/member/paging',
    allAdMembers: 'http://localhost:8000/jewelry/member'
  },
  mutations: {
    mypage (state, ob) {
      state.account = ob.account
    },
    admemberDetail (state, ob) {
      state.id = ob.id
      state.admemberPageUrl = ob.urlPage
    }
  }
}
export default member
