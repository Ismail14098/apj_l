package kz.bank.demo.persistance.repository;

import kz.bank.demo.persistance.model.Transfere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transfere, Long> {
    @Query(value = "SELECT * FROM transfere WHERE income_card = ?1", nativeQuery = true)
    List<Transfere> findByIncome_card(Long id);
}