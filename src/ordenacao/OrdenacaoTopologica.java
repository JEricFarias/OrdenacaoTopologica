package ordenacao;
/** Trabalho da disciplina de Estrutura de Dados - Fanor - 2016.1
 * 	Professor: 
 *		- Adriano Patrick do Nascimento Cunha - acunha2@fanor.edu.br
 *	Equipe:
 *		- nome - email
 *		- nome - email
 *	Problema:
 * 		QUEST�O �NICA: 
 *			Implementar um algoritmo de utilizando uma estrutura de dados do tipo FILA para solucionar o problema
 *			da ordena��o topol�gica. A Ordena��o topol�gica � um grafo ac�clico orientado, onde cada v�rtice 
 *			esteja ordenado pela depend�ncia existente entre os v�rtices. (https://www.youtube.com/watch?v=URdtNUdxU3o)
 *			Conceitos:
 *			- Uma permuta��o dos v�rtices de um digrafo � uma sequ�ncia em que cada v�rtice aparece uma s� vez
 *			- Uma ordena��o topol�gica � uma permuta��o dos v�rtices v1, ..., vn,
 *				de um digrafo ac�clico, de forma que para qualquer aresta (vi ; vj ), i < j .
 *				- qualquer caminho entre vi e vj n�o passa por vk se k < i ou k > j . 
 *			- Digrafos com ciclos n�o admitem ordena��o topol�gica.
 *		ENTRADA: 
 *			O arquivo de entrada cont�m v�rias inst�ncias do problema. Cada linha inicia com o n�mero de tarefas, 
 *			seguido da lista de depend�ncias entre estas tarefas, as depend�ncias s�o representas como v�rtices do
 *			tipo: (a, b), onde "a" representa a tarefa a qual "b" depende, ou seja, para que "b" seja executado � 
 *			necess�rio que "a" tenha terminado. N�o ser� inserido digrafo c�clico.
 *		SAIDA: 
 *			A sa�da consiste de tantas linhas quantas inst�ncias do problema houver. Em cada linha, devem ser
 *		impressos a lista de tarefas ordenadas topologicamente, ou seja, com base na depend�ncia das tarefas.
 *		EXEMPLO:
 *			Entrada:
 *				1 
 *				4 (2,1) (2,3)
 *				5 (2,1) (2,3) (3,1) (1,5)
 *			Sa�da:
 *				1
 *				4, 2, 1, 3
 *				4, 2, 3, 1, 5
 */

import java.io.*;
import java.util.*;


/**
 * @author patrick.cunha
 * @author ericfarrias@hotmail.com
 * @since 30/03/2016 - Ordena��o Topol�gica
 */
public class OrdenacaoTopologica {

