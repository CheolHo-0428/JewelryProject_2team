<template>
  <div class="outer">
    <p class="top">상품등록</p>

    <div class="AddWrap">
      <form>
        <table class="table">
          <colgroup>
            <col width="13%">
            <col width="38%">
          </colgroup>
          <tbody>
            <tr>
              <th scope="col">상품명</th>
              <td><input type="text" class="input" placeholder="상품명 입력" v-model="name"></td>
              <th scope="col">재고량</th>
              <td><input type="number" class="input" placeholder="재고량 입력" v-model="stock"></td>
            </tr>
            <tr>
              <th scope="col">상품분류</th>
              <td>
                <select name="product" class="op" v-model="category_id" @change="categoryChange($event)">
                  <option value="" selected>-- 선택하세요 --</option>
                  <option value="1">BRACELET</option>
                  <option value="2">EARRINGS</option>
                  <option value="3">NECKLACE</option>
                  <option value="4">RING</option>
                </select>
              </td>
              <th scope="col">상품가격</th>
              <td><input type="number" class="input" placeholder="상품가격 입력" v-model="price"></td>
            </tr>
            <tr>
              <th scope="col">이미지</th>
              <td class="img" colspan="3">
                <div class="main-container">
                  <div class="room-deal-information-container">
                    <div class="room-deal-information-title">이미지 등록</div>
                      <div class="room-picture-notice">
                        <ul class="room-write-wrapper" style="margin:0px">
                          <li style="text-align:left">가로 이미지를 권장합니다. (가로 사이즈 최소 800px)</li>
                          <li style="text-align:left">사진 용량은 사진 한 장당 10MB 까지 등록이 가능합니다.</li>
                        </ul>
                      </div>
                      <div class="room-file-upload-wrapper">
                        <div class="room-file-upload-example-container">
                          <div class="room-file-upload-example">
                            <div class="room-file-image-example-wrapper">이미지</div>
                            <div class="room-file-notice-item">
                              실사진 최소 3장 이상 등록하셔야 하며, 가로사진을 권장합니다.
                            </div>
                            <div class="room-file-notice-item room-file-notice-item-red">
                              불필요한 정보(워터마크,상호,전화번호 등)가 있는 이미지는 업로드 금지
                            </div>
                            <div class="room-file-notice-item room-file-upload-button">
                              <div class="image-box">
                                <v-file-input
                                  id="file" name="files"
                                  color="deep-purple accent-4"
                                  prepend-icon="mdi-paperclip"
                                  style="width:180px; margin-left:100px;"
                                  label="File input"
                                  multiple
                                  placeholder="Select your files"
                                  v-model="files"
                                  @click="isFileChange"
                                >
                                </v-file-input>
                                <table v-if="isFile" style="border:solid 1px;">
                                  <colgroup>
                                    <col width="15%">
                                    <col width="10%">
                                    <col width="10%">
                                  </colgroup>
                                  <tbody>
                                    <tr height="10">
                                      <th scope="col" style="border:none; font-size: 12px; padding: 2px">파일명</th>
                                      <th scope="col" style="border:none; font-size: 12px; padding: 2px">용량</th>
                                      <th scope="col" style="border:none; font-size: 12px; padding: 2px">썸네일등록</th>
                                    </tr>
                                    <tr v-for="(file, i) in files" :key="i">
                                      <td>{{ file.name }}</td>
                                      <td>{{ file.size * 0.001 }}kB</td>
                                      <td>
                                        <input type="checkbox" v-bind:id="'imageInfo'+i" v-model="true_false_list[i]">
                                      </td>
                                    </tr>
                                  </tbody>
                                </table>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>
    <div class="button">
      <v-btn color="#000" style="color:white;" x-large @click="list">상품목록</v-btn>
      <v-btn color="#000" style="color:white;" x-large @click="addItem">등록</v-btn>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      name: '',
      category_id: '',
      price: '',
      stock: '',
      delegate_thumbnail: '',
      true_false_list: [],
      delegate_thumbnail_list: [],
      item_id: '',
      isFile: false,
      files: []
    }
  },
  methods: {
    clickFunc (event) {
      for (let i = 0; i < this.true_false_list.length; i++) {
        if (this.true_false_list[i] !== event.target.value) {
          this.true_false_list[i] = !this.true_false_list[i]
        }
      }
    },
    list () {
      this.$swal.fire({
        icon: 'warning',
        title: '상품이 등록되지 않습니다.',
        text: '목록으로 돌아가시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        confirmButtonColor: '#FE9A2E',
        cancelButtonColor: '#BDBDBD',
        cancelButtonText: 'No'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push('/adproduct')
        }
      })
    },
    save () {
      this.$swal.fire({
        icon: 'success',
        title: '상품이 등록되었습니다.',
        text: '목록으로 이동합니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        this.$router.push('/adproduct')
      })
    },
    async addItem () {
      if (!this.name || !this.stock || !this.category_id || !this.price) {
        this.$swal.fire({
          icon: 'info',
          title: '제목 OR 재고 OR 분류 OR 가격을 적어주세요.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        await axios
          .post('http://localhost:8000/jewelry/item/reg', {
            name: this.name,
            stock: this.stock,
            category_id: this.category_id,
            price: this.price
          }).then(res => {
            console.log(res)
          }).catch(error => {
            console.log(error)
          })
        await axios
          .get(`http://localhost:8000/jewelry/item/search?keyword=${this.name}&page=0`, {
            data: {
              name: this.name
            }
          }).then((res) => {
            this.item_id = ''
            // console.log('**************************', res.data.data[0].id)
            this.item_id = res.data.data[0].id
          })
        let frm = new FormData()
        let imageFile = document.getElementById('file')
        for (let i = 0; i < imageFile.files.length; i++) {
          if (this.true_false_list[i] === true) {
            this.delegate_thumbnail_list.push('YES')
          } else {
            this.delegate_thumbnail_list.push('NO')
          }
          await console.log(imageFile.files[i])
          await frm.append('file', imageFile.files[i])
        }
        frm.append('delegateThumbnail', this.delegate_thumbnail_list)
        frm.append('itemId', this.item_id)
        // console.log('**********************' + this.delegate_thumbnail_list)
        if (imageFile.files[0]) {
          await axios.post('http://localhost:8000/jewelry/imageFile/regImg', frm, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).then((response) => {
            console.log(response)
          }).catch((error) => {
            console.log(error)
          })
        }
        this.save()
      }
    },
    categoryChange (event) {
      this.category_id = event.target.value
    },
    isFileChange () {
      this.isFile = true
      this.true_false_list = []
      this.delegate_thumbnail_list = []
    }
  }
}
</script>

