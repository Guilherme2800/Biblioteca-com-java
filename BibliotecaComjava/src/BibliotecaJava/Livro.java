package BibliotecaJava;

import java.io.IOException;
import java.util.Scanner;

public class Livro {

	protected String titulo, autor, lan�amento, codigo, quantidade, alugados = "0", pre�oAluguel, pre�oVenda;
	Scanner input = new Scanner(System.in);

	Registros ra = new Registros();
	Cliente cliente = new Cliente();
	Estoque estoque = new Estoque();

	// Realiza o cadastro recebendo dados e repassando para o estoque.
	public void cadastroDeLivro(Livro livro) {

		System.out.println("Informe o titulo do livro: ");
		livro.titulo = input.nextLine();

		System.out.println("Informe o autor do livro: ");
		livro.autor = input.nextLine();

		System.out.println("Informe o lan�amento do livro: ");
		livro.lan�amento = input.nextLine();

		System.out.println("Informe o c�digo de titulo do livro: ");
		livro.codigo = input.nextLine();

		System.out.println("Informe o quantidade do livro: ");
		livro.quantidade = input.nextLine();

		System.out.println("Informe o pre�o do livro para aluguel (Utilize ponto [.] inves da virgula [,]): ");
		livro.pre�oAluguel = input.nextLine();

		System.out.println("Informe o pre�o para venda (Utilize ponto [.] inves da virgula [,]): ");
		livro.pre�oVenda = input.nextLine();

		estoque.setEscreveArquivo(livro);

	}

