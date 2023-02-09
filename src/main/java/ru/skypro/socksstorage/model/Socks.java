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
    @NotBlank(message = "color can't be empty")
    @Column(name = "color",nullable = false, length = 50)
    private String color;
    @Min(value = 0,message = "cotton part must be >=0")
    @Max(value = 100,message = "cotton part must be <=100")
    @Column(name = "cotton_part",nullable = false)
    private Integer cottonPart;
    @Positive(message = "quantity must be positive only")
    @Column(name = "quantity",nullable = false)
    private Integer quantity;

}
