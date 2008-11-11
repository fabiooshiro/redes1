package br.unicarioca.ca.redes1.protocolo;

import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Quadro;


public class Receptor {
	private OutPut output;
	CamadaFisica camadaFisica = CamadaFisica.getInstance();
	private int sequencial = 0;
	private String quadrosRecebidos = "";
	private String mensagemRecebida = "";
	private static final String ESTADO_AGUARDANDO = "Aguardando novas";
	private static final String ESTADO_RECEBENDO = "recebendo novas";
	private String estado = ESTADO_AGUARDANDO;
	public void receberQuadro(Quadro quadro) throws Exception{
		output.println("Receptor recebendo quadro "+quadro.getId());
		//System.out.println("quadrosRecebidos=" + quadrosRecebidos);
		
		
		if(estado.equals(ESTADO_AGUARDANDO) && quadro.getCabecalho()!=null && quadro.getCabecalho().equals("ini")){
			sequencial = quadro.getNumero();
			estado = ESTADO_RECEBENDO;
		}
		
		if(sequencial == quadro.getNumero() && estado.equals(ESTADO_RECEBENDO)){
			sequencial++;
			sequencial=sequencial%8;
			quadrosRecebidos+=quadro.getNumero() + ", ";
			mensagemRecebida+=quadro.getDado();
			Ack ack = new Ack();
			ack.setNumero(quadro.getNumero());
			if(quadro.getCabecalho()!=null && quadro.getCabecalho().equals("fim")){
				estado = ESTADO_AGUARDANDO;
			}
			camadaFisica.enviarAck(ack);
		}else{
			//System.out.println("Receptor aguardando "+sequencial);
			Ack ack = new Ack();
			ack.setNumero(quadro.getNumero());
			camadaFisica.enviarAck(ack);
		}
		output.println("mensagemRecebida=" + mensagemRecebida);
	}
	public OutPut getOutput() {
		return output;
	}
	public void setOutput(OutPut output) {
		this.output = output;
	}
}
