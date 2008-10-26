package br.unicarioca.ca.redes1.protocolo;

import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Quadro;

public class Receptor {
	CamadaFisica camadaFisica = CamadaFisica.getInstance();
	private int sequencial = 0;
	private String quadrosRecebidos = "";
	private String mensagemRecebida = "";
	public void receberQuadro(Quadro quadro) throws Exception{
		System.out.println("Receptor recebendo quadro "+quadro.getId());
		System.out.println("quadrosRecebidos=" + quadrosRecebidos);
		System.out.println("mensagemRecebida=" + mensagemRecebida);
		if(sequencial == quadro.getNumero()){
			sequencial++;
			sequencial=sequencial%8;
			quadrosRecebidos+=quadro.getNumero() + ", ";
			mensagemRecebida+=quadro.getDado();
			Ack ack = new Ack();
			ack.setNumero(quadro.getNumero());
			camadaFisica.enviarAck(ack);
		}else{
			System.out.println("Receptor aguardando "+sequencial);
			Ack ack = new Ack();
			ack.setNumero(quadro.getNumero());
			camadaFisica.enviarAck(ack);
		}
	}
}
