<style lang="css" scoped>
.myinfo {
    font-size: 14px;
    color: #666;
    padding: 10px;
}
</style>

<template>
    <div class="myinfo">
        xl的个人博客  
        github地址: <a href="https://github.com/wjxdy/my-blog" target="_blank"
            rel="noopener noreferrer">https://github.com/wjxdy/my-blog</a>
    </div>
</template>


<script lang="ts" setup>
import { useRouter } from 'vue-router'
const router = useRouter()
</script>