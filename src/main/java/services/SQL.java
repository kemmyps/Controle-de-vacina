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
        return "SELECT * FROM Paciente where idPaciente =" + idPaciente;
    }

    public static String listInfoPacientes() {
        return "SELECT * FROM Paciente";
    }
     public static String deletePaciente() {
        return "DELETE FROM Paciente WHERE idPaciente = ?";
     }
     public static String deleteRegistroVacinaPaciente() {
        return "DELETE FROM registrovacina WHERE idpaciente = ? AND idvacina = ?";
     }
}
