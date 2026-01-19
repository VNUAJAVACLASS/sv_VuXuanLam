<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import Home from './components/Home.vue'
import Cart from './components/Cart.vue'
import ProductDetail from './components/ProductDetail.vue'
import { getCart, addToCart as apiAddToCart, removeCartItem, updateCartItem } from '@/services/cart.service'

const currentView = ref('home')
const cart = ref([])
const isLoggedIn = ref(false)
const username = ref('')
const selectedProduct = ref(null)

onMounted(() => {
  const token = localStorage.getItem('token')
  if (token) {
    isLoggedIn.value = true
    username.value = localStorage.getItem('username') || 'User'
    fetchCart()
  }
})


const handleLoginSuccess = () => {
  isLoggedIn.value = true
  username.value = localStorage.getItem('username') || 'User'
  currentView.value = 'home'
  fetchCart()
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  isLoggedIn.value = false
  username.value = ''
  cart.value = []
  currentView.value = 'login'
}

// Hàm xem chi tiết sản phẩm
const viewProduct = (product) => {
  selectedProduct.value = product
  currentView.value = 'product-detail'
}

const fetchCart = async () => {
  if (!isLoggedIn.value) return
  try {
    const response = await getCart()
    if (response.data && response.data.data) {
      const items = response.data.data.cartItems || [] 
      cart.value = items.map(item => ({
        id: item.bookId, 
        title: item.bookTitle,
        price: item.price,
        quantity: item.quantity,
        imgUrl: item.imgUrl
      }))
    }
  } catch (error) {
    console.error("Lỗi tải giỏ hàng:", error)
    if (error.response && error.response.status === 401) {
      isLoggedIn.value = false
      localStorage.removeItem('token')
    }
  }
}


const addToCart = async (product) => {
  if (!isLoggedIn.value) {
    alert("Vui lòng đăng nhập để mua hàng")
    currentView.value = 'login'
    return
  }
  try {
    await apiAddToCart({
      bookId: product.id,
      quantity: 1
    })
    
    alert('Đã thêm "' + product.title + '" vào giỏ hàng!')
    fetchCart() // Cập nhật lại giỏ hàng
  } catch (error) {
    console.error(error)
    alert("Lỗi khi thêm vào giỏ hàng")
  }
}

// Xóa khỏi giỏ hàng (API)
const removeFromCart = async (bookId) => {
  try {
    await removeCartItem(bookId)
    fetchCart()
  } catch (error) {
    console.error(error)
    alert("Lỗi khi xóa sản phẩm")
  }
}

// Cập nhật số lượng (API)
const updateQuantity = async (bookId, quantity) => {
  try {
    await updateCartItem({ bookId, quantity })
    fetchCart()
  } catch (error) {
    console.error(error)
  }
}

const cartCount = computed(() => {
  return cart.value.reduce((total, item) => total + item.quantity, 0)
})

// Tự động cuộn lên đầu trang khi chuyển trang để tránh bị nhảy xuống giữa trang
watch(currentView, () => {
  window.scrollTo(0, 0)
})
</script>

<template>
  <Header 
    :isLoggedIn="isLoggedIn"
    :username="username"
    :cartCount="cartCount"
    @navigate="(view) => currentView = view"
    @logout="logout"
  />

  <main>
    <Home v-if="currentView === 'home'" :addToCart="addToCart" :viewProduct="viewProduct" />
    <Login v-if="currentView === 'login'" @login-success="handleLoginSuccess" />
    <Register v-if="currentView === 'register'" />
    <Cart 
      v-if="currentView === 'cart'" 
      :cart="cart" 
      :removeFromCart="removeFromCart" 
      :updateQuantity="updateQuantity" 
    />
    <ProductDetail 
      v-if="currentView === 'product-detail'" 
      :product="selectedProduct" 
      :addToCart="addToCart"
      :goBack="() => currentView = 'home'"
    />
  </main>

  <Footer />
</template>

<style>
/* Reset & Base Styles */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Inter', 'Segoe UI', sans-serif;
  background-color: #f8f9fa;
  color: #2c3e50;
  line-height: 1.6;
  -webkit-font-smoothing: antialiased;
  overflow-y: scroll;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

main {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 2rem;
}

/* Global styles for forms */
.form-group {
  margin-bottom: 1.25rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #374151;
  font-size: 0.95rem;
}

.form-group input {
  width: 100%;
  padding: 0.85rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s;
}

.form-group input:focus {
  outline: none;
  border-color: #42b883;
  box-shadow: 0 0 0 4px rgba(66, 184, 131, 0.15);
  background-color: #fff;
}

button {
  padding: 0.75rem 1.25rem;
  background-color: #42b883;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.95rem;
  font-weight: 600;
  transition: all 0.2s ease;
  letter-spacing: 0.3px;
}

button:hover {
  background-color: #3aa876;
  transform: translateY(-1px);
}

button:active {
  transform: translateY(0);
}

.error { color: #e74c3c; margin-top: 0.5rem; font-size: 0.9rem; }
.success { color: #2ecc71; margin-top: 0.5rem; font-size: 0.9rem; }
</style>
