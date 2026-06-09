package br.com.fiap.space.infrastructure;

import br.com.fiap.space.model.domain.entidades.Sonda;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static BancoDeDados instance;

    private final List<Sonda> bancoDeDados = new ArrayList<>();
    private BancoDeDados() {}

    public static synchronized BancoDeDados getInstance() {
        if (instance == null) {
            instance = new BancoDeDados();
        }
        return instance;
    }

    public void salvar(Sonda sonda) {
        bancoDeDados.removeIf(s -> s.getIdString().equalsIgnoreCase(sonda.getIdString()));
        this.bancoDeDados.add(sonda);
        System.out.println(" [BANCO DE DADOS] Sonda " + sonda.getIdString() + " salva.");
    }

    // buscarPorId
    public Sonda buscarPorId(String idSonda) {
        return this.bancoDeDados.stream()
                .filter(s -> s.getIdString().equalsIgnoreCase(idSonda))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(" [Erro] Sonda '" + idSonda + "' não encontrada no Banco de Dados!"));
    }

    public List<Sonda> listarTodas() {
        return new ArrayList<>(this.bancoDeDados);
    }
}
