package br.unicarioca.ca.redes1.vo;

public class Ack extends Animavel{
	private int numero;
	public Ack(){
		setImagemPath("images/ack.png");
	}

	public void setNumero(int numero) {
		this.numero=numero;
	}
	public int getNumero(){
		return numero;
	}
}
