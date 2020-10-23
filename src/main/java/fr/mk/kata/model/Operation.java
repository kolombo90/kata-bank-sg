package fr.mk.kata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Table(name = "operation")
public class Operation {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private final Long id;

    @Column(name = "operation_date")
    private final Date date;
    @Column(name = "amount")
    private final float amount;
    @Column(name = "operation_type")
    private final String operationType;
    @Column(name = "balance")
    private float  balance;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
