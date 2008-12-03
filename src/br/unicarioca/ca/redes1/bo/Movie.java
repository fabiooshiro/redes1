package br.unicarioca.ca.redes1.bo;

import java.awt.image.BufferedImage;

import br.unicarioca.ca.redes1.vo.Animavel;
/**
 * Aluzão ao movie do flash
 * @author Fabio Issamu Oshiro
 */
public class Movie {
	private static long ids = 0;
	private long id;
	float _xinc;
	float _yinc;
	int _height;
	int _width;
	boolean logar = true;
	boolean logarPontilhado = true;
	BufferedImage image;
	Animavel animavel;
	public Movie(Animavel animavel){
		this.animavel = animavel;
		this.image = animavel.getBufferedImage();
		animavel.y = animavel.getOrigemY();
		_height = image.getHeight();
		_width = image.getWidth();
		animavel.x = animavel.getOrigemX();
		long tx = animavel.getFrameFinal() - animavel.getFrameInicio();
		_xinc = (animavel.getDestinoX() - animavel.getOrigemX()) / tx;
		_yinc = (float) (animavel.getDestinoY() - animavel.getOrigemY()) / (float) tx;
		id = ids++;
	}
	public boolean equals(Object obj){
		Movie mc =(Movie)obj;
		if(mc.id==this.id) return true;
		return false;
	}
	public long getId() {
		return id;
	}
	public Animavel getAnimavel() {
		return animavel;
	}
}
