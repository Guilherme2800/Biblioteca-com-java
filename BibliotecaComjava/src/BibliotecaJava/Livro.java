package BibliotecaJava;

import java.io.IOException;
import java.util.Scanner;

public class Livro {

	protected String titulo, autor, lançamento, codigo, quantidade, alugados = "0", preçoAluguel, preçoVenda;
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

		System.out.println("Informe o lançamento do livro: ");
		livro.lançamento = input.nextLine();

		System.out.println("Informe o código de titulo do livro: ");
		livro.codigo = input.nextLine();

		System.out.println("Informe o quantidade do livro: ");
		livro.quantidade = input.nextLine();

		System.out.println("Informe o preço do livro para aluguel (Utilize ponto [.] inves da virgula [,]): ");
		livro.preçoAluguel = input.nextLine();

		System.out.println("Informe o preço para venda (Utilize ponto [.] inves da virgula [,]): ");
		livro.preçoVenda = input.nextLine();

		estoque.setEscreveArquivo(livro);

	}

	// Realiza os metodos de venda de um livro.
	private void venda(Livro livro, int comprar) {

		String cpf = null;
		boolean existeCliente = false;
		int continuar = 1;
		double preço = 0.0;

		quantidade = estoque.pesquisa(livro, true, true);

		if (quantidade != "-1") {
			
			livro.preçoVenda.replace(',', '.');
			
			double preçoAlugar = Double.parseDouble(livro.preçoVenda);
			preço = calcular(preçoAlugar, comprar);

			System.out.println("Quantidade a ser paga: " + preço);
			System.out.print("\nDeseja continuar a operação ? 1-sim / 2- não" + "\nEscolha: ");
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

									ra.possuiRegistro(cpf, livro.codigo, 0, comprar, 0, preço);

								} catch (IOException e) {
									System.out.println("Ocorreu um erro em salvar o registro da venda");
									e.printStackTrace();
								}

							} else {

								try {

									ra.novoRegistro(cpf, livro.codigo, 0, comprar, preço);

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							int novaQuantidade = quantidadeConvertida - comprar;
							estoque.setAtualizaEstoqueVenda(livro, novaQuantidade);

						} else {
							System.out.println(
									"Não foi possivel realizar a venda\nA quantidade requerida é maior que a de estoque");
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
		double preço = 0.0;

		// Pega a quantidade de livros disponivel no estoque.
		quantidade = estoque.pesquisa(livro, true, true);

		if (quantidade != "-1") {

			livro.preçoAluguel.replace(',', '.');
			double preçoAlugar = Double.parseDouble(livro.preçoAluguel);
			preço = calcular(preçoAlugar, alugar);

			System.out.println("Quantidade a ser paga: " + preço);
			System.out.print("\nDeseja continuar a operação ? 1-sim / 2- não" + "\nEscolha: ");
			continuar = input.nextInt();

			if (continuar == 1) {

				// Pega CPF do cliente.
				System.out.println("Informe o cpf do cliente:");
				cpf = input.next();

				existeCliente = cliente.pesquisa(cpf, false);

				if (existeCliente) {
					// Retorna a quantidade já alugada do cliente.

					int qntjaalugada = cliente.pesquisa(cpf);

					// Converte a quantidade de livros para int.
					int quantidadeConvertida = Integer.parseInt(livro.quantidade);

					if (alugar <= quantidadeConvertida) {

						boolean existeRegistro = ra.pesquisaRegistros(cpf);

						if (existeRegistro) {

							try {

								ok = ra.possuiRegistro(cpf, livro.codigo, alugar, 0, qntjaalugada, preço);

							} catch (IOException e) {
								System.out.println("Ocorreu um erro em salvar o registro do aluguel");
								e.printStackTrace();
							}

						} else {

							try {

								ok = ra.novoRegistro(cpf, livro.codigo, alugar, 0, preço);

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
							System.out.println("Não foi possivel realizar o aluguel"
									+ "\npois o cliente ultrapassaria os limites de alugueis");
						}

					}

					System.out.println("Aluguel finalizada");

				}
			}

		}
	}

	// Realiza os metodos para devolução de um livro alugado.
	private void devolução(Livro livro, int devolvidos) {

		String cpf;

		// Pega CPF do cliente.
		System.out.println("Informe o cpf do cliente:");
		cpf = input.next();

		boolean existeCliente = cliente.pesquisa(cpf, false);

		if (existeCliente) {
			// Retorna quantidade já alugada
			int qntjaalugada = cliente.pesquisa(cpf);

			// Converte a quantidade de livros para int.

			if (qntjaalugada == 0) {
				System.out.println("O cliente não tem nem um livro para devolver");

			} else if (devolvidos <= qntjaalugada) {

				System.out.println("Informe o código do livro: ");
				livro.codigo = input.next();

				String existe = estoque.pesquisa(livro, true, false);

				switch (existe) {
				
				
				case "-1":
					break;
				
					default:
						// ATUALIZAÇÂO LIVRO
						int quantidadeLivrosConvertida = Integer.parseInt(livro.quantidade);
						int livrosAlugadosConvertida = Integer.parseInt(livro.alugados);

						quantidadeLivrosConvertida += devolvidos;
						livrosAlugadosConvertida -= devolvidos;

						estoque.setAtualizaEstoqueDevolução(livro, quantidadeLivrosConvertida, livrosAlugadosConvertida);

						// ATUALIZAÇÂO CLIENTE
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
				System.out.println("Quantidade a ser devolvida é maior que a alugada");
			}
			}
		
		}
	

	private double calcular(double preço, int quantidadeAlugarComprar) {

		double resultado = preço * quantidadeAlugarComprar;

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

	public void setDevolução(Livro livro) {
		int devolvidos;
		System.out.println("Informe a quantidade para devolução: ");
		devolvidos = input.nextInt();
		devolução(livro, devolvidos);
	}

}
