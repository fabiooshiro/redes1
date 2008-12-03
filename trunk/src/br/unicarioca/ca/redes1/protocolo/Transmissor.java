package br.unicarioca.ca.redes1.protocolo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import br.unicarioca.ca.redes1.bo.ImageIO;
import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Nack;
import br.unicarioca.ca.redes1.vo.Quadro;
/**
 * Transmissor com buffer
 * @author Fabio Issamu Oshiro
 *
 */
public class Transmissor implements InterfaceTransmissor {
	private OutPut output;
	private CamadaFisica camadaFisica = CamadaFisica.getInstance();
	private long intervaloEntreQuadros = 1000;
	boolean timeoutvalido[] = new boolean[80];
	private ArrayList<Quadro> buffer = new ArrayList<Quadro>();
	private int quadroAtual = 0;
	private boolean servicoIniciado = false;
	private int maximoQuadrosCirculando = 4;
	private int quantidadeCirculando = 0;
	private long tempoTimeOut;
	/**
	 * Numera os quadros de 0 até X
	 */
	int totalNumeros = 8;
	Thread threadServico;
	public Transmissor(){
		tempoTimeOut = getDefaultTimeOut();
	}
	
	
	public void receberAck(Ack ack) {
		synchronized (this) {
			try{
				output.println("Transmissor recebendo ack "+ack.getNumero());
				if(timeoutvalido[ack.getNumero()]){
					timeoutvalido[ack.getNumero()] = false;
					quantidadeCirculando--;
				}
				if(buffer.size()>0 && buffer.get(0).getNumero()==ack.getNumero()){
					buffer.remove(0);
					//if(quadroAtual>=0)
					quadroAtual--;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void receberNack(Nack nack) {

	}

	public void receberTimeOut(TimeOut timeOut) {
		output.println("Recebendo TimeOut "+timeOut.getNumero());
		if(timeoutvalido[timeOut.getNumero()]){
			timeoutvalido[timeOut.getNumero()] = false;
			quantidadeCirculando--;
			try{
				//reenviar quadros a partir do timeout
				for(int i=0;i<buffer.size() && i<quadroAtual;i++){
					if(buffer.get(i).getNumero()==timeOut.getNumero()){
						quadroAtual = i;
						break;
					}
				}
				servico();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void enviarQuadros(final int qtd) throws Exception {
		for (int i = 0; i < qtd; i++) {
			Quadro quadro = new Quadro();
			quadro.setNumero(i%totalNumeros);
			buffer.add(quadro);
		}
		servico();
	}
	public long getDefaultTimeOut(){
		return camadaFisica.getVelocidadeCanal()*2+camadaFisica.getVelocidadeCanal()/3;
	}
	public synchronized void servico(){
		if(servicoIniciado) return;
		servicoIniciado = true;
		output.println("Serviço iniciado");
		final Transmissor transmissor = this;
		threadServico = new Thread(){
			public void run(){
				while(true){
					try{
						if(buffer.size()>0 && quantidadeCirculando<maximoQuadrosCirculando && quadroAtual<buffer.size()){
							synchronized (transmissor) {
								if(quadroAtual>=0){
									Quadro quadro = buffer.get(quadroAtual++);
									camadaFisica.enviarQuadro(quadro);
									new TimeOut(transmissor,tempoTimeOut,quadro.getNumero());
									timeoutvalido[quadro.getNumero()]=true;
									quantidadeCirculando++;
								}
							}
						}else{
							if(buffer.size()==0){
								servicoIniciado = false;
								output.println("Serviço parado");
								//zerar
								quantidadeCirculando = 0;
								for(int i=0;i<timeoutvalido.length;i++){
									timeoutvalido[i]=false;
								}
								break;
							}
						}
						camadaFisica.sleep(intervaloEntreQuadros);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		};
		threadServico.start();
	}

	private int mumQuadro=0;
	public void enviarMensagem(String mensagem) throws Exception {
		for(int i=0;i<mensagem.length();i++){
			Quadro quadro = new Quadro();
			quadro.setNumero(mumQuadro++%totalNumeros);
			quadro.setDado(mensagem.charAt(i)+"");
			if(i==0){
				quadro.setCabecalho("ini");
			}else if(i==mensagem.length()-1){
				quadro.setCabecalho("fim");
			}
			buffer.add(quadro);
		}
		servico();
	}

	public long getTempoTimeOut() {
		return tempoTimeOut;
	}

	public void setTempoTimeOut(long tempoTimeOut) {
		this.tempoTimeOut = tempoTimeOut;
	}

	public long getIntervaloEntreQuadros() {
		return intervaloEntreQuadros;
	}

	public void setIntervaloEntreQuadros(long intervaloEntreQuadros) {
		this.intervaloEntreQuadros = intervaloEntreQuadros;
	}


	public int getMaximoQuadrosCirculando() {
		return maximoQuadrosCirculando;
	}


	public void setMaximoQuadrosCirculando(int maximoQuadrosCirculando) {
		this.maximoQuadrosCirculando = maximoQuadrosCirculando;
	}


	public int getTotalNumeros() {
		return totalNumeros;
	}


	public void setTotalNumeros(int totalNumeros) throws Exception {
		//verificar se existem
		for(int i=0;i<totalNumeros;i++){
			BufferedImage img = ImageIO.read(new File("images/pacote"+i+".png"));
			if(img == null){
				throw new Exception("O número para pacotes vai até "+(i-1)+".");
			}
		}
		this.totalNumeros = totalNumeros;
	}


	public OutPut getOutput() {
		return output;
	}


	public void setOutput(OutPut output) {
		this.output = output;
	}
}
