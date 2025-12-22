package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface KlineService {

    List<BinanceKlineEvent> getLatestKlines();

}
