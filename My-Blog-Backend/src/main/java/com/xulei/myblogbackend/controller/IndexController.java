package com.xulei.myblogbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class IndexController {

    @GetMapping("index")
    public Map<String, Object> index(){


        HashMap<String, Object> map = new HashMap<>();

        map.put("xulei","123456");

        return map;
    }




}
