package br.com.marcelosouza;

import java.math.BigInteger;
import java.security.MessageDigest;

// Criado por Marcelo Souza - map.souza1983@gmail.com

public class EncryptDecrypt {

	public static String criptografar(char[] mensagem, int chave) {

		String codigo = "";
		char[] alfabeto = "abcdefghijklmnopqrstuvwxyz".toCharArray(); //Converte alfabeto em array

		for (int i = 0; i < mensagem.length; i++) { //percorre palavra

			for (int j = 0; j < alfabeto.length; j++) { //Percorre alfabeto

				if (alfabeto[j] == mensagem[i]) { //Compara letra atual da palavra com a atual do texto

					if (j + chave >= 26) {
						mensagem[i] = alfabeto[j + chave - 26]; //Realiza troca de letra
					}

					else {
						mensagem[i] = alfabeto[j + chave];
					}

					j = 0; //reinicia alfabeto
					break;

				}

			}

			codigo += mensagem[i]; //concatena letra para retornar valor

		}

		return codigo;

	}
	
	public static String descriptografar(char[] textoCifrado, int chave2) {
		
		String textoDescriptografado = "";

		char[] alfabeto2 = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		for (int x = 0; x < textoCifrado.length; x++) {

			for (int y = 0; y < alfabeto2.length; y++) { 
				
				if (alfabeto2[y] == textoCifrado[x]) {

				if (y - chave2 < 0) {

					textoCifrado[x] = alfabeto2[y - chave2 + 26];

				} else {
					textoCifrado[x] = alfabeto2[y - chave2];
				}

				y = 0;
				break;

			}

			}

			textoDescriptografado += textoCifrado[x];

		}

		return textoDescriptografado;

	}
	
	public static String criptografarSHA(String textoDecodificado) {
		
		String sha = "";
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(textoDecodificado.getBytes("utf8"));
	        sha = String.format("%040x", new BigInteger(1, digest.digest()));
	        
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return sha;
		
	}

}