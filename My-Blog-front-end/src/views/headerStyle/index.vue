<style scoped lang="css">
/* 导航栏整体样式 */
.navbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 20px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* logo 样式 */
.logo {
    display: flex;
    align-items: center;
}

.logo img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
}

.logo span {
    font-size: 20px;
    font-weight: bold;
}

/* 导航菜单样式 */
.nav-menu {
    display: flex;
    list-style: none;
    margin: 0;
    padding: 0;
}

.nav-menu li {
    margin: 0 15px;
}

.nav-menu li a {
    text-decoration: none;
    color: #333;
    font-size: 16px;
}

.nav-menu li a:hover {
    color: #007bff;
}

/* 搜索框样式 */
.search-box {
    display: flex;
    align-items: center;
}

.search-box input {
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 20px;
    width: 200px;
    margin-right: 10px;
}

.search-box button {
    padding: 8px 20px;
    background-color: #000;
    color: #fff;
    border: none;
    border-radius: 20px;
    cursor: pointer;
}

.search-box button:hover {
    background-color: #333;
}
.button-row{
    padding-right: 200px;

}
</style>

<template>
    <nav class="navbar">
        <div class="logo">
            <img src="../../assets/myHeaderlogo/IMG_1787.jpeg" alt="logo">
            <span>赏微不</span>
        </div>
        <ul class="nav-menu">
            <li><router-link to="/homePage">首页</router-link></li>
            <li><a href="#">分类</a></li>
            <li><a href="#">标签</a></li>
            <li><a href="#">作品</a></li>
            <li><a href="#">友链</a></li>
            <li><a href="#">订阅</a></li>
            <li><a href="#">关于</a></li>
        </ul>
        <div class="search-box">
            <input type="text" placeholder="搜索文章...">
            <button>搜索</button>
        </div>
        <div class="button-row">
            <el-tooltip class="box-item" effect="dark" content="创作" placement="bottom">
                <el-button @click="$router.push({ path: '/ArticleEdit' })" circle size="large">
                    <el-icon size="25">
                        <EditPen />
                    </el-icon>
                </el-button>
            </el-tooltip>
        </div>
        <div>
            <el-button round @click="exit()" style="margin-right:200px ;">退出登录</el-button>
        </div>
    </nav>

</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus'
import {  useRouter } from 'vue-router'
import { useUserInfoStore } from "@/stores/counter";
const activeIndex = ref('1')

const router = useRouter()
const handleSelect = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}

const exit = () => {

    // 清除localStorage中的用户信息
    localStorage.removeItem('userInfo');

    // 更新pinia中的用户状态
    const userStore = useUserInfoStore();
    userStore.loginData = {
        sex: '',
        id: '',
        name: '',
        token: '',
        username: ''
    }; // 或清空为初始值

    // 显示退出成功消息
    ElMessage.success("已成功退出登录");

    // 跳转到登录页
    router.push("/login");



 }
</script>
