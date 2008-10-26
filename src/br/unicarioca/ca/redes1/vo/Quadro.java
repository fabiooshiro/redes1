package br.unicarioca.ca.redes1.vo;

public class Quadro extends Animavel{
	private int numero;
	private String dado;
	public Quadro(){
		setImagemPath("images/pacote.png");
	}
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		setImagemPath("images/pacote"+numero+".png");
		this.numero = numero;
	}
	public String getDado() {
		return dado;
	}
	public void setDado(String dado) {
		this.dado = dado;
	}
	
}
