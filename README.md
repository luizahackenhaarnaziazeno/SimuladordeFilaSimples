<div align="center">
  
## Simulador de Fila Simples
</div>

## 🎯 Sobre o Projeto
Este projeto é a primeira etapa de um trabalho focado na construção e aprimoramento de um simulador de fila simples. O objetivo principal é simular o comportamento de dois modelos de fila de atendimento, calculando e reportando métricas essenciais para análise de desempenho.

O simulador avalia os seguintes cenários:
* **Fila G/G/1/5**: 1 servidor, capacidade do sistema igual a 5.
* **Fila G/G/2/5**: 2 servidores, capacidade do sistema igual a 5.

Para ambos os cenários, os parâmetros utilizados são:
* **Chegadas**: Distribuição de tempo entre 2.0 e 5.0.
* **Atendimento**: Distribuição de tempo entre 3.0 e 5.0.
* **Condição Inicial**: Fila vazia com o primeiro cliente chegando no tempo 2.0.
* **Condição de Parada**: A simulação é encerrada após a utilização de 100.000 números aleatórios.

O programa reporta as seguintes métricas finais:
1. Tempo global da simulação.
2. Número de clientes perdidos devido à capacidade máxima (5).
3. Tempos acumulados para cada estado da fila (de 0 a 5 clientes).
4. Distribuição de probabilidades dos estados do sistema.

## ⚙️ Pré-requisitos e Dependências
Como o sistema foi implementado em Java puro utilizando as bibliotecas padrão da linguagem (como `PriorityQueue` e `HashMap`), não há necessidade de gerenciadores de dependências complexos. 

Você precisará apenas de:
* **Java Development Kit (JDK)**: Versão 8 ou superior instalada em sua máquina.

## 📁 Estrutura de Diretórios
Como se trata de um módulo inicial, a estrutura é simples, contendo apenas o arquivo principal de código-fonte:

```text
/
├── README.md
└── SimuladordeFilaSimples.java
```

## 🚀 Como Executar
Siga os passos abaixo para compilar e rodar a simulação no seu terminal:

1. Clone o repositório ou faça o download do arquivo `SimuladordeFilaSimples.java`.
2. Abra o terminal e navegue até o diretório onde o arquivo está localizado.
3. Compile o código Java com o seguinte comando:
   ```bash
   javac SimuladordeFilaSimples.java
   ```
4. Após a compilação, um arquivo `SimuladordeFilaSimples.class` será gerado. Execute a simulação com o comando:
   ```bash
   java SimuladordeFilaSimples
   ```
O resultado de ambas as simulações (G/G/1/5 seguida por G/G/2/5) será impresso diretamente no console.

## 🛠️ Pipeline de Execução
O ciclo de vida da execução no arquivo `SimuladordeFilaSimples.java` segue os seguintes passos lógicos:

1. **Inicialização**: A função `main` orquestra as duas simulações, chamando a função `runSimulation` passando o número de servidores (1 ou 2) e a capacidade da fila (5).
2. **Geração de Aleatórios**: O algoritmo pré-gera um total de 100.000 números aleatórios, dividindo-os igualmente (50.000) para calcular os tempos entre as chegadas e os tempos de atendimento.
3. **Escalonamento de Eventos**: Utiliza uma fila de prioridade (`PriorityQueue`) para ordenar os eventos de chegada (`Arrival`) e saída (`Departure`) de forma cronológica. O primeiro evento de chegada é agendado imediatamente.
4. **Processamento (Loop Principal)**: 
   * A simulação avança retirando o próximo evento mais próximo do escalonador.
   * O tempo de simulação (`globalTime`) avança para o momento desse evento.
   * O tempo decorrido desde o último evento é contabilizado no estado atual da fila (quantidade de pessoas).
   * O sistema atualiza o número de clientes na fila e agenda novas chegadas ou saídas baseando-se nos servidores livres e nos tempos gerados aleatoriamente.
   * Clientes que chegam enquanto o sistema está cheio (5 pessoas) são descartados e registrados na variável `lostCustomers`.
5. **Encerramento e Relatórios**: O loop se encerra assim que o limite de uso dos números aleatórios (100.000) é atingido. O simulador então formata e imprime os resultados estatísticos no terminal.

<div align="center">

# Autora:

| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/142232479?v=4" width=115><br><sub>Luiza Hackenhaar Naziazeno</sub>](https://github.com/luizahackenhaarnaziazeno)|
| :---: |
