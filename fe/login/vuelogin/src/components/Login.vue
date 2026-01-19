<script setup>
import { ref } from 'vue'
import { login } from '@/services/auth.service'

const username = ref('')
const password = ref('')
const message = ref('')
const emit = defineEmits(['login-success'])

const handleLogin = async () => {
  try {
    const response = await login({
      username: username.value,
      password: password.value
    })

    message.value = 'Đăng nhập thành công!'
    console.log('Auth Info:', response.data.data)
   
    localStorage.setItem('token', response.data.data.token)
    
    const user = response.data.data.username || response.data.data.fullname || username.value
    localStorage.setItem('username', user)
    emit('login-success')
  } catch (error) {
    console.error(error)
    if (error.response && error.response.data) {
      message.value = error.response.data.message || 'Đăng nhập thất bại'
    } else {
      message.value = 'Lỗi kết nối đến server'
    }
  }
}
</script>

<template>
  <div class="form-container">
    <h2>Đăng Nhập</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label>Tên đăng nhập</label>
        <input v-model="username" type="text" required />
      </div>
      <div class="form-group">
        <label>Mật khẩu</label>
        <input v-model="password" type="password" required />
      </div>
      <button type="submit">Đăng nhập</button>
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