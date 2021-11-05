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
                  <div v-for="i in 3" :key="i"></div>
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
      stored_file_name: '',
      deleteImg: false
    }
  },
  methods: {
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
          location.href = '/adproduct'
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
        location.href = '/adproduct'
      })
    },
    mod () {
      if (!this.name || !this.price || !this.stock) {
        this.$swal.fire({
          icon: 'info',
          title: '상품명 OR 가격 OR 재고량이 비어있습니다.',
          confirmButtonColor: '#A9E2F3'
        })
      } else {
        axios
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
    }
  },
  categoryChange (event) {
    this.category_id = event.target.value
  },
  statusChange (event) {
    this.status = event.target.value
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
  display: flex;
}
.imgBox div {
  width: 100px;
  height: 100px;
  background-size: cover;
  background-image: url(https://ifh.cc/g/W8P7ct.jpg);
  margin-left: 35px;
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
</style>
