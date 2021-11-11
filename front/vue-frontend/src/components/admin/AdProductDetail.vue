<template>
  <div class="outer">
    <p class="top">상품상세</p>

    <!-- <p class="group">상품 정보</p> -->
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
                <!-- <input type="text" v-model="category_id"> -->
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
              <!-- <th scope="col">등록자</th>
              <td><input type="text" v-model="created_by" disabled></td> -->
              <th scope="col">등록날짜</th>
              <td><input type="text" v-model="created_at" disabled></td>
              <th scope="col">수정날짜</th>
              <td><input type="text" v-model="updated_at" disabled></td>
            </tr>
            <tr>
              <th scope="col">이미지</th>
              <td class="img" colspan="3">
                <div class="imgBox">
                  <div v-for="(image, i) in stored_file_name_list" :key="i" class="itemImage">
                    <img :src="image" v-if="stored_file_name_list[i]">
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
                    <div>
                      <input type="radio" v-bind:id="'thumnailInfo'+i" value="YES" v-model="delegate_thumbnail_list[i]"> YES
                      <input type="radio" v-bind:id="'thumnailInfo'+i" value="NO" v-model="delegate_thumbnail_list[i]"> NO
                    </div>
                    <!-- <div>
                      <input type="radio" v-bind:id="'deleteInfo'+i" value="YES" v-model="delete_check_list[i]"> YES
                      <input type="radio" v-bind:id="'deleteInfo'+i" value="NO" v-model="delete_check_list[i]"> NO
                    </div> -->
                    <a @click="imgfun(i)">삭제</a>
                  </div>
                  <!-- <p>썸네일리스트: {{ delegate_thumbnail_list }}</p> -->
                  <!-- <p>deleteImg_list: {{ deleteImg_list }}</p> -->
                </div>
              </td>
            </tr>
            <tr>
              <th scope="col">이미지추가</th>
              <td class="img" colspan="3">
                <v-file-input
                  id="addFile" name="addFiles"
                  label="addFile input" style="width: 200px;"
                  multiple="multiple" v-model="files"
                  @click="isFileChange"
                  >
                </v-file-input>
                <div v-if="isFile">
                  <ol>
                    <li v-for="(file, i) in files" :key="i" class="groupli">
                      파일이름: {{ file.name }}&nbsp;&nbsp; || &nbsp;
                      파일용량: {{ file.size * 0.001 }}kB &nbsp;&nbsp; || &nbsp;&nbsp;
                      썸네일등록:
                        <input type="radio" v-bind:id="'imageInfo'+i" value="YES" v-model="addDelegate_thumbnail_list[i]"> YES
                        <input type="radio" v-bind:id="'imageInfo'+i" value="NO" v-model="addDelegate_thumbnail_list[i]"> NO
                    </li>
                  </ol>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>

    <div class="button">
      <v-btn color="#D1CFC4" x-large @click="list">상품목록</v-btn>
      <v-btn color="#FBEF97" x-large @click="mod">저장</v-btn>
      <v-btn color="#f27474" x-large @click="remove">삭제</v-btn>
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
      delegate_thumbnail_list: [],
      id_list: [],
      item_id: '',
      files: [],
      isFile: false,
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
              console.log('파일이 있어요~~~~~~~~')
              frm.append('id', this.id_list[i])
              frm.append('delegateThumbnail', this.delegate_thumbnail_list[i])
              frm.append('deleteCheck', 'YES')
              frm.append('itemId', this.item_id)
              frm.append('file', imageFile.files[0])
              axios.put('http://localhost:8000/jewelry/imageFile/updateImg', frm, {
                headers: {
                  'Content-Type': 'multipart/form-data'
                }
              }).then((response) => {
                console.log(response)
              }).catch((error) => {
                console.log(error)
              })
            } else {
              axios.delete(`http://localhost:8000/jewelry/imageFile/${this.id_list[i]}`, {
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
            axios.put('http://localhost:8000/jewelry/imageFile/update', frm, {
            }).then((response) => {
              console.log(response)
            }).catch((error) => {
              console.log(error)
            })
          }
        }
        let frm = new FormData()
        let imageFile = document.getElementById('addFile')
        frm.append('delegateThumbnail', this.addDelegate_thumbnail_list)
        frm.append('itemId', this.item_id)
        for (let i = 0; i < imageFile.files.length; i++) {
          frm.append('file', imageFile.files[i])
        }
        if (imageFile.files[0]) {
          axios.post('http://localhost:8000/jewelry/imageFile/regImg', frm, {
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
            this.delegate_thumbnail_list.push(this.image_file_list[i].delegate_thumbnail)
            this.id_list.push(this.image_file_list[i].id)
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
  text-align: center;
}
p.top {
  font-weight: 700;
  font-size: 1.5rem;
  margin-bottom: 3rem;
}

.group {
  float: left;
  margin-bottom: 2rem;
}
.table {
  width: 850px;
  border-top: 0.2rem solid black;
  border-bottom: 0.2rem solid black;
  margin-bottom: 5rem;
}
input {
  text-align: center;
  width: 100%;
  font-size: 0.8rem;
}
input.no {
  width: 5%;
  font-size: 0.8rem;
}
tr {
  border-bottom: 1.5px solid gray;
  border-right: 1px solid black;
}
th {
  padding: 0.6rem 0;
  font-size: 0.85rem;
  background-color: #fefff2;
  border-right: 1px solid black;
  border-left: 1px solid black;
  vertical-align: middle;
}
td {
  font-size: 0.85rem;
}
.img {
  overflow-x: scroll;
}
.imgBox {
  display: block;
  /* background-color: thistle; */
}
.imgBox div {
  width: 300px;
  height: 100px;
  background-size: cover;
  /* background-color: yellowgreen; */
  /* background-image: url(https://ifh.cc/g/W8P7ct.jpg); */
  /* margin-left: 35px; */
}
.itemImage {
  display: flex;
  cursor: pointer;
  width: 200px;
  margin-bottom: 30px;
}
.button button {
  border: 1px solid black;
  margin: 0 0.6rem;
  padding: 0.5rem 2rem;
  font-weight: 700;
}
.button {
  margin-bottom: 2rem;
}
.op {
  /* border: 1px solid black; */
  width: 100px;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.2rem;
  background-color: white;
  text-align: center;
}
.noblock {
  text-align: center;
  padding-top: 1%;
  border: 0px;
}
.groupli {
  float: left;
}
</style>
