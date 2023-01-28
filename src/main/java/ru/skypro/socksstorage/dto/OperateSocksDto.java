package ru.skypro.socksstorage.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OperateSocksDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String color;
    @Min(value = 0)
    @Max(value = 100)
    private Byte cottonPart;
    @Positive
    private Integer quantity;
}
