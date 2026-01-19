import request from '@/utils/request'

export function getBooks(params) {
  return request.get('/books', { params })
}