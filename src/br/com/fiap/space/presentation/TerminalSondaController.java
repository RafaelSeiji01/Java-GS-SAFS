package br.com.fiap.space.presentation;

import br.com.fiap.space.application.MissaoService;
import br.com.fiap.space.model.domain.entidades.Sonda;

import java.util.List;
import java.util.Scanner;

public class TerminalSondaController {

    public static void main(String[] args) {

        // Inicializa o serviço maestro da aplicação
        MissaoService missaoService = new MissaoService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println("   SISTEMA DE CONTROLE DE MISSÃO: FIAP SPACE     ");
        System.out.println("=================================================");

        boolean rodando = true;

        while (rodando) {
            System.out.println("\n--- MENU DE OPERAÇÕES ---");
            System.out.println("1. Lançar (Registrar) Nova Sonda");
            System.out.println("2. Iniciar Missão de uma Sonda (Mover/Atividade)");
            System.out.println("3. Listar Frota de Sondas Ativas");
            System.out.println("4. Recarregar Bateria da Sonda");
            System.out.println("5. Desconectar Terminal (Sair)");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:

                    System.out.println("\n[NOVO LANÇAMENTO]");

                    System.out.print("[ Digite 1 ] - EXPLORADORA \n[ Digite 2 ] - MINERADORA\nTipo de sonda > ");
                    String entradaTipo = scanner.nextLine().toUpperCase().trim();

                    String tipo = null;

                    // Mapeamos todas as possibilidades de digitação do usuário!
                    if (entradaTipo.equals("2") || entradaTipo.contains("MINERADORA")) {
                        tipo = "MINERADORA";
                    } else if (entradaTipo.equals("1") || entradaTipo.contains("EXPLORADORA")) {
                        tipo = "EXPLORADORA";
                    } else {

                        System.out.println(" ERRO: Tipo de sonda inválido! Digite 1 ou 2.");
                        break; // Volta para o menu principal automaticamente
                    }

                    System.out.print("Código de Identificação (ex: SND-01): ");
                    String id = scanner.nextLine();

                    System.out.print("Capacidade Máxima de Bateria: ");
                    double bateria = scanner.nextDouble();

                    int x = 0;
                    System.out.print("Coordenada Inicial X: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("ERRO: Entrada inválida! Digite apenas números inteiros");
                        System.out.print("Coordenada Inicial X: ");
                        scanner.next();
                    }
                    x = scanner.nextInt();
                    scanner.nextLine();

                    int y = 0;
                    System.out.print("Coordenada Inicial Y: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("ERRO: Entrada inválida! Digite apenas números inteiros");
                        System.out.print("Coordenada Inicial Y: ");
                        scanner.next();
                    }
                    y = scanner.nextInt();
                    scanner.nextLine();

                    double paramEspecifico = 0;
                    if (tipo.equals("MINERADORA")) {
                        System.out.print("Capacidade Máxima de Carga (KG): ");
                            paramEspecifico = scanner.nextDouble();
                        } else {
                            System.out.print("Alcance do Sensor (Metros): ");
                            paramEspecifico = scanner.nextDouble();
                        }
                        scanner.nextLine();

                        try {
                            missaoService.lancarNovaSonda(tipo, id, bateria, x, y, paramEspecifico);
                            System.out.println("🟢 Sonda lançada com sucesso!");
                        } catch (IllegalArgumentException e) {
                            System.out.println("\n ALERTA DE CONFIGURAÇÃO: " + e.getMessage());
                            System.out.println("Retornando ao menu principal para nova tentativa...\n");
                        }

                        break;

                    case 2:

                        System.out.println("\n[INICIAR MISSÃO AUTÔNOMA]");

                        // Captura a frota atual para exibição
                        List<Sonda> frotaDisponivel = missaoService.listarFrota();

                        if (frotaDisponivel.isEmpty()) {
                            System.out.println("ALERTA: Nenhuma sonda em órbita ou solo. Lance uma sonda primeiro no menu 1.");
                            break;
                        }

                    // Lista as sondas numeradas para o usuário escolher por índice
                    System.out.println("Selecione qual sonda deseja comandar:");
                    for (int i = 0; i < frotaDisponivel.size(); i++) {
                        System.out.println((i + 1) + " - " + frotaDisponivel.get(i).getIdString() +
                                " [Posição: " + frotaDisponivel.get(i).getCoordenadaAtual() + "]");
                    }

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
                        String idBusca = frotaDisponivel.get(escolhaSonda - 1).getIdString();

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
                    System.out.println("\n[FROTA DE SONDAS EM MARTE]");
                    List<Sonda> frota = missaoService.listarFrota();

                    if (frota.isEmpty()) {
                        System.out.println("Nenhuma sonda orbitando ou em solo no momento.");
                    } else {

                        for (Sonda s : frota) {
                            // Puxa a capacidade atual da bateria da sonda
                            double bateriaAtual = s.getNivelDeEnergia().getCapacidadeAtual();

                            String bateriaFormatada = String.format("%.2f", bateriaAtual);

                            System.out.println("- " + s.getIdString() + " | Posição: " + s.getCoordenadaAtual() + " | Bateria: " + bateriaFormatada + "%");
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n[PROTOCOLO DE RECARGA]");
                    List<Sonda> frotaParaRecarga = missaoService.listarFrota();

                    if (frotaParaRecarga.isEmpty()) {
                        System.out.println("⚠️ ALERTA: Nenhuma sonda disponível para recarga.");
                        break;
                    }

                    System.out.println("Selecione qual sonda deseja trazer para a base:");
                    for (int i = 0; i < frotaParaRecarga.size(); i++) {
                        System.out.println((i + 1) + " - " + frotaParaRecarga.get(i).getIdString() +
                                " [Bateria Atual: " + String.format("%.2f%%", frotaParaRecarga.get(i).getNivelDeEnergia().getCapacidadeAtual()) + "]");
                    }

                    System.out.print("Digite o número da sonda: ");
                    try {
                        int escolha = scanner.nextInt();
                        scanner.nextLine(); // Limpa buffer

                        if (escolha < 1 || escolha > frotaParaRecarga.size()) {
                            System.out.println("ERRO: Opção inválida!");
                            break;
                        }

                        String idSonda = frotaParaRecarga.get(escolha - 1).getIdString();

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
