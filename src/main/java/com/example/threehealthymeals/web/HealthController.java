package com.example.threehealthymeals.web;

import com.example.threehealthymeals.config.auth.UserAccount;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {

    @GetMapping("/health")
    public String index(Model model, @AuthenticationPrincipal UserAccount user){
        if(user != null){
            model.addAttribute("nickName", user.getUsername());
        }
        return "health";
    }
}
