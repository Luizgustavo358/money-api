package br.pucminas.crc.model;

import javax.persistence.*;

/**
 * Created by luiz on 04/10/17.
 */

@Entity
@Table(name = "categoria")
public class Categoria
{
    // definir dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String nome;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}// end class Categoria
