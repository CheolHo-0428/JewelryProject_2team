const notice = {
  state: {
    noticeId: 0,
    noticePageUrl: 'http://localhost:8000/jewelry/noticeBoard/paging',
    allNotices: 'http://localhost:8000/jewelry/noticeBoard'
  },
  mutations: {
    noticeDetail (state, ob) {
      state.noticeId = ob.id
      state.noticePageUrl = ob.urlPage
    },
    noticeReset (state) {
      state.noticePageUrl = 'http://localhost:8000/jewelry/noticeBoard/paging'
    }
  },
  actions: {
  },
  modules: {
  }
}

export default notice
