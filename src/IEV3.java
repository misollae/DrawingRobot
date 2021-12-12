
public interface IEV3 {
		//Se o valor da distancia for positivo o robot anda em frente, se o valor da distancia for negativo 
		//o robot anda para tras
		public void Reta(int distancia);
		
		/*
		 * Para o robot imediatamente quando o parâmetro de entrada for true
		 * caso seja false, so para o robot depois dos outros comandos terem sido executados
		 */
		public void Parar(boolean assincrono);
		
		/*
		 * O robot curva para a direita com o raio dado em centrimentros e segundo um dado angulo em graus
		 */
		public void CurvarDireita(int raio, int angulo);
		
		/*
		 * O robot curva para a esquerda com o raio dado em centrimetros e segundo um dado angulo em graus
		 */
		public void CurvarEsquerda(int raio, int angulo);
		
		
		/*
		 * Com este comando pode alterar a velocidade do robot, dando um valor para o parametro
		 * "percentagem" entre [-100,100]. Os comandos de movimento seguintes cumprem-se à nova velocidade.
		 */
//		public void SetVelocidade(int percentagem); 
		
		
//		public boolean OpenEV3(String nome);
//
//		public void CloseEV3();
}
