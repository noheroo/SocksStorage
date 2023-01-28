package ru.skypro.socksstorage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "socks")
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "socks_id")
    private Integer id;
    @Size(min = 3,max = 50)
    @NotBlank
    @Column(name = "color")
    private String color;
    @Min(0)
    @Max(100)
    @Column(name = "cotton_part")
    private Byte cottonPart;
    @Positive
    @Column(name = "quantity")
    private Integer quantity;

}
