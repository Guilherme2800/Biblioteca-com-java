package BibliotecaJava;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registros {

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	Date date = new Date(); 
	String dStr = dateFormat.format(date); 
	
	String codigo, cpf;
	protected final static int MAXALUGUEIS = 3;

	public boolean pesquisaRegistros(String cpf) {

		File registro = new File(cpf + "registro.txt");

		boolean existe = registro.exists();

		if (existe == false) {
			System.out.println("Não a registros com esse cpf.");

			// Retorna -1 caso não tenha registros do livro

			return false;
		} else {

			try (BufferedReader br = new BufferedReader(new FileReader(registro))) {

				String s;

				while ((s = br.readLine()) != null) {
					System.out.println(s);
				}

				// System.out.println("Informação na tela concluida");
			} catch (IOException e) {
				e.printStackTrace();

			}

		}
		return true;
	}

	public boolean novoRegistro(String cpf, String codigo, int quantidadeAlugado, int quantidadeComprado, double preço)
			throws IOException {

		File registro = new File(cpf + "registro.txt");

		if (quantidadeAlugado > MAXALUGUEIS) {
			return false;
		}

		boolean existe = registro.exists();

		if (existe == false) {
			registro.createNewFile();
			// System.out.println("Criado novo arquivo de registro");
		}
		
		

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(registro))) {
			bw.flush();
			bw.write("cpf: " + cpf);
			bw.newLine();
			bw.write("Codigo do livro: " + codigo);
			bw.newLine();
			bw.write("Quantidade alugados: " + quantidadeAlugado);
			bw.newLine();
			bw.write("Quantidade comprado: " + quantidadeComprado);
			bw.newLine();
			bw.write("Valor pago: " + preço);
			bw.newLine();
			bw.write("Data: " + dStr);
			bw.newLine();
			bw.write(" ");
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return true;
	}

	public boolean possuiRegistro(String cpf, String codigo, int quantidadeAlugado, int quantidadeComprado,
			int qntjaalugada, double preço) throws IOException {

		int qntfinal = quantidadeAlugado + qntjaalugada;

		if (qntfinal > MAXALUGUEIS) {
			return false;
		}

		File registro = new File(cpf + "registro.txt");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(registro, true))) {
			bw.flush();
			bw.newLine();
			bw.write("Codigo do livro: " + codigo);
			bw.newLine();
			bw.write("Quantidade alugados: " + quantidadeAlugado);
			bw.newLine();
			bw.write("Quantidade comprado: " + quantidadeComprado);
			bw.newLine();
			bw.write("Valor pago: " + preço);
			bw.newLine();
			bw.write("Data: " + dStr);
			bw.newLine();
			bw.write(" ");
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

		return true;
	}

	public boolean possuiRegistro(String cpf, int devolvidos, String codigo) throws IOException {

		File registro = new File(cpf + "registro.txt");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(registro, true))) {
			bw.flush();
			bw.newLine();
			bw.write("Codigo do livro: " + codigo);
			bw.newLine();
			bw.write("Quantidade devolvidos: " + devolvidos);
			bw.newLine();
			bw.write("Data: " + dStr);
			bw.write(" ");
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

		return false;

	}

}
