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
        bancoDeDados.salvar(sonda);
        System.out.println("[CENTRO DE COMANDO] Sonda " + sonda.getIdString() + " integrada à frota operacional.");
    }

    public Sonda buscarSonda(String idSonda) {
        return bancoDeDados.buscarPorId(idSonda);
    }

    public List<Sonda> listarFrota() {
        return bancoDeDados.listarTodas();
    }

}
