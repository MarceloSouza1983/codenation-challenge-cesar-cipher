package br.com.marcelosouza;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

// Criado por Marcelo Souza - map.souza1983@gmail.com

public class RunProgram {

	public static void main(String[] args) {

		long inicio = System.currentTimeMillis();

		VerificaConexaoInternet internet = new VerificaConexaoInternet();
		if (internet.verificaConexao() == false) {
			System.out.println("Não existe conexão com a Internet ativa.");

		} else {

			System.out.println("Estabelecendo conexão com a API\n");
			Endereco end = capturaUrl();

			HttpURLConnection con = null;
			try {
				URL url = new URL(end.getUrl());
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();

				// Captura o retorno da url
				StringBuilder sb = capturaJson(con);
				// fim da captura, o StringBuilder sb contém as chaves e valores do Json

				switch (con.getResponseCode()) {
				case 200:
					System.out.println("Código de resposta = " + con.getResponseCode() + " - " + con.getResponseMessage());
					System.out.println("Tamanho do arquivo = " + con.getContentLength() + " bytes");
					System.out.println("Tempo de conexão = " + con.getReadTimeout() + " ms");
					System.out.println("Método de conexão = " + con.getRequestMethod());
					System.out.println("Tipo de arquivo = " + con.getContentType());
					System.out.println("Contéudo do Json:" + "\n");
					System.out.println(sb.toString());
					System.out.println("JSON recebido com sucesso!");
					System.out.println();

					String json = sb.toString();

					// Instância um objeto e armazena na classse DadosObjetoCriptografado
					Gson gson = new Gson();
					DadosObjetoCriptografado objeto = gson.fromJson(json, DadosObjetoCriptografado.class);

					// Método para salvar String em um arquivo
					gravarArquivo(json);

					// Método para decifrar o código

					EncryptDecrypt decrypt = new EncryptDecrypt();
					String textoCifrado = objeto.getCifrado().toString();
					String cifrado = decrypt.descriptografar(textoCifrado.toCharArray(), 8);
					String descriptografado = cifrado.toString();

					System.out.println("\nMensagem descriptografada: " + descriptografado + "\n"); // Mostra a mensagem descriptografada

					// Método para gravar Mensagem descriptografada no arquivo
					objeto.setDecifrado(descriptografado);

					// Método para
					String numero_casas = objeto.getNumero_casas().toString();
					String token = objeto.getToken();

					// Faz a montagem de uma String em formato JSON
					String builderText = "{\"" + "numero_casas" + "\":" + numero_casas + ",\"" + "token"
							+ "\":\"" + token + "\",\"" + "cifrado" + "\":\"" + textoCifrado
							+ "\",\"" + "decifrado" + "\":\"" + descriptografado
							+ "\",\"" + "resumo_criptografico" + "\":\"" + "" + "\"}";
					System.out.println(builderText);

					// Método para salvar String em um arquivo
					gravarArquivo2(builderText);

					// Método para capturar a criptografia SHA de uma string
					EncryptDecrypt sha = new EncryptDecrypt();
					String textoCriptografadoSHA = sha.criptografarSHA(objeto.getDecifrado().toString());
					System.out.println(textoCriptografadoSHA);

					// Método para escrever o textoCriptografadoSHA no arquivo
					// Faz a montagem de uma String em formato JSON
					String builderText2 = "{\"" + "numero_casas" + "\":" + numero_casas + ",\"" + "token"
							+ "\":\"" + token + "\",\"" + "cifrado" + "\":\"" + textoCifrado
							+ "\",\"" + "decifrado" + "\":\"" + descriptografado
							+ "\",\"" + "resumo_criptografico" + "\":\"" + textoCriptografadoSHA + "\"}";
					System.out.println(builderText);

					// Método para salvar String em um arquivo
					gravarArquivo3(builderText2);

					// Método para enviar arquivo para a API
					SendFileToAPI send = new SendFileToAPI();
					send.enviarArquivo("https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=8092cbb81fce8e0ab2551c9a1bbb4ca2ec7e7867", "D://answer.json");
					System.out.println("Parabéns!!! Arquivo transmitido com sucesso.");

					// Mostra o tempo gasto no processamento do programa
					long fim = System.currentTimeMillis();
					long tempo = (fim - inicio) / 1000;
					System.out.println("\nTempo gasto: " + tempo + " s");

					break;

				case 400:
					System.out.println("Bad Request - 400");
					break;

				case 500:
					System.out.println("Status - 500");
					break;
				}


			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (con != null)
					con.disconnect();
			}

		}
	}

	private static StringBuilder capturaJson(HttpURLConnection con) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		br.close();
		return sb;
	}

	public static Endereco capturaUrl() {
		Endereco end = new Endereco();
		end.setUrl("https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=8092cbb81fce8e0ab2551c9a1bbb4ca2ec7e7867");
		return end;
	}

	private static void gravarArquivo(String json) {
		String nomeArquivo = "d://answer.json";

		// Cria um arquivo com o caminho e nome especificado na String nomeArquivo
		try (FileWriter fw = new FileWriter (nomeArquivo)) {

			fw.write(json); // Escreve no arquivo

		} catch (IOException ex) {
			System.out.println("Não foi possível criar um arquivo.");
		}

		System.out.println("Texto " + nomeArquivo.replaceFirst("/", "") + " gravado com sucesso.");
	}

	private static void gravarArquivo2(String builderText) {
		String nomeArquivo = "d://answer.json";

		// Cria um arquivo com o caminho e nome especificado na String nomeArquivo
		try (FileWriter fw = new FileWriter (nomeArquivo)) {

			fw.write(builderText); // Escreve no arquivo

		} catch (IOException ex) {
			System.out.println("Não foi possível criar um arquivo.");
		}

		System.out.println("\nTexto " + nomeArquivo.replaceFirst("/", "") + " gravado com sucesso.");
	}

	private static void gravarArquivo3(String builderText2) {
		String nomeArquivo = "d://answer.json";

		// Cria um arquivo com o caminho e nome especificado na String nomeArquivo
		try (FileWriter fw = new FileWriter (nomeArquivo)) {

			fw.write(builderText2); // Escreve no arquivo

		} catch (IOException ex) {
			System.out.println("Não foi possível criar um arquivo.");
		}

		System.out.println("\nTexto " + nomeArquivo.replaceFirst("/", "") + " gravado com sucesso.");
	}

}