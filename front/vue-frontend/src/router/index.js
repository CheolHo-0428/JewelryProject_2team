import ALL from '../components/ProductList/All.vue'
import RING from '../components/ProductList/Ring.vue'
import EARRINGS from '../components/ProductList/Earrings.vue'
import BRACELET from '../components/ProductList/Bracelet.vue'
import NECKLACE from '../components/ProductList/Necklace.vue'

import DETAIL from '../components/product/Detail.vue'
import CART from '../components/member/Cart'
import SIGNUP from '../components/member/Signup.vue'
import LOGIN from '../components/member/Login.vue'
import NOTICE from '../components/notice/Notice.vue'
import ORDER from '../components/order/Order.vue'
import ORDERCOMPLETE from '../components/order/OrderComplete.vue'
import MYPAGE from '../components/member/MyPage.vue'
import ORDERLIST from '../components/order/OrderList'
import ORDERDETAIL from '../components/order/OrderDetail.vue'
import ORDERCANCLE from '../components/order/OrderCancle'
import MODIFY from '../components/member/Modify.vue'
import REGNOTICE from '../components/notice/RegNotice'
import NOTICEDETAIL from '../components/notice/NoticeDetail.vue'
import REVIEWDETAIL from '../components/product/ReviewDetail.vue'
import QNADETAIL from '../components/product/QnaDetail.vue'
import ADMEMBER from '../components/admin/AdMember.vue'
import ADMINPAGE from '../components/admin/AdminPage.vue'
import ADPRODUCT from '../components/admin/AdProduct.vue'
import ADORDER from '../components/admin/AdOrder.vue'
import ADSALES from '../components/admin/AdSales.vue'
import ADMEMBERDETAIL from '../components/admin/AdMemberDetail.vue'
import ADPRODUCTDETAIL from '../components/admin/AdProductDetail.vue'
import REGPRODUCT from '../components/admin/RegProduct.vue'
import ADORDERDETAIL from '../components/admin/AdOrderDetail.vue'
import SEARCHID from '../components/member/SearchID.vue'
import SEARCHPWD from '../components/member/SearchPW.vue'
import FINDID from '../components/member/FindID.vue'
import FINDPW from '../components/member/FindPW.vue'
import REGREVIEW from '../components/product/RegReview.vue'
import REGQNA from '../components/product/RegQna.vue'

import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const route = [
  {
    path: '/detail',
    component: DETAIL
  },
  {
    path: '/cart',
    component: CART
  },
  {
    path: '/signup',
    component: SIGNUP
  },
  {
    path: '/login',
    component: LOGIN
  },
  {
    path: '/notice',
    component: NOTICE
  },
  {
    path: '/',
    component: ALL
  },
  {path: '/ring', component: RING},
  {path: '/earrings', component: EARRINGS},
  {path: '/necklace', component: NECKLACE},
  {path: '/bracelet', component: BRACELET},
  {
    path: '/order',
    component: ORDER
  },
  {
    path: '/order_',
    component: ORDERCOMPLETE
  },
  {
    path: '/mypage',
    component: MYPAGE
  },
  {
    path: '/orderlist',
    component: ORDERLIST
  },
  {
    path: '/orderdetail',
    component: ORDERDETAIL
  },
  {
    path: '/ordercancle',
    component: ORDERCANCLE
  },
  {
    path: '/modify',
    component: MODIFY
  },
  {
    path: '/regnotice',
    component: REGNOTICE
  },
  {
    path: '/notice_',
    component: NOTICEDETAIL
  },
  {
    path: '/review_',
    component: REVIEWDETAIL
  },
  {
    path: '/qna_',
    component: QNADETAIL
  },
  {
    path: '/admember',
    component: ADMEMBER
  },
  {
    path: '/adminpage',
    component: ADMINPAGE
  },
  {
    path: '/adproduct',
    component: ADPRODUCT
  },
  {
    path: '/adorder',
    component: ADORDER
  },
  {
    path: '/adsales',
    component: ADSALES
  },
  {
    path: '/admember_',
    component: ADMEMBERDETAIL
  },
  {
    path: '/adproduct_',
    component: ADPRODUCTDETAIL
  },
  {
    path: '/regproduct',
    component: REGPRODUCT
  },
  {
    path: '/adorder_',
    component: ADORDERDETAIL
  },
  {
    path: '/searchid',
    component: SEARCHID
  },
  {
    path: '/searchpw',
    component: SEARCHPWD
  },
  {
    path: '/findid',
    component: FINDID
  },
  {
    path: '/findpw',
    component: FINDPW
  },
  {
    path: '/regreview',
    component: REGREVIEW
  },
  {
    path: '/regqna',
    component: REGQNA
  }
]

const router = new VueRouter({
  mode: 'history',
  routes: route
})

export default router
