package br.com.fiap.space.model.domain.enumeration;

public enum Terreno {

    PLANICE(10.0),
    ROCHOSO(15.0),
    MONTANHOSO(25.0)
    ;

    private final double custoEnergia;

    private Terreno(double custoEnergia){
        this.custoEnergia = custoEnergia;
    }

    public double getCustoEnergia() {
        return custoEnergia;
    }

}
