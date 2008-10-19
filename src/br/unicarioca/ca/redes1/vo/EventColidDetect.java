package br.unicarioca.ca.redes1.vo;

import br.unicarioca.ca.redes1.bo.Movie;
/**
 * Abriga informações sobre a colisão
 * @author Renato Tomaz Nati
 *
 */
public class EventColidDetect {
	Float x;
	Float y;
	private Movie movie1;
	private Movie movie2;
	public EventColidDetect(){
		x=0.0F;
		y=0.0F;
	}
	
	public Float getXColid() {
		return x;
	}
	public void setXColid(Float x) {
		this.x = x;
	}
	public Float getYColid() {
		return y;
	}
	public void setYColid(Float y) {
		this.y = y;
	}

	public Movie getMovie1() {
		return movie1;
	}

	public void setMovie1(Movie movie1) {
		this.movie1 = movie1;
	}

	public Movie getMovie2() {
		return movie2;
	}

	public void setMovie2(Movie movie2) {
		this.movie2 = movie2;
	}
	
	
}
