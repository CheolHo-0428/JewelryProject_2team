const item = {
  state: {
    itemId: 0,
    reviewId: 0,
    qnaId: 0
  },
  mutations: {
    itemDetail (state, ob) {
      state.itemId = ob.id
    },
    reviewDetail (state, ob) {
      state.reviewId = ob.id
    },
    qnaDetail (state, ob) {
      state.qnaId = ob.id
    }
  },
  actions: {
  },
  modules: {
  }
}

export default item
