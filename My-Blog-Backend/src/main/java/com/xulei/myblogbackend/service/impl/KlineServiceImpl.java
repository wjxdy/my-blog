package com.xulei.myblogbackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import com.xulei.myblogbackend.service.KlineService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class KlineServiceImpl implements KlineService {

    String key = "kline:BTCUSDT:1m";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<BinanceKlineEvent> getLatestKlines() {
        // 取最新 100 条
        Set<ZSetOperations.TypedTuple<String>> latestKlines = stringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(key, 0, 99);

        if (latestKlines == null) {
            return Collections.emptyList();
        }
        List<BinanceKlineEvent> list = latestKlines.stream()
                .map(item -> JSON.parseObject(item.getValue(), BinanceKlineEvent.class))
                .toList();
        return list;
    }
}
