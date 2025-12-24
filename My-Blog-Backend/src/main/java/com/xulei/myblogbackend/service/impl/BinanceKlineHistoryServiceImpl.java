package com.xulei.myblogbackend.service.impl;

import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import com.xulei.myblogbackend.entity.BinanceKlineHistory;
import com.xulei.myblogbackend.entity.Kline;
import com.xulei.myblogbackend.mapper.BinanceKlineHistoryMapper;
import com.xulei.myblogbackend.service.BinanceKlineHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BinanceKlineHistoryServiceImpl
        extends ServiceImpl<BinanceKlineHistoryMapper, BinanceKlineHistory>
        implements BinanceKlineHistoryService {

    private final BinanceKlineHistoryMapper binanceKlineHistoryMapper;

    public BinanceKlineHistoryServiceImpl(BinanceKlineHistoryMapper binanceKlineHistoryMapper) {
        this.binanceKlineHistoryMapper = binanceKlineHistoryMapper;
    }

    @Override
    public void addKlineHistoryCache(List<BinanceKlineEvent> list) {

        List<BinanceKlineHistory> historyList = list.stream().map(event -> {
            BinanceKlineHistory history = new BinanceKlineHistory()
                    .setEventType(event.getEventType())
                    .setEventTime(event.getEventTime())
                    .setSymbol(event.getS() != null ? event.getS() : event.getK().getS())
                    .setKlineStartTime(event.getK().getStartTime())
                    .setKlineEndTime(event.getK().getEndTime())
                    .setIntervalType(event.getK().getI())
                    .setFirstTradeId(event.getK().getF())
                    .setLastTradeId(event.getK().getLastTradeId())
                    .setOpenPrice(parseBigDecimal(event.getK().getO()))
                    .setClosePrice(parseBigDecimal(event.getK().getC()))
                    .setHighPrice(parseBigDecimal(event.getK().getH()))
                    .setLowPrice(parseBigDecimal(event.getK().getLowPrice()))
                    .setVolume(parseBigDecimal(event.getK().getV()))
                    .setTradeCount((int) event.getK().getN())
                    .setIsKlineClosed(event.getK().isX())
                    .setQuoteVolume(parseBigDecimal(event.getK().getQ()))
                    .setBuyVolume(parseBigDecimal(event.getK().getV()))
                    .setBuyQuoteVolume(parseBigDecimal(event.getK().getQ()))
                    .setIgnoreParam(event.getK().getB());
            return history;
        }).toList();

        this.saveBatch(historyList, 100);
    }

    private BigDecimal parseBigDecimal(String value) {
        return value != null ? new BigDecimal(value) : BigDecimal.ZERO;
    }

}
