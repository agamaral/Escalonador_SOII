
/* Projeto de SO2 - Simulador de Escalonador de Processos
@author: Antonio Amaral e Vinícius Ferraz
*/
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {
	public static void main(String args[]) {

		Queue<Processo> filaProntos = new LinkedList<Processo>(); // constroi uma fila vazia pros processos
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Produtora(filaProntos); // cria um processo e o insere numa lista de processos
			}
		});
		t.start();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Consumidora(filaProntos); // consome os processos
			}
		});
		t2.start();

	}

	public synchronized static void Consumidora(Queue<Processo> filaProntos) {
		FCFS(new LinkedList<Processo>(filaProntos));
		SJF(new LinkedList<Processo>(filaProntos));
		RR(new LinkedList<Processo>(filaProntos));
	}

	public synchronized static void Produtora(Queue<Processo> filaProntos) {

		@SuppressWarnings("all")
		Scanner entrada = new Scanner(System.in);

		int contID = 0;

		// para finalizar o programa inserir um caracter nao-inteiro
		while (entrada.hasNextInt()) {
			Processo tmp = new Processo(); // cria o processo

			int var;

			var = entrada.nextInt(); // tempo de chegada/entrada

			tmp.setTempoDeChegada(var); // seta o tempo de chegada e entrada
			tmp.setTempoDeEntrada(var);

			var = entrada.nextInt(); // duração
			tmp.setDuracaoDoProcesso(var);

			tmp.setID(contID); // id do processo
			contID++; // a cada ciclo atribui o id do processo

			filaProntos.add(tmp); //adiciona o processo na fila
		}
	}

	public static void FCFS(Queue<Processo> filaProntos) {

		int clock = 0; // clock atual do sistema
		float throughputMedio = 0;
		int num = filaProntos.size(); // n° de processos a ser escalonados

		Processo tmp = new Processo();

		// O escalonador vai executar enquanto existir processos na fila de prontos.
		while (filaProntos.peek() != null) {
			/*
			 * O sistema só pode escalonar processos que já se encontrem na fila de prontos,
			 * devido a isso é necessário realizar um teste para ter certeza que o clock do
			 * sistema, naquele instante, é maior ou igual a o tempo de chegada do processo.
			 * Caso contrario precisamos atualizar o clock do sistema para o tempo de
			 * chegada do processo.
			 */

			if (clock >= filaProntos.peek().getTempoDeChegada()) {
				tmp = filaProntos.poll(); // Simulação do momento em que o processo sai da fila
											// e assume o processador (consumidora)
				System.out.println("Rodar Processo [" + tmp.getID() + "] de [" + clock + "]" + " até ["
						+ (tmp.getDuracaoDoProcesso() + clock) + "]");

				clock += tmp.getDuracaoDoProcesso();// Atualização do tempo do sistema, momento em que
													// o processo deixa o processador - (contadora)
				throughputMedio += clock - tmp.getTempoDeChegada();
			} else {
				/*
				 * Caso nenhum processo tenha chegado na fila para ser escalonado essa instrução
				 * simula a passagem de clock do sistema
				 */
				clock = filaProntos.peek().getTempoDeChegada();
			}
		}
		System.out.printf("FCFS - Throughput medio: %.1f\n", (throughputMedio / num));
	}

	public static void SJF(Queue<Processo> filaProntos) {

		PriorityQueue<Processo> pqueue = new PriorityQueue<Processo>(); // Fila de prioridade auxiliar
		int clock = 0;
		float throughputMedio = 0;
		int num = filaProntos.size(); // Número de processos que serão escalonados

		Processo tmp = new Processo();

		// Enquanto existir processos na fila de prontos ou na fila auxiliar o
		// escalonador deve continuar a executar
		while (filaProntos.peek() != null || pqueue.peek() != null) {
			/*
			 * Caso ainda existam procesoss na fila de prontos e o tempo do sistema sistema
			 * seja maior ou igual ao tempo de chegada desses processos, podemos retirar
			 * esses processos da fila de prontos e inserir na fila de prioridades do SJF
			 */
			while (filaProntos.peek() != null && clock >= filaProntos.peek().getTempoDeChegada()) {
				pqueue.add(filaProntos.poll());
			}
			/*
			 * Teste que verifica se existe algum processo na fila do SJF
			 */
			if (pqueue.peek() != null) {
				tmp = pqueue.poll(); // Simulação do momento em que o processo sai da fila e assume o processador

				// Print que exibe que processo está no processador e o intervalo que o processo
				// permanece no processador
				System.out.println("Rodar Processo [" + tmp.getID() + "] de [" + clock + "]" + " até ["
						+ (tmp.getDuracaoDoProcesso() + clock) + "]");

				clock += tmp.getDuracaoDoProcesso();
				throughputMedio += clock - tmp.getTempoDeChegada();
			} else {
				/*
				 * Caso nenhum processo tenha chegado na fila do SJF para ser escalonado essa
				 * instrução simula a passagem de tempo do sistema
				 */
				clock = filaProntos.peek().getTempoDeChegada();
			}
		}
		System.out.printf("SJF - Throughput medio: %.1f\n", (throughputMedio / num));
	}

	public static void RR(Queue<Processo> filaProntos) {

		// quantum predefinido de 2!!!

		int clock = 0; // Variável que armazena o tempo atual do sistema
		float throughputMedio = 0;
		int num = filaProntos.size(); // Número de processos que serão escalonados

		Queue<Processo> rrQueue = new LinkedList<Processo>();// Fila auxiliar
		Processo tmp = new Processo();

		// Enquanto existir processos na fila de prontos ou na fila auxiliar o
		// escalonador deve continuar a executar
		while (filaProntos.peek() != null || rrQueue.peek() != null) {
			/*
			 * Caso ainda existam processos na fila de prontos e o tempo do sistema seja
			 * maior ou igual ao tempo de chegada desses processos, podemos retirar esses
			 * processos da fila de prontos e inserir na fila de auxiliar do RR
			 */
			while (filaProntos.peek() != null && clock >= filaProntos.peek().getTempoDeChegada())
				rrQueue.add(filaProntos.poll());

			/*
			 * Teste que verifica se existe algum processo na fila do RR
			 */
			if (rrQueue.peek() != null) {

				tmp = rrQueue.poll(); // Simulação do momento em que o processo sai da fila e assume o processador

				// Teste que verifica se é a primeira resposta do processo
				if (tmp.getehAPrimeiraVez()) {
					tmp.setehAPrimeiraVez(false);
				}
				/*
				 * Teste que verifica se o tempo restante de processamento do processo é menor
				 * que o quantum, que é 2, para garantir que processo só executara o tempo
				 * necessário
				 */
				if (tmp.getDuracaoDoProcesso() < 2) {
					System.out.println("Rodar Processo [" + tmp.getID() + "] de [" + clock + "]" + " até ["
							+ (clock + tmp.getDuracaoDoProcesso()) + "]");
					clock += tmp.getDuracaoDoProcesso();
					tmp.setDuracaoDoProcesso(0);
				} else {
					System.out.println(
							"Rodar Processo [" + tmp.getID() + "] de [" + clock + "]" + " até [" + (clock + 2) + "]");
					clock += 2;
					tmp.setDuracaoDoProcesso(tmp.getDuracaoDoProcesso() - 2);
				}
				/*
				 * Após a atualização do clock do sistema devemos garantir que os processos que
				 * possuem tempo de chegada maior ou igual ao clock do sistema entrem na fila do
				 * RR antes de inserir novamente o processo que está ocupando o processador.
				 */
				while (filaProntos.peek() != null && clock >= filaProntos.peek().getTempoDeChegada())
					rrQueue.add(filaProntos.poll());

				/*
				 * Teste que verifica se o processo terminou ou se deve ser inserido novamente
				 * na fila do RR
				 */
				if (tmp.getDuracaoDoProcesso() > 0) {
					tmp.setTempoDeEntrada(clock);
					rrQueue.add(tmp);
				} else {
					/*
					 * Caso o processo não seja inserido novamente na fila, devemos considerar que
					 * ele já retornou
					 */
					throughputMedio += clock - tmp.getTempoDeChegada();
				}
			} else
				/*
				 * Caso nenhum processo tenha chegado na fila do RR para ser escalonado essa
				 * instrução simula a passagem de clock do sistema
				 */
				clock = filaProntos.peek().getTempoDeChegada();
		}
		System.out.printf("RR - Throughput medio: %.1f\n", (throughputMedio / num));
	}
}