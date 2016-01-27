package net.ruready.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
 
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/hello";
    }
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(ModelMap model) {
        String str = "Hello World!";
        model.addAttribute("message", str);
 
        return "hello";
    }
 
}