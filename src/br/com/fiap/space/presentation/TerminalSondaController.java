package br.com.fiap.space.presentation;

import br.com.fiap.space.application.MissaoService;

import java.util.List;
import java.util.Scanner;

public class TerminalSondaController {

    public static void main(String[] args) {

        // Inicializa o serviço maestro da aplicação
        MissaoService missaoService = new MissaoService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("┌────────────────────────────────────────────────────────┐");
        System.out.println("│                FIAP SPACE ORBITAL NETWORK              │");
        System.out.println("│           SISTEMA DE CONTROLE DE MISSÃO AUTÔNOMA       │");
        System.out.println("└────────────────────────────────────────────────────────┘");
        System.out.println();

        boolean rodando = true;

        while (rodando) {
            System.out.println("┌────────────────────────────────────────────────────────┐");
            System.out.println("│              SAFS - CONTROL PANEL v1.0.0               │");
            System.out.println("├────────────────────────────────────────────────────────┤");
            System.out.println("│  [1]   LANÇAR (REGISTRAR) NOVA SONDA                   │");
            System.out.println("│  [2]   INICIAR MISSÃO (MOVIMENTAÇÃO / EXTRAÇÃO)        │");
            System.out.println("│  [3]   PAINEL DE TELEMETRIA (LISTAR FROTA ATIVA)       │");
            System.out.println("│  [4]   PROTOCOLO DE RECARGA (CONECTAR À BASE)          │");
            System.out.println("│  [5]   DESCONECTAR TERMINAL (ENCERRAR SISTEMA)         │");
            System.out.println("└────────────────────────────────────────────────────────┘");
            System.out.print("COMANDANTE, DIGITE A OPERAÇÃO DESEJADA: ");

            while (!scanner.hasNextInt()) {
                System.out.println(" [ERRO] Opção inválida! Digite apenas números de 1 a 5.");
                System.out.print("Escolha uma opção: ");
                scanner.next();
            }

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:

                    System.out.println("┌────────────────────────────────────────────────────────┐");
                    System.out.println("│             MÓDULO DE LANÇAMENTO DE SONDA              │");
                    System.out.println("├────────────────────────────────────────────────────────┤");
                    System.out.println("│  [1] EXPLORADORA (Mapeamento óptico e sensores)        │");
                    System.out.println("│  [2] MINERADORA  (Perfuração e extração ISRU)          │");
                    System.out.println("└────────────────────────────────────────────────────────┘");
                    System.out.print(" [CONFIGURAÇÃO] SELECIONE O TIPO DE DIRETRIZ DA SONDA: ");
                    String entradaTipo = scanner.nextLine().toUpperCase().trim();

                    String tipo = null;

                    // Mapeamos todas as possibilidades de digitação do usuário!
                    if (entradaTipo.equals("2") || entradaTipo.contains("MINERADORA")) {
                        tipo = "MINERADORA";
                    } else if (entradaTipo.equals("1") || entradaTipo.contains("EXPLORADORA")) {
                        tipo = "EXPLORADORA";
                    } else {

                        System.out.println(" [ERRO] Tipo de sonda inválido! Digite 1 ou 2.");
                        System.out.println();
                        break;
                    }

                    System.out.print(" [CONFIGURAÇÃO] Código de Identificação (ex: SND-01): ");
                    String id = scanner.nextLine();

                    System.out.print(" [CONFIGURAÇÃO] Capacidade Máxima de Bateria: ");
                    double bateria = scanner.nextDouble();

                    int x = 0;
                    System.out.print(" [CONFIGURAÇÃO] Coordenada Inicial X: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println(" [ERRO] Entrada inválida! Digite apenas números inteiros");
                        System.out.print(" [CONFIGURAÇÃO] Coordenada Inicial X: ");
                        scanner.next();
                    }
                    x = scanner.nextInt();
                    scanner.nextLine();

                    int y = 0;
                    System.out.print(" [CONFIGURAÇÃO] Coordenada Inicial Y: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println(" [ERRO] Entrada inválida! Digite apenas números inteiros");
                        System.out.print(" [CONFIGURAÇÃO] Coordenada Inicial Y: ");
                        scanner.next();
                    }
                    y = scanner.nextInt();
                    scanner.nextLine();

                    double paramEspecifico = 0;
                    if (tipo.equals("MINERADORA")) {
                        System.out.print(" [CONFIGURAÇÃO] Capacidade Máxima de Carga (KG): ");
                            paramEspecifico = scanner.nextDouble();
                        } else {
                            System.out.print(" [CONFIGURAÇÃO] Alcance do Sensor (Metros): ");
                            paramEspecifico = scanner.nextDouble();
                        }
                        scanner.nextLine();

                        //tratamento de erro
                        try {
                            missaoService.lancarNovaSonda(tipo, id, bateria, x, y, paramEspecifico);
                            System.out.println(" [STATUS] Sonda lançada com sucesso!");
                            System.out.println("─────────────────────────────────────────────────────────");
                         } catch (IllegalArgumentException | br.com.fiap.space.model.domain.exception.BateriaCriticaException | br.com.fiap.space.model.domain.exception.CargaExcedidaException e){
                            System.out.println("ALERTA DE CONFIGURAÇÃO: " + e.getMessage());
                            System.out.println("Retornando ao menu principal para nova tentativa...");
                            System.out.println("─────────────────────────────────────────────────────────");
                         }

                        break;

                    case 2:

                        System.out.println("┌────────────────────────────────────────────────────────┐");
                        System.out.println("│               INICIAR MISSÃO AUTÔNOMA                  │");
                        System.out.println("└────────────────────────────────────────────────────────┘");
                        System.out.println();

                        // Captura a frota atual para exibição
                        List<String[]> frotaDisponivel = missaoService.listarFrotaFormatada();

                        if (frotaDisponivel.isEmpty()) {
                            System.out.println(" [ALERTA] Nenhuma sonda em órbita ou solo. Lance uma sonda primeiro no menu 1.");
                            break;
                        }

                    System.out.println("Selecione qual sonda deseja comandar:");
                        for (int i = 0; i < frotaDisponivel.size(); i++) {
                            String[] s = frotaDisponivel.get(i);

                            System.out.printf(" [%d] │ ID: %-7s │ Posição: %-12s\n",
                                    (i + 1),
                                    s[0],
                                    s[1]);
                        }
                        System.out.println("──────────────────────────────────────────────────────────");
                        System.out.print("Escolha o número da sonda para a ação: ");

                    System.out.print("Digite o número da sonda desejada: ");

                    // Tratamento para caso o usuário digite letras no lugar do número do índice
                    try {
                        int escolhaSonda = scanner.nextInt();
                        scanner.nextLine();

                        // Validação do índice digitado
                        if (escolhaSonda < 1 || escolhaSonda > frotaDisponivel.size()) {
                            System.out.println("ERRO: Opção de sonda inválida! Esse número não consta na frota.");
                            System.out.println("Retornando ao menu principal...");
                            break; // Aborta a operação e volta ao menu
                        }

                        // Puxa o ID de forma automatizada
                        String idBusca = frotaDisponivel.get(escolhaSonda - 1)[0];

                        // Solicita as coordenadas de destino
                        System.out.print("Coordenada de Destino X: ");
                        int destX = scanner.nextInt();

                        System.out.print("Coordenada de Destino Y: ");
                        int destY = scanner.nextInt();
                        scanner.nextLine();

                        missaoService.iniciarMissaoSonda(idBusca, destX, destY);

                    } catch (java.util.InputMismatchException e) {
                        System.out.println("ERRO: Você deve digitar um número inteiro para selecionar a sonda e as coordenadas!");
                        scanner.nextLine();
                        System.out.println("Retornando ao menu principal...");
                    } catch (Exception e) {
                        // Captura qualquer outro erro
                        System.out.println(" Falha na operação de controle: " + e.getMessage());
                        System.out.println("Retornando ao menu principal...");
                    }

                    break;

                case 3:
                    System.out.println();
                    System.out.println("┌────────────────────────────────────────────────────────┐");
                    System.out.println("│               FROTA DE SONDAS EM MARTE                 │");
                    System.out.println("└────────────────────────────────────────────────────────┘");

                    List<String[]> frota = missaoService.listarFrotaFormatada();

                    if (frota.isEmpty()) {
                        System.out.println("Nenhuma sonda orbitando ou em solo no momento.");
                    } else {

                        for (String[] s : frota) {
                            System.out.printf("ID: %-7s │ Posição: %-12s │ Bateria: %s%%\n",
                                    s[0],
                                    s[1],
                                    s[2]);
                        }
                        System.out.println("──────────────────────────────────────────────────────────");
                        System.out.println();
                    }
                    break;

                case 4:
                    List<String[]> frotaParaRecarga = missaoService.listarFrotaFormatada();

                    if (frotaParaRecarga.isEmpty()) {
                        System.out.println(" [ALERTA] Nenhuma sonda disponível para recarga.");
                        break;
                    }

                    System.out.println("┌────────────────────────────────────────────────────────┐");
                    System.out.println("│               PROTOCOLO DE RECARGA DE FROTA            │");
                    System.out.println("└────────────────────────────────────────────────────────┘");
                    System.out.println("   Selecione qual sonda deseja trazer para a base:");
                    System.out.println("──────────────────────────────────────────────────────────");

                    for (int i = 0; i < frotaParaRecarga.size(); i++) {
                        String[] s = frotaParaRecarga.get(i);

                        System.out.printf(" [%d] │ ID: %-7s │ Bateria Atual: %s%%\n",
                                (i + 1),
                                s[0],
                                s[2]);
                    }

                    System.out.println("──────────────────────────────────────────────────────────");

                    System.out.print("Digite o número da sonda: ");
                    try {
                        int escolha = scanner.nextInt();
                        scanner.nextLine();

                        if (escolha < 1 || escolha > frotaParaRecarga.size()) {
                            System.out.println(" [ERRO] Opção inválida!");
                            break;
                        }

                        String idSonda = frotaParaRecarga.get(escolha - 1)[0];

                        // Dispara o serviço de recarga que criamos acima
                        missaoService.recarregarSonda(idSonda);

                    } catch (Exception e) {
                        System.out.println("Erro ao processar recarga: " + e.getMessage());
                        scanner.nextLine();
                    }
                    break;

                case 5:
                    System.out.println("\nDesconectando das Sondas de Marte... Até logo, Comandante!");
                    rodando = false;
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }
}
