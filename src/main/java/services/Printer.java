package services;

import models.Paciente;
import models.RegistroVacinacao;

import java.util.List;

public class Printer {

    static void showPacienteList(List<Paciente> listPacientes) {
        // Header
        System.out.println(" ");
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-3s | %-20s | %-15s | %-12s | %-12s | %-50s | %15s |\n", "IDPaciente", "Nome", "CPF", "Data Nasc.", "Telefone", "Endereco","Região Moradia");
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");

        // imprime paciente por paciente
        for (Paciente paciente : listPacientes) {
            System.out.println(paciente.getLineInfoFormatted());
        }

        // Footer
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| Quantidade de resultados: %-127d|\n", listPacientes.size());
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println(" ");
    }

    static void showPacienteDetail(Paciente paciente) {

        // TODO: imprimir dados do paciente
        System.out.println(" ");
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| Vacinas do paciente: %-131s |\n", paciente.getNome());

        // Header
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-8s | %-20s | %-94s | %-21s |\n", "idVacina", "Nome da Vacina", "Descrição", "Data da Vacinação");
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");

        // imprime paciente por paciente
        for (RegistroVacinacao registro : paciente.getRegistrosVacinacao()) {
            System.out.println(registro.getLineInfoFormatted());
        }

        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| Quantidade de vacinas: %-129d |\n", paciente.getRegistrosVacinacao().size());
        System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println(" ");
    }
    static void idInvalido() {
        System.out.println("|--------------------------------------------|");
        System.out.println("| ID informado não existe. Tente novamente   |");
        System.out.println("|--------------------------------------------|");
        System.out.println(" ");
    }
    static void registroAdicionado() {
        System.out.println("|----------------------------------------|");
        System.out.println("| Registro(s) adicionado(s) com sucesso! |");
        System.out.println("|----------------------------------------|");
        System.out.println(" ");
    }

    static void registroDeletado() {
        System.out.println("|--------------------------------------|");
        System.out.println("| Registro(s) deletado(s) com sucesso! |");
        System.out.println("|--------------------------------------|");
        System.out.println(" ");
    }
}
