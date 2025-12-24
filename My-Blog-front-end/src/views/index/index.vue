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
            <el-header style="min-height: 100px;padding-bottom: 10px; background: transparent;">

                <headerView />

            </el-header>
            

            <el-main style="margin: 20px,0; padding: 0; flex: 1; overflow: auto;">


                <RouterView />


            </el-main>
            
        </el-container>

        <newsView />
    </div>
    <el-dialog v-model="useUserInfoStore().headerCenterDialogVisible" title="添加标签" width="500" align-center>
        <el-input v-model="input" style="width: 240px;padding: 0px auto;" placeholder="添加标签" />
        
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="useUserInfoStore().headerCenterDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="addTag()">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script lang="ts" setup>
import { useUserInfoStore } from "../../stores/counter";
import { ref } from "vue";
import headerView from "@/views/headerStyle/index.vue";
import newsView from "@/views/mapInfo/index.vue"
import { addTagApi } from "@/api/tag";
import { ElMessage } from 'element-plus';

const userInfoStore = useUserInfoStore();
const input = ref("");
const addTag = async () => {
    
    if (!input.value.trim()) { // 建议用trim()过滤纯空白输入
        ElMessage.error("标签不能为空");
        return;
    }
    const result = await addTagApi(input.value);
    if (result.code == 200) {
        ElMessage.success("添加成功");
        console.log(result.data);
        userInfoStore.tagList.push(result.data);
        userInfoStore.headerCenterDialogVisible = false;
        input.value = "";
    } else {
        ElMessage.error(result.message || "添加失败"); // 增加可选链避免报错
    }
};
</script>