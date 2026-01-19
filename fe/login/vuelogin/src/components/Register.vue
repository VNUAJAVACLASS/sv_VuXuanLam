<script setup>
import { ref } from 'vue'
import { register } from '@/services/auth.service'

const form = ref({
  username: '',
  password: '',
  fullname: '',
  email: '',
  phone: ''
})
const message = ref('')

const handleRegister = async () => {
  try {
    const response = await register(form.value)

    message.value = 'Đăng ký thành công! Vui lòng đăng nhập.'
    // Reset form
    form.value = { username: '', password: '', fullname: '', email: '', phone: '' }
  } catch (error) {
    console.error(error)
    if (error.response && error.response.data) {
      message.value = error.response.data.message || 'Đăng ký thất bại'
    } else {
      message.value = 'Lỗi kết nối đến server'
    }
  }
}
</script>

<template>
  <div class="form-container">
    <h2>Đăng Ký</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label>Tên đăng nhập</label>
        <input v-model="form.username" type="text" required />
      </div>
      <div class="form-group">
        <label>Mật khẩu</label>
        <input v-model="form.password" type="password" required />
      </div>
      <div class="form-group">
        <label>Họ và tên</label>
        <input v-model="form.fullname" type="text" required />
      </div>
      <div class="form-group">
        <label>Email</label>
        <input v-model="form.email" type="email" required />
      </div>
      <div class="form-group">
        <label>Số điện thoại</label>
        <input v-model="form.phone" type="text" required />
      </div>
      <button type="submit">Đăng ký</button>
    </form>
    <p v-if="message" :class="{ 'error': !message.includes('thành công'), 'success': message.includes('thành công') }">
      {{ message }}
    </p>
  </div>
</template>

<style scoped>
.form-container {
  background-color: #ffffff;
  max-width: 400px;
  width: 100%;
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}
h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #2c3e50;
}
button {
  width: 100%;
  margin-top: 1rem;
}
</style>