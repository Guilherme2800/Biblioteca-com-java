package BibliotecaJava;

import java.io.*;
import java.util.Scanner;

public class Estoque {

	// Escreve dentro de um arquivo, recebendo um tipo livro.
	private void escreveArquivo(Livro livro) {

		File file = new File(livro.codigo + ".txt");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.flush();
			bw.write(livro.codigo);
			bw.newLine();
			bw.write(livro.titulo);
			bw.newLine();
			bw.write(livro.autor);
			bw.newLine();
			bw.write(livro.lan�amento);
			bw.newLine();
			bw.write(livro.quantidade);
			bw.newLine();
			bw.write(livro.alugados);
			bw.newLine();
			bw.write(livro.pre�oAluguel);
			bw.newLine();
			bw.write(livro.pre�oVenda);
			bw.newLine();
			bw.write(" ");
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

		System.out.println("Cadastro realizado com sucesso");
	}

	// Pesquisa por um livro no estoque e retorna sua quantidade.
	public String pesquisa(Livro livro, boolean escrever, boolean pedircodigo) {

		Scanner input = new Scanner(System.in);

		if (pedircodigo == true) {
			System.out.println("Informe o codigo do livro: ");
			livro.codigo = input.nextLine();
		}
		File file = new File(livro.codigo + ".txt");

		boolean existe = file.exists();

		if (existe == false) {
			System.out.println("N�o a registros de livro com esse c�digo.");

			// Retorna -1 caso n�o tenha registros do livro
			return "-1";

		} else {

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				String s;
				byte continua = 1;

				while ((s = br.readLine()) != null) {

					if (continua == 1 && escrever == true) {
						System.out.println("\nCodigo do titulo: " + s);
					} else if (continua == 1) {
						livro.codigo = s;
					}

					if (continua == 2 && escrever == true) {
						System.out.println("titulo: " + s);
						livro.titulo = s;

					} else if (continua == 2) {
						livro.titulo = s;
					}

					if (continua == 3 && escrever == true) {
						System.out.println("Autor: " + s);
						livro.autor = s;

					} else if (continua == 3) {
						livro.autor = s;
					}

					if (continua == 4 && escrever == true) {
						System.out.println("lan�amento: " + s);
						livro.lan�amento = s;

					} else if (continua == 4) {
						livro.lan�amento = s;
					}

					if (continua == 5 && escrever == true) {
						System.out.println("quantidade: " + s);
						livro.quantidade = s;

					} else if (continua == 5) {
						livro.quantidade = s;
					}

					if (continua == 6 && escrever == true) {
						System.out.println("Alugados: " + s);
						livro.alugados = s;

					} else if (continua == 6) {
						livro.alugados = s;
					}

					if (continua == 7 && escrever == true) {
						System.out.println("Pre�o aluguel: " + s);
						livro.pre�oAluguel = s;

					} else if (continua == 7) {
						livro.pre�oAluguel = s;
					}

					if (continua == 8 && escrever == true) {
						System.out.println("Pre�o venda: " + s + "\n");
						livro.pre�oVenda = s;

					} else if (continua == 8) {
						livro.pre�oVenda = s;
					}

					continua++;

				}

				// System.out.println("Informa��o na tela concluida");
			} catch (IOException e) {
				e.printStackTrace();

			}
			return livro.quantidade;
		}
	}

	// Atualiza o estoque apos vender.
	private void atualizaEstoqueVenda(Livro livro, int novaQuantidade) {

		pesquisa(livro, false, false);

		livro.quantidade = Integer.toString(novaQuantidade);

		escreveArquivo(livro);

		// System.out.println("Atualiza��o de estoque concluida.");
	}

	// Atualiza o estoque apos um aluguel.
	private void atualizaEstoqueAluguel(Livro livro, int novaQuantidade, int quantidadeAlugados) {

		pesquisa(livro, false, false);

		quantidadeAlugados += Integer.parseInt(livro.alugados);

		livro.quantidade = Integer.toString(novaQuantidade);
		livro.alugados = Integer.toString(quantidadeAlugados);

		escreveArquivo(livro);

		// System.out.println("Atualiza��o de estoque concluida.");
	}

	// Atualiza o estoque apos comprar novas unidades para o estoque
	private void atualizaEstoqueNovasUnidades(Livro livro, int unidades) {

		String possui = pesquisa(livro, false, false);

		if (possui != "-1") {
			unidades += Integer.parseInt(livro.quantidade);

			livro.quantidade = Integer.toString(unidades);

			escreveArquivo(livro);

			// System.out.println("Atualiza��o de estoque concluida.");
		}
	}

	// Atualiza o estoque apos uma devolu��o
	private void atualizaEstoqueDevolu��o(Livro livro, int novaQuantidade, int quantidadeAlugados) {

		// Pega as informa��es do livro no estoque
		pesquisa(livro, false, false);

		// Passa a nova quantidade de livros e a nova quantidade alugada
		livro.quantidade = Integer.toString(novaQuantidade);
		livro.alugados = Integer.toString(quantidadeAlugados);

		escreveArquivo(livro);

		// System.out.println("Atualiza��o de estoque concluida.");
	}

	// Metodos set e get
	public void setEscreveArquivo(Livro livro) {

		if (livro.titulo == null || livro.codigo == null || livro.autor == null || livro.lan�amento == null) {
			System.out.println("N�o foi possivel realizar o cadastro, pois h� variaveis null");
		} else {
			escreveArquivo(livro);
		}
	}

	public void setAtualizaEstoqueVenda(Livro livro, int novaQuantidade) {
		atualizaEstoqueVenda(livro, novaQuantidade);
	}

	public void setAtualizaEstoqueAluguel(Livro livro, int novaQuantidade, int quantidadeAlugados) {
		atualizaEstoqueAluguel(livro, novaQuantidade, quantidadeAlugados);
	}

	public void setAtualizaEstoqueNovasUnidades(Livro livro, int unidades) {
		atualizaEstoqueNovasUnidades(livro, unidades);
	}

	public void setAtualizaEstoqueDevolu��o(Livro livro, int novaQuantidade, int quantidadeAlugados) {
		atualizaEstoqueDevolu��o(livro, novaQuantidade, quantidadeAlugados);
	}
}