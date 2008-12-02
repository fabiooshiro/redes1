package br.unicarioca.ca.redes1.protocolo;

import br.unicarioca.ca.redes1.bo.Tela;
import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Quadro;


public class Receptor {
	private OutPut output;
	CamadaFisica camadaFisica = CamadaFisica.getInstance();
	private int sequencial = 0;
	int totalNumeros = 8;
	private String quadrosRecebidos = "";
	private String mensagemRecebida = "";
	private static final String ESTADO_AGUARDANDO = "Aguardando novas";
	private static final String ESTADO_RECEBENDO = "recebendo novas";
	private String estado = ESTADO_AGUARDANDO;
	private Tela tela;
	public void receberQuadro(Quadro quadro) throws Exception{
		output.println("Receptor recebendo quadro "+quadro.getId());
		//System.out.println("quadrosRecebidos=" + quadrosRecebidos);
		
		
		if(estado.equals(ESTADO_AGUARDANDO) && quadro.getCabecalho()!=null && quadro.getCabecalho().equals("ini")){
			sequencial = quadro.getNumero();
			mensagemRecebida="";
			estado = ESTADO_RECEBENDO;
		}
		
		if(sequencial == quadro.getNumero() && estado.equals(ESTADO_RECEBENDO)){
			sequencial++;
			sequencial=sequencial%totalNumeros;
			quadrosRecebidos+=quadro.getNumero() + ", ";
			mensagemRecebida+=quadro.getDado();
			Ack ack = new Ack();
			ack.setNumero(quadro.getNumero());
			if(quadro.getCabecalho()!=null && quadro.getCabecalho().equals("fim")){
				estado = ESTADO_AGUARDANDO;
			}
			camadaFisica.enviarAck(ack);
		}else{
			output.println("\tReceptor aguardando "+sequencial);
			Ack ack = new Ack();
			ack.setNumero(quadro.getNumero());
			camadaFisica.enviarAck(ack);
		}
		output.println("mensagemRecebida=" + mensagemRecebida);
		if(tela!=null){
			tela.setMensagemRecebida(mensagemRecebida);
		}
	}
	public OutPut getOutput() {
		return output;
	}
	public void setOutput(OutPut output) {
		this.output = output;
	}
	public String getMensagemRecebida() {
		return mensagemRecebida;
	}
	public void setMensagemRecebida(String mensagemRecebida) {
		this.mensagemRecebida = mensagemRecebida;
	}
	public void setTela(Tela tela) {
		this.tela = tela;
	}
	public void setTotalNumeros(int totalNumeros) {
		this.totalNumeros = totalNumeros;
	}
}
