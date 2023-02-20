package br.com.api.labBeatch.labParadises.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bairro")
public class Bairro {
    // identificador único do bairro
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nome do bairro
    private String nome;

    // descrição do bairro
    private String descricao;

    // número de habitantes do bairro
    private Integer populacao;

}
