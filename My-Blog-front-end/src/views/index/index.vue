<style lang="css" scoped>
.common-layout {
    /* 绝对不要保留 min-width: 2000px，这是最可能的罪魁祸首 */
    width: 100%;
    /* 自适应父容器宽度 */
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    /* 避免宽度计算错误 */
}

.content-wrapper {
    /* 核心：水平居中并限制最大宽度 */
    max-width: 1200px;
    /* 根据设计需求调整最大宽度 */
    width: 100%;
    margin: 0 auto;
    /* 左右自动外边距实现水平居中 */

    /* 可选：添加左右内边距，在小屏幕上有更好的留白 */
    padding: 0 15px;
    box-sizing: border-box;
}
</style>
<template>



    <div class="common-layout">
        <el-container>
            <el-header style="min-height: 80px;">

                <headerView />

            </el-header>
            <el-container>

                <el-main>

                    <div class="content-wrapper">
                        <RouterView />
                    </div>

                </el-main>
            </el-container>
        </el-container>
    </div>



</template>

<script lang="ts" setup>
import { ref } from "vue";
import headerView from "@/views/headerStyle/index.vue";



</script>