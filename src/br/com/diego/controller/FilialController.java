package br.com.diego.controller;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.diego.model.Filial;

public class FilialController {
	//private List<Filial> listaFiliais = new ArrayList<Filial>();
	private Map<String, List<Filial>> listaFiliais = new HashMap<String, List<Filial>>();

	public Map<String, List<Filial>> getListaFiliais() {
		BufferedReader leitura;
		String[] arrDados;
		try {
			leitura = new BufferedReader(new FileReader("D:\\JAVA\\PROJETOS_ESTUDO\\Teste\\movimentacoes.txt"));
			String line = "";
			while (line != null) {  				
	            arrDados = line.split("	");	            
	            if (arrDados.length == 3){
					try {
						Filial filial = new Filial();
		            	filial.setNome(arrDados[0]);
		            	filial.setMes(arrDados[1]);
		            	filial.setLucro(Double.parseDouble(arrDados[2].replace(".", "").replace(",",".")));
		            	if(listaFiliais.containsKey(arrDados[0])){
		     		        List<Filial> list = listaFiliais.get(arrDados[0]);
		     		        list.add(filial);
		     		    }else{
		     		        List<Filial> list = new ArrayList<Filial>();
		     		        list.add(filial);
		     		       listaFiliais.put(arrDados[0], list);
		     		    }
					} catch (NumberFormatException e) {
					  
					}	            	
	            } 
	            line = leitura.readLine();
				
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaFiliais;
	}

	public void setListaFiliais(Map<String, List<Filial>> listaFiliais) {
		this.listaFiliais = listaFiliais;
	}
	
	public String getFilialMaisVendeu(Map<String, List<Filial>> filiais){		
		Iterator<String> itr2 = filiais.keySet().iterator();
		String nomeAnterior = "";
    	String filialMaisVendeu = "";
    	double totalLucroTemp = (double) 0;
    	double totalLucroMaior = (double) 0;
		while (itr2.hasNext()) {
		    String key = itr2.next();
		    if (key!=null){
		    	List<Filial> listaFiliais = filiais.get(key);		    	
				for (Filial filial : listaFiliais){
					if (!nomeAnterior.equalsIgnoreCase(filial.getNome())){
						if (totalLucroTemp > totalLucroMaior){
							totalLucroMaior = totalLucroTemp;
							filialMaisVendeu = "A filial " + nomeAnterior + " foi a que mais vendeu totalizando um lucro de " + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalLucroMaior);
						}						
						totalLucroTemp = 0;						
					}
					totalLucroTemp += filial.getLucro();
					nomeAnterior = filial.getNome();
				}
				
		    }
		}
		if (totalLucroTemp > totalLucroMaior){
			totalLucroMaior = totalLucroTemp;
			filialMaisVendeu = "A filial " + nomeAnterior + " foi a que mais vendeu totalizando um lucro de " + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalLucroMaior);
		}		
		return filialMaisVendeu;
	}
	
	public String getFilialMaiorCrescimento(Map<String, List<Filial>> filiais){		
		Iterator<String> itr2 = filiais.keySet().iterator();
		String nomeAnterior = "";
    	String filialMaiorCrescimento = "";    	
    	double lucroMenorTemp = 0;
    	double lucroMaiorTemp = 0;
    	double porcentagem = 0;
    	double porcentagemMaior = 0;
    	
		while (itr2.hasNext()) {
		    String key = itr2.next();
		    if (key!=null){
		    	List<Filial> listaFiliais = filiais.get(key);		    	
				for (Filial filial : listaFiliais){
					if (!nomeAnterior.equalsIgnoreCase(filial.getNome())){
						if (porcentagem > porcentagemMaior){
							porcentagemMaior = porcentagem;
							filialMaiorCrescimento = "A filial " + nomeAnterior + " foi a que teve maior crescimento totalizando " + new DecimalFormat("#.##").format(porcentagemMaior) + "%";
						}
						lucroMenorTemp = 0;
				    	lucroMaiorTemp = 0;
					}
					if (filial.getLucro() < lucroMenorTemp || lucroMenorTemp == 0){
						lucroMenorTemp = filial.getLucro();
					}
					if (filial.getLucro() > lucroMaiorTemp){
						lucroMaiorTemp = filial.getLucro();
					}
					nomeAnterior = filial.getNome();				
				}
				porcentagem = lucroMaiorTemp * 100 / lucroMenorTemp;
				//System.out.println(nomeAnterior + ": " + lucroMenorTemp + " - " + lucroMaiorTemp + " = " + porcentagem + "%");				
		    }		    
		}
		if (porcentagem > porcentagemMaior){
			porcentagemMaior = porcentagem;
			filialMaiorCrescimento = "A filial " + nomeAnterior + " foi a que teve maior crescimento totalizando " + new DecimalFormat("#.##").format(porcentagemMaior) + "%";
		}
		return filialMaiorCrescimento;
	}
	
