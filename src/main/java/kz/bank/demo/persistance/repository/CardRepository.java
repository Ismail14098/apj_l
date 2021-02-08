package kz.bank.demo.persistance.repository;

import kz.bank.demo.persistance.model.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findByOwnerId(Long user_id);

    Card findByNumber(Long number);

}
