package br.unicarioca.ca.redes1.protocolo;

import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Quadro;

public class Receptor {
	CamadaFisica camadaFisica = CamadaFisica.getInstance();
	public void receberQuadro(Quadro quadro) throws Exception{
		System.out.println("Receptor recebendo quadro "+quadro.getId());
		Ack ack = new Ack();
		ack.setNumero(quadro.getNumero());
		camadaFisica.enviarAck(ack);
	}
}
