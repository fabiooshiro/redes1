package br.unicarioca.ca.redes1.vo;

import java.awt.image.BufferedImage;

/**
 * Foge um pouco ao padrão
 * @author Fabio Issamu Oshiro
 *
 */
public class Animavel {
	private long id = -1l;
	public float x;
	public float y;
	private float origemX;
	private float origemY;
	private float destinoX;
	private float destinoY;
	private long frameInicio;
	private long frameFinal;
	private String imagemPath;
	private BufferedImage bufferedImage;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
}
