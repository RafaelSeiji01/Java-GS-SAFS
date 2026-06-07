package br.com.fiap.space.model.domain.entidades;

import br.com.fiap.space.model.domain.enumeration.Terreno;
import br.com.fiap.space.model.domain.valueObject.Coordenadas;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;

public abstract class Sonda {
    protected String idString;
    protected NivelDeEnergia nivelDeEnergia;
    protected Coordenadas coordenadaAtual;

    public Sonda(String idString,NivelDeEnergia nivelDeEnergia,Coordenadas coordenadaAtual){
        this.idString = idString;
        this.nivelDeEnergia = nivelDeEnergia;
        this.coordenadaAtual = coordenadaAtual;
    }

    public String getIdString() {
        return idString;
    }

    public NivelDeEnergia getNivelDeEnergia() {
        return nivelDeEnergia;
    }

    public Coordenadas getCoordenadaAtual() {
        return coordenadaAtual;
    }

    //metodo mover q usa Coordenada e terreno como parametros
    public void mover(Coordenadas destino, Terreno terreno){
        double gasto = terreno.getCustoEnergia();
        double novaBateria = this.nivelDeEnergia.getCapacidadeAtual() - gasto;

        if (novaBateria < 0){
            throw new RuntimeException("Falha Crítica: Bateria insuficiente para mover a sonda:" + idString);
        }

        //Criando novo objetos com os valores para substituição
        this.nivelDeEnergia = new NivelDeEnergia(novaBateria,this.nivelDeEnergia.getCapacidadeMaxima());
        this.coordenadaAtual = destino;

        System.out.println("Sonda " + idString + " se moveu para " + destino + " através de terreno " + terreno + " (Gasto: " + gasto + "%)");
    }

    public final void executarRotinaAutonoma(Coordenadas destino, Terreno terreno){
        mover(destino, terreno);
        processarAtividadeEspecifica();
        enviarRelatorio();
    }

    protected abstract void processarAtividadeEspecifica();
    public abstract void enviarRelatorio();
}
