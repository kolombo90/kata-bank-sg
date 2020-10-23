package fr.mk.kata.repository;

import fr.mk.kata.model.Account;
import fr.mk.kata.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByAccount(Account account);
}
