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

/* 退出按钮区域：不被挤压，固定大小 */
.navbar>div:last-child {
    flex-shrink: 0;
    /* 移除原有固定 margin-right，避免布局错乱 */
    margin-left: 10px;
}

/* 设置按钮样式 */
.settings-btn {
    flex-shrink: 0;
}

/* 移动端汉堡菜单按钮 */
.mobile-menu-btn {
    display: none;
    flex-shrink: 0;
}

/* 移动端菜单遮罩层 */
.mobile-menu-overlay {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    z-index: 998;
}

.mobile-menu-overlay.show {
    display: block;
}

/* 移动端侧边菜单 */
.mobile-menu {
    display: none;
    position: fixed;
    top: 0;
    right: -280px;
    width: 280px;
    height: 100vh;
    background: #fff;
    z-index: 999;
    flex-direction: column;
    padding: 60px 20px 20px;
    box-shadow: -2px 0 8px rgba(0, 0, 0, 0.1);
    transition: right 0.3s ease;
}

.mobile-menu.show {
    right: 0;
}

.mobile-menu .mobile-nav-menu {
    list-style: none;
    padding: 0;
    margin: 0 0 20px 0;
}

.mobile-menu .mobile-nav-menu li {
    margin-bottom: 15px;
}

.mobile-menu .mobile-nav-menu li a {
    text-decoration: none;
    color: #333;
    font-size: 16px;
    display: block;
    padding: 10px 0;
    border-bottom: 1px solid #eee;
}

.mobile-menu .mobile-nav-menu li a:hover {
    color: #007bff;
}

.mobile-menu .mobile-search-box {
    margin-bottom: 20px;
}

.mobile-menu .mobile-search-box button {
    width: 100%;
    margin-top: 10px;
    padding: 10px;
    background-color: #000;
    color: #fff;
    border: none;
    border-radius: 20px;
    cursor: pointer;
}

.mobile-menu .mobile-actions {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.mobile-menu .mobile-actions button {
    width: 100%;
}

/* 关闭按钮 */
.mobile-menu-close {
    position: absolute;
    top: 15px;
    right: 15px;
    background: none;
    border: none;
    font-size: 24px;
    cursor: pointer;
    color: #666;
}

/* 移动端适配 */
@media (max-width: 768px) {
    .navbar {
        padding: 10px 15px;
        gap: 10px;
    }
    
    .logo span {
        font-size: 18px;
    }
    
    .nav-menu {
        display: none;
    }
    
    .search-box {
        display: none;
    }
    
    .button-row,
    .settings-btn,
    .navbar>div:last-child {
        display: none;
    }
    
    .mobile-menu-btn {
        display: block;
    }
    
    .mobile-menu {
        display: flex;
    }
}
</style>

<template>
    <nav class="navbar">
        <div class="logo">
            <span>XL Blog</span>
        </div>
        <ul class="nav-menu">
            <li><router-link to="/homePage" @click="resetTags()">首页</router-link></li>
            <li><router-link to="/archive">归档</router-link></li>
            <li><a :href="userInfoStore.blogSettings.githubUrl || 'https://github.com/wjxdy'" target="_blank" rel="noopener noreferrer">作品</a></li>
            <li><a href="#" @click="selectTool()">工具</a></li>
        </ul>
        <div class="search-box">
            <el-input v-model="userInfoStore.pageInfo.condition" size="large" placeholder="输入搜索内容"
                 clearable />
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
        <!-- 设置按钮 -->
        <div class="settings-btn">
            <el-tooltip class="box-item" effect="dark" content="设置" placement="bottom">
                <el-button @click="$router.push({ path: '/settings' })" circle size="large">
                    <el-icon size="20">
                        <Setting />
                    </el-icon>
                </el-button>
            </el-tooltip>
        </div>
        <div>
            <el-button round @click="exit()">{{ loginStatus }}</el-button>
        </div>
        
        <!-- 移动端汉堡菜单按钮 -->
        <div class="mobile-menu-btn">
            <el-button @click="showMobileMenu = true" circle size="large">
                <el-icon size="20">
                    <Menu />
                </el-icon>
            </el-button>
        </div>
    </nav>
    
    <!-- 移动端菜单遮罩 -->
    <div class="mobile-menu-overlay" :class="{ show: showMobileMenu }" @click="showMobileMenu = false"></div>
    
    <!-- 移动端侧边菜单 -->
    <div class="mobile-menu" :class="{ show: showMobileMenu }">
        <button class="mobile-menu-close" @click="showMobileMenu = false">✕</button>
        
        <ul class="mobile-nav-menu">
            <li><router-link to="/homePage" @click="resetTags(); showMobileMenu = false">首页</router-link></li>
            <li><router-link to="/archive" @click="showMobileMenu = false">归档</router-link></li>
            <li><a :href="userInfoStore.blogSettings.githubUrl || 'https://github.com/wjxdy'" target="_blank" rel="noopener noreferrer" @click="showMobileMenu = false">作品</a></li>
            <li><a href="#" @click.prevent="selectTool(); showMobileMenu = false">工具</a></li>
        </ul>
        
        <div class="mobile-search-box">
            <el-input v-model="userInfoStore.pageInfo.condition" size="large" placeholder="输入搜索内容" clearable />
            <button @click="handleMobileSearch()">搜索</button>
        </div>
        
        <div class="mobile-actions">
            <el-button @click="$router.push({ path: '/ArticleEdit' }); showMobileMenu = false" type="primary">
                <el-icon><EditPen /></el-icon> 创作
            </el-button>
            <el-button @click="$router.push({ path: '/settings' }); showMobileMenu = false">
                <el-icon><Setting /></el-icon> 设置
            </el-button>
            <el-button @click="handleMobileExit()">
                {{ loginStatus }}
            </el-button>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { Menu } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus'
import {  useRouter } from 'vue-router'
import { useUserInfoStore } from "@/stores/counter";
import { getArticlePageApi } from "@/api/article";

const showMobileMenu = ref(false);

const handleMobileSearch = () => {
    showMobileMenu.value = false;
    getArticlePage();
};

const handleMobileExit = () => {
    showMobileMenu.value = false;
    exit();
};

// 工具标签ID（需要替换为实际的工具标签ID）
const TOOL_TAG_ID = '699da7cd5f3340449cbf2977c5263037'

// 重置标签（点击首页时使用）
const resetTags = () => {
    userInfoStore.pageInfo.tags = []
    userInfoStore.pageInfo.currentPage = 1
    userInfoStore.pageInfo.condition = ''
    getArticlePage()
}

const selectTool = () => {
    // 清除其他标签，只保留工具标签
    userInfoStore.pageInfo.tags = [
        { articleTagId: TOOL_TAG_ID, articleTagName: '工具' }
    ]
    userInfoStore.pageInfo.currentPage = 1
    getArticlePage()
}

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
})
</script>
