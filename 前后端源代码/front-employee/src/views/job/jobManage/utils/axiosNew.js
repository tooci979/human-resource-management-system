import axios from 'axios'


axios.defaults.baseURL = '/api/'

axios.interceptors.request.use(config => config)

axios.interceptors.request.use(
  res => res,
  err => Promise.reject(err)
)

export default axios