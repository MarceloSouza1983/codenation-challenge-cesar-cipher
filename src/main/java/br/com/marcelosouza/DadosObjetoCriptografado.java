package br.com.marcelosouza;

// Criado por Marcelo Souza - map.souza1983@gmail.com

public class DadosObjetoCriptografado {

	private Integer numero_casas;
	private String token;
	private String cifrado;
	private static String decifrado;
	private String resumo_criptografico;

	public Integer getNumero_casas() {
		return numero_casas;
	}

	public void setNumero_casas(Integer numero_casas) {
		this.numero_casas = numero_casas;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCifrado() {
		return cifrado;
	}

	public void setCifrado(String cifrado) {
		this.cifrado = cifrado;
	}

	public static String getDecifrado() {
		return decifrado;
	}

	public void setDecifrado(String decifrado) {
		this.decifrado = decifrado;
	}

	public String getResumo_criptografico() {
		return resumo_criptografico;
	}

	public void setResumo_criptografico(String resumo_criptografico) {
		this.resumo_criptografico = resumo_criptografico;
	}

	public void imprime() {
		System.out.println(getNumero_casas());
		System.out.println(getToken());
		System.out.println(getCifrado());
		System.out.println(getDecifrado());
		System.out.println(getResumo_criptografico());
	}

}