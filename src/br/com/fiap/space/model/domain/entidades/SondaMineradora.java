package br.com.fiap.space.model.domain.entidades;

import br.com.fiap.space.model.domain.valueObject.Compartimento;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;
import br.com.fiap.space.model.domain.valueObject.Coordenadas;

public class SondaMineradora extends Sonda{

    private Compartimento compartimento;

    public SondaMineradora(String idString, NivelDeEnergia nivelDeEnergia, Coordenadas coordenadasAtual, Compartimento compartimento){
        super(idString, nivelDeEnergia, coordenadasAtual);
        this.compartimento = compartimento;

    }

    public Compartimento getCompartimento() {
        return compartimento;
    }

    @Override
    protected void processarAtividadeEspecifica() {
        System.out.println("[PERFURATRIZ ATIVADA] Sonda Mineradora " + idString + " iniciando extração de minério...");

        double novoPeso = this.compartimento.getPesoAtual() + 5.0;

        this.compartimento = new Compartimento(novoPeso, this.compartimento.getPesoMaximo());

        System.out.println("[PRODUÇÃO] 5.0kg extraídos. Status da Carga: " + this.compartimento);
    }

    @Override
    public void enviarRelatorio() {
        System.out.println("[RELATÓRIO DE EXTRAÇÃO] Sonda " + idString +
                " confirma retenção de carga na coordenada " + getCoordenadaAtual() +
                ". Pronto para transporte.");
    }
}
