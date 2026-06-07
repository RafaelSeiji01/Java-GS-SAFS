package br.com.fiap.space.model.domain.entidades;

import br.com.fiap.space.model.domain.valueObject.Coordenadas;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;

public class SondaExploradora extends Sonda {
    private double alcanceSensor;

    public SondaExploradora(String idString, NivelDeEnergia nivelDeEnergia, Coordenadas coordenadaAtual, double alcanceSensor) {
        super(idString, nivelDeEnergia, coordenadaAtual);
        this.alcanceSensor = alcanceSensor;
    }

    public double getAlcanceSensor() {
        return alcanceSensor;
    }

    @Override
    protected void processarAtividadeEspecifica() {
        System.out.println("[SCANNER ACTIVATED] Sonda Exploradora " + idString +
                " mapeando terreno em um raio de " + alcanceSensor + " metros.");
    }

    @Override
    public void enviarRelatorio() {
        System.out.println("[RELATÓRIO ORBITAL] Enviando dados topográficos coletados na coordenada " +
                getCoordenadaAtual() + " para o satélite de comunicação.");
    }
}
