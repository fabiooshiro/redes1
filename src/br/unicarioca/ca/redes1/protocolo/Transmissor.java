package br.unicarioca.ca.redes1.protocolo;

import java.util.ArrayList;

import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Nack;
import br.unicarioca.ca.redes1.vo.Quadro;

public class Transmissor implements InterfaceTransmissor {
	private CamadaFisica camadaFisica = CamadaFisica.getInstance();
	private long intervaloEntreQuadros = 1000;
	boolean numerosUsados[] = new boolean[80];
	boolean timeoutvalido[] = new boolean[80];
	ArrayList<Quadro> buffer = new ArrayList<Quadro>();
	boolean servicoIniciado = false;
	public void receberAck(Ack ack) {
		System.out.println("Transmissor recebendo ack "+ack.getNumero());
		timeoutvalido[ack.getNumero()] = false;
	}

	public void receberNack(Nack nack) {

	}

	public void receberTimeOut(TimeOut timeOut) {
		System.out.println("Recebendo TimeOut "+timeOut.getNumero());
		if(timeoutvalido[timeOut.getNumero()]){
			try{
				Quadro quadro = new Quadro();
				quadro.setNumero(timeOut.getNumero());
				buffer.add(0,quadro);
				servico();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void enviarQuadros(final int qtd) throws Exception {
		for (int i = 0; i < qtd; i++) {
			Quadro quadro = new Quadro();
			quadro.setNumero(i%8);
			buffer.add(quadro);
		}
		servico();
	}
	
	public void servico(){
		if(servicoIniciado) return;
		servicoIniciado = true;
		final Transmissor transmissor = this;
		Thread t = new Thread(){
			public void run(){
				while(true){
					try{
						if(buffer.size()>0){
							Quadro quadro = buffer.get(0);
							buffer.remove(0);
							camadaFisica.enviarQuadro(quadro);
							TimeOut timeOut = new TimeOut(transmissor,camadaFisica.getVelocidadeCanal()*2+camadaFisica.getVelocidadeCanal()/3);
							timeOut.setNumero(quadro.getNumero());
							timeoutvalido[quadro.getNumero()]=true;
						}else{
							servicoIniciado = false;
							break;
						}
						Thread.sleep(intervaloEntreQuadros);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}
}
