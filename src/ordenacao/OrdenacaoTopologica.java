package ordenacao;
/** Trabalho da disciplina de Estrutura de Dados - Fanor - 2016.1
 * 	Professor: 
 *		- Adriano Patrick do Nascimento Cunha - acunha2@fanor.edu.br
 *	Equipe:
 *		- nome - email
 *		- nome - email
 *	Problema:
 * 		QUESTÃO ÚNICA: 
 *			Implementar um algoritmo de utilizando uma estrutura de dados do tipo FILA para solucionar o problema
 *			da ordenação topológica. A Ordenação topológica é um grafo acíclico orientado, onde cada vértice 
 *			esteja ordenado pela dependência existente entre os vértices. (https://www.youtube.com/watch?v=URdtNUdxU3o)
 *			Conceitos:
 *			- Uma permutação dos vértices de um digrafo é uma sequência em que cada vértice aparece uma só vez
 *			- Uma ordenação topológica é uma permutação dos vértices v1, ..., vn,
 *				de um digrafo acíclico, de forma que para qualquer aresta (vi ; vj ), i < j .
 *				- qualquer caminho entre vi e vj não passa por vk se k < i ou k > j . 
 *			- Digrafos com ciclos não admitem ordenação topológica.
 *		ENTRADA: 
 *			O arquivo de entrada contém várias instâncias do problema. Cada linha inicia com o número de tarefas, 
 *			seguido da lista de dependências entre estas tarefas, as dependências são representas como vértices do
 *			tipo: (a, b), onde "a" representa a tarefa a qual "b" depende, ou seja, para que "b" seja executado é 
 *			necessário que "a" tenha terminado. Não será inserido digrafo cíclico.
 *		SAIDA: 
 *			A saída consiste de tantas linhas quantas instâncias do problema houver. Em cada linha, devem ser
 *		impressos a lista de tarefas ordenadas topologicamente, ou seja, com base na dependência das tarefas.
 *		EXEMPLO:
 *			Entrada:
 *				1 
 *				4 (2,1) (2,3)
 *				5 (2,1) (2,3) (3,1) (1,5)
 *			Saída:
 *				1
 *				4, 2, 1, 3
 *				4, 2, 3, 1, 5
 */

import java.io.*;
import java.util.*;


/**
 * @author patrick.cunha
 * @author ericfarrias@hotmail.com
 * @since 30/03/2016 - Ordenação Topológica
 */
public class OrdenacaoTopologica {

	private static final String DELIMITADORES = "\\(|\\)";
	private static List<Problema> problemas = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		processa(args[0]);
		System.exit(0);
	}

	/**
	 * Método principal
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
		
		// for está colocando todos os valores do arquivo dentro de um vetor.
		for (int i = 0; i < dependencias.length; i++) {
			dependencias[i] = token.nextToken();
		}
		
		// for está criando tarefas, tantas quanto a quantidade de tarefas especificado pelo aquivo.
		for (int i = 0; i < qtdaTarefas; i++) {
			Tarefa tarefa = new Tarefa();
			tarefa.setIdTarefa(i + 1);
			tarefas.add(tarefa);
		}
		
		for(int i = 0; i < qtdaTarefas;i++){
			tarefas.get(i).setQtdaDependencias(retornaQuantidadeDeDependencias(
					dependencias, tarefas.get(i).getIdTarefa()));
				tarefas.get(i).setListaDependencias(retornaListaDeDependencias(dependencias,
						tarefas.get(i).getIdTarefa(), tarefas));
		}

		return tarefas;
	}

	// TODO implementarar
	private static int retornaQuantidadeDeDependencias(String[] dependencias,
			Integer idTarefa) {
		int numeroDependencias = 0;
		for(String aux: dependencias){
			if(Integer.parseInt( aux.split(",")[1] ) == idTarefa){
				numeroDependencias++;
			}
		}
		return numeroDependencias; // numero de dependencias esta ok
	}

	// TODO implementarar
	private static List<Tarefa> retornaListaDeDependencias(String[] dependencias, Integer idTarefa, List<Tarefa> tarefas) {
		
		List<Tarefa> tempTarefas = new ArrayList<>();
	
		for(String aux: dependencias){
			if(Integer.parseInt( aux.split(",")[0] ) == idTarefa){
				tempTarefas.add(tarefas.get(Integer.parseInt( aux.split(",")[1]) - 1));
			}
		}
		
		return tempTarefas;
	}

	/**
	 * Método que realiza a ordenação topológica.
	 */// TODO implementarar
	private static void ordena() {
		for(Problema problema: problemas){
			
			List<Tarefa> tarefasDoProblema = problema.getTarefasEntrada(); // lista de entradas
			
			List<Tarefa> filaExecucao = new ArrayList<>(); // Fila de Excução, armazena as os valores que serão avaliado.
			
			List<Tarefa> saida = new ArrayList<>();
			
			for(Tarefa tarefa: tarefasDoProblema){
				if(tarefa.getQtdaDependencias() == 0){
					filaExecucao.add(tarefa);
				}
			}
			
			int i;
			for(i = 0; i < filaExecucao.size(); i++){
				for(Tarefa dependenteDoExecultando: filaExecucao.get(i).getListaDependencias()){
					if(dependenteDoExecultando.getQtdaDependencias() > 0){
						dependenteDoExecultando.setQtdaDependencias(dependenteDoExecultando.getQtdaDependencias() - 1);
					}else{
						filaExecucao.add(dependenteDoExecultando); 
					}
					
					if(dependenteDoExecultando.getQtdaDependencias() <= 0){
						filaExecucao.add(dependenteDoExecultando);
						
					}
					
				}
				saida.add(filaExecucao.get(i));
			}
			
			for(Tarefa s: saida){
				System.out.print("Tarefa: " + s.getIdTarefa() + " ");
			}
			System.out.println();
			problema.setTarefasSaida(saida);
		}
	}

	/**
	 * Processa a saída
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