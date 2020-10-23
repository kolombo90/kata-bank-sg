package fr.mk.kata.model;

public enum OperationType {
    DEPOSIT("Dépôt"),
    WITHDRAW("Retrait");

    private String value;

    OperationType(String value) {
        this.value = value;
    }
}
