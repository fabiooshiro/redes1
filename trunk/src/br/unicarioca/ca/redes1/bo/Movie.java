package br.unicarioca.ca.redes1.bo;

import java.awt.image.BufferedImage;

import br.unicarioca.ca.redes1.vo.Animavel;

class Movie {
	private static long ids = 0;
	private long id;
	float _y;
	float _x;
	float _xinc;
	float _yinc;
	float _height;
	boolean logar=true;
	BufferedImage image;
	Animavel animavel;
	public Movie(Animavel animavel){
		this.animavel = animavel;
		this.image = animavel.getBufferedImage();
		_y = animavel.getOrigemY();
		_height=image.getHeight();
		_x = animavel.getOrigemX();
		long tx = animavel.getFrameFinal() - animavel.getFrameInicio();
		_xinc = (animavel.getDestinoX()-animavel.getOrigemX())/tx;
		_yinc = (animavel.getDestinoY()-animavel.getOrigemY())/tx;
		
		id = ids++;
	}
	public boolean equals(Object obj){
		Movie mc =(Movie)obj;
		if(mc.id==this.id) return true;
		return false;
	}
}
