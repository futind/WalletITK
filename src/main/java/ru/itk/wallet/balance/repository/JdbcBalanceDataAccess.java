package ru.itk.wallet.balance.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcBalanceDataAccess implements BalanceDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    public JdbcBalanceDataAccess(JdbcTemplate jdbcTemplate,
                                 PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    /**
     * Gets the balance of a wallet from a database
     *
     * @param walletId - id of a particular wallet
     * @return balance of a wallet {@link BigDecimal}
     */
    @Override
    public BigDecimal getBalance(UUID walletId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        // I must ensure that we read only committed data.
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setReadOnly(true);

        return Optional.ofNullable(transactionTemplate.execute(
                transactionStatus -> jdbcTemplate.query(
                        "SELECT balance FROM wallet WHERE id = ?",
                        rs -> rs.next() ? rs.getBigDecimal("balance") : null,
                        walletId
                )
        )).orElseThrow(WalletNotFoundException::new);
    }
}
