<style lang="css" scoped>
.common-layout {
    
    width: 100vw;
    /* 占满屏幕宽度 */
    height: 100vh;
    /* 强制占满屏幕高度（核心） */
    
    margin: 0;
    padding: 0;
    box-sizing: border-box;

    /* 避免宽度计算错误 */
    /* 用100vh确保完全占满屏幕高度 */
    background-image: url('../../assets/myHeaderlogo/BackGround2.png');
    background-size: auto 100%;
    /* 背景图覆盖全屏，保持比例（推荐） */
    background-position: center center;
    background-repeat: no-repeat;
    background-attachment: fixed;
}
.el-container {
    height: 100%;
}


</style>
<template>



    <div class="common-layout">
        <el-container>
            <el-header style="min-height: 80px; background: transparent;">

                <headerView />

            </el-header>
            

            <el-main style="margin: 0; padding: 0; flex: 1; overflow: auto;">


                <RouterView />


            </el-main>
            
        </el-container>

        <newsView />
    </div>




</template>

<script lang="ts" setup>
import { ref } from "vue";
import headerView from "@/views/headerStyle/index.vue";
import newsView from "@/views/mapInfo/IpDeviceDetector.vue";


</script>