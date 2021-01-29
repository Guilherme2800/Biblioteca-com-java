package BibliotecaJava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Cliente {

	protected String nome,nascimento,CPF,qntAlugados="0",endereço;
	
	Scanner input = new Scanner(System.in);
	
	public void cadastro() {
	
		System.out.println("Informe o nome do cliente: ");
		nome = input.nextLine();
		System.out.println("Informe o nascimento");
		nascimento = input.nextLine();
		System.out.println("Informe o CPF: ");
		CPF= input.nextLine();
		System.out.println("Informe o endereço");
		endereço = input.nextLine();
		
		gravarArquivoCliente();
		
		
	}

	
	public void gravarArquivoCliente() {
		
		File arqCliente = new File(CPF+".txt");
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(arqCliente))){
			
			bw.flush();
			bw.write(nome);
			bw.newLine();
			bw.write(nascimento);
			bw.newLine();
			bw.write(CPF);
			bw.newLine();
			bw.write(endereço);
			bw.newLine();
			bw.write(qntAlugados);
			bw.newLine();
			bw.write(" ");
			bw.newLine();
			bw.close();
			
			
			System.out.println("Cadastro realizado com sucesso.");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
    public void atualizarArquivoCliente(String qntAlugada) {
		
		File arqCliente = new File(CPF+".txt");
		
		qntAlugados = qntAlugada;
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(arqCliente))){
			
			bw.flush();
			bw.write(nome);
			bw.newLine();
			bw.write(nascimento);
			bw.newLine();
			bw.write(CPF);
			bw.newLine();
			bw.write(endereço);
			bw.newLine();
			bw.write(qntAlugados);
			bw.newLine();
			bw.write(" ");
			bw.newLine();
			bw.close();
			
			
			System.out.println("Cadastro realizado com sucesso.");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
    public void atualizarArquivoClienteEndereço(String novoendereço) {
		
		File arqCliente = new File(CPF+".txt");
		
		endereço = novoendereço;
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(arqCliente))){
			
			bw.flush();
			bw.write(nome);
			bw.newLine();
			bw.write(nascimento);
			bw.newLine();
			bw.write(CPF);
			bw.newLine();
			bw.write(endereço);
			bw.newLine();
			bw.write(qntAlugados);
			bw.newLine();
			bw.write(" ");
			bw.newLine();
			bw.close();
			
			
			System.out.println("Cadastro realizado com sucesso.");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
    
	public boolean pesquisa(String cpf, boolean escrever) {

		File file = new File(cpf +".txt");

		boolean existe = file.exists();

		if (existe == false) {
			System.out.println("Não a registros de cliente com esse cpf.");
		return false;
		} else {

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				String s;
				byte continua = 1;

				while ((s = br.readLine()) != null) {

					if (continua == 1 && escrever == true) {
						System.out.println("nome: " + s);
					}else if(continua == 1 ) {
						this.nome = s;
						
					}

					if (continua == 2 && escrever == true) {
						System.out.println("Nascimento: " + s);
						this.nascimento = s;

					} else if (continua == 2) {
						this.nascimento = s;
					}

					if (continua == 3 && escrever == true) {
						System.out.println("CPF: " + s);
						this.CPF = s;

					} else if (continua == 3) {
						this.CPF = s;
					}
					
					if (continua == 4 && escrever == true) {
						System.out.println("Endereço: " + s);
						this.endereço = s;

					} else if (continua == 4) {
						this.endereço = s;
					}
					
					if (continua == 5 && escrever == true) {
						System.out.println("qnt Alugados: " + s);
						this.qntAlugados = s;

					} else if (continua == 5) {
						this.qntAlugados = s;
					}
					
					continua++;

				}

				//System.out.println("Informação na tela concluida");
			} catch (IOException e) {
				e.printStackTrace();

			}
			
		}
		return true;
	}
	
	//Pesquisa apenas para pegar a quantidade de filmes alugados
	public int pesquisa(String cpf) {
		
		File file = new File(cpf +".txt");


			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				String s;
				byte continua = 1;

				while ((s = br.readLine()) != null) {
					
					if (continua == 5) {
						this.qntAlugados = s;
					}
					
					continua++;

				}

				//System.out.println("Informação na tela concluida");
			} catch (IOException e) {
				e.printStackTrace();

			}
			
			int alugados = Integer.parseInt(qntAlugados);
			
			return alugados;
		}
	
	
	
}



