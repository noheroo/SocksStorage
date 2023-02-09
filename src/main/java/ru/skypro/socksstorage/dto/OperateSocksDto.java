package ru.skypro.socksstorage.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OperateSocksDto {
    @NotBlank(message = "color can't be empty")
    @Size(min = 3, max = 50, message = "color must be more 2 characters and less 51 characters")
    private String color;
    @Min(value = 0, message = "cotton part must be >=0")
    @Max(value = 100, message = "cotton part must be <=100")
    private Integer cottonPart;
    @Positive(message = "quantity must be positive only")
    private Integer quantity;
}
