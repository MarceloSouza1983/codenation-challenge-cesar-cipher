package br.com.marcelosouza;

import java.io.File;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

// Criado por Marcelo Souza - map.souza1983@gmail.com

public class SendFileToAPI {
	
	public void enviarArquivo(String addressUrl, String filename) throws UnirestException {
		
		// Consulte https://www.baeldung.com/unirest
		try {
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.post(addressUrl)
		  .header("User-Agent", "Java/1.8.0_241")
		  .field("answer", new File(filename))
		  .asString();
		
		System.out.println(response.getStatus() + " - " + response.getStatusText());
		System.out.println(response.getBody());
		
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
	}

}