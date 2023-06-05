package com.example.staticcheck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("home")
    public String homepage() {
        return "index";
    }
    @GetMapping("login")
    public String loginpage() {
        return "login";
    }
    @GetMapping("admin")
    public String adminpage() {
        return "admin/index";
    }
    @GetMapping("users/user")
    public String userpage() {
        return "user/index";
    }
    @GetMapping("manage")
    public String managepage() {
        return "management/index";
    }
    @GetMapping("users/customer")
    public String customerpage() {
        return "admin/customer";
    }
    @GetMapping("users/employ")
    public String emploeepage() {
        return "admin/employ";
    }

    }

