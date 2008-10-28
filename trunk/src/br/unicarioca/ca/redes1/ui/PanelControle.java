package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import br.unicarioca.ca.redes1.protocolo.CamadaFisica;

public class PanelControle extends JPanel{
	private static final long serialVersionUID = -1907642558005285055L;
	MainFrame mainFrame;
	JButton btnEnviarPacote;
	JButton btnTrocaImagem;
	
	JButton btnAplicarCamadaFisica;
	JTextField txtTaxaPerdaQuadro;
	JTextField txtTaxaPerdaAck;
	JTextField txtTempo;
	JTextField txtTimeOut;
	JTextField txtQtd;
	JTextField txtIntervalo;
	JTextField txtAckDelay;
	JTextField txtBitsNumero;
	JLabel lblBitsNumero;
	JTextField txtMaxQuadroCirculando;
	JLabel lblMaxQuadroCirculando;
	JCheckBox chkMostrarHistorico;
	JLabel lblTaxaPerdaQuadro;
	JLabel lblTaxaPerdaAck;
	JLabel lblTempo;
	JLabel lblQtd;
	JLabel lblIntervalo;
	JLabel lblTimeOut;
	JLabel lblAckDelay;
	JPanel panelTransmissor = new JPanel();
	JPanel panelCenter = new JPanel();
	JPanel panelCamadaFisica = new JPanel();
	
