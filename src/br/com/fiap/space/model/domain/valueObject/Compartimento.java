package br.com.fiap.space.model.domain.valueObject;

public class Compartimento {
    private final double pesoAtual;
    private final double pesoMaximo;

    public Compartimento(double pesoAtual, double pesoMaximo) {

        if(pesoAtual > pesoMaximo){
            throw new RuntimeException("Falha: O compartimento excedeu o limite máximo de carga!");
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
