package fr.mk.kata.model;

import fr.mk.kata.exception.BalanceException;
import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;

import static fr.mk.kata.model.OperationType.WITHDRAW;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "balance")
    private Long balance;

    public void changeBalance(Long amount, OperationType operationType) throws BalanceException {
        if (!hasFunds(amount, operationType)) {
            throw new BalanceException("Insufficient balance for this operation");
        }

        switch (operationType) {
            case DEPOSIT:
                setBalance(balance + amount);
                break;
            case WITHDRAW:
                setBalance(balance - amount);
                break;
        }
    }

    private boolean hasFunds(Long amount, OperationType operationType) {
        if (amount > balance && operationType == WITHDRAW) {
            return false;
        }
        return true;
    }
}
