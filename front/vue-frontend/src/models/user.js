
export default class User {
  constructor (account, name, password, email, phone, postCode, address, detailAddress) {
    this.account = account
    this.name = name
    this.password = password
    this.email = email
    this.phone = phone
    this.postCode = postCode
    this.address = address
    this.detailAddress = detailAddress
  }
}
