package org.example.codepart.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @GetMapping("/index")
    public String index(){
        return "Hello SpringBoot!";
    }
    @GetMapping("/test")
    public String testMapping() {
        return "This is a test mapping!";
    }

}

