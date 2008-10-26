package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import br.unicarioca.ca.redes1.bo.Animador;
import br.unicarioca.ca.redes1.protocolo.CamadaFisica;
import br.unicarioca.ca.redes1.protocolo.InterfaceTransmissor;
import br.unicarioca.ca.redes1.protocolo.Receptor;
import br.unicarioca.ca.redes1.protocolo.Transmissor;
import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Animavel;
import br.unicarioca.ca.redes1.vo.Quadro;
import br.unicarioca.ca.redes1.vo.TimeOut;

/**
 * Só por trocadilho
 * @author Fabio Issamu Oshiro
 *
 */
public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4293189701804295061L;
	public static final int X_TRANSMISSOR=365;
	public static final int Y_TRANSMISSOR=55;
	public static final int Y_RECEPTOR=155;
	public static final int MIN_INTERVALOR = 7;
	private static final int MAX_QUANTIDADE = 10;
	private PanelAnimacao panelAnimacao;
	private PanelControle panelControle;
	private static Animador animador;
	private InterfaceTransmissor transmissor;
	private Receptor receptor;
	public MainFrame() throws Exception{
		super("Transmissão");
		panelAnimacao = new PanelAnimacao();
		panelControle = new PanelControle(this);
		add(panelAnimacao,BorderLayout.CENTER);
		add(panelControle,BorderLayout.SOUTH);
		animador = new Animador(panelAnimacao);
		CamadaFisica.setAnimador(animador);
		transmissor = new Transmissor();
		receptor = new Receptor();
		CamadaFisica camadaFisica = CamadaFisica.getInstance();
		camadaFisica.setReceptor(receptor);
		camadaFisica.setTransmissor((Transmissor) transmissor);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		this.pack();
		this.setVisible(true);
	}
	public void enviarQuadros(int qtd){
		try{
			transmissor.enviarQuadros(qtd);
		}catch(Exception e){
			
		}
	}
	public void enviarPacote(int qtd,int tempo,int intervalo,int ackDelay,int taxaErro,int taxaErroAck)throws Exception{
		if(qtd<=0){
			throw new Exception("A quantidade deve ser maior do que 0.");
		}
		if(qtd>MAX_QUANTIDADE){
			throw new Exception("A quantidade deve ser menor ou igual a "+MAX_QUANTIDADE+" (0<n<="+MAX_QUANTIDADE+").");
		}
		if(tempo<12){
			throw new Exception("O tempo deve ser maior do que 12.");
		}
		if(intervalo<MIN_INTERVALOR){
			throw new Exception("O intervalo deve ser maior ou igual a "+MIN_INTERVALOR+" (n>="+MIN_INTERVALOR+").");
		}
		if(ackDelay<=0){
			throw new Exception("O ACK delay deve ser inteiro positivo (n>=0).");
		}
		try{
			for(int i=0;i<qtd;i++){
				long cframe = animador.getCurrentFrame();
				setTimeOut(tempo, intervalo, ackDelay, i, cframe);
				Quadro quadro = new Quadro();
				quadro.setImagemPath("images/pacote"+i+".png");
				
				quadro.setOrigemX(X_TRANSMISSOR-5);
				quadro.setOrigemY(Y_TRANSMISSOR);
				quadro.setDestinoX(quadro.getOrigemX());
				quadro.setFrameInicio(cframe+i*intervalo);
				
				Random rd = new Random();
				int perda = rd.nextInt(100)+1;
				if(perda<=taxaErro){
					quadro.setDestinoY((Y_RECEPTOR-Y_TRANSMISSOR)/2+Y_TRANSMISSOR);
					quadro.setFrameFinal(cframe+tempo/2+i*intervalo);
					Quadro perdido = new Quadro();
					perdido.setImagemPath("images/puff.png");
					perdido.setOrigemX(quadro.getOrigemX());
					perdido.setOrigemY(quadro.getDestinoY());
					perdido.setDestinoX(quadro.getDestinoX());
					perdido.setDestinoY(quadro.getDestinoY()+5);
					perdido.setFrameInicio(quadro.getFrameFinal());
					perdido.setFrameFinal(quadro.getFrameFinal()+1);
					animador.animar(perdido);
					animador.animar(quadro,false);
					enviarNovamente(tempo, intervalo, ackDelay, i, cframe);
				}else{					
					quadro.setDestinoY(Y_RECEPTOR);
					quadro.setFrameFinal(cframe+tempo+i*intervalo);
					animador.animar(quadro);
					Ack ack = new Ack();
					ack.setImagemPath("images/ack"+i+".png");
					ack.setOrigemX(X_TRANSMISSOR+10);
					ack.setOrigemY(Y_RECEPTOR);
					ack.setFrameInicio(cframe+tempo+ackDelay+i*intervalo);
					rd = new Random();
					perda = rd.nextInt(100)+1;
					ack.setDestinoX(ack.getOrigemX());
					if(perda<=taxaErroAck){
						ack.setDestinoY((Y_RECEPTOR-Y_TRANSMISSOR)/2+Y_TRANSMISSOR);
						ack.setFrameFinal(cframe+tempo+tempo/2+ackDelay+i*intervalo);
						
						Quadro perdido = new Quadro();
						perdido.setImagemPath("images/puffack.png");
						perdido.setOrigemX(ack.getOrigemX());
						perdido.setOrigemY(ack.getDestinoY());
						perdido.setDestinoX(ack.getDestinoX());
						perdido.setDestinoY(ack.getDestinoY()-10);
						perdido.setFrameInicio(ack.getFrameFinal());
						perdido.setFrameFinal(ack.getFrameFinal()+1);
						animador.animar(perdido);
						animador.animar(ack,false);
						enviarNovamente(tempo, intervalo, ackDelay, i, cframe);
					}else{
						ack.setDestinoY(Y_TRANSMISSOR);
						ack.setFrameFinal(cframe+tempo+tempo+ackDelay+i*intervalo);
						animador.animar(ack);
					}
				}
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
	private void enviarNovamente(int tempo, int intervalo, int ackDelay, int i,long cframe) throws Exception {
		Quadro retrans = new Quadro();
		setCaminhoTrans2Recep(retrans);
		retrans.setImagemPath("images/pacote"+i+".png");
		retrans.setFrameInicio(cframe+2*tempo+ackDelay+i*intervalo);
		retrans.setFrameFinal(cframe+3*tempo+ackDelay+i*intervalo);
		animador.animar(retrans);
		Ack ack = new Ack();
		setCaminhoRecep2Trans(ack);
		ack.setImagemPath("images/ack"+i+".png");
		ack.setFrameInicio(cframe+3*tempo+2*ackDelay+i*intervalo);
		ack.setFrameFinal(cframe+4*tempo+2*ackDelay+i*intervalo);
		animador.animar(ack);
		setTimeOut(tempo, intervalo, 4*ackDelay+3, i, cframe+2*tempo);
	}
	private void setTimeOut(int tempo, int intervalo, int ackDelay, int i, long cframe) throws Exception {
			TimeOut timeOut = new TimeOut();
			timeOut.setDestinoY(Y_TRANSMISSOR-10);
			timeOut.setOrigemY(timeOut.getDestinoY());
			timeOut.setOrigemX(X_TRANSMISSOR+8*tempo+intervalo+ackDelay+3);
			timeOut.setDestinoX(timeOut.getOrigemX());
			timeOut.setFrameInicio(cframe+i*intervalo);
			timeOut.setFrameInicio(cframe+i*intervalo+1);
			animador.animar(timeOut);
	}
	private void setCaminhoRecep2Trans(Animavel ack){
		ack.setOrigemX(X_TRANSMISSOR+10);
		ack.setOrigemY(Y_RECEPTOR);
		ack.setDestinoX(ack.getOrigemX());
		ack.setDestinoY(Y_TRANSMISSOR);
	}
	private void setCaminhoTrans2Recep(Quadro quadro){
		quadro.setOrigemX(X_TRANSMISSOR-5);
		quadro.setOrigemY(Y_TRANSMISSOR);
		quadro.setDestinoX(quadro.getOrigemX());
		quadro.setDestinoY(Y_RECEPTOR);
	}
	
	public void enviarOnda()throws Exception{
		//int tempo=50;
		try{
			/*
				long cframe = animador.getCurrentFrame();
				Onda onda = new Onda();
				onda.setImagemPath("images/onda.gif");
				onda.setOrigemX(-300);
				onda.setOrigemY(Y_TRANSMISSOR+30);
				onda.setDestinoX(900);
				onda.setFrameInicio(cframe);
				onda.setDestinoY(Y_TRANSMISSOR+30);
				onda.setFrameFinal(cframe+tempo);
				animador.animar(onda);*/
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
		
	}
	
	
	
	public static void main(String args[]){
		try{
			
			new MainFrame();
			//new Thread(new ColidListenerThread()).start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void mostrarHistorico(boolean selected) {
		animador.mostrarHistorico(selected);
		
	}
	public static Animador getAnimador() {
		return animador;
	}
	public void enviarMensagem(String mensagem) {
		try{
			transmissor.enviarMensagem(mensagem);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
