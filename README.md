#  SAFS - Surface Autonomous Fleet System (FIAP Space)

##  Contexto da Missão
Este projeto foi desenvolvido como a entrega da **Global Solution (1º Semestre de 2026)** para a disciplina de **Domain Driven Design - Java** no curso de Engenharia de Software da FIAP

O objetivo do sistema é gerenciar uma frota autônoma de sondas (Rovers e Drones) operando na superfície de Marte 49, 70.O ecossistema auxilia o Comandante da Missão no processo de **ISRU (In-Situ Resource Utilization)** para extração de recursos vitais (como água e ferro) sem a necessidade de intervenção humana em tempo real, contornando a alta latência de comunicação espacial que pode ultrapassar os 20 minutos.

---

##  Engenharia do Sistema e Motores de Cálculo

O SAFS não apenas armazena dados, mas simula as leis físicas e ambientais do solo marciano através de motores lógicos internos:

### 1. Motor de Telemetria e Distância Euclidiana
Para calcular o deslocamento da sonda entre o ponto atual $(x_1, y_1)$ e o destino atribuído pelo operador $(x_2, y_2)$, o sistema utiliza a equação da **Distância Euclidiana** bidimensional:

$$d = \sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}$$

O resultado obtido em metros é processado pelo motor de energia para prever a viabilidade da rota antes do acionamento dos motores físicos.

### 2. Algoritmo de Consumo Energético Proativo
O gasto de energia não é linear. Ele se baseia na distância total convertida em blocos de 100 metros, multiplicada pelo atrito dinâmico do `Terreno` sorteado pelos sensores de bordo:

$$\text{Gasto Total} = \left( \frac{\text{Distância Total}}{100} \right) \times \text{Multiplicador Consumo}$$

Se o $\text{Gasto Total}$ calculado for superior à capacidade atualizada do Value Object `NivelDeEnergia`, o sistema executa uma **diretriz defensiva**, abortando a decolagem antes de estolar o veículo no deserto[cite: 127].

---

## Padrões de Projeto & Arquitetura de Software

O projeto aplica padrões integrados à filosofia do DDD:

```text
br.com.fiap.space
├── presentation          # Interface CLI, tratamento de buffers e espias de entrada (hasNextInt)
├── application           # Serviços aplicativos (MissaoService) que orquestram fluxos transacionais
└── model
    ├── domain            # O Coração do Software (Regras imutáveis da física marciana)
    │   ├── contract      # Contratos de interfaces de hardware (Recarregavel)
    │   ├── entidades     # Classes com identidade (Sonda, SondaMineradora, SondaExploradora)
    │   ├── enumeration   # Enums ricos (Terreno, Recurso) com propriedades matemáticas
    │   ├── exceptions    # Exceções explícitas de violação de órbita e solo
    │   └── valueObject   # Objetos imutáveis (Coordenadas, NivelDeEnergia, Compartimento)
    ├── factory           # Fábricas isoladas para desacoplamento de criação
    └── infrastructure    # Persistência em memória operando sob barramento Singleton
```
##  Integrantes do Grupo - 2ESPH

* **Cesar Aaron Herrera** – RM 565398
* **Kaue Soares Madarazzo** – RM 562100
* **Nicolas Mendes dos Santos** – RM 566290 
* **Rafael Seiji Aoke Arakaki** – RM 561993
* **Rafael Yuji Nakaya** – RM 563624 
