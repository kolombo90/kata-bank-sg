package fr.mk.kata.dto;

import fr.mk.kata.model.OperationType;
import fr.mk.kata.validator.ValueOfEnum;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;

@Builder
@Data
public class OperationDto {
    @ValueOfEnum(enumClass = OperationType.class, message = "operation type invalid please type WITHDRAW or DEPOSIT")
    private String operationType;
    @DecimalMin(value = "0.1", inclusive = true, message = "Amount should be more than 0.1")
    private Long amount;
}
