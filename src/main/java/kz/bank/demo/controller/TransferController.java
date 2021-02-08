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
public class TransferController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transfer")
    public String transfere(Model model, @AuthenticationPrincipal User user) {
        List<Card> cards = cardRepository.findByOwnerId(user.getId());
        if (cards.isEmpty())
            return "redirect:/main";

        model.addAttribute("cards", cards);

        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfereMoney(@AuthenticationPrincipal User user,
                                @RequestParam String outcomeCard,
                                @RequestParam String incomeCard,
                                @RequestParam String amount, Model model){
        Card outcome = cardRepository.findByNumber(Long.valueOf(outcomeCard));
        Card income = cardRepository.findByNumber(Long.valueOf(incomeCard));
        if (outcome == null || income == null)
            return "redirect:/main";
        outcome.setBalance_kzt(outcome.getBalance_kzt() - Double.valueOf(amount));
        income.setBalance_kzt(income.getBalance_kzt() + Double.valueOf(amount));


        cardRepository.save(outcome);
        cardRepository.save(income);

        Transfere transaction = new Transfere(income, outcome, Double.valueOf(amount));
        transactionRepository.save(transaction);

        return "redirect:/main";
    }

    @GetMapping("/send")
    public String send(Model model, @AuthenticationPrincipal User user) {
        List<Card> cards = cardRepository.findByOwnerId(user.getId());
        Iterable<Card> all1 = cardRepository.findAll();

        if (cards.isEmpty())
            return "redirect:/main";

        List<Card> all = new ArrayList<>();
        all1.forEach(card -> {
            if (!card.getOwner().getId().equals(user.getId())){
                all.add(card);
            }
        });

        model.addAttribute("cards", cards);
        model.addAttribute("all", all);

        return "send";
    }

    @PostMapping("/send")
    public String sendMoney(@AuthenticationPrincipal User user,
                                 @RequestParam String outcomeCard,
                                 @RequestParam String incomeCard,
                                 @RequestParam String amount, Model model){
        if (incomeCard == null){
            return "redirect:/main";
        }
        Card outcome = cardRepository.findByNumber(Long.valueOf(outcomeCard));
        Card income = cardRepository.findByNumber(Long.valueOf(incomeCard));
        if (outcome == null || income == null)
            return "redirect:/main";

        if (outcome.getBalance_kzt() - Double.valueOf(amount) < 0) {
            return "redirect:/main";
        }
        outcome.setBalance_kzt(outcome.getBalance_kzt() - Double.valueOf(amount));
        income.setBalance_kzt(income.getBalance_kzt() + Double.valueOf(amount));

        cardRepository.save(outcome);
        cardRepository.save(income);

        Transfere transaction = new Transfere(income, outcome, Double.valueOf(amount));
        transactionRepository.save(transaction);

        return "redirect:/main";
    }
}
