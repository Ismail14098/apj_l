package kz.bank.demo.persistance.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long number;
    private String bank;
    private Double balance_kzt;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    public Card() {
    }

}

