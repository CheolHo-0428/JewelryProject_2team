const item = {
  state: {
    itemId: 0,
    reviewId: 0,
    qnaId: 0,
    itemPageUrl: 'http://localhost:8000/jewelry/item/paging',
    allItems: 'http://localhost:8000/jewelry/item',
    searchedItem: ''
  },
  mutations: {
    itemDetail (state, ob) {
      state.itemId = ob.id
      state.itemPageUrl = ob.urlPage
    },
    reviewDetail (state, ob) {
      state.reviewId = ob.id
    },
    qnaDetail (state, ob) {
      state.qnaId = ob.id
    },
    searchItem (state, keyword) {
      state.searchedItem = keyword
    }
  },
  actions: {
  },
  modules: {
  }
}

export default item
