package com.ecg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller para servir páginas HTML
 */
@Controller
public class ViewController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
