package ru.itk.wallet.balance.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class JdbcBalanceDataAccessTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcBalanceDataAccess balanceDAO;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void throwsWalletNotFoundExceptionWhenProvidedFalseID() {
        assertThrowsExactly(
                WalletNotFoundException.class,
                () -> balanceDAO.getBalance(UUID.randomUUID())
        );
    }

    @Test
    void returnsCorrectValueWhenProvidedWithCorrectId() {
        UUID walletId = UUID.randomUUID();
        BigDecimal balance = new BigDecimal("1234.56");

        jdbcTemplate.update(
                "INSERT INTO wallet (id, balance) VALUES (?, ?)",
                walletId, balance
        );

        assertDoesNotThrow(() -> balanceDAO.getBalance(walletId));
        assertEquals(balance, balanceDAO.getBalance(walletId));
    }

}