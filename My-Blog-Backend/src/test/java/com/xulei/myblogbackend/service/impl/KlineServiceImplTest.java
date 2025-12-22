package com.xulei.myblogbackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class KlineServiceImplTest {
    String key = "kline:BTCUSDT:1m";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    void getLatestKlines() {
        // 取最新 100 条
        Set<ZSetOperations.TypedTuple<String>> latestKlines = stringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(key, 0, 99);

        if (latestKlines == null) {
            return;
        }
        List<BinanceKlineEvent> list = latestKlines.stream()
                .map(item -> JSON.parseObject(item.getValue(), BinanceKlineEvent.class))
                .toList();
        // 如果你希望按时间升序返回（老数据在前），可以反转列表
        Collections.reverse(list);
        log.info("redis info:{}",list);
    }
}