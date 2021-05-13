package clienteAutentic;

import java.util.Random;

public class GeradorSerial {
	
	private String []serial= {"A","B","C","D","E","F","G","H","I","J","L","M","N","O","P","Q","R","S","T","U","V","X","Z","W","Y","K"};
	public String gerar() {
		Random gerador = new Random();
		String s="";
		int cont=0;
		for(int i=0; i<serial.length;i++) {
			    int num = gerador.nextInt(26);
                 s+=serial[num];
                if(cont==5) {
                	if(s.length()<28) {
                	s+="-";
                	}
                	cont=0;
                }
                cont++;
		}
			
		return s; 
	}
	public static void main(String[] args) {
		GeradorSerial g= new GeradorSerial();
		System.out.println(g.gerar());
	}

}
