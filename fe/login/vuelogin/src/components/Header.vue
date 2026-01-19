<script setup>
defineProps({
  isLoggedIn: Boolean,
  username: String,
  cartCount: Number
})

const emit = defineEmits(['navigate', 'logout'])
</script>

<template>
  <header>
    <div class="container">
      <div class="brand" @click="emit('navigate', 'home')">
        <h1>BookShop</h1>
      </div>

      <nav>
        <a href="#" @click.prevent="emit('navigate', 'home')">Trang chủ</a>
        
        
        <template v-if="isLoggedIn">
          <a href="#" @click.prevent="emit('navigate', 'cart')" class="cart-link">
            Giỏ hàng <span class="badge" v-if="cartCount > 0">{{ cartCount }}</span>
          </a>
          <span class="user-greeting">Xin chào, <b>{{ username }}</b></span>
          <a href="#" @click.prevent="emit('logout')" class="logout-link">Đăng xuất</a>
        </template>

        
        <template v-else>
          <a href="#" @click.prevent="emit('navigate', 'login')">Đăng nhập</a>
          <a href="#" @click.prevent="emit('navigate', 'register')">Đăng ký</a>
        </template>
      </nav>
    </div>
  </header>
</template>

<style scoped>
header {
  background-color: #ffffff;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.brand h1 {
  color: #42b883;
  margin: 0;
  font-size: 1.5rem;
  cursor: pointer;
}

nav {
  display: flex;
  align-items: center;
  gap: 20px;
}

nav a {
  text-decoration: none;
  color: #2c3e50;
  font-weight: 600;
  transition: color 0.2s;
}

nav a:hover {
  color: #42b883;
}

.cart-link {
  position: relative;
}

.badge {
  background-color: #ff4444;
  color: white;
  font-size: 0.75rem;
  padding: 2px 6px;
  border-radius: 10px;
  position: absolute;
  top: -8px;
  right: -10px;
}

.user-greeting {
  color: #555;
}

.logout-link {
  color: #e74c3c !important;
}
</style>