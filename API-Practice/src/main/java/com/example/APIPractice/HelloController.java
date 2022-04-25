package com.example.APIPractice;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    //    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

}
