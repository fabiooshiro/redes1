package br.unicarioca.ca.redes1.vo;

/**
 * Foge um pouco ao padrão
 * @author Fabio Issamu Oshiro
 *
 */
public class Animavel {
	private int id;
	private float origemX;
	private float origemY;
	private float destinoX;
	private float destinoY;
	private long frameInicio;
	private long frameFinal;
	private String imagemPath;
	public float getOrigemX() {
		return origemX;
	}
	public void setOrigemX(float origemX) {
		this.origemX = origemX;
	}
	public float getOrigemY() {
		return origemY;
	}
	public void setOrigemY(float origemY) {
		this.origemY = origemY;
	}
	public float getDestinoX() {
		return destinoX;
	}
	public void setDestinoX(float destinoX) {
		this.destinoX = destinoX;
	}
	public float getDestinoY() {
		return destinoY;
	}
	public void setDestinoY(float destinoY) {
		this.destinoY = destinoY;
	}
	public long getFrameInicio() {
		return frameInicio;
	}
	public void setFrameInicio(long frameInicio) {
		this.frameInicio = frameInicio;
	}
	public long getFrameFinal() {
		return frameFinal;
	}
	public void setFrameFinal(long frameFinal) {
		this.frameFinal = frameFinal;
	}
	public String getImagemPath() {
		return imagemPath;
	}
	public void setImagemPath(String imagemPath) {
		this.imagemPath = imagemPath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
