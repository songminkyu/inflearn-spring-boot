package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HellowController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Hello Spring!");
        return "hello";
    }
}
