const notice = {
  state: {
    noticeNum: 0,
    noticePageUrl: 'http://localhost:8000/jewelry/noticeBoard/paging'
  },
  mutations: {
    noticeDetail (state, ob) {
      state.noticeNum = ob.index
      state.noticePageUrl = ob.urlPage
    },
    urlBase (state) {
      state.noticePageUrl = 'http://localhost:8000/jewelry/noticeBoard/paging'
    }
  },
  actions: {
  },
  modules: {
  }
}

export default notice
