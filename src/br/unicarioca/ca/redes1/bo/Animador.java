package br.unicarioca.ca.redes1.bo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import br.unicarioca.ca.redes1.vo.Animavel;

/**
 * Poderíamos chamar de engine
 * @author Fabio Issamu Oshiro
 *
 */
public class Animador {
	private Timer timer;
	private long frameCounter;
	private ArrayList<Movie> listMovie;
	private BufferedImage bufferedImage;
	//private BufferedImage historico;
	private BufferedImage hist[]=new BufferedImage[2];
	private int hx=0;
	private int hflag=0;
	private int width = 800;
	private BufferedImage fundo;
	private Tela tela;
	private int velocidadeHistorico;
	private boolean mostrarHistorico;
	public Animador(Tela tela) throws Exception {
		listMovie = new ArrayList<Movie>();
		bufferedImage = new BufferedImage(width,420,BufferedImage.TYPE_INT_ARGB);
		//historico = new BufferedImage(width,600,BufferedImage.TYPE_INT_ARGB);
		hist[0]=ImageIO.read(new File("images/hist.png"));
		hist[1]=ImageIO.read(new File("images/hist.png"));//new BufferedImage(width,600,BufferedImage.TYPE_INT_ARGB);
		fundo = ImageIO.read(new File("images/fundo.png"));
		//historico = new BufferedImage(550,1680,BufferedImage.TYPE_INT_ARGB);
		this.tela = tela;
		timer = new Timer(this);
		timer.start();
		velocidadeHistorico = 4;
	}
	
	/**
	 * Qualquer semelhança com o Flash é mera coincidência
	 */
	private void onEnterFrame() throws Exception{
		for(Movie mc:listMovie){
			if(mc.animavel.getFrameInicio()>=frameCounter) continue;
			mc._x+=mc._xinc;
			mc._y+=mc._yinc;
		}
		
	}
	
	/**
	 * Chamado pelo timer
	 */
	void timeOut(){
		try{
			frameCounter++;
			onEnterFrame();
			Graphics graphics = bufferedImage.getGraphics();
			graphics.drawImage(fundo, 0, 0,null);
			Graphics hgr;
			Graphics hgrb;
			hx-=velocidadeHistorico;
			
			hgr = hist[hflag%2].getGraphics();
			hgrb = hist[(hflag+1)%2].getGraphics();
			hgrb.setColor(Color.GRAY);
			hgr.setColor(Color.GRAY);
			for(Movie mc:listMovie){
				if(mc.animavel.getFrameInicio()>=frameCounter) continue;
				if(mc.animavel.getFrameFinal()<frameCounter){
					if(mc.logar){
						drawImage(hgr,mc,(int)mc._x-hx,(int)mc.animavel.getDestinoY(),Color.WHITE);
						drawImage(hgrb,mc,(int)mc._x-hx-800,(int)mc.animavel.getDestinoY(),Color.WHITE);
					}
					listMovie.remove(mc);
				}else{
					drawImage(graphics,mc,(int)mc._x,(int)mc._y);
					//marca a linha do historico
					drawPoint(hgr,(int)mc._x-hx,(int)(mc._y+mc._height/2));
					drawPoint(hgrb,(int)mc._x-hx-800,(int)(mc._y+mc._height/2));
				}
			}
			if(this.mostrarHistorico){
				bufferedImage.getGraphics().drawImage(hist[hflag%2],hx,0,null);
				bufferedImage.getGraphics().drawImage(hist[(hflag+1)%2],800+hx,0,null);
			}
			if(hx<-800){
				hx=0;
				hist[hflag%2]=ImageIO.read(new File("images/hist.png"));
				hflag++;
			}
			tela.setBufferedImage(bufferedImage);
		}catch(Exception e){
			
		}
	}
	private void drawPoint(Graphics hgr, int x, int y){
		hgr.drawRect(x,y, 1, 1);
	}
	private void drawImage(Graphics hgr, Movie mc,int x,int y,Color color){
		try{
			hgr.drawImage(mc.image,x,y,color, null);
		}catch(Exception e){
			
		}
	}
	private void drawImage(Graphics hgr, Movie mc,int x,int y){
		try{
			hgr.drawImage(mc.image,x,y, null);
		}catch(Exception e){
			
		}
	}
	/**
	 * Anima um objeto
	 * @param obj
	 * @throws Exception
	 */
	public void animar(Animavel obj) throws Exception{
		BufferedImage bi = ImageIO.read(new File(obj.getImagemPath()));
		obj.setBufferedImage(bi);
		listMovie.add(new Movie(obj));
	}
	public void animar(Animavel obj,boolean logar) throws Exception{
		BufferedImage bi = ImageIO.read(new File(obj.getImagemPath()));
		obj.setBufferedImage(bi);
		Movie mov = new Movie(obj);
		mov.logar = logar;
		listMovie.add(mov);
	}
	public long getCurrentFrame(){
		return frameCounter;
	}
	public int getVelocidadeHistorico() {
		return velocidadeHistorico;
	}

	public void setVelocidadeHistorico(int velocidadeHistorico) {
		this.velocidadeHistorico = velocidadeHistorico;
	}

	public void mostrarHistorico(boolean selected) {
		this.mostrarHistorico = selected;
	}
}
