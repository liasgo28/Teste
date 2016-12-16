

import br.com.diego.controller.FilialController;




public class Teste {

	
	public static void main(String[] args) {
		FilialController filiais = new FilialController();
		System.out.println(filiais.getFilialMaisVendeu(filiais.getListaFiliais()));
		System.out.println(filiais.getFilialMaiorCrescimento(filiais.getListaFiliais()));
		System.out.println(filiais.getFilialMaiorQuedaVendas(filiais.getListaFiliais()));
		System.out.println(filiais.getMaisVendas());
		
		
		
		
	}

}
