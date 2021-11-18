<template>
  <div class="outer">
    <p class="top">상품상세</p>

    <div class="AddWrap">
      <form>
        <table class="table">
          <colgroup>
            <col width="13%">
            <col width="38%">
          </colgroup>
          <tbody>
            <tr>
              <th scope="col">상품번호</th>
              <td class="noblock">
                gguluck-<input type="text" v-model="id" class="no" disabled>-21Y11M
              </td>
              <th scope="col">상품명</th>
              <td><input type="text" v-model="name"></td>
            </tr>
            <tr>
              <th scope="col">상품분류</th>
              <td>
                <select name="category_id" id="category_id" v-model="category_id" class="op" @change="categoryChange($event)">
                  <option value="1">BRACELET</option>
                  <option value="2">EARRINGS</option>
                  <option value="3">NECKLACE</option>
                  <option value="4">RING</option>
                </select>
              </td>
              <th scope="col">상품가격</th>
              <td><input type="text" v-model="price"></td>
            </tr>
            <tr>
              <th scope="col">재고량</th>
              <td><input type="text" v-model="stock"></td>
              <th scope="col">등록상태</th>
              <td>
                <select name="status" id="status" v-model="status" class="op" @change="statusChange($event)">
                  <option value="REGISTERED">REGISTERED</option>
                  <option value="UNREGISTERED">UNREGISTERED</option>
                </select>
              </td>
            </tr>
            <tr>
              <th scope="col">등록날짜</th>
              <td><input type="text" v-model="created_at" disabled></td>
              <th scope="col">수정날짜</th>
              <td><input type="text" v-model="updated_at" disabled></td>
            </tr>
            <!-- 여기서부터 테스트 -->
            <tr>
              <th scope="col">이미지 수정</th>
              <td class="img" colspan="3">
                <div class="main-container">
                  <div class="room-deal-information-container">
                    <div class="room-deal-information-title">이미지 수정</div>
                    <div class="room-picture-notice">
                      <ul class="room-write-wrapper" style="margin:0px">
                        <li style="text-align:left">이미지 삭제 후 새로운 이미지 추가 가능합니다.</li>
                        <li style="text-align:left">썸네일 이미지는 1개만 선택해주세요.(다중체크일 경우 가장 상단 이미지가 선택됩니다.)</li>
                      </ul>
                    </div>
                    <div class="room-file-upload-wrapper" style="background:white">
                      <div class="room-file-upload-example-container">
                        <div class="room-file-upload-example">
                          <div class="room-file-notice-item room-file-upload-button">
                            <div class="image-box">
                              <table style="border:solid 1px;" width="500">
                                <colgroup>
                                  <col width="15%">
                                  <col width="10%">
                                  <col width="10%">
                                </colgroup>
                                <tbody>
                                  <tr height="20">
                                    <th scope="col" style="border:none; font-size: 12px; padding: 2px;">이미지</th>
                                    <th scope="col" style="border:none; font-size: 12px; padding: 2px;">썸네일등록</th>
                                    <th scope="col" style="border:none; font-size: 12px; padding: 2px;">삭제</th>
                                  </tr>
                                  <tr v-for="(image, i) in stored_file_name_list" :key="i">
                                    <td>
                                      <img :src="image" v-if="stored_file_name_list[i]" style="width:auto; height:100px">
                                      <div v-if="!stored_file_name_list[i]">
                                        <v-file-input
                                          v-bind:id="'file'+i"
                                          name="files"
                                          label="File input"
                                          multiple="multiple"
                                          style="width: 200px;"
                                        >
                                        </v-file-input>
                                      </div>
                                    </td>
                                    <td>
                                      <input type="checkbox" v-bind:id="'updateImageInfo'+i" v-model="del_true_false_list[i]">
                                    </td>
                                    <td>
                                      <a @click="imgfun(i)" style="cursor:pointer; font-size:13px;color:red;">삭제</a>
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
            <!-- 여기서부터 -->
            <tr>
            <th scope="col">이미지 추가</th>
            <td class="img" colspan="3">
                <div class="main-container">
                  <div class="room-deal-information-container">
                    <div class="room-deal-information-title">이미지 추가</div>
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
                                id="addFile" name="addFiles"
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
      <v-btn color="#000" style="color:white" x-large @click="list">상품목록</v-btn>
      <v-btn color="#000" style="color:white" x-large @click="mod">저장</v-btn>
      <v-btn color="#000" style="color:white" x-large @click="remove">삭제</v-btn>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      id: 0,
      name: '',
      category_id: '',
      price: '',
      stock: '',
      created_at: '',
      created_by: '',
      updated_at: '',
      status: '',
      stored_file_name_list: [],
      deleteImg_list: [],
      image_file_list: [],
      del_true_false_list: [],
      delegate_thumbnail_list: [],
      id_list: [],
      item_id: '',
      files: [],
      isFile: false,
      true_false_list: [],
      addDelegate_thumbnail_list: []
    }
  },
  methods: {
    imgfun (i) {
      this.$set(this.deleteImg_list, i, true)
      this.$set(this.stored_file_name_list, i, false)
    },
    list () {
      this.$swal.fire({
        icon: 'warning',
        title: '수정사항이 저장되지 않습니다.',
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
        title: '상품정보가 수정되었습니다.',
        text: '목록으로 이동합니다.',
        confirmButtonColor: '#CEF6CE'
      }).then(() => {
        this.$router.push('/adproduct')
      })
    },
    async mod () {
      for (let i = 0; i < this.del_true_false_list.length; i++) {
        if (this.del_true_false_list[i] === true) {
          this.delegate_thumbnail_list[i] = 'YES'
        } else {
          this.delegate_thumbnail_list[i] = 'NO'
        }
      }
      if (!this.name || !this.price || !this.stock) {
        this.$swal.fire({
          icon: 'info',
          title: '상품명 OR 가격 OR 재고량이 비어있습니다.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        await axios
          .put('http://localhost:8000/jewelry/item/update', {
            id: this.id,
            name: this.name,
            category_id: this.category_id,
            price: this.price,
            stock: this.stock,
            status: this.status
          })
          .then((res) => {
            console.log(res)
          })
          .catch((error) => {
            console.log(error)
          })
        for (let i = 0; i < this.deleteImg_list.length; i++) {
          if (this.deleteImg_list[i]) {
            let frm = new FormData()
            let imageFile = document.getElementById('file' + i)
            console.log('****************************' + 'file' + i)
            // console.log('****************************' + imageFile.files[0].name)
            if (imageFile.files[0]) {
              frm.append('id', this.id_list[i])
              frm.append('delegateThumbnail', this.delegate_thumbnail_list[i])
              frm.append('deleteCheck', 'YES')
              frm.append('itemId', this.item_id)
              frm.append('file', imageFile.files[0])
              await axios.put('http://localhost:8000/jewelry/imageFile/updateImg', frm, {
                headers: {
                  'Content-Type': 'multipart/form-data'
                }
              }).then((response) => {
                console.log(response)
              }).catch((error) => {
                console.log(error)
              })
            } else {
              await axios.delete(`http://localhost:8000/jewelry/imageFile/${this.id_list[i]}`, {
                data: {
                  id: this.id_list[i]
                }
              }).then((res) => {
                console.log(res)
              }).catch((error) => {
                console.log(error)
              })
            }
          } else {
            let frm = new FormData()
            frm.append('id', this.id_list[i])
            frm.append('delegateThumbnail', this.delegate_thumbnail_list[i])
            frm.append('deleteCheck', 'NO')
            frm.append('itemId', this.item_id)
            await axios.put('http://localhost:8000/jewelry/imageFile/update', frm, {
            }).then((response) => {
              console.log(response)
            }).catch((error) => {
              console.log(error)
            })
          }
        }
        let frm = new FormData()
        let imageFile = document.getElementById('addFile')
        for (let i = 0; i < imageFile.files.length; i++) {
          if (this.true_false_list[i] === true) {
            this.addDelegate_thumbnail_list.push('YES')
          } else {
            this.addDelegate_thumbnail_list.push('NO')
          }
          frm.append('file', imageFile.files[i])
        }
        frm.append('delegateThumbnail', this.addDelegate_thumbnail_list)
        frm.append('itemId', this.item_id)
        // for (let i = 0; i < imageFile.files.length; i++) {
        //   frm.append('file', imageFile.files[i])
        // }
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
    remove () {
      this.$swal
        .fire({
          icon: 'warning',
          title: '해당상품이 삭제됩니다.',
          text: '목록으로 이동하시겠습니까?',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          confirmButtonColor: '#FE9A2E',
          cancelButtonColor: '#BDBDBD',
          cancelButtonText: 'No'
        })
        .then(async (result) => {
          if (result.isConfirmed) {
            await axios
              .delete(`http://localhost:8000/jewelry/item/${this.id}`, {
                data: {
                  id: this.id
                }
              })
              .then(function (response) {
                console.log(response)
              })
              .catch(function (error) {
                console.log(error)
              })
            await this.$store.commit('itemDetail', {id: 0, urlPage: 'http://localhost:8000/jewelry/item/paging'})
            await this.$router.push('/adproduct')
          }
        })
    },
    async item () {
      await axios
        .get(this.$store.state.item.allItems)
        .then((res) => {
          let items = res.data.data
          let index = items.findIndex(
            (i) => i.id === this.$store.state.item.itemId
          )
          this.id = res.data.data[index].id
          this.name = res.data.data[index].name
          this.category_id = res.data.data[index].category_id
          this.price = res.data.data[index].price
          this.stock = res.data.data[index].stock
          this.status = res.data.data[index].status
          this.created_at = res.data.data[index].created_at.split('T')[0] + ' ' + res.data.data[index].created_at.split('T')[1].split('.')[0]
          this.created_by = res.data.data[index].created_by
          this.updated_at = res.data.data[index].updated_at.split('T')[0] + ' ' + res.data.data[index].updated_at.split('T')[1].split('.')[0]
        })
        .catch((err) => {
          console.log(err)
        })
      await axios
        .get(`http://localhost:8000/jewelry/item/${this.id}/itemInfo`, {
          data: {
            id: this.id
          }
        }).then((res) => {
          this.item_id = ''
          this.deleteImg_list = []
          this.stored_file_name_list = []
          this.delegate_thumbnail_list = []
          this.id_list = []
          this.image_file_list = []
          this.item_id = res.data.data.item_response.id
          this.image_file_list = res.data.data.item_response.image_file_response_list
          for (let i = 0; i < this.image_file_list.length; i++) {
            this.deleteImg_list.push(false)
            this.stored_file_name_list.push(this.image_file_list[i].stored_file_name)
            this.id_list.push(this.image_file_list[i].id)
            this.delegate_thumbnail_list.push(this.image_file_list[i].delegate_thumbnail)
            if (this.image_file_list[i].delegate_thumbnail === 'YES') {
              this.del_true_false_list.push(true)
            } else {
              this.del_true_false_list.push(false)
            }
          }
        })
    },
    categoryChange (event) {
      this.category_id = event.target.value
    },
    statusChange (event) {
      this.status = event.target.value
    },
    isFileChange () {
      this.isFile = true
      this.addDelegate_thumbnail_list = []
    }
  },
  created () {
    this.item()
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
  margin-bottom: 5rem
}

input {
  text-align: center;
  width: 100%;
  font-size: .8rem;
  height: 21px
}

input.no {
  width: 5%;
  font-size: .8rem
}

tr {
  border-bottom: 1px solid gray
}

th {
  padding: .6rem 0;
  font-size: .85rem;
  background-color: #e7e7e7;
  vertical-align: middle
}

td {
  font-size: .85rem
}

.img {
  overflow-x: scroll
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

.op {
  width: 100px;
  font-size: .8rem;
  border-radius: 2px;
  padding: .2rem;
  background-color: #fff;
  text-align: center
}

.noblock {
  text-align: center;
  padding-top: 1%;
  border: 0
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
