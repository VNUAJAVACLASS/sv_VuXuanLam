<script setup>
import { ref, onMounted } from 'vue'
import { getBooks } from '@/services/book.service'

const props = defineProps({
  addToCart: Function,
  viewProduct: Function
})

const books = ref([])
const loading = ref(true)
const error = ref(null)
const currentPage = ref(0)
const totalPages = ref(0)

const fetchBooks = async (page = 0) => {
  loading.value = true
  try {
    
    const response = await getBooks({
      page: page,
      size: 8, 
      sort: 'createdAt,desc'
    })

    
    if (response.data && response.data.data) {
      books.value = response.data.data.content
      totalPages.value = response.data.data.totalPages
      currentPage.value = page
    }
  } catch (err) {
    console.error(err)
    error.value = 'Không thể tải danh sách sách. Vui lòng thử lại sau.'
  } finally {
    loading.value = false
  }
}

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)
}

onMounted(() => {
  fetchBooks()
})
</script>

<template>
  <div class="home-container">
    <h2 class="section-title">Danh Sách Sách Mới</h2>

    <div v-if="loading" class="loading">Đang tải dữ liệu...</div>
    <div v-if="error" class="error">{{ error }}</div>

    
    <div v-if="!loading && !error" class="book-grid">
      <div v-for="book in books" :key="book.id" class="book-card">
        <div class="image-wrapper" @click="viewProduct(book)">
          
          <img :src="book.imgUrl || 'https://via.placeholder.com/200x300?text=No+Image'" :alt="book.title" />
        </div>
        <div class="book-info">
          <h3 :title="book.title" @click="viewProduct(book)">{{ book.title }}</h3>
          <p class="author">Tác giả: {{ book.author }}</p>
          <p class="price">{{ formatPrice(book.price) }}</p>
          <button class="buy-btn" @click="addToCart(book)">Thêm vào giỏ hàng</button>
        </div>
      </div>
    </div>

    
    <div v-if="!loading && !error && totalPages > 1" class="pagination">
      <button :disabled="currentPage === 0" @click="fetchBooks(currentPage - 1)">Trước</button>
      <span>Trang {{ currentPage + 1 }} / {{ totalPages }}</span>
      <button :disabled="currentPage === totalPages - 1" @click="fetchBooks(currentPage + 1)">Sau</button>
    </div>
  </div>
</template>

<style scoped>
.home-container {
  width: 100%;
  max-width: 1200px;
}

.section-title {
  font-size: 2rem;
  margin-bottom: 2rem;
  color: #111827;
  text-align: center;
  font-weight: 800;
}

.book-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 30px;
  margin-bottom: 40px;
}

.book-card {
  background: #fff;
  border: none;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
}

.book-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.image-wrapper {
  height: 250px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f3f4f6;
  cursor: pointer;
}

.image-wrapper img {
  height: 100%;
  width: auto;
  object-fit: cover;
}

.book-info {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.book-info h3 {
  font-size: 1.15rem;
  margin: 0 0 8px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #1f2937;
  font-weight: 700;
  cursor: pointer;
}

.book-info h3:hover {
  color: #42b883;
}

.author {
  font-size: 0.95rem;
  color: #6b7280;
  margin-bottom: 12px;
}

.price {
  font-size: 1.25rem;
  color: #059669;
  font-weight: bold;
  margin-top: auto;
  margin-bottom: 15px;
}

.buy-btn {
  width: 100%;
  background-color: #10b981;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
}

.buy-btn:hover {
  background-color: #059669;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 20px;
}

.pagination button {
  width: auto;
  padding: 8px 20px;
  margin: 0;
  background-color: white;
  color: #374151;
  border: 1px solid #d1d5db;
}

.pagination button:disabled {
  background-color: #f3f4f6;
  color: #9ca3af;
  cursor: not-allowed;
}
</style>