const order = {
  state: {
    count: 1,
    itemId: 0,
    price: 0,
    name: '',
    orderId: 0
  },
  mutations: {
    changeCount (state, count) {
      state.count = count
    },
    changeItemId (state, id) {
      state.itemId = id
    },
    changePrice (state, price) {
      state.price = price
    },
    changeName (state, name) {
      state.name = name
    },
    changeOrderId (state, id) {
      state.orderId = id
    }
  },
  actions: {
  },
  modules: {
  }
}

export default order
