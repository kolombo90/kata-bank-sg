package fr.mk.kata.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponsePayload {
    private String result;
    private Long actualBalance;
}
