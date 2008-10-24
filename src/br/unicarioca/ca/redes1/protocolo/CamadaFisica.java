package br.unicarioca.ca.redes1.protocolo;

import java.util.HashMap;

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
	private long velocidadeCanal = 2000;
	private Transmissor transmissor;
	private Receptor receptor;
	private static long autoIncAnimacao=0;
	private HashMap<Long,Quadro> quadrosCirculando = new HashMap<Long,Quadro>();
	private HashMap<Long,Ack> acksCirculando = new HashMap<Long,Ack>();
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
		ack.setFrameFinal(animador.getCurrentFrame()+velocidadeCanal/(1000/animador.getFps()));
		animador.animar(ack);
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
		quadro.setDestinoY(MainFrame.Y_RECEPTOR);
		quadro.setFrameInicio(animador.getCurrentFrame());
		quadro.setFrameFinal(animador.getCurrentFrame()+velocidadeCanal/(1000/animador.getFps()));
		quadro.setId(quadro.getNumero());
		quadrosCirculando.put(quadro.getId(),quadro);
		animador.animar(quadro);
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
		if(animavel.y<MainFrame.Y_RECEPTOR+1){
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
