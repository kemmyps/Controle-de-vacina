import java.util.Scanner;

public class VacinaCadastro {

    ServiceCadastroVacina serviceCadastroVacina = new ServiceCadastroVacina();

    public void open() {
        showMenu();
        selectOption();
    }

    private void showMenu() {
        String menuOptions = String.join("\n",
                "O que você deseja fazer?",
                "1. Visualizar informações pessoais dos pacientes cadastrados",
                "2. Cadastrar novo paciente",
                "3. Editar informações do paciente",
                "4. Excluir registro de um paciente",
                "5. Adicionar registro de vacina para um paciente",
                "6. Excluir registro de vacina para um paciente",
                "7. Excluir todos os registros de pacientes",
                "0. Sair"
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
                System.out.println("Até mais!");
                break;
            case 1:
                serviceCadastroVacina.listInfoPacientes();
                break;
            case 2:
                serviceCadastroVacina.insertInfoPaciente();
                break;
            case 3:
                serviceCadastroVacina.editInfoPaciente();
                break;
            case 4:
                serviceCadastroVacina.deleteInfoPaciente();
                break;
            case 5:
                // TODO: Implementar
                break;
            case 6:
                // TODO: Implementar
                break;
            case 7:
                serviceCadastroVacina.deleteTodosPacientes();
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