	private static final String DELIMITADORES = "\\(|\\,)";
	private static List<Problema> problemas = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		processa(args[0]);
		System.exit(0);
	}

	/**
	 * M�todo principal
	 */
	private static void processa(String file) {
		entrada(file);
		ordena();
		saida();
	}

	/**
	 * Processa a entrada
	 */
	private static void entrada(String file) {

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String linha;
			while ((linha = br.readLine()) != null) {
				List<Tarefa> tarefas = criaTarefas(new StringTokenizer(
						linha.replaceAll(" ", ""), DELIMITADORES));
				Problema problema = new Problema();
				problema.getTarefasEntrada().addAll(tarefas);
				problemas.add(problema);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	private static List<Tarefa> criaTarefas(StringTokenizer token) {
		
		List<Tarefa> tarefas = new ArrayList<>(); // qtdaTarefa ok
		int qtdaTarefas = Integer.parseInt(token.nextToken()); // qtdaTarefa ok
		String[] dependencias = new String[token.countTokens()]; // quantidade de tokens ok
		
		// for est� colocando todos os valores do arquivo dentro de um vetor.
		for (int i = 0; i < dependencias.length; i++) {
			dependencias[i] = token.nextToken();
			System.out.println(dependencias[i]);
		}
		
		// for est� criando tarefas, tantas quanto a quantidade de tarefas especificado pelo aquivo.
		for (int i = 0; i < qtdaTarefas; i++) {
			Tarefa tarefa = new Tarefa();
			tarefa.setIdTarefa(i + 1);
			tarefas.add(tarefa);
		}
		
		for(int i = 0; i < qtdaTarefas;i++){
			tarefas.get(i).setQtdaDependencias(retornaQuantidadeDeDependencias(
					dependencias, tarefas.get(i).getIdTarefa()));
			//if(tarefas.get(i).getQtdaDependencias() > 0){
				tarefas.get(i).setListaDependencias(retornaListaDeDependencias(dependencias,
						tarefas.get(i).getIdTarefa(), tarefas));
			//}
		}

		return tarefas;
	}

	// TODO implementarar
	private static int retornaQuantidadeDeDependencias(String[] dependencias,
			Integer idTarefa) {
		int numeroDependencias = 0;
		for(int i = 1; i < dependencias.length; i += 2){
			if(Integer.parseInt(dependencias[i]) == idTarefa){
				numeroDependencias++;
			}
		}
		return numeroDependencias; // numero de dependencias esta ok
	}

	// TODO implementarar
	private static List<Tarefa> retornaListaDeDependencias(String[] dependencias, Integer idTarefa, List<Tarefa> tarefas) {
		
		List<Tarefa> tempTarefas = new ArrayList<>();
	
		for(int i = 0; i < dependencias.length; i+=2){
			//System.out.println("i = " + i + " id tarefa = " + idTarefa + " id de acesso ao dependentes " + (Integer.parseInt(dependencias[i-1]) - 1));
			if(Integer.parseInt(dependencias[i]) == idTarefa){
				tempTarefas.add(tarefas.get(Integer.parseInt(dependencias[i + 1]) - 1) );
			}
		}
		
		return tempTarefas;
	}

	/**
	 * M�todo que realiza a ordena��o topol�gica.
	 */// TODO implementarar
	private static void ordena() {
		for(Problema problema: problemas){
			
			List<Tarefa> tarefasDoProblema = problema.getTarefasEntrada(); // lista de entradas
			
			List<Tarefa> filaExecucao = new ArrayList<>(); // Fila de Excu��o, armazena as os valores que ser�o avaliado.
			
			List<Tarefa> saida = new ArrayList<>();
			
			for(Tarefa tarefa: tarefasDoProblema){
				if(tarefa.getQtdaDependencias() == 0){
					filaExecucao.add(tarefa);
				}
			}
			int i;
			for(i = 0; i < filaExecucao.size(); i++){
				System.out.println("FilaExecucao");
				for(Tarefa dependenteDoExecultando: filaExecucao.get(i).getListaDependencias()){
					System.out.println("DependenteDoExecultando");
					if(tarefasDoProblema.get(dependenteDoExecultando.getIdTarefa() - 1).getQtdaDependencias() > 0){
						System.out.println("Quantidade de dependentes maior que zero");
						tarefasDoProblema.get(dependenteDoExecultando.getIdTarefa() - 1).setQtdaDependencias(tarefasDoProblema.get(dependenteDoExecultando.getIdTarefa() - 1).getQtdaDependencias() - 1);
					}else{
						System.out.println("Quantidade de dependentes menor ou igual a zero adiciona na filaDeExecul��o");
						filaExecucao.add(tarefasDoProblema.get(dependenteDoExecultando.getIdTarefa() - 1)); 
					}
					
					if(tarefasDoProblema.get(dependenteDoExecultando.getIdTarefa() - 1).getQtdaDependencias() <= 0){
						System.out.println("Adicionado na lista ");
						filaExecucao.add(tarefasDoProblema.get(dependenteDoExecultando.getIdTarefa() - 1));
						System.out.println("Tamanho da filaDeExecu��o: " + filaExecucao.size());
						
					}
					
				}
				saida.add(filaExecucao.get(i));
			}
			
			for(Tarefa s: saida){
				System.out.print("Tarefa: " + s.getIdTarefa() + " ");
			}
			problema.setTarefasSaida(saida);
		}
	}

	/**
	 * Processa a sa�da
	 */
	private static void saida() {

		try (PrintWriter arquivo = new PrintWriter(
				new FileWriter("saida.out", true))) {
			for (Problema problema : problemas) {
				for (Tarefa tarefa : problema.getTarefasSaida()) {
					arquivo.append((tarefa.getIdTarefa()) + ", ");
				}
				arquivo.printf("\r\n");
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println("deu erro");
			e.printStackTrace();
		}

	}

}