package br.com.api.labBeatch.labParadises.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "praia")
public class Praia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // relacionamento com a entidade Bairro
    @JoinColumn(name = "bairro_id", nullable = false) // especifica o nome da coluna na tabela do banco de dados
    private Bairro bairro;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String acessibilidade;

    @Column(nullable = false)
    private String status;

}
