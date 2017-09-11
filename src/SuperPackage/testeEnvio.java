package SuperPackage;

public class testeEnvio {
	
	public static void main(String[] args) {
		
		try {
			
			System.out.println("Inicio!");

			throw new Exception("Gerando Excessão XYZ - 123456789");
			
		} catch (Exception ex) {
						
			new enviaExcecaoPorEmail(ex);
			
			System.out.println("Fim!");

		}
	}
	
	
	
	
}