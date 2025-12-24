<style scoped lang="css">
/* 导航栏整体样式：核心 Flex 布局，确保所有元素同一排 */
.navbar {
    
    display: flex;
    align-items: center;
    /* 垂直居中 */
    justify-content: space-between;
    /* 元素两端分布，中间自动填充空间 */
    padding: 10px 20px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    min-height:80%;
    width: 100%;
    /* 占满全屏宽度 */
    box-sizing: border-box;
    /* 避免 padding 撑大宽度 */
    flex-wrap: nowrap;
    /* 强制所有元素不换行，保持同一排 */
    gap: 20px;
    /* 元素之间的均匀间距，替代零散 margin */
}

/* logo 样式 */
.logo {
    display: flex;
    align-items: center;
    flex-shrink: 0;
    /* 不被挤压，保持固定大小 */
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

/* 导航菜单样式：固定在左侧（logo右侧），不被挤压 */
.nav-menu {
    display: flex;
    list-style: none;
    margin: 0;
    padding: 0;
    flex-shrink: 0;
    /* 不被挤压，保持菜单布局完整 */
    gap: 25px;
    /* 菜单项之间均匀间距，替代 margin */
}

.nav-menu li {
    margin: 0;
    /* 移除原有 margin，改用 gap 控制间距 */
}

.nav-menu li a {
    text-decoration: none;
    color: #333;
    font-size: 16px;
}

.nav-menu li a:hover {
    color: #007bff;
}

/* 搜索框样式：核心布局，实现输入框居中、左右分布菜单和按钮 */
.search-box {
    display: flex;
    align-items: center;
    /* 输入框与按钮垂直居中 */
    justify-content: center;
    /* 内部元素水平居中 */
    flex: 1;
    /* 关键：占据 logo、菜单、右侧按钮之间的所有剩余空间，实现输入框居中 */
    gap: 15px;
    /* 输入框与搜索按钮之间的均匀间距 */
    min-width: 300px;
    /* 最小宽度，避免窗口缩小后挤压变形 */
}

/* 让 Element UI/Plus 输入框占满搜索区域的中间空间 */
.search-box :deep(.el-input) {
    width: 50%;
    /* 输入框占满父容器剩余空间，实现居中填充 */
}

.search-box button {
    min-width: 60px;
    padding: 8px 20px;
    background-color: #000;
    color: #fff;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    flex-shrink: 0;
    /* 按钮不被挤压，保持固定大小 */
}

.search-box button:hover {
    background-color: #333;
}

/* 创作按钮区域：不被挤压，固定大小 */
.button-row {
    flex-shrink: 0;
    /* 移除原有固定 padding，改用 flex 布局控制 */
    margin: 0 10px;
    /* 适度间距，替代固定 padding */
}

/* 标签样式 */
.tag-item {
    margin-bottom: 10px;
    text-align: center;
}

/* 退出按钮区域：不被挤压，固定大小 */
.navbar>div:last-child {
    flex-shrink: 0;
    /* 移除原有固定 margin-right，避免布局错乱 */
    margin-left: 10px;
}

/* 新增：TagFrom 样式 - 独立一行 + 搜索框正下方精准对齐 */
.TagFrom {
    /* 1. 上下间距：与导航栏分隔，避免紧贴 */
    margin: 0px 50% 0px 20% ;
    /* 2. 宽度匹配：与搜索框内输入框宽度一致，实现左右对齐 */
    width: 40%;
    /* 3. 标签水平居中排列，保持美观 */
    text-align: center;
    /* 5. 可选：限制最大宽度，大屏下不显得过宽 */
    max-width: 600px;
    /* 6. 可选：盒子模型优化，避免padding撑宽 */
    box-sizing: border-box;
}

/* 新增：标签项间距优化，避免重叠拥挤 */
.TagFrom .el-tag {
    margin: 0 5px 5px 0;
}
</style>

<template>
    <nav class="navbar">
        <div class="logo">
            <img src="../../assets/myHeaderlogo/IMG_1787.jpeg" alt="logo">
            <span>{{ sign !== null ? userInfoStore.loginData.name :"" }}</span>
            
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
                                
                                <div class="tag-item" v-for="item in userInfoStore.tagList" :key="item.articleTagId">
                                    <el-col :span="6" :gutter="20" class="tag-title">
                                        <el-button round @click="handletagSelect(item)">{{ item.articleTagName }}</el-button>   
                                    </el-col>                     
                                </div>
                                <el-col :span="6" :gutter="20">
                                    <el-button @click="addTag()" round>
                                        <el-icon ><Plus /></el-icon>
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
            <el-input v-model="userInfoStore.pageInfo.condition" size="large" placeholder="输入搜索内容"
                 clearable />
            <!-- 2. 用于显示已创建的 Tag 标签 -->
            

            <button @click="getArticlePage()">搜索</button>
            
            
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
    <div class="TagFrom">
        <el-tag v-for="tag in userInfoStore.pageInfo.tags" @close="handleClose(tag)" :key="tag.articleTagId" closable :type="tag.articleTagId === '' ? 'success' : 'info'">
            {{ tag.articleTagName }}
        </el-tag>
    </div>

</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus'
import {  useRouter } from 'vue-router'
import { useUserInfoStore } from "@/stores/counter";
import { getTagListApi } from '@/api/tag';
import { getArticlePageApi } from "@/api/article";
import type {  Tag } from "@/types/api";


const handleClose = (tag: Tag) => {
    const index = userInfoStore.pageInfo.tags.indexOf(tag);
    if (index > -1) {
        userInfoStore.pageInfo.tags.splice(index, 1);
    }
};
const getArticlePage = async () => {
    try {

        const result = await getArticlePageApi(userInfoStore.pageInfo);

        if (result.code == 200) {
            userInfoStore.ArticleList = result.data.data;
            userInfoStore.pageInfo.pageTotal = result.data.pageTotal;
        } else {
            ElMessage.error("查询失败");
        }
    } catch (error) {
        console.error('Error fetching articles:', error);
        ElMessage.error("网络错误，请稍后重试");
    }
}
const userInfoStore = useUserInfoStore();

const addTag = () => {

    userInfoStore.headerCenterDialogVisible = true;
}


const handletagSelect = (item: Tag) => {
    console.log('Selected tag:', item);
    // 在这里处理分类选择逻辑，例如导航到分类页面
    userInfoStore.pageInfo.tags.push(item);
};


const loadTagList =async() => {
    // 调用tagList接口  
    const res = await getTagListApi()
    userInfoStore.tagList = res.data;
};

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
        const userStore = userInfoStore;
        userStore.loginData = {
            id: '',
            name: '',
            token: '',
            username: ''
        }; // 或清空为初始值
        userStore.pageInfo = {
          pageSize: 5,
          pageTotal: 0,
          currentPage: 1,
          condition: '',
          tags: []
        };
        userStore.ArticleList = [];
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
