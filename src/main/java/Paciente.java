import java.sql.Date;

public class Paciente {
    String nome;

    String CPF;
    String endereco;
    Date dataNascimento;
    String regiaoMoradia;
    String telefone;

    public Paciente(String nome, String CPF, String endereco, Date dataNascimento, String regiaoMoradia, String telefone) {
        this.nome = nome;
        this.CPF = CPF;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.regiaoMoradia = regiaoMoradia;
        this.telefone = telefone;
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

}
