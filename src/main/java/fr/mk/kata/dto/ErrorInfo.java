package fr.mk.kata.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorInfo {
    private String errorMessage;
}
