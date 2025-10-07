package com.example.PrevidenciAgi.Enum;

public enum TempoRecebendo {
    ANOS_5,
    ANOS_10,
    ANOS_15,
    VITALICIO;

    public int toAnos() {
        return switch (this) {
            case ANOS_5 -> 5;
            case ANOS_10 -> 10;
            case ANOS_15 -> 15;
            case VITALICIO -> 25;
        };
    }
}
