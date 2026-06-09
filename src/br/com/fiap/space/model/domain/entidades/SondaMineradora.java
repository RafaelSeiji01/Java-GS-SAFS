package br.com.fiap.space.model.domain.entidades;

import br.com.fiap.space.model.domain.enumeration.Recurso;
import br.com.fiap.space.model.domain.exception.CargaExcedidaException;
import br.com.fiap.space.model.domain.valueObject.Compartimento;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;
import br.com.fiap.space.model.domain.valueObject.Coordenadas;

import java.util.Random;

public class SondaMineradora extends Sonda{
    //classe filha de sonda

    private Compartimento compartimento;
    private final Random random;

    public SondaMineradora(String idString, NivelDeEnergia nivelDeEnergia, Coordenadas coordenadasAtual, Compartimento compartimento){
        super(idString, nivelDeEnergia, coordenadasAtual);

        if (compartimento.getPesoMaximo() < 5.0 || compartimento.getPesoMaximo() > 30.0) {
            throw new IllegalArgumentException(" [DOMÍNIO] Erro: A capacidade máxima do compartimento da Sonda Mineradora deve estar entre 5kg e 30kg.");
        }
        this.compartimento = compartimento;
        this.random = new Random();

    }

    public Compartimento getCompartimento() {
        return compartimento;
    }

    @Override
    protected void processarAtividadeEspecifica() {


        Recurso[] recursosPossiveis = Recurso.values();
        int indiceSorteado = random.nextInt(recursosPossiveis.length);
        // O recurso encontrado passa a ser o sorteado
        Recurso recursoEncontrado = recursosPossiveis[indiceSorteado];

        System.out.println(" [PERFURATRIZ] Sonda " + getIdString() + " minerando: " + recursoEncontrado.getNomeExibicao());

        double pesoExtraido = recursoEncontrado.getPesoUnidade();
        double novoPesoTotal = this.compartimento.getPesoAtual() + pesoExtraido;

        // Validação de Sobrecarga (Opcional, caso queira travar se passar do peso máximo)
        if (novoPesoTotal > this.compartimento.getPesoMaximo()) {
            throw new CargaExcedidaException("Falha de Operação: Extração abortada! Coletar +" + pesoExtraido + "kg de " + recursoEncontrado.getNomeExibicao() + " excederia o limite máximo do compartimento (" + this.compartimento.getPesoMaximo() + "kg).");
        }

        this.compartimento = new Compartimento(novoPesoTotal, this.compartimento.getPesoMaximo());

        System.out.println(" [PRODUÇÃO] +" + pesoExtraido + "kg coletados. Carga total: " + this.compartimento);
    }

    @Override
    public void enviarRelatorio() {
        System.out.println(" [RELATÓRIO] Mineração concluída com sucesso em " + getCoordenadaAtual());
    }

    @Override
    public void conectarBase() {
        super.conectarBase();
        double capacidadeMaxima = this.compartimento.getPesoAtual();
        this.compartimento = new Compartimento(0.0, compartimento.getPesoMaximo());

        System.out.println(" [DESCARGA] Compartimento de carga esvaziado. Minérios transferidos para os silos da base.");
    }
}
