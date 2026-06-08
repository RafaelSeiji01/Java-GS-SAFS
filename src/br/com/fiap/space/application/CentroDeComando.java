package br.com.fiap.space.application;

import br.com.fiap.space.infrastructure.BancoDeDados;
import br.com.fiap.space.model.domain.entidades.Sonda;

import java.util.List;

public class CentroDeComando {
    // Atributo privado estático para o padrão Singleton
    private static CentroDeComando instance;

    // Referência para o componente de infraestrutura que simula a persistência
    private final BancoDeDados bancoDeDados;

    // Construtor PRIVADO: impede o uso de "new CentroDeComando()" fora da classe
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
        bancoDeDados.salvar(sonda);
        System.out.println("[CENTRO DE COMANDO] Sonda " + sonda.getIdString() + " integrada à frota operacional.");
    }

    // + buscarSonda(idSonda: String): Sonda
    public Sonda buscarSonda(String idSonda) {
        return bancoDeDados.buscarPorId(idSonda);
    }

    public List<Sonda> listarFrota() {
        return bancoDeDados.listarTodas();
    }

}
