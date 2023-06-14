package menu;

import services.PacienteService;
import services.VacinaService;
import utils.Util;

import java.util.Scanner;

public class MenuPaciente {
    //Exibir o menu e tratar as opções selecionadas pelo usuário.
    PacienteService pacienteService = new PacienteService();
    VacinaService vacinaService = new VacinaService();
    public void open() {
        showMenu();
        selectOption();
    }

    private void showMenu() {
        String menuOptions = String.join("\n",
                "O que você deseja fazer?",
                "1. Visualizar pacientes cadastrados",
                "2. Visualizar Vacinas Disponiveis no Sistema",
                "3. Visualizar resgistros de vacinas de um paciente",
                "4. Adicionar novo paciente",                           //Opcoes 3, 5 e 6 envolvem o relacionamento entre models.Paciente e vacinas
                "5. Adicionar novo registro de vacina ao paciente",
                "6. Editar informações pessoais de paciente",
                "7. Excluir registro de vacina de um paciente",
                "8. Excluir paciente",
                "0. Voltar ao menu principal"

                //VISUALIZAR: Pacientes cadastrados, vacinas disponiveis, Registros de vacinas de um paciente
                //ADICIONAR: Novo paciente, Registro de vacinas
                //EDITAR: Informações pessoais de pacientes
                //DELETAR: Pacientes, Resgistro de vacinas

        );
        System.out.println(menuOptions);
    }

    private void selectOption() {
        System.out.print(">> ");
        int choice = new Scanner(System.in).nextInt();
        execMenuOption(choice);
    }

    private void execMenuOption(int choice) {
        switch (choice) {
            case 0:
                MenuPrincipal.mostrarMenu();
                return;
            case 1:
                pacienteService.listInfoPacientes();
                break;
            case 2:
                vacinaService.listInfoVacinas();
                break;
            case 3:
                pacienteService.listInfoVacinasPaciente();
                break;
            case 4:
                pacienteService.addPaciente();
                break;
            case 5:
                pacienteService.addRegistroVacinaPaciente();
                break;
            case 6:
                pacienteService.editInfoPaciente();
                break;
            case 7:
                pacienteService.deleteRegistroVacinaPaciente();
                break;
            case 8:
                pacienteService.deletePaciente();
                break;
            default:
                invalidOptions();
        }

        open();
    }

    private void invalidOptions() {
        Util.clearScreen();
        System.out.println("Não consegui entender. Por favor, escolha uma opção válida.");
    }
}

