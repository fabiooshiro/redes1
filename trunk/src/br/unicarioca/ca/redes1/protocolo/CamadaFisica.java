package br.unicarioca.ca.redes1.protocolo;

import java.util.HashMap;
import java.util.Random;

import br.unicarioca.ca.redes1.bo.Animador;
import br.unicarioca.ca.redes1.bo.FimAnimacaoListener;
import br.unicarioca.ca.redes1.ui.MainFrame;
import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Animavel;
import br.unicarioca.ca.redes1.vo.Nack;
import br.unicarioca.ca.redes1.vo.Quadro;

public class CamadaFisica implements FimAnimacaoListener{
	private static Animador animador;
	private static CamadaFisica instance;
	private long velocidadeCanal = 3000;
	private Transmissor transmissor;
	private Receptor receptor;
	private static long autoIncAnimacao=0;
	private HashMap<Long,Quadro> quadrosCirculando = new HashMap<Long,Quadro>();
	private HashMap<Long,Ack> acksCirculando = new HashMap<Long,Ack>();
	private int taxaErro = 30;
	private int taxaErroAck = 30;
	private CamadaFisica(){
		instance = this;
		animador.addFimAnimacaoListener(this);
	}
	/**
	 * Envia um nack para o transmissor
	 * @param nack
	 */
	public void enviarNack(Nack nack){
		
	}
	/**
	 * Envia um ack para o transmissor
	 * @param ack
	 */
	public void enviarAck(Ack ack) throws Exception{
		ack.setId(autoIncAnimacao++);
		acksCirculando.put(ack.getId(), ack);
		setCaminhoRecep2Trans(ack);
		ack.setFrameInicio(animador.getCurrentFrame());
		long frameFinal = velocidadeCanal/(1000/animador.getFps());
		ack.setFrameFinal(animador.getCurrentFrame()+frameFinal);
		Random rd = new Random();
		int sorte = rd.nextInt(100)+1;
		if(sorte>taxaErroAck){
			animador.animar(ack);
		}else{
			ack.setDestinoY((MainFrame.Y_RECEPTOR-MainFrame.Y_TRANSMISSOR)/2+MainFrame.Y_TRANSMISSOR);
			ack.setFrameFinal(animador.getCurrentFrame()+frameFinal/2);
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
		}
	}
	/**
	 * Envia um quadro para o receptor
	 * @param quadro
	 */
	public void enviarQuadro(Quadro quadro) throws Exception{
		System.out.println("Camada Física enviando quadro "+ quadro.getNumero());
		quadro.setId(autoIncAnimacao++);
		quadro.setOrigemX(MainFrame.X_TRANSMISSOR-5);
		quadro.setOrigemY(MainFrame.Y_TRANSMISSOR);
		quadro.setDestinoX(MainFrame.X_TRANSMISSOR-5);
		quadro.setFrameInicio(animador.getCurrentFrame());
		long frameFinal = velocidadeCanal/(1000/animador.getFps());
		Random rd = new Random();
		int sorte = rd.nextInt(100)+1;
		if(sorte>taxaErro){
			quadro.setDestinoY(MainFrame.Y_RECEPTOR);
			quadro.setFrameFinal(animador.getCurrentFrame()+frameFinal);
			animador.animar(quadro);
		}else{
			//erro
			quadro.setDestinoY((MainFrame.Y_RECEPTOR-MainFrame.Y_TRANSMISSOR)/2+MainFrame.Y_TRANSMISSOR);
			quadro.setFrameFinal(animador.getCurrentFrame()+frameFinal/2);
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
		}
		quadro.setId(quadro.getNumero());
		quadrosCirculando.put(quadro.getId(),quadro);
		
	}
	
	private void setCaminhoRecep2Trans(Animavel ack){
		ack.setOrigemX(MainFrame.X_TRANSMISSOR+10);
		ack.setOrigemY(MainFrame.Y_RECEPTOR);
		ack.setDestinoX(ack.getOrigemX());
		ack.setDestinoY(MainFrame.Y_TRANSMISSOR);
	}
	
	public static CamadaFisica getInstance() {
		if(instance==null){
			instance = new CamadaFisica();
		}
		return instance;
	}
	public static void setAnimador(Animador animador) {
		CamadaFisica.animador = animador;
		TimeOut.setAnimador(animador);
	} 
	
	public void fimDaAnimacao(Animavel animavel)throws Exception {
		if(animavel.y>MainFrame.Y_RECEPTOR-1){
			Quadro quadro = quadrosCirculando.get(animavel.getId());
			if(quadro!=null){
				receptor.receberQuadro(quadro);
				quadrosCirculando.remove(animavel.getId());
			}
		}
		if(animavel.y<MainFrame.Y_TRANSMISSOR+1){
			Ack ack = acksCirculando.get(animavel.getId());
			if(ack!=null){
				transmissor.receberAck(ack);
				acksCirculando.remove(animavel.getId());
			}
		}
	}
	public Transmissor getTransmissor() {
		return transmissor;
	}
	public void setTransmissor(Transmissor transmissor) {
		this.transmissor = transmissor;
	}
	public Receptor getReceptor() {
		return receptor;
	}
	public void setReceptor(Receptor receptor) {
		this.receptor = receptor;
	}
	public long getVelocidadeCanal() {
		return velocidadeCanal;
	}
	public void setVelocidadeCanal(long velocidadeCanal) {
		this.velocidadeCanal = velocidadeCanal;
	}
	
}
