package com.example.APIPractice.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class HelloController {

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

}
