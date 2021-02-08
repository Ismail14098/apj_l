package kz.bank.demo.controller;

import kz.bank.demo.persistance.model.Role;
import kz.bank.demo.persistance.model.User;
import kz.bank.demo.persistance.repository.CardRepository;
import kz.bank.demo.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class LoginController {
    PasswordEncoder passwordEncoder;

    @Autowired
    public void PasswordEncoder(PasswordEncoder passwordEncoder)
    { this.passwordEncoder = passwordEncoder;}

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        User foundUser = userRepository.findByUsernameAndPassword(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        if (foundUser != null) {
            return "registration";
        }
        return "redirect:/main";
    }
}
