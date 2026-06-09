package br.com.fiap.space.application;

import br.com.fiap.space.infrastructure.BancoDeDados;
import br.com.fiap.space.model.domain.entidades.Sonda;

import java.util.List;

public class CentroDeComando {

    private static CentroDeComando instance;
    private final BancoDeDados bancoDeDados;

    private CentroDeComando() {
        this.bancoDeDados = BancoDeDados.getInstance();
    }

    public static synchronized CentroDeComando getInstance() {
        if (instance == null) {
            instance = new CentroDeComando();
        }
        return instance;
    }

    public void registrarSonda(Sonda sonda) {

        if (sonda == null || sonda.getIdString() == null || sonda.getIdString().trim().isEmpty()) {
            throw new IllegalArgumentException("Sonda sem ID adicionado!");
        }

        for (Sonda sondaExistente : bancoDeDados.listarTodas()) {


            if (sondaExistente.getIdString().equalsIgnoreCase(sonda.getIdString())) {
                throw new IllegalArgumentException("O ID '" + sonda.getIdString() + "' já está em uso por outra sonda operacional!");
            }
        }
        bancoDeDados.salvar(sonda);
        System.out.println(" [CENTRO DE COMANDO] Sonda " + sonda.getIdString() + " integrada à frota operacional.");
    }

}
