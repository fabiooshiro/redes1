package br.unicarioca.ca.redes1.vo;

public class Quadro extends Animavel{
	private int numero;
	private String dado;
	private String cabecalho;
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
	public String getCabecalho() {
		return cabecalho;
	}
	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}
	
}
