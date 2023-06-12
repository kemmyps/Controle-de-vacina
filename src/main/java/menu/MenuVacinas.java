package menu;

import services.VacinaService;
import utils.Util;

import java.util.Scanner;

public class MenuVacinas {
    public void open() {
        showMenuVacina();
        selectOption();
    }
    VacinaService vacinaService = new VacinaService();

    private void showMenuVacina() {
        String menuOptions = String.join("\n",
                "O que você deseja fazer?",
                "1. Visualizar informações das vacinas cadastradas",
                "2. Cadastrar nova vacina",
                "3. Editar informações de vacina",
                "4. Excluir registro de vacina",
                "5. Excluir todos os registros de vacinas",
                "0. Voltar ao menu principal"
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
                // TODO: Testar
                vacinaService.listInfoVacinas();
                break;
            case 2:
                // TODO: Testar
                vacinaService.addVacina();
                break;
            case 3:
                // TODO: Testar
                vacinaService.editInfoVacina();
                break;
            case 4:
                // TODO: Testar
                vacinaService.deleteVacina();
                break;
            case 5:
                // TODO: Testar
                vacinaService.deleteTodasVacinas();
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

