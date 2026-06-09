package br.com.fiap.space.application;

import br.com.fiap.space.infrastructure.BancoDeDados;
import br.com.fiap.space.model.domain.entidades.Sonda;
import br.com.fiap.space.model.domain.valueObject.Compartimento;
import br.com.fiap.space.model.domain.valueObject.Coordenadas;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;
import br.com.fiap.space.model.factory.SondaFactory;
import br.com.fiap.space.model.domain.enumeration.Terreno;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MissaoService {
    //orquestração de fluxo de dados

    private final BancoDeDados bancoDeDados;
    private final Random random;

    public MissaoService() {
        this.bancoDeDados = BancoDeDados.getInstance();
        this.random = new Random();
    }

    public void lancarNovaSonda(String tipo, String idSonda, double capacidadeBateria, int x, int y, double parametroEspecifico){

        System.out.println();
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println(" [MISSAO SERVICE] Lançando nova sonda: " + idSonda);

        SondaFactory factory = SondaFactory.getInstance();
        Coordenadas posicaoInicial = new Coordenadas(x, y);
        NivelDeEnergia bateriaInicial = new NivelDeEnergia(capacidadeBateria, capacidadeBateria);

        Sonda novaSonda;

        if (tipo.equalsIgnoreCase("MINERADORA")) {
            Compartimento cargaInicial = new Compartimento(0.0, parametroEspecifico);
            novaSonda = factory.criarSondaMineradora(idSonda, bateriaInicial, posicaoInicial, cargaInicial);
        } else if (tipo.equalsIgnoreCase("EXPLORADORA")) {
            novaSonda = factory.criarSondaExploradora(idSonda, bateriaInicial, posicaoInicial, parametroEspecifico);
        } else {
            throw new IllegalArgumentException("Tipo de sonda desconhecido: " + tipo);
        }

        CentroDeComando centro = CentroDeComando.getInstance();
        centro.registrarSonda(novaSonda);
    }

    public List<Sonda> listarFrota() {
        return bancoDeDados.listarTodas();
    }

    public void iniciarMissaoSonda(String idSonda, int destinoX, int destinoY) {
        System.out.println("──────────────────────────────────────────────────────────");
        System.out.println(" [MISSAO SERVICE] Iniciando cálculo de viabilidade de rota para: " + idSonda);

        try {

            Sonda sonda = bancoDeDados.buscarPorId(idSonda);

            if (sonda == null) {
                throw new IllegalArgumentException("A sonda com o identificador '" + idSonda + "' não foi encontrada na base de dados de Marte.");
            }

            // Sorteio dinâmico do Terreno
            Terreno[] terrenosPossiveis = Terreno.values(); // Pega todos os Enums (ARENOSO, ROCHOSO, etc)
            int indiceSorteado = random.nextInt(terrenosPossiveis.length);
            Terreno terrenoDaRota = terrenosPossiveis[indiceSorteado];

            // Cálculo da Distância Euclidiana
            double x1 = sonda.getCoordenadaAtual().getX();
            double y1 = sonda.getCoordenadaAtual().getY();

            double deltaX = destinoX - x1;
            double deltaY = destinoY - y1;
            double distanciaTotal = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

            //Calcula o gasto de energia
            double blocosDeCemMetros = distanciaTotal / 100.0;
            double gastoTotalCalculado = blocosDeCemMetros * terrenoDaRota.getCustoEnergia();

            Coordenadas destino = new Coordenadas(destinoX, destinoY);

            // Prints informativos atualizados com o radar de terreno
            System.out.println(" [SENSORES DE BORDO] Terreno detectado na rota: " + terrenoDaRota + " (Custo base: " + terrenoDaRota.getCustoEnergia() + "% por 100m)");
            System.out.println(" [TELEMETRIA] Distância calculada pela aplicação: " + String.format("%.2fm", distanciaTotal));
            System.out.println(" [TELEMETRIA] Custo energético previsto: " + String.format("%.2f%%", gastoTotalCalculado));

            sonda.executarRotinaAutonoma(destino, terrenoDaRota, gastoTotalCalculado);

            bancoDeDados.salvar(sonda);
            System.out.println(" [CASO DE USO] Missão finalizada com sucesso e registrada na base.");
            System.out.println("──────────────────────────────────────────────────────────");
            System.out.println();

        } catch (RuntimeException e) {
            System.out.println("──────────────────────────────────────────────────────────");
            System.out.println(" [MISSAO SERVICE - ERRO OPERACIONAL] Comando abortado!");
            System.out.println(" Motivo: " + e.getMessage());
            System.out.println("──────────────────────────────────────────────────────────");
        }

    }

    public void recarregarSonda(String idSonda) {
        System.out.println("──────────────────────────────────────────────────────────");
        System.out.println(" [MISSAO SERVICE] Solicitando retorno da sonda para acoplamento...");

        Sonda sonda = bancoDeDados.buscarPorId(idSonda);

        if (sonda == null) {
            throw new IllegalArgumentException("A sonda com o identificador '" + idSonda + "' não foi encontrada para recarga.");
        }

        sonda.conectarBase();

        // Salva o novo status da bateria cheia no banco de dados
        bancoDeDados.salvar(sonda);

        System.out.println(" [CASO DE USO] Protocolo de recarga concluído e registrado.");
        System.out.println("──────────────────────────────────────────────────────────");
        System.out.println();
    }

    public List<String[]> listarFrotaFormatada() {
        List<Sonda> frotaReal = this.listarFrota();
        List<String[]> frotaFormatada = new ArrayList<>();

        for (Sonda s : frotaReal) {
            double bateriaAtual = s.getNivelDeEnergia().getCapacidadeAtual();
            String bateriaFormatada = String.format("%.2f", bateriaAtual);

            String[] dadosSonda = new String[] {
                    s.getIdString(),
                    s.getCoordenadaAtual().toString(),
                    bateriaFormatada
            };
            frotaFormatada.add(dadosSonda);
        }

        return frotaFormatada;
    }
}