package br.com.fiap.space.model.domain.valueObject;

import br.com.fiap.space.model.domain.exception.CargaExcedidaException;

public class Compartimento {
    private final double pesoAtual;
    private final double pesoMaximo;

    public Compartimento(double pesoAtual, double pesoMaximo) {

        if (pesoAtual > pesoMaximo) {
            throw new CargaExcedidaException("Falha de Consistência: Não é possível instanciar um compartimento com carga superior ao limite de fábrica!");
        }

        if (pesoAtual < 0) {
            throw new CargaExcedidaException("Falha de Consistência: A carga atual não pode ser negativa!");
        }

        this.pesoAtual = pesoAtual;
        this.pesoMaximo = pesoMaximo;
    }

    public double getPesoAtual() {
        return pesoAtual;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    @Override
    public String toString() {
        return pesoAtual + "kg / " + pesoMaximo + "kg";
    }
}
