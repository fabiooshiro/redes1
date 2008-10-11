package br.unicarioca.ca.redes1.vo;

public class Quadro extends Animavel{
	private int numero;

	public Quadro(){
		setImagemPath("images/pacote.png");
	}
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
}