<style scoped>
.outer {
  margin: 4rem auto;
  width: 53rem;
  text-align: center
}

p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem
}

.table {
  width: 850px;
  border-top: .2rem solid #000;
  border-bottom: .2rem solid #000;
  margin-bottom: 5rem
}

.input {
  text-align: center;
  width: 100% !important;
  font-size: .8rem
}

tr {
  border-right: 1px solid #000
}

th {
  padding: .6rem 0;
  font-size: .85rem;
  background-color: #e7e7e7;
  border-right: 1px solid #000;
  border-left: 1px solid #000;
  vertical-align: middle
}

.op {
  border: 1px solid #e7e7e7;
  width: fit-content;
  font-size: .8rem;
  border-radius: 2px;
  padding: .2rem;
  background-color: #fff;
  text-align: center;
  color: gray
}

.button button {
  border: 1px solid #000;
  margin: 0 .6rem;
  padding: .5rem 2rem;
  font-weight: 700
}

.button {
  margin-bottom: 2rem
}

.main-container {
  width: 650px;
  height: fit-content;
  margin: 0 auto
}
.room-deal-information-container {
  margin-top: 10px;
  color: #222;
  border: 1px solid #ddd
}
.room-deal-information-title {
  text-align: center;
  font-size: 15px;
  font-weight: bolder;
  line-height: 40px;
  border-bottom: 1px solid #ddd
}
.room-deal-option-item {
  width: 100px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer
}
.room-deal-option-item:last-child {
  margin-left: 10px
}
.room-deal-option-notice {
  margin-left: auto;
  font-size: 14px;
  color: #888
}
.room-deal-information-wrapper {
  display: flex;
  flex-direction: column
}
.room-deal-information-option {
  padding: 10px;
  display: flex;
  align-items: center
}
.room-deal-information-option:last-child {
  border-bottom: 1px solid #ddd
}
.room-deal-information-item-type {
  font-size: 13px;
  color: #fff;
  background-color: #61b6e5;
  min-width: 50px;
  height: 26px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 3px
}
.room-deal-information-item-wrapper {
  display: flex;
  align-items: center;
  margin-left: 10px;
  height: 46px;
  width: 100%
}
.room-deal-information-item-wrapper>input {
  border: 1px solid #ddd;
  width: 140px;
  height: 100%;
  padding: 0 15px;
  font-size: 15px
}
.room-deal-information-example {
  color: #888
}
.room-deal-information-option:not(:first-child) {
  margin-top: 10px
}
.room-file-upload-example {
  height: 100%
}
.room-picture-notice {
  font-size: 13px;
  margin: 20px;
  padding: 20px 20px;
  border: 1px solid #ddd
}
.room-file-upload-wrapper {
  margin: 20px;
  border: 1px solid #ddd;
  background-color: #f4f4f4;
  min-height: 210px;
  font-size: 15px;
  color: #888;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%
}
.room-file-upload-example-container {
  display: flex;
  align-items: center;
  justify-content: center
}
.room-file-image-example-wrapper {
  text-align: center;
  font-size: 13px
}
.room-file-notice-item {
  margin-top: 5px;
  text-align: center;
  font-size: 13px
}
.room-file-notice-item-red {
  color: #ef4351
}
.image-box {
  padding-bottom: 20px;
  text-align: center
}
.image-box input[type=file] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0
}
.image-box label {
  display: inline-block;
  padding: 10px 20px;
  background-color: #232d4a;
  color: #fff;
  vertical-align: middle;
  font-size: 15px;
  cursor: pointer;
  border-radius: 5px
}
.room-write-button-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #222
}
.room-write-button-wrapper>div {
  width: 160px;
  height: 50px;
  border: 1px solid #ddd;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  cursor: pointer
}
.room-write-button {
  margin-left: 15px;
  color: #fff;
  background-color: #1564f9
}
.room-write-button:hover {
  opacity: .8
}
</style>
