package com.xulei.myblogbackend.utils;

import com.alibaba.fastjson2.JSON;
import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import com.xulei.myblogbackend.service.BinanceKlineHistoryService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.xulei.myblogbackend.utils.StringContext.*;

@Component
public class ZSetKlineCacheUtil {

    private StringRedisTemplate stringRedisTemplate;

    private BinanceKlineHistoryService binanceKlineHistoryService;

    public ZSetKlineCacheUtil(StringRedisTemplate stringRedisTemplate, BinanceKlineHistoryService binanceKlineHistoryService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.binanceKlineHistoryService = binanceKlineHistoryService;
    }

    public void checkAndHandleZSetData(){

        Long zSetSize = stringRedisTemplate.opsForZSet().zCard(KLINE_CACHE_KEY);
        if (zSetSize == null || zSetSize <= ZSET_THRESHOLD) {
            // 未超过200条，无需处理
            return;
        }
        // 2. 获取ZSet中最后100条数据（score最大的100条，即倒序前100条）
        // zRevRange：按score倒序获取，参数：key、起始索引（0）、结束索引（99）→ 取前100条（即最后100条）
        Set<String> zSetLast100Str = stringRedisTemplate.opsForZSet().reverseRange(KLINE_CACHE_KEY, 0, BATCH_INSERT_COUNT - 1);
        if (CollectionUtils.isEmpty(zSetLast100Str)) {
            return;
        }
        // 3. JSON字符串转BinanceKlineEvent列表
        List<BinanceKlineEvent> klineEventList = zSetLast100Str.stream()
                .map(jsonStr -> {
                    try {
                        return JSON.parseObject( jsonStr, BinanceKlineEvent.class);
                    } catch (Exception e) {
                        // 可选：添加日志记录
                        // log.error("ZSet元素反序列化失败", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        // 4. 批量入库
        if (!CollectionUtils.isEmpty(klineEventList)) {
            binanceKlineHistoryService.addKlineHistoryCache(klineEventList);
        }
        // 5. 关键修正：使用removeRangeByRank替代zRemRangeByRank，删除已入库数据
        // 正序排名：起始索引=总条数-100，结束索引=总条数-1（精准删除最后100条）
        long startRank = zSetSize - BATCH_INSERT_COUNT;
        long endRank = zSetSize - 1;
        stringRedisTemplate.opsForZSet()
                .removeRange(KLINE_CACHE_KEY, startRank, endRank);
    }

}
