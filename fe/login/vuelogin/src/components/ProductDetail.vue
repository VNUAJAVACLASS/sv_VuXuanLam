<script setup>
const props = defineProps({
  product: {
    type: Object,
    required: true
  },
  addToCart: Function,
  goBack: Function
})

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)
}
</script>

<template>
  <div class="product-detail-container">
    <button class="back-btn" @click="goBack">← Quay lại</button>
    
    <div class="product-content">
      <div class="product-image">
        <img :src="product.imgUrl || 'https://via.placeholder.com/300x450'" :alt="product.title">
      </div>
      
      <div class="product-info">
        <h1>{{ product.title }}</h1>
        <p class="author">Tác giả: <span>{{ product.author }}</span></p>
        <p class="price">{{ formatPrice(product.price) }}</p>
        
        <div class="description">
          <h3>Mô tả sản phẩm:</h3>
          <p>{{ product.description || 'Chưa có mô tả cho sản phẩm này.' }}</p>
        </div>

        <div class="meta-info">
          <p><strong>Nhà xuất bản:</strong> {{ product.publisher || 'Đang cập nhật' }}</p>
          <p><strong>Tồn kho:</strong> {{ product.stock }}</p>
        </div>

        <button class="add-to-cart-btn" @click="addToCart(product)">Thêm vào giỏ hàng</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.product-detail-container {
  max-width: 1000px;
  width: 100%;
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}

.back-btn {
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  font-size: 1rem;
  margin-bottom: 20px;
  padding: 0;
  text-decoration: underline;
}

.product-content {
  display: flex;
  gap: 40px;
}

.product-image img {
  width: 300px;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

.product-info {
  flex: 1;
}

h1 {
  font-size: 2rem;
  margin-bottom: 10px;
  color: #2c3e50;
}

.author {
  font-size: 1.1rem;
  color: #555;
  margin-bottom: 15px;
}

.price {
  font-size: 1.5rem;
  color: #d32f2f;
  font-weight: bold;
  margin-bottom: 20px;
}

.description {
  margin-bottom: 20px;
  line-height: 1.6;
  color: #444;
}

.meta-info {
  margin-bottom: 30px;
  font-size: 0.95rem;
  color: #666;
}

.add-to-cart-btn {
  padding: 15px 30px;
  font-size: 1.1rem;
  background-color: #42b883;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s;
}

.add-to-cart-btn:hover {
  background-color: #3aa876;
}
</style>