	// Realiza os metodos de venda de um livro.
	private void venda(Livro livro, int comprar) {

		String cpf = null;
		boolean existeCliente = false;
		int continuar = 1;
		double pre�o = 0.0;

		quantidade = estoque.pesquisa(livro, true, true);

		if (quantidade != "-1") {
			
			livro.pre�oVenda.replace(',', '.');
			
			double pre�oAlugar = Double.parseDouble(livro.pre�oVenda);
			pre�o = calcular(pre�oAlugar, comprar);

			System.out.println("Quantidade a ser paga: " + pre�o);
			System.out.print("\nDeseja continuar a opera��o ? 1-sim / 2- n�o" + "\nEscolha: ");
			continuar = input.nextInt();

			if (continuar == 1) {

				System.out.println("Informe o cpf do cliente:");
				cpf = input.next();
				existeCliente = cliente.pesquisa(cpf, false);

				if (existeCliente) {
					int quantidadeConvertida = Integer.parseInt(livro.quantidade);

					if (quantidadeConvertida != -1) {

						if (comprar <= quantidadeConvertida) {
							System.out.println("Venda autorizada");

							boolean existeRegistro = ra.pesquisaRegistros(cpf);

							if (existeRegistro) {

								try {

									ra.possuiRegistro(cpf, livro.codigo, 0, comprar, 0, pre�o);

								} catch (IOException e) {
									System.out.println("Ocorreu um erro em salvar o registro da venda");
									e.printStackTrace();
								}

							} else {

								try {

									ra.novoRegistro(cpf, livro.codigo, 0, comprar, pre�o);

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							int novaQuantidade = quantidadeConvertida - comprar;
							estoque.setAtualizaEstoqueVenda(livro, novaQuantidade);

						} else {
							System.out.println(
									"N�o foi possivel realizar a venda\nA quantidade requerida � maior que a de estoque");
						}

						System.out.println("Venda finalizada");

					}

				}

			}
		}
	}

	// Realiza os metodos de aluguel de um livro.
	private void aluguel(Livro livro, int alugar) {

		String quantidade, cpf = null;
		boolean ok = false;
		boolean existeCliente = false;
		int continuar = 1;
		double pre�o = 0.0;

		// Pega a quantidade de livros disponivel no estoque.
		quantidade = estoque.pesquisa(livro, true, true);

		if (quantidade != "-1") {

			livro.pre�oAluguel.replace(',', '.');
			double pre�oAlugar = Double.parseDouble(livro.pre�oAluguel);
			pre�o = calcular(pre�oAlugar, alugar);

			System.out.println("Quantidade a ser paga: " + pre�o);
			System.out.print("\nDeseja continuar a opera��o ? 1-sim / 2- n�o" + "\nEscolha: ");
			continuar = input.nextInt();

			if (continuar == 1) {

				// Pega CPF do cliente.
				System.out.println("Informe o cpf do cliente:");
				cpf = input.next();

				existeCliente = cliente.pesquisa(cpf, false);

				if (existeCliente) {
					// Retorna a quantidade j� alugada do cliente.

					int qntjaalugada = cliente.pesquisa(cpf);

					// Converte a quantidade de livros para int.
					int quantidadeConvertida = Integer.parseInt(livro.quantidade);

					if (alugar <= quantidadeConvertida) {

						boolean existeRegistro = ra.pesquisaRegistros(cpf);

						if (existeRegistro) {

							try {

								ok = ra.possuiRegistro(cpf, livro.codigo, alugar, 0, qntjaalugada, pre�o);

							} catch (IOException e) {
								System.out.println("Ocorreu um erro em salvar o registro do aluguel");
								e.printStackTrace();
							}

						} else {

							try {

								ok = ra.novoRegistro(cpf, livro.codigo, alugar, 0, pre�o);

							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						if (ok == true) {

							int novaQuantidade = quantidadeConvertida - alugar;
							System.out.println(novaQuantidade);

							String qntAlugada = Integer.toString(alugar);
							cliente.atualizarArquivoCliente(qntAlugada);

							estoque.setAtualizaEstoqueAluguel(livro, novaQuantidade, alugar);

						} else {
							System.out.println("N�o foi possivel realizar o aluguel"
									+ "\npois o cliente ultrapassaria os limites de alugueis");
						}

					}

					System.out.println("Aluguel finalizada");

				}
			}

		}
	}

	// Realiza os metodos para devolu��o de um livro alugado.
	private void devolu��o(Livro livro, int devolvidos) {

		String cpf;

		// Pega CPF do cliente.
		System.out.println("Informe o cpf do cliente:");
		cpf = input.next();

		boolean existeCliente = cliente.pesquisa(cpf, false);

		if (existeCliente) {
			// Retorna quantidade j� alugada
			int qntjaalugada = cliente.pesquisa(cpf);

			// Converte a quantidade de livros para int.

			if (qntjaalugada == 0) {
				System.out.println("O cliente n�o tem nem um livro para devolver");

			} else if (devolvidos <= qntjaalugada) {

				System.out.println("Informe o c�digo do livro: ");
				livro.codigo = input.next();

				String existe = estoque.pesquisa(livro, true, false);

				switch (existe) {
				
				
				case "-1":
					break;
				
					default:
						// ATUALIZA��O LIVRO
						int quantidadeLivrosConvertida = Integer.parseInt(livro.quantidade);
						int livrosAlugadosConvertida = Integer.parseInt(livro.alugados);

						quantidadeLivrosConvertida += devolvidos;
						livrosAlugadosConvertida -= devolvidos;

						estoque.setAtualizaEstoqueDevolu��o(livro, quantidadeLivrosConvertida, livrosAlugadosConvertida);

						// ATUALIZA��O CLIENTE
						int alugadosCliente = Integer.parseInt(cliente.qntAlugados);

						alugadosCliente -= devolvidos;
						cliente.qntAlugados = Integer.toString(alugadosCliente);

						cliente.atualizarArquivoCliente(cliente.qntAlugados);

						// ATUALIZA REGISTRO
						try {
							ra.possuiRegistro(cpf, devolvidos, livro.codigo);
						} catch (IOException e) {

							System.out.println("Ocorreu um erro ao atualizar o registro do aluguel");

						}
						break;
					
				}
				
				
				
			} else {
				System.out.println("Quantidade a ser devolvida � maior que a alugada");
			}
			}
		
		}
	

	private double calcular(double pre�o, int quantidadeAlugarComprar) {

		double resultado = pre�o * quantidadeAlugarComprar;

		return resultado;
	}

	// Metodos set e get
	public void setVenda(Livro livro) {

		int quantidade;

		System.out.println("Informe a quantidade para venda: ");
		quantidade = input.nextInt();

		if (quantidade < 0) {
			System.out.println("Valor Invalido para venda");
		} else {
			venda(livro, quantidade);
		}
	}

	public void setAluguel(Livro livro) {

		int quantidade;

		System.out.println("Informe a quantidade para alugar: ");
		quantidade = input.nextInt();

		if (quantidade < 0 || quantidade > 3) {
			System.out.println("Valor Invalido para aluguel");
		} else {
			aluguel(livro, quantidade);
		}
	}

	public void setDevolu��o(Livro livro) {
		int devolvidos;
		System.out.println("Informe a quantidade para devolu��o: ");
		devolvidos = input.nextInt();
		devolu��o(livro, devolvidos);
	}

}
