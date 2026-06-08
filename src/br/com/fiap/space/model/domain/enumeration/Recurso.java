package br.com.fiap.space.model.domain.enumeration;

public enum Recurso {
    //Tipos de recursos possiveis de encontrar

    FERRO("Minério de Ferro", 5.0),
    GELO("Água em Gelo", 3.5),
    REGOLITO("Regolito", 2.2);

    private final String nomeExibicao;
    private final double pesoUnidade;

    private Recurso(String nomeExibicao, double pesoUnidade) {
        this.nomeExibicao = nomeExibicao;
        this.pesoUnidade = pesoUnidade;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public double getPesoUnidade() {
        return pesoUnidade;
    }
}
