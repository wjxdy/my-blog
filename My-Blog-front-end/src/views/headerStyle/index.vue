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

.tag-item{

    margin-bottom: 10px;
    text-align: center;
}



</style>

<template>
    <nav class="navbar">
        <div class="logo">
            <img src="../../assets/myHeaderlogo/IMG_1787.jpeg" alt="logo">
            <span>{{ sign !== null ? useUserInfoStore().loginData.name :"" }}</span>
            
        </div>
        <ul class="nav-menu">
            <li><router-link to="/homePage">首页</router-link></li>
            <li>
                <el-tooltip placement="bottom" effect="light" :show-arrow="true"
                    :popper-style="{ width: '250px', padding: '10px' }" 
                    >
                    <!-- 触发元素 -->
                    <a href="javascript:;" class="tag-trigger">分类</a>

                    <!-- 自定义 content 插槽（嵌入分类选择） -->
                    <template #content>
                        <div class="tag-selector">
                            <!-- 简单分类列表（可替换为 el-select/自定义组件） -->
                             <el-row justify="space-evenly">
                                
                                <div class="tag-item" v-for="item in tagList" :key="item.articleTagId">
                                    <el-col :span="6" :gutter="20" class="tag-title">
                                        <el-button round @click="handletagSelect(item)">{{ item.articleTagName }}</el-button>   
                                    </el-col>                     
                                </div>
                                <el-col :span="6" :gutter="20">
                                    <el-button round>
                                        <el-icon @click="addTag()"><Plus /></el-icon>
                                    </el-button>
                                </el-col>
                                
                            </el-row>   
                            
                        </div>
                    </template>
                </el-tooltip>
            </li>
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
            <el-button round @click="exit()" style="margin-right:200px ;">{{ loginStatus }}</el-button>
        </div>
    </nav>

</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus'
import {  useRouter } from 'vue-router'
import { useUserInfoStore } from "@/stores/counter";
import { getTagListApi } from '@/api/tag';
const addTag = () => {
    ElMessage.info("分类添加功能正在开发中，敬请期待！")
}

const handletagSelect = (item: { articleTagId: string; articleTagName: string }) => {
    console.log('Selected tag:', item);
    // 在这里处理分类选择逻辑，例如导航到分类页面
};


const loadTagList = () => {
    // 调用tagList接口  
    getTagListApi().then(res => {
        tagList.value = res.data
    })
};

const tagList = ref();

const loginStatus = ref('退出登录')
const router = useRouter()



const sign = ref(localStorage.getItem('userInfo'))
const loadLoginInfo = () => { 
    if (sign.value !==null) {
        loginStatus.value = '退出登录'
    } else { 
        loginStatus.value = '登录'
    }

}

const exit = () => {

    if (loginStatus.value === '退出登录') {
        // 清除localStorage中的用户信息
        localStorage.removeItem('userInfo');

        // 更新pinia中的用户状态
        const userStore = useUserInfoStore();
        userStore.loginData = {
            id: '',
            name: '',
            token: '',
            username: ''
        }; // 或清空为初始值
        loginStatus.value = '登录'
        // 显示退出成功消息
        ElMessage.success("已成功退出登录");
    }else {
        router.push('/login')
     }
}




onMounted(() => { 
    loadLoginInfo()
    loadTagList()

})
</script>
