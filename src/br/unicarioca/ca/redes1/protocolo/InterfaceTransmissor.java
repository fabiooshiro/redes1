package br.unicarioca.ca.redes1.protocolo;

public interface InterfaceTransmissor {
	public void enviarQuadros(int qtd) throws Exception;
	public void enviarMensagem(String mensagem) throws Exception;
}
