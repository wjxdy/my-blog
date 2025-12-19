<template>
    
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { newsApi }from '@/api/map'
// 核心状态
const loading = ref(false); // 整体加载状态
const globalError = ref(''); // 全局错误（如网络异常）
const locationDenied = ref(false); // 定位权限被拒绝
const locationUnsupported = ref(false); // 浏览器不支持定位

// 存储所有信息（和数据库字段对应）
const info = ref({
    ip: '',
    ipLocation: '',
    browser: '',
    os: '',
    deviceType: '',
    longitude: null, // 经度
    latitude: null,  // 纬度
    accuracy: null   // 定位精度（米）
});

// 计算是否有可展示的结果
const hasResult = computed(() => {
    return info.value.ip || info.value.browser || info.value.longitude !== null;
});

/**
 * 点击链接后：并行获取所有信息
 */
const fetchAllInfo = async () => {
    // 重置状态
    loading.value = true;
    globalError.value = '';
    locationDenied.value = false;
    locationUnsupported.value = false;
    info.value = { ...info.value, longitude: null, latitude: null, accuracy: null };

    try {
        // 并行执行两个任务：获取 IP/设备信息 + 获取经纬度
        await Promise.all([
            fetchIpAndDeviceInfo(), // 任务1：IP + 设备信息
            fetchLocation()         // 任务2：经纬度（自动触发权限申请）
        ]);
    } catch (err) {
        globalError.value = `获取失败：${err.message || '网络异常'}`;
    } finally {
        newsApi(info.value)
        loading.value = false;
    }
};

/**
 * 任务1：获取 IP + 设备信息
 */
const fetchIpAndDeviceInfo = async () => {
    // 1. 解析设备信息（浏览器原生 API，无网络请求）
    parseDeviceInfo();

    // 2. 调用免费接口获取公网 IP 和归属地
    try {
        // 备选接口：https://ipapi.co/json/ 或 https://api.ipify.org?format=json
        const res = await fetch('https://ipleak.net/json/');
        if (!res.ok) throw new Error('IP 接口请求失败');

        const data = await res.json();
        info.value.ip = data.ip;
        info.value.ipLocation = `${data.country_name || ''} ${data.region_name || ''} ${data.city_name || ''}`.trim();
    } catch (err) {
        info.value.ip = '获取失败';
        console.warn('IP 获取失败：', err);
    }
};

/**
 * 解析设备信息（浏览器 userAgent）
 */
const parseDeviceInfo = () => {
    const ua = navigator.userAgent;
    // 浏览器
    if (ua.includes('Chrome')) info.value.browser = 'Chrome';
    else if (ua.includes('Firefox')) info.value.browser = 'Firefox';
    else if (ua.includes('Safari') && !ua.includes('Chrome')) info.value.browser = 'Safari';
    else if (ua.includes('Edge')) info.value.browser = 'Edge';
    else if (ua.includes('MSIE') || ua.includes('Trident')) info.value.browser = 'IE';
    else info.value.browser = '未知';

    // 操作系统
    if (ua.includes('Windows')) info.value.os = 'Windows';
    else if (ua.includes('Mac OS')) info.value.os = 'Mac OS';
    else if (ua.includes('Linux')) info.value.os = 'Linux';
    else if (ua.includes('Android')) info.value.os = 'Android';
    else if (ua.includes('iOS')) info.value.os = 'iOS';
    else info.value.os = '未知';

    // 设备类型
    if (ua.includes('Mobile') || ua.includes('Android') || ua.includes('iOS')) info.value.deviceType = '移动设备';
    else if (ua.includes('Tablet')) info.value.deviceType = '平板设备';
    else info.value.deviceType = '桌面设备';
};

/**
 * 任务2：获取经纬度（自动触发定位权限申请）
 */
const fetchLocation = async () => {
    // 检查浏览器是否支持定位
    if (!navigator.geolocation) {
        locationUnsupported.value = true;
        return;
    }

    // 调用定位 API（自动弹窗申请权限）
    return new Promise((resolve) => {
        navigator.geolocation.getCurrentPosition(
            // 授权成功：获取经纬度
            (position) => {
                info.value.longitude = position.coords.longitude;
                info.value.latitude = position.coords.latitude;
                info.value.accuracy = position.coords.accuracy;
                resolve();
            },
            // 授权失败/定位失败
            (error) => {
                if (error.code === error.PERMISSION_DENIED) {
                    locationDenied.value = true;
                } else {
                    console.warn('定位失败：', error.message);
                }
                resolve(); // 即使失败也 resolve，不阻塞其他任务
            },
            // 定位配置
            { enableHighAccuracy: true, timeout: 10000, maximumAge: 60000 }
        );
    });
};

onMounted(() => { 
    fetchAllInfo()

})
</script>

