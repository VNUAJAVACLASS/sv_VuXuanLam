import request from '@/utils/request'

export function getCart() {
  return request.get('/cart')
}

export function addToCart(data) {
  return request.post('/cart/add', data)
}

export function updateCartItem(data) {
  return request.put('/cart/update', data)
}

export function removeCartItem(bookId) {
  return request.delete(`/cart/remove/${bookId}`)
}