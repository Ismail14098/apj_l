package kz.bank.demo.controller;

import kz.bank.demo.persistance.model.Card;
import kz.bank.demo.persistance.model.Transfere;
import kz.bank.demo.persistance.model.User;
import kz.bank.demo.persistance.repository.CardRepository;
import kz.bank.demo.persistance.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/main")
    public String main(Model model, @AuthenticationPrincipal User user) {
        Iterable<Card> cards = cardRepository.findByOwnerId(user.getId());
        List<Transfere> allcards = new ArrayList<>();
        cards.forEach(card -> {
            allcards.addAll(transactionRepository.findByIncome_card(card.getId()));
        });
        model.addAttribute("tranfers", allcards);
        model.addAttribute("cards", cards);
        model.addAttribute("name", user.getUsername());

        return "main";
    }
}