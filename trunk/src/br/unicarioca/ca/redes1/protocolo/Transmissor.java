package br.unicarioca.ca.redes1.protocolo;

import java.util.ArrayList;

import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Nack;
import br.unicarioca.ca.redes1.vo.Quadro;
/**
 * Transmissor com buffer
 * @author fabio
 *
 */
public class Transmissor implements InterfaceTransmissor {
	private CamadaFisica camadaFisica = CamadaFisica.getInstance();
	private long intervaloEntreQuadros = 1000;
	boolean numerosUsados[] = new boolean[80];
	boolean timeoutvalido[] = new boolean[80];
	ArrayList<Quadro> buffer = new ArrayList<Quadro>();
	int quadroAtual = 0;
	boolean servicoIniciado = false;
	int quantidadeCirculando = 0;
	public void receberAck(Ack ack) {
		System.out.println("Transmissor recebendo ack "+ack.getNumero());
		timeoutvalido[ack.getNumero()] = false;
		numerosUsados[ack.getNumero()] = false;
		quantidadeCirculando--;
		if(buffer.get(0).getNumero()==ack.getNumero()){
			buffer.remove(0);
			quadroAtual--;
		}
	}

	public void receberNack(Nack nack) {

	}

	public void receberTimeOut(TimeOut timeOut) {
		System.out.println("Recebendo TimeOut "+timeOut.getNumero());
		if(timeoutvalido[timeOut.getNumero()]){
			quantidadeCirculando--;
			try{
				//reenviar quadros a partir do timeout
				for(int i=0;i<buffer.size();i++){
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
			quadro.setNumero(i%8);
			buffer.add(quadro);
		}
		servico();
	}
	
	public synchronized void servico(){
		if(servicoIniciado) return;
		servicoIniciado = true;
		System.out.println("Serviço iniciado");
		final Transmissor transmissor = this;
		Thread t = new Thread(){
			public void run(){
				while(true){
					try{
						System.out.println("quantidadeCirculando = " + quantidadeCirculando);
						if(buffer.size()>0 && quantidadeCirculando<2 && quadroAtual<buffer.size()){
							Quadro quadro = buffer.get(quadroAtual++);
							camadaFisica.enviarQuadro(quadro);
							new TimeOut(transmissor,camadaFisica.getVelocidadeCanal()*2+camadaFisica.getVelocidadeCanal()/3,quadro.getNumero());
							timeoutvalido[quadro.getNumero()]=true;
							numerosUsados[quadro.getNumero()] = true;
							quantidadeCirculando++;
						}else{
							if(buffer.size()==0){
								servicoIniciado = false;
								break;
							}
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
