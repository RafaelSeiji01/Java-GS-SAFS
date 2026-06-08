package br.com.fiap.space.model.factory;

import br.com.fiap.space.model.domain.entidades.Sonda;
import br.com.fiap.space.model.domain.entidades.SondaExploradora;
import br.com.fiap.space.model.domain.entidades.SondaMineradora;
import br.com.fiap.space.model.domain.valueObject.Compartimento;
import br.com.fiap.space.model.domain.valueObject.Coordenadas;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;

public class SondaFactory {

    private static SondaFactory instance;

    private SondaFactory() {}

    public static synchronized SondaFactory getInstance() {
        if (instance == null) {
            instance = new SondaFactory();
        }
        return instance;
    }

    // criarSondaMineradora
    public Sonda criarSondaMineradora(String idSonda, NivelDeEnergia bateria, Coordenadas coordenadaAtual,  Compartimento carga) {
        return new SondaMineradora(idSonda, bateria, coordenadaAtual, carga);
    }

    // criarSondaExploradora
    public Sonda criarSondaExploradora(String idSonda, NivelDeEnergia bateria, Coordenadas coordenadaAtual, double alcanceSensor) {
        return new SondaExploradora(idSonda, bateria, coordenadaAtual, alcanceSensor);
    }

}
