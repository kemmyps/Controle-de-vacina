package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    Integer id;
    String nome;
    String CPF;
    String endereco;
    Date dataNascimento;
    String regiaoMoradia;
    String telefone;
    List<RegistroVacinacao> registrosVacinacao;

    public Paciente(Integer id, String nome, String CPF, String endereco, Date dataNascimento, String regiaoMoradia, String telefone, List<RegistroVacinacao> registrosVacinacao) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.regiaoMoradia = regiaoMoradia;
        this.telefone = telefone;
        this.registrosVacinacao = registrosVacinacao;
    }

    public Paciente(Integer id, String nome, String CPF, String endereco, Date dataNascimento, String regiaoMoradia, String telefone) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.regiaoMoradia = regiaoMoradia;
        this.telefone = telefone;
        this.registrosVacinacao = new ArrayList<>();
    }

    public Paciente(String nome, String CPF, String endereco, Date dataNascimento, String regiaoMoradia, String telefone) {
        this.nome = nome;
        this.CPF = CPF;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.regiaoMoradia = regiaoMoradia;
        this.telefone = telefone;
        this.registrosVacinacao = new ArrayList<>();
    }

    public String getLineInfoFormatted() {
        // formatar dados para caber na tabela
        if(nome.length() > 20) { nome = nome.substring(0, 20); }
        if(CPF.length() > 11) { CPF = CPF.substring(0, 12); }
        if(endereco.length() > 50) { endereco = endereco.substring(0, 50); }
        if(telefone.length() > 12) { nome = nome.substring(0, 12); }
        if(regiaoMoradia.length() > 15) { regiaoMoradia = regiaoMoradia.substring(0, 15); }

        return String.format("| %-10d | %-20s | %-15s | %-12s | %-12s | %-50s | %15s |", id, nome, CPF, dataNascimento, telefone,  endereco,regiaoMoradia);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRegiaoMoradia() {
        return regiaoMoradia;
    }

    public void setRegiaoMoradia(String regiaoMoradia) {
        this.regiaoMoradia = regiaoMoradia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<RegistroVacinacao> getRegistrosVacinacao() {
        return registrosVacinacao;
    }

}
