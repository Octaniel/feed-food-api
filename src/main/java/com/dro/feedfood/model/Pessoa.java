package com.dro.feedfood.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@EqualsAndHashCode
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatorio")
    private String nome;

    @NotBlank(message = "O apelido é obrigatorio")
    private String apelido;

    @NotBlank(message = "O email é obrigatorio")
    private String email;

    @NotNull(message = "pais é obrigatorio")
    private String pais;

    @NotNull(message = "Telemovel é obrigatorio")
    private String telemovel;

    private LocalDate dataNascimento;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "dt_cria")
    private LocalDateTime dataCriacao;

    @Column(name = "dt_alter")
    private LocalDateTime dataAlteracao;
}
