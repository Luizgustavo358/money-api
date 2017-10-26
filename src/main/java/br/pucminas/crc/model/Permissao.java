package br.pucminas.crc.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by luiz on 26/10/17.
 */

@Entity
@Table(name = "permissao")
public class Permissao
{
    @Id
    private Long codigo;
    private String descricao;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}// end class Permissao
