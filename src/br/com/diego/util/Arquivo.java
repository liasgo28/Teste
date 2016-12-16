package br.com.diego.util;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Arquivo {	
	private BufferedReader leitura;
	private String[] arrDados;

	public void leArquivo(){		
		try {	        
			leitura = new BufferedReader(new FileReader("D:\\JAVA\\PROJETOS_ESTUDO\\Teste\\movimentacoes.txt"));
			String line = "";
			while (line != null) {  
                arrDados = line.split("	");
                if (arrDados.length == 3){
                	System.out.println(arrDados[0]);
                	System.out.println(arrDados[1]);
                	System.out.println(arrDados[2]);
                }               
                
                line = leitura.readLine();  
            }
		}catch ( IOException e ) {
            System.err.printf( "Erro na abertura do arquivo: %s.\n", e.getMessage() );
        }	
		
	}
}
