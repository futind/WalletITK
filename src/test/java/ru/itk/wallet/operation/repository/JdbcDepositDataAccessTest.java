package ru.itk.wallet.operation.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.itk.wallet.operation.exception.NonPositiveAmountException;
import ru.itk.wallet.operation.exception.OperationForbiddenException;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class JdbcDepositDataAccessTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcDepositDataAccess depositDAO;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private UUID walletId;
    private BigDecimal balance;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS wallet");
        jdbcTemplate.execute("CREATE TABLE wallet (id UUID PRIMARY KEY, balance NUMERIC)");
        walletId = UUID.randomUUID();
        balance = new BigDecimal("1000.00");
        jdbcTemplate.update("INSERT INTO wallet (id, balance) VALUES (?, ?)", walletId, balance);
    }

    @Test
    void depositIsCorrectlyDoneWhenIdCorrectAndAmountPositive() {
        BigDecimal amountToDeposit = balance;
        BigDecimal expectedResult = balance.add(amountToDeposit);

        BigDecimal result = depositDAO.deposit(walletId, amountToDeposit);
        assertTrue(expectedResult.compareTo(result) == 0);

        BigDecimal dbResult = jdbcTemplate.queryForObject("SELECT balance FROM wallet WHERE id = ?", BigDecimal.class, walletId);
        assertTrue(expectedResult.compareTo(dbResult) == 0);
    }

    @Test
    void depositThrowsIfIdNotValid() {
        assertThrowsExactly(WalletNotFoundException.class, () -> depositDAO.deposit(UUID.randomUUID(), balance));
    }

    @Test
    void depositThrowsIfAmountToDepositIsZero() {
        BigDecimal amountToDeposit = BigDecimal.ZERO;

        assertThrowsExactly(NonPositiveAmountException.class, () -> depositDAO.deposit(walletId, amountToDeposit));
    }

    @Test
    void depositThrowsIfAmountIsNegative() {
        BigDecimal amountToDeposit = new BigDecimal("-1000.00");

        assertThrowsExactly(NonPositiveAmountException.class, () -> depositDAO.deposit(walletId, amountToDeposit));
    }
}