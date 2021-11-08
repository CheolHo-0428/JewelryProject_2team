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
              <td><input type="text" placeholder="상품명 입력" v-model="name"></td>
              <th scope="col">재고량</th>
              <td><input type="number" placeholder="재고량 입력" v-model="stock"></td>
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
              <td><input type="number" placeholder="상품가격 입력" v-model="price"></td>
            </tr>
            <tr>
              <th scope="col">이미지</th>
              <td class="img" colspan="3">
                <v-file-input id="file" name="files" label="File input" style="width: 200px;" multiple="multiple"></v-file-input>
                <!-- <input type="file" multiple> -->
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>

    <div class="button">
      <v-btn color="#D1CFC4" x-large @click="list">상품목록</v-btn>
      <v-btn color="#FBEF97" x-large @click="addItem">등록</v-btn>
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
      item_id: ''
    }
  },
  methods: {
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
        frm.append('delegateThumbnail', this.delegate_thumbnail)
        frm.append('itemId', this.item_id)
        console.log('************************', imageFile.files)
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
    categoryChange (event) {
      this.category_id = event.target.value
    }
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
  width: 100% !important;
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
.op {
  border: 1px solid black;
  width: fit-content;
  font-size: 0.8rem;
  border-radius: 2px;
  padding: 0.2rem;
  background-color: white;
  text-align: center;
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

</style>