	public PanelControle(final MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		JTabbedPane tabbedPane = new JTabbedPane();

		btnEnviarPacote=new JButton("Enviar");
		btnTrocaImagem= new JButton("Trocar");
		
		txtTempo = new JTextField("3000");
		txtQtd = new JTextField("5");
		txtIntervalo = new JTextField("1000");
		txtAckDelay = new JTextField("5");
		txtTaxaPerdaAck = new JTextField("0");
		txtTaxaPerdaQuadro = new JTextField("0");
		txtTimeOut = new JTextField(mainFrame.getTransmissor().getTempoTimeOut()+"");
		
		lblTaxaPerdaAck = new JLabel("Erro ACK(%):");
		
		lblTaxaPerdaQuadro = new JLabel("Erro(%):");
		lblTempo = new JLabel("Tempo (ms):");
		
		
		lblQtd = new JLabel("QTD Quadros: ");
		lblQtd.setHorizontalAlignment(JLabel.RIGHT);
		
		lblIntervalo = new JLabel("Intervalo entre quadros (ms): ");
		lblIntervalo.setHorizontalAlignment(JLabel.RIGHT);
		
		lblTimeOut = new JLabel("Time Out (ms): ");
		lblTimeOut.setHorizontalAlignment(JLabel.RIGHT);
		
		txtBitsNumero = new JTextField("3");
		lblBitsNumero = new JLabel("Bits de Número: ");
		lblBitsNumero.setToolTipText("Número de bits para numerar os quadros (2^n).");
		lblBitsNumero.setHorizontalAlignment(JLabel.RIGHT);
		
		txtMaxQuadroCirculando = new JTextField(mainFrame.getTransmissor().getMaximoQuadrosCirculando()+"");
		lblMaxQuadroCirculando = new JLabel("Máx. Quadros Circulando: ");
		lblMaxQuadroCirculando.setHorizontalAlignment(JLabel.RIGHT);
		lblMaxQuadroCirculando.setToolTipText("Número máximo de quadros circulando sem receber timeOut ou ACK.");
		
		
		lblAckDelay = new JLabel("Ack delay:");
		
		btnEnviarPacote.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				enviar();	
			}			
		});
		
		Dimension txtDimension = new Dimension(35,26);
		txtIntervalo.setPreferredSize(txtDimension);
		txtAckDelay.setPreferredSize(txtDimension);
		txtQtd.setPreferredSize(txtDimension);
		txtTempo.setPreferredSize(txtDimension);
		txtTimeOut.setPreferredSize(txtDimension);
		txtTaxaPerdaQuadro.setPreferredSize(txtDimension);
		txtTaxaPerdaAck.setPreferredSize(txtDimension);
		chkMostrarHistorico = new JCheckBox("Time Line",true);
		chkMostrarHistorico.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent arg0) {
				mainFrame.mostrarHistorico(chkMostrarHistorico.isSelected());
			}
		});
		
		panelTransmissor.setLayout(new GridLayout(0,2));

		panelTransmissor.add(chkMostrarHistorico);
		panelTransmissor.add(new JLabel());
		
		panelTransmissor.add(lblQtd);
		panelTransmissor.add(txtQtd);		
		
		panelTransmissor.add(lblIntervalo);
		panelTransmissor.add(txtIntervalo);
		
		//panelTop.add(lblAckDelay);
		//panelTop.add(txtAckDelay);
		
		panelTransmissor.add(lblTimeOut);
		panelTransmissor.add(txtTimeOut);
		
		{//camada fisica
			btnAplicarCamadaFisica = new JButton("Aplicar");
			panelCamadaFisica.add(lblTempo);
			panelCamadaFisica.add(txtTempo);
			
			panelCamadaFisica.add(lblTaxaPerdaQuadro);
			panelCamadaFisica.add(txtTaxaPerdaQuadro);
			
			panelCamadaFisica.add(lblTaxaPerdaAck);
			panelCamadaFisica.add(txtTaxaPerdaAck);
			
			panelCamadaFisica.add(btnAplicarCamadaFisica);
			
			
			btnAplicarCamadaFisica.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try{
						long velocidadeCanal = Long.valueOf(txtTempo.getText());
						CamadaFisica.getInstance().setVelocidadeCanal(velocidadeCanal);
					}catch(Exception e){
						JOptionPane.showMessageDialog(panelCamadaFisica,"Tempo inválido.");
					}
					try{
						int taxaPerdaAck = Integer.valueOf(txtTaxaPerdaAck.getText());
						CamadaFisica.getInstance().setTaxaErroAck(taxaPerdaAck);
					}catch(Exception e){
						JOptionPane.showMessageDialog(panelCamadaFisica,"Taxa de perda de ACK inválida.");
					}
					try{
						int taxaPerda = Integer.valueOf(txtTaxaPerdaQuadro.getText());
						CamadaFisica.getInstance().setTaxaErro(taxaPerda);
					}catch(Exception e){
						JOptionPane.showMessageDialog(panelCamadaFisica,"Taxa de perda de ACK inválida.");
					}
				}
			});
		}
		
		panelTransmissor.add(lblBitsNumero);
		panelTransmissor.add(txtBitsNumero);
		
		panelTransmissor.add(lblMaxQuadroCirculando);
		panelTransmissor.add(txtMaxQuadroCirculando);
		
		panelTransmissor.add(new JLabel());
		panelTransmissor.add(btnEnviarPacote);
		//panelTransmissor.setPreferredSize(new Dimension(400,100));
		//panelTop.add(btnTrocaImagem);
		JPanel pT = new JPanel();
		pT.add(panelTransmissor);
		tabbedPane.addTab("Transmissor Receptor", pT);
		tabbedPane.addTab("Camada Física", panelCamadaFisica);
		
		//this.add(panelTop,BorderLayout.NORTH);
		this.setLayout(new GridLayout(1,1));
		this.add(tabbedPane);
		//this.setPreferredSize(new Dimension(800,330));
	}
	private void enviar(){
		try{
			long tempoTimeOut = Long.valueOf(txtTimeOut.getText());
			long intervalo = Long.valueOf(txtIntervalo.getText());
			int qtd = Integer.valueOf(txtQtd.getText());
			int totalNumeros = (int)Math.pow(2, Integer.valueOf(txtBitsNumero.getText()));
			System.out.println("totalNumeros = " + totalNumeros);
			int maximoQuadrosCirculando = Integer.valueOf(txtMaxQuadroCirculando.getText());
			//this.mainFrame.enviarMensagem("Olá mundo!");
			this.mainFrame.enviarQuadros(qtd);
			
			this.mainFrame.getTransmissor().setTotalNumeros(totalNumeros);
			this.mainFrame.getTransmissor().setMaximoQuadrosCirculando(maximoQuadrosCirculando);
			this.mainFrame.getTransmissor().setTempoTimeOut(tempoTimeOut);
			this.mainFrame.getTransmissor().setIntervaloEntreQuadros(intervalo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
	
}
