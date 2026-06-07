package br.com.fiap.space;

import br.com.fiap.space.model.domain.entidades.SondaExploradora;
import br.com.fiap.space.model.domain.entidades.SondaMineradora;
import br.com.fiap.space.model.domain.valueObject.Compartimento;
import br.com.fiap.space.model.domain.valueObject.Coordenadas;
import br.com.fiap.space.model.domain.valueObject.NivelDeEnergia;
import br.com.fiap.space.model.domain.enumeration.Terreno;

public class Teste {
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTE DE SISTEMAS: MARS MISSION ---");

        try {
            // 1. Preparando os dados iniciais (Value Objects)
            Coordenadas inicial = new Coordenadas(0, 0);
            NivelDeEnergia energia = new NivelDeEnergia(100.0, 100.0);

            // 2. Instanciando a Sonda Exploradora
            SondaExploradora exploradora = new SondaExploradora(
                    "SND-XP-01",
                    energia,
                    inicial,
                    150.0 // alcance do sensor
            );

            System.out.println("Sonda criada: " + exploradora.getIdString());
            System.out.println("Status inicial: Bateria em " + exploradora.getNivelDeEnergia().getCapacidadeAtual() + "%");

            // 3. Testando a Rotina Autônoma (Template Method)
            // Isso vai: Mover -> Escanear (Atividade Específica) -> Relatar
            System.out.println("\n--- DISPARANDO ROTINA AUTÔNOMA ---");
            Coordenadas destino = new Coordenadas(5, 12);
            exploradora.executarRotinaAutonoma(destino,Terreno.PLANICE);

            // 4. Validando o estado final após a rotina
            System.out.println("\n--- VALIDAÇÃO DE ESTADO PÓS-MISSÃO ---");
            System.out.println("Nova Posição: " + exploradora.getCoordenadaAtual());
            System.out.println("Bateria Restante: " + exploradora.getNivelDeEnergia().getCapacidadeAtual() + "%");

        } catch (Exception e) {
            System.err.println("ERRO DURANTE O TESTE: " + e.getMessage());
        }

        System.out.println("\n=================================================");
        System.out.println("--- INICIANDO TESTE DA SONDA MINERADORA ---");

// 1. Criando os estados iniciais para a Mineradora
        Coordenadas localInicialMineradora = new Coordenadas(2, 5);
        NivelDeEnergia energiaMineradora = new NivelDeEnergia(100.0, 100.0);
        Compartimento cargaInicial = new Compartimento(0.0, 30.0); // Começa vazia, cabe 30kg

// 2. Instanciando a Sonda Mineradora
        SondaMineradora mineradora = new SondaMineradora(
                "SND-MINE-02",
                energiaMineradora,
                localInicialMineradora,
                cargaInicial
        );

// 3. Disparando a Rotina Autônoma (Mover -> Perfurar/Adicionar Carga -> Relatar)
        System.out.println("\n--- DISPARANDO ROTINA AUTÔNOMA (MINERADORA) ---");
        Coordenadas veioDeFerro = new Coordenadas(3, 9);
        mineradora.executarRotinaAutonoma(veioDeFerro,Terreno.ROCHOSO);

// 4. Validando o estado final do robô de mineração
        System.out.println("\n--- VALIDAÇÃO DE ESTADO PÓS-MINERAÇÃO ---");
        System.out.println("Posição Atual: " + mineradora.getCoordenadaAtual());
        System.out.println("Bateria Restante: " + mineradora.getNivelDeEnergia().getCapacidadeAtual() + "%");
        System.out.println("Peso Final na Carga: " + mineradora.getCompartimento());

    }
}
