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
	private ArrayList<FrameAction> frameActions = new ArrayList<FrameAction>();
	private BufferedImage bufferedImage;
	private ArrayList<FimAnimacaoListener> listFimAnimacaoListener = new ArrayList<FimAnimacaoListener>();
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
		
		hist[0]=ImageIO.read(new File("images/hist.png"));
		hist[1]=ImageIO.read(new File("images/hist.png"));
		fundo = ImageIO.read(new File("images/fundo.png"));
		int height = fundo.getHeight();
		bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		this.tela = tela;
		timer = new Timer(this);
		timer.start();
		velocidadeHistorico = 4;
	}
	
	/**
	 * Qualquer semelhança com o Flash é mera coincidência
	 */
	private void onEnterFrame() throws Exception{
		ArrayList<FrameAction> remover = new ArrayList<FrameAction>();
		for(FrameAction fa: frameActions){
			if(fa.getFrame()==(frameCounter)){
				fa.executar();
				remover.add(fa);
				
			}
		}
		for(FrameAction fa: remover){
			frameActions.remove(fa);
		}
	}
	
	/**
	 * Chamado pelo timer
	 */
	synchronized void timeOut(){
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
			int tot = listMovie.size();
			ArrayList<Movie> retirar = new ArrayList<Movie>();
			for(int i=0;i<tot;i++){
				Movie mc = listMovie.get(i);
				if(mc.animavel.getFrameInicio()>=frameCounter) continue;
				mc.animavel.x+=mc._xinc;
				mc.animavel.y+=mc._yinc;
				if(mc.animavel.getFrameFinal()<frameCounter){
					if(mc.logar){
						drawImage(hgr,mc,(int)mc.animavel.x-hx,(int)mc.animavel.getDestinoY(),Color.WHITE);
						drawImage(hgrb,mc,(int)mc.animavel.x-hx-800,(int)mc.animavel.getDestinoY(),Color.WHITE);
					}
					retirar.add(mc);
				}else{
					drawImage(graphics,mc,(int)mc.animavel.x,(int)mc.animavel.y);
					//marca a linha do historico
					if(mc.logar){
						drawPoint(hgr,(int)mc.animavel.x-hx+mc._width/2,(int)(mc.animavel.y+mc._height/2));
						drawPoint(hgrb,(int)mc.animavel.x-hx-800+mc._width/2,(int)(mc.animavel.y+mc._height/2));
					}
				}
			}
			for (Movie movie : retirar) {
				for(FimAnimacaoListener listener:listFimAnimacaoListener){
					listener.fimDaAnimacao(movie.getAnimavel());
				}
				listMovie.remove(movie);	
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
			e.printStackTrace();
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
	/**
	 * 
	 * @param obj objeto para animar
	 * @param logar logar no historico
	 * @throws Exception algum erro
	 */
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
	public void trocarImagemByIdAnimavel(int id, String imagem) {
		for(int i=0;i<listMovie.size();i++){
			if(listMovie.get(i).animavel.getId()==id){
				listMovie.get(i).image = ImageIO.read(new File(imagem));
			}
		}
	}
	public void trocarImagemByIdMovie(long id, String imagem) {
		for(int i=0;i<listMovie.size();i++){
			if(listMovie.get(i).getId()==id){
				listMovie.get(i).image = ImageIO.read(new File(imagem));
			}
		}
	}

	public void addFimAnimacaoListener(FimAnimacaoListener fimAnimacaoListener) {
		listFimAnimacaoListener.add(fimAnimacaoListener);
	}

	public int getFps() {
		return timer.getFps();
	}

	public void addFrameAction(FrameAction frameAction) {
		frameActions.add(frameAction);
	}	
}
