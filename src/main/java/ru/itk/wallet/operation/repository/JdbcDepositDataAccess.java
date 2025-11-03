package ru.itk.wallet.operation.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import ru.itk.wallet.operation.exception.NonPositiveAmountException;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcDepositDataAccess implements DepositDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    public JdbcDepositDataAccess(JdbcTemplate jdbcTemplate,
                                 PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    /**
     * A method which allows user to deposit some amount of money to their wallet.
     *
     * @param walletId        - unique identifier of a wallet (UUID).
     * @param amountToDeposit - amount of money user wants to deposit
     * @return updated amount of money on the account (in the wallet)
     * @throws NonPositiveAmountException - if amountToDeposit is negative or zero
     * @throws WalletNotFoundException    - if wallet does not exist
     */
    @Override
    public BigDecimal deposit(UUID walletId, BigDecimal amountToDeposit) {

        if (amountToDeposit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NonPositiveAmountException();
        }

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setReadOnly(false);

        return transactionTemplate.execute( status -> {
            BigDecimal balance = Optional.ofNullable(jdbcTemplate.query(
                    "SELECT balance FROM wallet WHERE id = ? FOR UPDATE",
                    rs -> rs.next() ? rs.getBigDecimal("balance") : null,
                    walletId
            )).orElseThrow(WalletNotFoundException::new);

            BigDecimal newBalance = balance.add(amountToDeposit);

            jdbcTemplate.update(
                    "UPDATE wallet SET balance = ? WHERE id = ?",
                    newBalance,
                    walletId
            );

            return newBalance;
        });
    }
}
