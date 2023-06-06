import java.util.Scanner;

public class MenuPrincipal {
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        // Mostrando o menu de opções
        System.out.println(" __      __        _                   _         ____                 _ \n" +
                " \\ \\    / /       (_)                 | |       |  _ \\               | |\n" +
                "  \\ \\  / /_ _  ___ _ _ __   __ _    __| | ___   | |_) | ___ _ __ ___ | |\n" +
                "   \\ \\/ / _` |/ __| | '_ \\ / _` |  / _` |/ _ \\  |  _ < / _ \\ '_ ` _ \\| |\n" +
                "    \\  / (_| | (__| | | | | (_| | | (_| | (_) | | |_) |  __/ | | | | |_|\n" +
                "     \\/ \\__,_|\\___|_|_| |_|\\__,_|  \\__,_|\\___/  |____/ \\___|_| |_| |_(_)\n" +
                "                                                                        \n" +
                "                                                                        \n" +
                "Para começar, escolha uma das opções:");
        System.out.println("1. Gerenciamento de Pacientes (Adicionar, Alterar, Excluir, registros de vacinas do paciente e mais!)");
        System.out.println("2. Gerenciamento de Vacinas (Adicionar nova vacina e descrição ao sistema)");
        System.out.println("3. Ajuda");
        System.out.println("0. Sair");
        System.out.print(">> ");

        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha pendente

        switch (opcao) {
            case 1:
                // Opção de Gerenciamento de Pacientes
                MenuPaciente menuPaciente = new MenuPaciente();
                menuPaciente.open();
                break;
            case 2:
                // Opção de Gerenciamento de Vacinas
                MenuVacinas menuVacinas = new MenuVacinas();
                menuVacinas.open();
                break;
            case 3:
                // TODO: Implementar
                break;
            case 0:
                System.out.println("Até mais!");
                return;
            default:
                System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                break;
        }

        scanner.close();
    }
}
