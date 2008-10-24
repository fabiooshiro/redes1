package br.unicarioca.ca.redes1.protocolo;

import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Nack;
import br.unicarioca.ca.redes1.vo.Quadro;

public class Transmissor implements InterfaceTransmissor {
	private CamadaFisica camadaFisica = CamadaFisica.getInstance();
	private long intervaloEntreQuadros = 1000;
	public void receberAck(Ack ack) {
		System.out.println("Transmissor recebendo ack "+ack.getNumero());
	}

	public void receberNack(Nack nack) {

	}

	public void receberTimeOut(TimeOut timeOut) {
		System.out.println("Recebendo TimeOut "+timeOut.getNumero());
	}

	public void enviarQuadros(final int qtd) throws Exception {
		final Transmissor transmissor = this;
		Thread t = new Thread(){
			public void run(){
				for (int i = 0; i < qtd; i++) {
					try{
						Quadro quadro = new Quadro();
						quadro.setNumero(i);
						camadaFisica.enviarQuadro(quadro);
						TimeOut timeOut = new TimeOut(transmissor,camadaFisica.getVelocidadeCanal()*2+camadaFisica.getVelocidadeCanal()/3);
						timeOut.setNumero(i);
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
