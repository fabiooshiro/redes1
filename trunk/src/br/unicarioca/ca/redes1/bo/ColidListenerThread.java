package br.unicarioca.ca.redes1.bo;


import java.util.ArrayList;
import br.unicarioca.ca.redes1.vo.Animavel;
import br.unicarioca.ca.redes1.vo.EventColidDetect;


/**
 * Thread que detecta colisões
 * @author Renato Tomaz Nati
 *
 */
public class ColidListenerThread implements Runnable {
	private static ArrayList<Movie> listMovieDetect;
	public ColidListenerThread(){
		listMovieDetect=new ArrayList<Movie>();
	}
	public static void addMovie(Movie movie){
		listMovieDetect.add(movie);
	}
	public void run(){
		while(true){
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<listMovieDetect.size();i++){
				Movie atual=listMovieDetect.get(i);
				Float atualInitXAbs=atual._x; //0
				Float atualEndXAbs=atual.getAnimavel().getBufferedImage().getWidth()+atual._x; //12+0
				Float atualInitYAbs=atual._y; //0
				Float atualEndYAbs=atual.getAnimavel().getBufferedImage().getHeight()+atual._y; //0+12
				for(int j=i+1;j<listMovieDetect.size();j++){
					Movie verificando=listMovieDetect.get(j);
					Float verInitXAbs=verificando._x; //0
					Float verEndXAbs=verificando.getAnimavel().getBufferedImage().getWidth()+verificando._x; //12+0
					Float verInitYAbs=verificando._y; //0
					Float verEndYAbs=verificando.getAnimavel().getBufferedImage().getHeight()+verificando._y; //0+12
					if((verInitXAbs.compareTo(atualInitXAbs)==+1) && (verInitXAbs.compareTo(atualEndXAbs)==-1)){
						if((verInitYAbs.compareTo(atualInitYAbs)==+1) && (verInitYAbs.compareTo(atualEndYAbs)==-1)){
							EventColidDetect eventoColisao=new EventColidDetect();
							eventoColisao.setMovie1(listMovieDetect.get(j));
							eventoColisao.setMovie2(listMovieDetect.get(i));
							eventoColisao.setXColid(verInitXAbs);
							eventoColisao.setYColid(verInitYAbs);
							ActionColisao(eventoColisao);
						}
					}
					if((atualInitXAbs.compareTo(verInitXAbs)==+1) && (atualInitXAbs.compareTo(verEndXAbs)==-1)){
						if((atualInitYAbs.compareTo(verInitYAbs)==+1) && (atualInitYAbs.compareTo(verEndYAbs)==-1)){
							EventColidDetect eventoColisao=new EventColidDetect();
							eventoColisao.setMovie1(listMovieDetect.get(j));
							eventoColisao.setMovie2(listMovieDetect.get(i));
							eventoColisao.setXColid(verInitXAbs);
							eventoColisao.setYColid(verInitYAbs);
							ActionColisao(eventoColisao);
						}
					}
				}
			}
		}
	}
	public void ActionColisao(EventColidDetect e){
		Animavel animavel1=e.getMovie1().getAnimavel();
		Animavel animavel2=e.getMovie2().getAnimavel();
		if((animavel1.getImagemPath().indexOf("onda")!=-1 && animavel2.getImagemPath().indexOf("pacote")!=-1)){ 
			System.out.println("colidiu1");
		}
		if((animavel1.getImagemPath().indexOf("pacote")!=-1 && animavel2.getImagemPath().indexOf("onda")!=-1)){
			System.out.println("colidiu2");
		}
	}
}
