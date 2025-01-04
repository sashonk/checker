package ru.asocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.asocial.entity.InvestorEntity;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<InvestorEntity, Long> {

    InvestorEntity findByCode(String code);

    @Query(nativeQuery = true, value = "select coalesce(sum(amount), 0) from bank_transaction where client_code = :clientCode")
    Optional<BigDecimal> calculateSaldo(String clientCode);
}
