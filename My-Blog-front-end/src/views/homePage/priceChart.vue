<template>
    <div ref="el" class="price-chart"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import { createChart, CandlestickSeries } from 'lightweight-charts'
import { Client, IMessage } from '@stomp/stompjs'
import { fetchHistoryDataApi } from '@/api/kLine'

const el = ref<HTMLDivElement | null>(null)
let chart: any = null
let series: any = null
let stompClient: Client | null = null

/* =========================
   1️⃣ 初始化图表
========================= */
const initChart = () => {
    if (!el.value) return

    chart = createChart(el.value, {
        width: el.value.clientWidth,
        height: 140,
        layout: {
            background: { color: 'transparent' },
            textColor: '#6b7280',
        },
        grid: {
            vertLines: { visible: false },
            horzLines: { visible: false },
        },
        rightPriceScale: { visible: true, borderVisible: false },
        timeScale: { timeVisible: true, visible: true, borderVisible: true },
        crosshair: { vertLine: { visible: false }, horzLine: { visible: true } },
    })
}

/* =========================
   2️⃣ 创建蜡烛图 series
========================= */
const createSeries = () => {
    if (!chart) return

    series = chart.addSeries(CandlestickSeries, {
        upColor: '#22c55e',
        downColor: '#ef4444',
        borderVisible: false,
        wickUpColor: '#22c55e',
        wickDownColor: '#ef4444',
        priceFormat: { type: 'price', precision: 4, minMove: 0.0001 },
    })
}

/* =========================
   3️⃣ 获取历史数据
========================= */
const fetchHistoryData = async () => {
    try {
        const res = await fetchHistoryDataApi()
        const data = res.data // 假设返回数组

        data.reverse().forEach((item: any) => {
            const k = item.k
            if (!k || !k.x) return
            series?.update({
                time: Math.floor(k.t / 1000),
                open: Number(k.o),
                high: Number(k.h),
                low: Number(k.l),
                close: Number(k.c),
            })
        })
    } catch (err) {
        console.error('获取历史 K 线数据失败', err)
    }
}

/* =========================
   4️⃣ 连接 STOMP WebSocket 并订阅
========================= */
const connectWebSocket = () => {
    stompClient = new Client({
        brokerURL: 'wss://xuleii.cn/ws',
        reconnectDelay: 5000,
    })

    stompClient.onConnect = () => {
        stompClient?.subscribe('/topic/kline/', (message: IMessage) => {
            try {
                const data = JSON.parse(message.body)
                const k = data.k
                if (!k || !k.x) return
                series?.update({
                    time: Math.floor(k.t / 1000),
                    open: Number(k.o),
                    high: Number(k.h),
                    low: Number(k.l),
                    close: Number(k.c),
                })
            } catch (err) {
                console.error('解析 K 线数据异常', err)
            }
        })
    }

    stompClient.onStompError = (frame) => {
        console.error('STOMP 错误', frame)
    }

    stompClient.activate()
}

/* =========================
   生命周期
========================= */
onMounted(async () => {
    await nextTick()
    initChart()
    createSeries()
    await fetchHistoryData() // 先加载历史数据
    connectWebSocket()       // 再连接 WebSocket
})

onBeforeUnmount(() => {
    stompClient?.deactivate()
    chart?.remove()
})
</script>

<style scoped>
.price-chart {
    width: 100%;
    height: 140px;
}
</style>
