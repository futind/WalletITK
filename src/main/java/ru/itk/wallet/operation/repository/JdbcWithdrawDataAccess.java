package ru.itk.wallet.operation.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import ru.itk.wallet.operation.dto.enumerations.OperationType;
import ru.itk.wallet.operation.exception.NonPositiveAmountException;
import ru.itk.wallet.operation.exception.OperationForbiddenException;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcWithdrawDataAccess implements WithdrawDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    public JdbcWithdrawDataAccess(JdbcTemplate jdbcTemplate,
                                  PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    /**
     * A method which allows user to withdraw funds from their wallet (if such a wallet exists).
     * Operation can be disallowed if funds are insufficient.
     *
     * @param walletId         - unique identifier of wallet (UUID).
     * @param amountToWithdraw - an amount of money user wants to withdraw
     * @return updated balance of a wallet
     * @throws OperationForbiddenException - if there is not enough funds on the user's account
     * @throws NonPositiveAmountException  - if amountToWithdraw is negative or zero
     * @throws WalletNotFoundException     - if wallet does not exist
     */
    @Override
    public BigDecimal withdraw(UUID walletId, BigDecimal amountToWithdraw) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setReadOnly(false);

        if (amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NonPositiveAmountException();
        }

        return transactionTemplate.execute( status -> {
            BigDecimal balance = Optional.ofNullable(jdbcTemplate.query(
                    "SELECT balance FROM wallet WHERE id = ? FOR UPDATE",
                    rs -> rs.next() ? rs.getBigDecimal("balance") : null,
                    walletId
            )).orElseThrow(WalletNotFoundException::new);

            BigDecimal newBalance = balance.subtract(amountToWithdraw);

            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new OperationForbiddenException(OperationType.WITHDRAW);
            }

            jdbcTemplate.update(
                    "UPDATE wallet SET balance = ? WHERE id = ?",
                    newBalance,
                    walletId
            );

            return newBalance;
        });
    }
}
