package br.com.fiap.space.model.domain.entidades;

import br.com.fiap.space.model.domain.contract.Recarregavel;
import br.com.fiap.space.model.domain.enumeration.Terreno;
import br.com.fiap.space.model.domain.exception.BateriaCriticaException;
import br.com.fiap.space.model.domain.exception.TerrenoInvalidoException;
import br.com.fiap.space.model.domain.valueObject.Coordenadas;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;

public abstract class Sonda implements Recarregavel {
    //Classe principal , tendo contrato com interface
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
    public void mover(Coordenadas destino, Terreno terreno, double gastoTotal){

        double novaBateria = this.nivelDeEnergia.getCapacidadeAtual() - gastoTotal;

        if (terreno.name().equalsIgnoreCase("CRATERA_PROFUNDA")) {
            throw new TerrenoInvalidoException("Falha de Navegação: Movimento abortado! Terreno do tipo CRATERA PROFUNDA é intransitável para o sistema de locomoção atual.");
        }

        if (novaBateria < 0) {
            System.out.println();
            throw new BateriaCriticaException("Falha Crítica: Bateria insuficiente para mover a sonda pelo terreno " + terreno + "!");
        }

        this.nivelDeEnergia = new NivelDeEnergia(novaBateria, this.nivelDeEnergia.getCapacidadeMaxima());
        this.coordenadaAtual = destino;
        System.out.println(" [DOMÍNIO] Sonda moveu-se com sucesso pelo terreno: " + terreno);
        System.out.println(" [ATUALIZADO] Sonda " + idString + " se moveu para " + destino + " através de terreno " + terreno + " | Energia gasta no deslocamento: " + String.format("%.2f%%", gastoTotal) + " | Bateria restante atual: " + String.format("%.2f%%", this.nivelDeEnergia.getCapacidadeAtual()));

    }


    public final void executarRotinaAutonoma(Coordenadas destino, Terreno terreno, double gastoTotal){
        mover(destino, terreno,gastoTotal);
        processarAtividadeEspecifica();
        enviarRelatorio();
    }

    protected abstract void processarAtividadeEspecifica();
    public abstract void enviarRelatorio();

    @Override
    public void conectarBase() {
        System.out.println(" [DOCKING STATION] Sonda " + this.idString + " acoplada com sucesso à base de solo.");

        // Puxa o limite máximo que a bateria dela suporta
        double maximoPermitido = this.nivelDeEnergia.getCapacidadeMaxima();

        this.nivelDeEnergia = new NivelDeEnergia(maximoPermitido, maximoPermitido);

        System.out.println(" [ENERGIA] Carga restaurada para o limite máximo de fábrica: " + maximoPermitido + "%");
    }
}
