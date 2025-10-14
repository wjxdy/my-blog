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
</style>
<template>


    
        <div class="common-layout">
            <el-container>
                <el-header style="min-height: 80px;">
                    
                    <headerView />

                </el-header>
                <el-container>
                    <el-aside width="260px" style="min-height: 700px;"></el-aside>
                    <el-main>
                        
                        <RouterView />

                    </el-main>
                </el-container>
            </el-container>
        </div>
   


</template>

<script lang="ts" setup>
import { ref } from "vue";
import headerView from "@/views/headerStyle/index.vue";



</script>