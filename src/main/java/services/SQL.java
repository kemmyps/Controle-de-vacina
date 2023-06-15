package services;

public class SQL {

    public static String updateSqlEditInfoPaciente() {
        return "UPDATE Paciente SET nome = ?, CPF = ?, dataNascimento = ?, telefone = ?, endereco = ?, regiaoMoradia = ? WHERE idPaciente = ?";
    }
    public static String updateSqlAddRegistroVacinaPaciente() {
        return "INSERT INTO registrovacina (idPaciente, idVacina, dataVacinacao) VALUES (?, ?, ?)";
    }

    public static  String updateSqlAddPaciente() {
       return "INSERT INTO paciente (nome, CPF, dataNascimento, endereco, telefone, regiaoMoradia) VALUES (?, ?, ?, ?, ?, ?)";
    }

    public static String registroVacinacao(Integer idPaciente) {
        return "SELECT v.idVacina, v.nome, v.descricao, r.datavacinacao  FROM vacinas v INNER JOIN registrovacina r ON v.idvacina = r.idvacina WHERE r.idpaciente = " + idPaciente;
    }
    public static String pacientesDetails(Integer idPaciente) {
        return "SELECT * FROM Paciente where idPaciente =" + idPaciente + " ORDER BY nome";
    }

    public static String listInfoPacientes() {
        return "SELECT * FROM Paciente ORDER BY nome";
    }
     public static String deletePaciente() {
        return "DELETE FROM Paciente WHERE idPaciente = ?";
     }
     public static String deleteRegistroVacinaPaciente() {
        return "DELETE FROM registrovacina WHERE idpaciente = ? AND idvacina = ?";
     }

    static String listVacinas() {
        return "SELECT * FROM vacinas ORDER BY idVacina";
    }

    static String vacinaDetails() {
        return "SELECT * FROM vacinas WHERE idVacina = ?";
    }

    static String addVacinas() {
        return "INSERT INTO vacinas (nome, descricao) VALUES (?, ?)";
    }

    static String editInfoVacina() {
        return "UPDATE vacinas SET nome = ?, descricao = ? WHERE idvacina = ?";
    }

    static String deleteVacina() {
        return "DELETE FROM vacinas WHERE idvacina = ?";
    }

    static String deleteTodasVacinas() {
        return "DELETE FROM vacinas";
    }
}
