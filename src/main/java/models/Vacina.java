package models;

import java.util.List;

public class Vacina {
    Integer id;
    String nome;

    String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Vacina(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getLineFormatted() {
        String nomeFormatted = nome.length() > 20 ? nome.substring(0, 20) : nome;
        String descricaoFormatted = descricao.length() > 116 ? descricao.substring(0, 116) : descricao;

        return String.format("| %-8s | %-20s | %-116s |\n", id, nomeFormatted, descricaoFormatted);
    }

}
