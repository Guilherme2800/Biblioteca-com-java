package BibliotecaJava;

//Importanto classes
import java.util.Scanner;

public class Biblioteca {

	public static void main(String[] agrs) {

		// Declara��o de objetos de instacia
		Scanner input = new Scanner(System.in);
		Livro livro = new Livro();
		Estoque estoque = new Estoque();

		// Declara��o variaveis
		byte op = 0, escolha = 0;

		// While mantem o usuario no menu at� ele querer sair
		while (op != 6) {
			System.out.println("Bem vindo ao sistema de gerenciamento da biblioteca Amis");
			System.out.println("\n\n1-Vender/Alugar" + "\n2-Atualizar dados" + "\n3-Pesquisar dados"
					+ "\n4-Devolu��o de livros" + "\n5-Cadastro" + "\n6-Sair" + "\n\nInforme sua op��o: ");
			op = input.nextByte();

			// Switch faz entrar em um sub menu.
			switch (op) {

			// SubMenu de venda/aluguel
			case 1:
				System.out.println("\n\nSistema de gerenciamento da biblioteca Amis" + "\n\n1-Vender" + "\n2-Alugar"
						+ "\n\nSua op��o: ");
				escolha = input.nextByte();

				if (escolha == 1) {

					// Chama o m�todo setvenda do livro.
					livro.setVenda(livro);

				} else if (escolha == 2) {

					// Chama o m�todo setaluguel do livro.
					livro.setAluguel(livro);

				} else {
					System.out.println("Op��o invalida");
				}

				break;

			// SubMenu de Atualiza��o
			case 2:

				System.out.println("\n\nSistema de gerenciamento da biblioteca Amis"
						+ "\n\n1-Atualizar estoque de livro" + "\n2-Atualizar dado do cliente" + "\n\nSua op��o: ");

				escolha = input.nextByte();

				if (escolha == 1) {
					System.out.println("Informe o codigo do livro: ");
					livro.codigo = input.next();

					System.out.println("Informe a quantidade: ");
					int quantidade = input.nextInt();

					// Atualiza a quantidade de livros chamando o m�todo estoque
					estoque.setAtualizaEstoqueNovasUnidades(livro, quantidade);

				} else if (escolha == 2) {

					String novoendere�o;

					System.out.println("Informe o CPF do cliente:");
					String cpf = input.next();
					livro.cliente.pesquisa(cpf, true);

					String limpar = input.nextLine();

					System.out.print("\n\nInforme o novo endere�o: ");
					novoendere�o = input.nextLine();

					// Atualiza o endere�o do cliente
					livro.cliente.atualizarArquivoClienteEndere�o(novoendere�o);
					// Escreve o cliente com o novo endere�o
					livro.cliente.pesquisa(cpf, true);

				} else {
					System.out.println("Op��o invalida");
				}

				break;

			// Submenu de pesquisa
			case 3:

				System.out.println("\n\nSistema de gerenciamento da biblioteca Amis" + "\n\n1-Pesquisar livro"
						+ "\n2-Pesquisar cliente" + "\n3-Pesquisa registro do cliente" + "\n\nSua op��o: ");

				escolha = input.nextByte();

				if (escolha == 1) {
					estoque.pesquisa(livro, true, true);

				} else if (escolha == 2) {

					System.out.println("Informe o CPF do cliente:");
					String cpf = input.next();
					livro.cliente.pesquisa(cpf, true);

				} else if (escolha == 3) {

					System.out.println("Informe o CPF do cliente:");
					String cpf = input.next();
					livro.ra.pesquisaRegistros(cpf);

				} else {
					System.out.println("Op��o invalida");
				}

				break;

			// Submenu Para devolu��o de livros
			case 4:
				livro.setDevolu��o(livro);
				break;

			// Submenu para cadastro
			case 5:

				System.out.println("\n\nSistema de gerenciamento da biblioteca Amis" + "\n\n1-Cadastrar livro"
						+ "\n2-Cadastrar cliente" + "\n\nSua op��o: ");

				escolha = input.nextByte();

				if (escolha == 1) {
					livro.cadastroDeLivro(livro);
				} else if (escolha == 2) {
					livro.cliente.cadastro();
				} else {
					System.out.println("Op��o invalida");
				}

				break;

			default:
				op = 6;
				break;

			}

		}
	}

}
