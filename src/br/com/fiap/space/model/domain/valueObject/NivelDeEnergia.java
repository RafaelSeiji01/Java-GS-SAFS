package br.com.fiap.space.model.domain.valueObject;

import br.com.fiap.space.model.domain.exception.BateriaCriticaException;

public class NivelDeEnergia {

    private final double capacidadeAtual;
    private final double capacidadeMaxima;

    public NivelDeEnergia(double capacidadeAtual, double capacidadeMaxima) {

        if (capacidadeMaxima > 100.0) {
            throw new IllegalArgumentException("Erro de Hardware: A capacidade máxima da bateria não pode ultrapassar 100%.");
        }
        if (capacidadeAtual > capacidadeMaxima) {
            throw new IllegalArgumentException("Erro de Sobrecarga: A capacidade atual não pode ser maior que a capacidade máxima.");
        }
        if (capacidadeAtual <= 0) {
            throw new BateriaCriticaException("FALHA DE INICIALIZAÇÃO: Sistemas em colapso. Energia informada de " + capacidadeAtual + "% é insuficiente para ativar os motores da frota SAFS."
            );
        }

        this.capacidadeAtual = capacidadeAtual;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public double getCapacidadeAtual() {
        return capacidadeAtual;
    }

    public double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}
