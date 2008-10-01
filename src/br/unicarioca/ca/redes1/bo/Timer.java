package br.unicarioca.ca.redes1.bo;
/**
 * 
 * @author Fabio Issamu Oshiro
 *
 */
public class Timer extends Thread{
	private Animador animador;
	private int fps = 12;
	public Timer(Animador animador) {
		this.animador = animador;
	}
	public void run(){
		while(true){
			try{
				Thread.sleep(1000/fps);
				animador.timeOut();
			}catch(Exception e){
				
			}
		}
	}
	public int getFps() {
		return fps;
	}
	public void setFps(int fps) {
		this.fps = fps;
	}
}
