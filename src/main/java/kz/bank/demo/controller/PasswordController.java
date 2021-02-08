package kz.bank.demo.controller;

import kz.bank.demo.persistance.model.User;
import kz.bank.demo.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PasswordController {
    PasswordEncoder passwordEncoder;
    @Autowired
    public void PasswordEncoder(PasswordEncoder passwordEncoder)
    { this.passwordEncoder = passwordEncoder;}

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/change")
    public String main(@AuthenticationPrincipal User user,
                       Model model) {
        model.addAttribute("user", user);
        model.addAttribute("passwordName", user.getUsername());
        return "change_password";
    }

    @PostMapping("/change")
    public String changePassword(@RequestParam String username,
                               @RequestParam String newPassword,
                                 Map<String, Object> model){
        User user = userRepository.findByUsername(username);
        user.setPassword("$2a$10$ItPuOYRPZzNT1pRoMg0jYOIFR0JQg9OBogvWm8AheCEW9.pHWli1S");
        userRepository.save(user);
        return "redirect:/main";
    }
}
