package ru.asocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asocial.entity.BankTransactionEntity;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransactionEntity, Long> {
}
