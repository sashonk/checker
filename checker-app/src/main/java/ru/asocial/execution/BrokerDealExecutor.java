package ru.asocial.execution;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.asocial.entity.BankTransactionEntity;
import ru.asocial.entity.InvestorEntity;
import ru.asocial.model.BrokerDeal;
import ru.asocial.model.ExecutionResult;
import ru.asocial.repository.BankTransactionRepository;
import ru.asocial.repository.InvestorRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class BrokerDealExecutor {

    private static final Logger log = LoggerFactory.getLogger(BrokerDealExecutor.class);

    @PostConstruct
    public void init() {
        investorRepository.deleteAll();

        InvestorEntity inv1 = new InvestorEntity();
        inv1.setActive(true);
        inv1.setCode("invN1");
        inv1.setName("Investor One");
        investorRepository.save(inv1);

        InvestorEntity inv2 = new InvestorEntity();
        inv2.setActive(true);
        inv2.setCode("invN2");
        inv2.setName("Investor Two");
        investorRepository.save(inv2);

        InvestorEntity inv3 = new InvestorEntity();
        inv3.setActive(true);
        inv3.setCode("invN3");
        inv3.setName("Investor 3");
        investorRepository.save(inv3);

        InvestorEntity inv4 = new InvestorEntity();
        inv4.setActive(true);
        inv4.setCode("invN4");
        inv4.setName("Investor 4");
        investorRepository.save(inv4);

        InvestorEntity inv5 = new InvestorEntity();
        inv5.setActive(true);
        inv5.setCode("invest5");
        inv5.setName("Investor Five");
        investorRepository.save(inv5);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    @KafkaListener(topics = "broker-deals.registered", groupId = "deal-executors")
    public void onBrokerDeal(String brokerDeal, Acknowledgment acknowledgment) throws Exception {
        try {
            log.debug("received deal: " + brokerDeal);
            BrokerDeal deal = objectMapper.readValue(brokerDeal, BrokerDeal.class);
            InvestorEntity investor = investorRepository.findByCode(deal.getClientCode());

            if (investor == null || !Boolean.TRUE.equals(investor.getActive())) {
                log.debug("Missing or inactive investor. Skip deal");
                ExecutionResult result = new ExecutionResult();
                result.setBrokerDealId(deal.getId());
                result.setResult("Investor not found or is not active");
                result.setExecutedAt(LocalDateTime.now());
                kafkaTemplate.send("execution.results", objectMapper.writeValueAsString(result));
                acknowledgment.acknowledge();
                return;
            }
            log.debug("investor ok.");
            BigDecimal quantity = deal.getSecuritiesQuantity();

            log.debug("check saldo");
            BigDecimal saldo = investorRepository.calculateSaldo(investor.getCode()).orElse(BigDecimal.ZERO);

            if (saldo.add(quantity).compareTo(BigDecimal.ZERO) < 0) {
                log.debug("negative saldo");
                ExecutionResult result = new ExecutionResult();
                result.setBrokerDealId(deal.getId());
                result.setResult("Negative saldo");
                result.setExecutedAt(LocalDateTime.now());
                kafkaTemplate.send("execution.results", objectMapper.writeValueAsString(result));
                acknowledgment.acknowledge();
                return;
            }

            log.debug("perform fin operation");
            BankTransactionEntity transaction = new BankTransactionEntity();
            transaction.setAmount(quantity);
            transaction.setClientCode(investor.getCode());
            transaction.setSecurityCode(deal.getSecurityCode());
            transaction.setTs(LocalDateTime.now());
            transaction.setDescription(deal.getTradeNumber());
            bankTransactionRepository.save(transaction);

            log.debug("send success to kafka");
            ExecutionResult result = new ExecutionResult();
            result.setBrokerDealId(deal.getId());
            result.setResult("SUCCESS");
            result.setExecutedAt(LocalDateTime.now());
            kafkaTemplate.send("execution.results", objectMapper.writeValueAsString(result));
            acknowledgment.acknowledge();
            log.debug("finished");
        }
        catch (Exception exc) {
            log.error("Deal execution failed", exc);
        }
    }
}
