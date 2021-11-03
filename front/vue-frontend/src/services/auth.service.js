import axios from 'axios'

// 인증 서비스
// 이 서비스는 HTTP 요청 및 응답에 대한 axios의 도움으로 세 가지 중요한 방법을 제공합니다.

// login(): POST {username, password} & JWT로컬 저장소에 저장
// logout(): JWT로컬 저장소에서 제거
// register(): POST {사용자 이름, 이메일, 비밀번호}

const API_URL = 'http://localhost:8000/jewelry/auth/'

class AuthService {
  login (user) {
    return axios
      .post(API_URL + 'signin', {
        account: user.account,
        password: user.password
      })
      .then(response => {
        // if (response.data.accessToken) {
        //   localStorage.setItem('user', JSON.stringify(response.data))
        // }
        console.log(response.data)
        localStorage.setItem('user', JSON.stringify(response.data))
        return response.data
      })
  }
  logout () {
    localStorage.removeItem('user')
  }

  register (user) {
    return axios.post(API_URL + 'signup', {
      account: user.account,
      name: user.name,
      password: user.password,
      email: user.email,
      phone: user.phone,
      post_code: user.postCode,
      address: user.address,
      detail_address: user.detailAddress
    })
  }
  findId (user) {
    return axios
      .get(API_URL + 'findId', {
        params: {
          name: user.name,
          email: user.email
        }
      })
      .then(response => {
        return response.data
      })
  }
  findPw (user) {
    return axios
      .get(API_URL + 'findPw', {
        params: {
          name: user.name,
          account: user.account,
          email: user.email
        }
      })
      .then(response => {
        return response.data
      })
  }
  changePw (user) {
    return axios
      .put(API_URL + 'changePw', {
        account: user.account,
        password: user.password
      }).then(response => {
        return response.data
      })
  }
  findMember (user) {
    return axios
      .post(API_URL + 'findMember', {
        params: {
          account: user.account
        }
      })
      .then(response => {
        console.log(response.data)
        return response.data
      })
  }
  modify (user) {
    console.log('1')
    return axios.put(API_URL + 'modify', {
      account: user.account,
      name: user.name,
      password: user.password,
      email: user.email,
      phone: user.phone,
      post_code: user.postCode,
      address: user.address,
      detail_address: user.detailAddress
    }).then(response => {
      return response.data
    })
  }
}

export default new AuthService()
