package com.yingke.tiostarter.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyk 2019/6/6 9:32
 * 测试
 */
@RestController
public class DemoController {

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo() {
        return "hello,SpringBoot!";
    }
}
