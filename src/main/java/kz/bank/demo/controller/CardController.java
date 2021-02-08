package kz.bank.demo.controller;

import kz.bank.demo.persistance.model.Card;
import kz.bank.demo.persistance.model.Role;
import kz.bank.demo.persistance.model.User;
import kz.bank.demo.persistance.repository.CardRepository;
import kz.bank.demo.persistance.repository.UserRepository;
import kz.bank.demo.utils.CreditCardNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collections;

@Controller
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/create")
    public String creation(Model model) {
        model.addAttribute("card", new Card());
        return "create";
    }

    @PostMapping("/create")
    public String addCard(@ModelAttribute("card") @Valid Card card, @AuthenticationPrincipal User user) {
//        CreditCardNumberGenerator c = new CreditCardNumberGenerator();
//        String num = c.generate("Own", 19);
        card.setOwner(user);
        card.setBank("RBK");
        cardRepository.save(card);
        return "redirect:/main";
    }
}
