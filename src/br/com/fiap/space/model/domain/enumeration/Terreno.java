package br.com.fiap.space.model.domain.enumeration;

public enum Terreno {
    //Opções de terrenos em solo extraterrestre

    PLANICE(10.0),
    CRATERA(15.0),
    CRATERA_PROFUNDA(15.0),
    ROCHOSO(25.0)
    ;

    private final double custoEnergia;

    private Terreno(double custoEnergia){
        this.custoEnergia = custoEnergia;
    }

    public double getCustoEnergia() {
        return custoEnergia;
    }

}
