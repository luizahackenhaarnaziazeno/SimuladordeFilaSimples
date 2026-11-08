import java.util.PriorityQueue;
import java.util.Random;
import java.util.HashMap;

public class Simulacao{

    private static final int NUM_RANDOMS = 100000;
    private static final int RANDOM_NUMBERS_PER_TYPE = NUM_RANDOMS / 2;
    private static final Random random = new Random(42);

    public static void main(String[] args) {
        // Executa a simulação G/G/1/5
        System.out.println("----- Iniciando Simulação G/G/1/5 -----");
        runSimulation(1, 5);
        
        System.out.println("\n----------------------------------------\n");
        
        // Executa a simulação G/G/2/5
        System.out.println("----- Iniciando Simulação G/G/2/5 -----");
        runSimulation(2, 5);
    }
    
    private static void runSimulation(int numServers, int systemCapacity) {
        // Gerando números aleatórios
        double[] randomCh = new double[RANDOM_NUMBERS_PER_TYPE];
        double[] randomSa = new double[RANDOM_NUMBERS_PER_TYPE];
        for (int i = 0; i < RANDOM_NUMBERS_PER_TYPE; i++) {
            randomCh[i] = random.nextDouble() * (5 - 2) + 2; // Chegadas entre 2 e 5
            randomSa[i] = random.nextDouble() * (5 - 3) + 3; // Atendimento entre 3 e 5
        }

        int chIdx = 0;
        int saIdx = 0;

        // Variáveis de estado da simulação
        double globalTime = 0.0;
        int queueStatus = 0; // Número de clientes no sistema
        int lostCustomers = 0;
        double lastEventTime = 0.0;

        // Armazena o tempo acumulado para cada estado
        HashMap<Integer, Double> accumulatedTimeByState = new HashMap<>();
        for (int i = 0; i <= systemCapacity; i++) {
            accumulatedTimeByState.put(i, 0.0);
        }

        // O escalonador de eventos
        PriorityQueue<Event> eventScheduler = new PriorityQueue<>();
        eventScheduler.add(new Event(randomCh[chIdx++], "Arrival"));

        // Loop principal da simulação
        while (!eventScheduler.isEmpty() && (chIdx + saIdx) <= NUM_RANDOMS) {
            Event currentEvent = eventScheduler.poll();
            double eventTime = currentEvent.getEventTime();
            String eventType = currentEvent.getEventType();

            // Acumula tempo
            double timeInState = eventTime - lastEventTime;
            if (timeInState > 0) {
                accumulatedTimeByState.put(queueStatus, accumulatedTimeByState.get(queueStatus) + timeInState);
            }

            globalTime = eventTime;
            lastEventTime = globalTime;

            // Processa o evento
            if (eventType.equals("Arrival")) {
                if (queueStatus < systemCapacity) {
                    queueStatus++;
                    if (queueStatus <= numServers) {
                        if (saIdx < randomSa.length) {
                            double serviceTime = randomSa[saIdx++];
                            double departureTime = globalTime + serviceTime;
                            eventScheduler.add(new Event(departureTime, "Departure"));
                        }
                    }
                } else {
                    lostCustomers++;
                }
                
                if (chIdx < randomCh.length) {
                    double interArrivalTime = randomCh[chIdx++];
                    double nextArrivalTime = globalTime + interArrivalTime;
                    eventScheduler.add(new Event(nextArrivalTime, "Arrival"));
                }
            } else if (eventType.equals("Departure")) {
                queueStatus--;
                if (queueStatus >= numServers) {
                    if (saIdx < randomSa.length) {
                        double serviceTime = randomSa[saIdx++];
                        double departureTime = globalTime + serviceTime;
                        eventScheduler.add(new Event(departureTime, "Departure"));
                    }
                }
            }
        }

        // Atualização final do tempo acumulado
        double timeInState = globalTime - lastEventTime;
        if (timeInState > 0) {
            accumulatedTimeByState.put(queueStatus, accumulatedTimeByState.get(queueStatus) + timeInState);
        }

        // Calculando e exibindo os resultados
        HashMap<Integer, Double> probabilityDistribution = new HashMap<>();
        for (int state : accumulatedTimeByState.keySet()) {
            double time = accumulatedTimeByState.get(state);
            probabilityDistribution.put(state, (globalTime > 0) ? time / globalTime : 0);
        }

        System.out.println("--- Resultados da Simulação G/G/" + numServers + "/" + systemCapacity + " ---");
        System.out.printf("Tempo Global da Simulação: %.4f unidades de tempo\n", globalTime);
        System.out.println("Número de Clientes Perdidos: " + lostCustomers);
        System.out.println("Total de Aleatórios Usados: " + (chIdx + saIdx));
        
        System.out.println("\n--- Tempos Acumulados por Estado do Sistema ---");
        accumulatedTimeByState.entrySet().stream()
            .sorted(HashMap.Entry.comparingByKey())
            .forEach(entry -> System.out.printf("Estado %d: %.4f unidades de tempo\n", entry.getKey(), entry.getValue()));
            
        System.out.println("\n--- Distribuição de Probabilidades dos Estados do Sistema ---");
        probabilityDistribution.entrySet().stream()
            .sorted(HashMap.Entry.comparingByKey())
            .forEach(entry -> System.out.printf("Estado %d: %.4f%%\n", entry.getKey(), entry.getValue() * 100));
    }
    
    // Classe interna para representar eventos
    static class Event implements Comparable<Event> {
        private final double eventTime;
        private final String eventType;

        public Event(double eventTime, String eventType) {
            this.eventTime = eventTime;
            this.eventType = eventType;
        }

        public double getEventTime() {
            return eventTime;
        }

        public String getEventType() {
            return eventType;
        }

        @Override
        public int compareTo(Event other) {
            return Double.compare(this.eventTime, other.eventTime);
        }
    }
}