package br.com.fiap.space.model.domain.valueObject;

public class NivelDeEnergia {

    private final double capacidadeAtual;
    private final double capacidadeMaxima;

    public NivelDeEnergia(double capacidadeAtual, double capacidadeMaxima) {
        if (capacidadeAtual < 0) {
            this.capacidadeAtual = 0;
        } else {
            this.capacidadeAtual = capacidadeAtual;
        }
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public double getCapacidadeAtual() {
        return capacidadeAtual;
    }

    public double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}
