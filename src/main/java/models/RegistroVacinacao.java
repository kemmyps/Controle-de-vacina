package models;

import java.sql.Date;

public class RegistroVacinacao {
    Vacina vacina;
    Date dataVacinacao;

    public RegistroVacinacao(Vacina vacina, Date dataVacinacao) {
        this.vacina = vacina;
        this.dataVacinacao = dataVacinacao;
    }

    public String getLineInfoFormatted() {

        String nomeVacina = vacina.nome.length() > 20
                ? vacina.nome.substring(0, 20)
                : vacina.nome;

        String descricaoVacina = vacina.descricao.length() > 71
                ? vacina.descricao.substring(0, 68) + "..."
                : vacina.descricao;

        return String.format("| %-8s | %-20s | %-94s | %-21s |", vacina.id, nomeVacina, descricaoVacina, dataVacinacao);
    }
}
