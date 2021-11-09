const order = {
  state: {
    count: 1,
    itemId: 0,
    price: 0,
    name: '',
    orderId: 0,
    isCart: false,
    ccount: [],
    citemId: [],
    cprice: [],
    cname: [],
    corderId: [],
    cartId: []
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
    },
    changeOrderCart (state, tf) {
      state.isCart = tf
    },
    cchangeCount (state, count) {
      state.ccount.push(count)
    },
    cchangeItemId (state, id) {
      state.citemId.push(id)
    },
    cchangePrice (state, price) {
      state.cprice.push(price)
    },
    cchangeName (state, name) {
      state.cname.push(name)
    },
    resetCart (state) {
      state.ccount = []
      state.citemId = []
      state.cprice = []
      state.cname = []
    },
    removeCartList (state, cartId) {
      state.cartId.push(cartId)
    }
  },
  actions: {
  },
  modules: {
  }
}

export default order
