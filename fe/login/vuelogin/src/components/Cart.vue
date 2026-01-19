<script setup>
import { computed } from 'vue'

const props = defineProps({
  cart: {
    type: Array,
    required: true
  },
  removeFromCart: Function,
  updateQuantity: Function
})

const totalPrice = computed(() => {
  return props.cart.reduce((total, item) => total + (item.price * item.quantity), 0)
})

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)
}
</script>

<template>
  <div class="cart-container">
    <h2>Giỏ Hàng Của Bạn</h2>
    
    <div v-if="cart.length === 0" class="empty-cart">
      <p>Giỏ hàng đang trống.</p>
    </div>

    <div v-else>
      <div class="cart-items">
        <div v-for="item in cart" :key="item.id" class="cart-item">
          <img :src="item.imgUrl || 'https://via.placeholder.com/100'" :alt="item.title" class="item-img">
          <div class="item-info">
            <h3>{{ item.title }}</h3>
            <p class="price">{{ formatPrice(item.price) }}</p>
          </div>
          <div class="item-quantity">
            <button @click="updateQuantity(item.id, item.quantity - 1)" :disabled="item.quantity <= 1">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="updateQuantity(item.id, item.quantity + 1)">+</button>
          </div>
          <button class="remove-btn" @click="removeFromCart(item.id)">Xóa</button>
        </div>
      </div>

      <div class="cart-summary">
        <h3>Tổng cộng: {{ formatPrice(totalPrice) }}</h3>
        <button class="checkout-btn">Thanh Toán</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cart-container {
  width: 100%;
  max-width: 800px;
  background: white;
  padding: 30px;
  border-radius: 16px;
  box-shadow: 0 10px 25px rgba(0,0,0,0.05);
}

h2 {
  margin-bottom: 25px;
  color: #111827;
  font-size: 1.8rem;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 20px 0;
  border-bottom: 1px solid #f3f4f6;
  gap: 20px;
}

.item-img {
  width: 80px;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.item-info {
  flex: 1;
}

.item-info h3 {
  font-size: 1.1rem;
  color: #1f2937;
  margin-bottom: 5px;
}

.item-info .price {
  color: #059669;
  font-weight: 600;
}

.item-quantity {
  display: flex;
  align-items: center;
  gap: 12px;
  background: #f9fafb;
  padding: 5px;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
}

.item-quantity button {
  padding: 4px 12px;
  background: white;
  color: #374151;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  font-weight: bold;
}

.item-quantity button:hover:not(:disabled) {
  border-color: #42b883;
  color: #42b883;
}

.remove-btn {
  background-color: #fee2e2;
  color: #ef4444;
  padding: 8px 16px;
  font-size: 0.9rem;
  transition: all 0.2s;
}

.remove-btn:hover {
  background-color: #ef4444;
  color: white;
}

.cart-summary {
  margin-top: 20px;
  text-align: right;
  border-top: 2px solid #f3f4f6;
  padding-top: 20px;
}

.checkout-btn {
  margin-top: 15px;
  font-size: 1.1rem;
  padding: 12px 40px;
  background: linear-gradient(135deg, #10b981, #059669);
  box-shadow: 0 4px 10px rgba(16, 185, 129, 0.3);
}

.checkout-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(16, 185, 129, 0.4);
}

.empty-cart {
  text-align: center;
  padding: 40px;
}
</style>