	public String getFilialMaiorQuedaVendas(Map<String, List<Filial>> filiais){		
		Iterator<String> itr2 = filiais.keySet().iterator();
		String nomeAnterior = "";
    	String filialMaiorQueda = "";    	
    	double lucroPrimeiroTemp = 0;
    	double lucroUltimoTemp = 0;
    	double diferenca = 0;
    	double diferencaMenor = 0;
    	
		while (itr2.hasNext()) {
		    String key = itr2.next();
		    if (key!=null){
		    	List<Filial> listaFiliais = filiais.get(key);
		    	lucroPrimeiroTemp = listaFiliais.get(0).getLucro();	
		    	lucroUltimoTemp = listaFiliais.get(listaFiliais.size()-1).getLucro();
		    	diferenca = lucroUltimoTemp - lucroPrimeiroTemp;
		    	if (diferenca < diferencaMenor){
		    		diferencaMenor = diferenca;
		    		filialMaiorQueda = "A filial " + listaFiliais.get(0).getNome() + " foi a que teve maior queda totalizando " + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(diferencaMenor)+ " de prejuizo em comparação ao 1º mês.";
		    	}
		    	//System.out.println(listaFiliais.get(0).getNome() + " - " + listaFiliais.get(0).getLucro()+ " - " + listaFiliais.get(listaFiliais.size()-1).getLucro() + "::" + diferenca);
		    	
		    	nomeAnterior = listaFiliais.get(0).getNome();
		    }		    
		}
		if (diferenca < diferencaMenor){
    		diferencaMenor = diferenca;
    		filialMaiorQueda = "A filial " + nomeAnterior  + " foi a que teve maior queda totalizando " + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(diferencaMenor) + " de prejuizo em comparação ao 1º mês.";
    	}
		
		return filialMaiorQueda;
	}
	
	public String getMaisVendas(){		
		BufferedReader leitura;
		String[] arrDados;
		Map<String, Double> listaMeses = new HashMap<String, Double>();
		String retMes = "";
		try {
			leitura = new BufferedReader(new FileReader("D:\\JAVA\\PROJETOS_ESTUDO\\Teste\\movimentacoes.txt"));
			String line = "";
			while (line != null) {  				
	            arrDados = line.split("	");	            
	            if (arrDados.length == 3){
					try {
						if(listaMeses.containsKey(arrDados[1])){
							//System.out.print(arrDados[1] + ": " + listaMeses.get(arrDados[1]) + "+" + Double.parseDouble(arrDados[2].replace(".", "").replace(",",".")));
		     		       	Double total = listaMeses.get(arrDados[1]) + Double.parseDouble(arrDados[2].replace(".", "").replace(",","."));
		     		        //System.out.println("=" + total);
		     		       	listaMeses.put(arrDados[1], total);
		     		    }else{
		     		    	//System.out.println(arrDados[1] + " = " + Double.parseDouble(arrDados[2].replace(".", "").replace(",",".")));
		     		    	listaMeses.put(arrDados[1], Double.parseDouble(arrDados[2].replace(".", "").replace(",",".")));
		     		    }
						
					} catch (NumberFormatException e) {
					  
					}	            	
	            } 
	            line = leitura.readLine();
				
	        }
			Iterator<String> itr2 = listaMeses.keySet().iterator();
			Double valorAnterior = (double) 0;
			while (itr2.hasNext()) {				
			    String key = itr2.next();
			    if (valorAnterior < listaMeses.get(key)){
			    	valorAnterior = listaMeses.get(key);
			    	retMes = key + " foi o mês em que a empresa mais vendeu totalizando " + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorAnterior) + " de vendas.";
			    }
			    //System.out.println(key);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retMes;
	}
	
	
	
}
