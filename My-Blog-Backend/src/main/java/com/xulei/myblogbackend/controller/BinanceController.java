package com.xulei.myblogbackend.controller;

import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import com.xulei.myblogbackend.service.KlineService;
import com.xulei.myblogbackend.webSocketService.BinanceWebSocketService;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.utils.BinanceWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/binance")
@CrossOrigin
public class BinanceController {

    @Autowired
    private KlineService klineService;


    @GetMapping()
    public List<BinanceKlineEvent> getHistoryInfo() {
        List<BinanceKlineEvent> latestKlines = klineService.getLatestKlines();
        return latestKlines;
    }


}