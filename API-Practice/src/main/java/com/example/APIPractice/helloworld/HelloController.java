package com.example.APIPractice.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private MessageSource messageSource;

    //    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("hello-world-bean/path-variable1/{name}")
    public HelloWorldBean helloWorldBean1(@PathVariable String name) {
        return new HelloWorldBean(name);
    }

    @GetMapping("hello-world-bean/path-variable2/{name}")
    public HelloWorldBean helloWorldBean2(@PathVariable String name) {
        log.info("info log = {}", name);
        return new HelloWorldBean(String.format("Hello World %s", name));
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        log.info("Accept-Language : {}", locale);
        return messageSource.getMessage("greeting.message", null, locale);
    }

}
