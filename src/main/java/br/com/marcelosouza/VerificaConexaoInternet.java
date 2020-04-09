package br.com.marcelosouza;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// Criado por Marcelo Souza - map.souza1983@gmail.com

public class VerificaConexaoInternet {

	public boolean verificaConexao() {
		try {
			URL url = new URL("https://www.google.com");
			URLConnection connection = url.openConnection();
			connection.connect();
			return true;

		} catch (MalformedURLException e) {
			return false;

		} catch (IOException e) {
			return false;
		}

	}
}