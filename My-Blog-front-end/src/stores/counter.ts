import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})
export const useUserInfoStore = defineStore('userInfo', {
    state: () => ({
        // 定义需要绑定的变量
        loginData: {
            id: '',
            name:'',
            token:'',
            username:''
        } //登录信息
    })
})    