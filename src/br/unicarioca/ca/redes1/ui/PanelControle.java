package br.unicarioca.ca.redes1.ui;

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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.unicarioca.ca.redes1.protocolo.CamadaFisica;
import br.unicarioca.ca.redes1.protocolo.OutPut;

public class PanelControle extends JPanel implements OutPut {
	private static final long serialVersionUID = -1907642558005285055L;
	MainFrame mainFrame;
	JButton btnEnviarPacote;
	JButton btnTrocaImagem;
	JButton btnInterferencia;

	JButton btnAplicarCamadaFisica;
	JButton btnPausa;
	JButton btnStopAndWait;
	JTextField txtTaxaPerdaQuadro;
	JTextField txtTaxaPerdaAck;
	JTextField txtTempoCamadaFisica;
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
	JPanel panelOutPut = new JPanel();
	JTextArea textAreaOutPut = new JTextArea();

	boolean pausado = false; 
	
	public PanelControle(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		JTabbedPane tabbedPane = new JTabbedPane();

		btnEnviarPacote = new JButton("Enviar");
		btnTrocaImagem = new JButton("Trocar");
		btnInterferencia = new JButton("Interferência");
		btnStopAndWait = new JButton("Stop and Wait");
		btnPausa = new JButton("Pausar");
		btnStopAndWait.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setupStopAndWait();
			}			
		});
		btnPausa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(pausado){
						CamadaFisica.getInstance().continuar();
						btnPausa.setText("Pausar");
					}else{
						btnPausa.setText("Continuar");
						CamadaFisica.getInstance().pausar();
					}
					pausado=!pausado;
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainFrame, e.getMessage());
				}
			}
		});
		btnInterferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CamadaFisica.getInstance().interferir();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainFrame, e.getMessage());
				}
			}
		});

		txtTempoCamadaFisica = new JTextField("3000");
		txtQtd = new JTextField("5");
		txtIntervalo = new JTextField("1000");
		txtAckDelay = new JTextField("5");
		txtTaxaPerdaAck = new JTextField("0");
		txtTaxaPerdaQuadro = new JTextField("0");
		txtTimeOut = new JTextField(mainFrame.getTransmissor().getTempoTimeOut()+ "");

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
		lblBitsNumero
				.setToolTipText("Número de bits para numerar os quadros (2^n).");
		lblBitsNumero.setHorizontalAlignment(JLabel.RIGHT);

		txtMaxQuadroCirculando = new JTextField(mainFrame.getTransmissor()
				.getMaximoQuadrosCirculando()
				+ "");
		lblMaxQuadroCirculando = new JLabel("Máx. Quadros Circulando: ");
		lblMaxQuadroCirculando.setHorizontalAlignment(JLabel.RIGHT);
		lblMaxQuadroCirculando
				.setToolTipText("Número máximo de quadros circulando sem receber timeOut ou ACK.");

		lblAckDelay = new JLabel("Ack delay:");

		btnEnviarPacote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviar();
			}
		});

		Dimension txtDimension = new Dimension(35, 26);
		txtIntervalo.setPreferredSize(txtDimension);
		txtAckDelay.setPreferredSize(txtDimension);
		txtQtd.setPreferredSize(txtDimension);
		txtTempoCamadaFisica.setPreferredSize(txtDimension);
		txtTimeOut.setPreferredSize(txtDimension);
		txtTaxaPerdaQuadro.setPreferredSize(txtDimension);
		txtTaxaPerdaAck.setPreferredSize(txtDimension);
		chkMostrarHistorico = new JCheckBox("Time Line", true);
		chkMostrarHistorico.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				mainFrame.mostrarHistorico(chkMostrarHistorico.isSelected());
			}
		});

		panelTransmissor.setLayout(new GridLayout(0, 2));

		panelTransmissor.add(btnInterferencia);
		panelTransmissor.add(btnPausa);

		panelTransmissor.add(chkMostrarHistorico);
		panelTransmissor.add(new JLabel());

		panelTransmissor.add(lblQtd);
		panelTransmissor.add(txtQtd);

		panelTransmissor.add(lblIntervalo);
		panelTransmissor.add(txtIntervalo);

		panelTransmissor.add(lblTimeOut);
		panelTransmissor.add(txtTimeOut);

		{// camada fisica
			btnAplicarCamadaFisica = new JButton("Aplicar");
			panelCamadaFisica.add(lblTempo);
			panelCamadaFisica.add(txtTempoCamadaFisica);

			panelCamadaFisica.add(lblTaxaPerdaQuadro);
			panelCamadaFisica.add(txtTaxaPerdaQuadro);

			panelCamadaFisica.add(lblTaxaPerdaAck);
			panelCamadaFisica.add(txtTaxaPerdaAck);

			panelCamadaFisica.add(btnAplicarCamadaFisica);

			btnAplicarCamadaFisica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setupCamadaFisica();
				}
			});
		}

		panelTransmissor.add(lblBitsNumero);
		panelTransmissor.add(txtBitsNumero);

		panelTransmissor.add(lblMaxQuadroCirculando);
		panelTransmissor.add(txtMaxQuadroCirculando);

		panelTransmissor.add(btnStopAndWait);
		panelTransmissor.add(btnEnviarPacote);

		panelOutPut.setLayout(new GridLayout(0, 1));
		panelOutPut.add(new JScrollPane(textAreaOutPut));
		
		JPanel pT = new JPanel();
		pT.add(panelTransmissor);
		tabbedPane.addTab("Transmissor Receptor", pT);
		tabbedPane.addTab("Camada Física", panelCamadaFisica);
		tabbedPane.addTab("Output", panelOutPut);
		this.setLayout(new GridLayout(1, 1));
		this.add(tabbedPane);

		this.mainFrame.getTransmissor().setOutput(this);
		this.mainFrame.getReceptor().setOutput(this);
		CamadaFisica.getInstance().setOutput(this);
	}
	/**
	 * Configura para stop and wait
	 */
	protected void setupStopAndWait() {
		txtBitsNumero.setText("1");
		txtIntervalo.setText("8000");
		txtMaxQuadroCirculando.setText("1");
		txtTimeOut.setText("7000");
		txtTempoCamadaFisica.setText("3000");
		txtTaxaPerdaAck.setText("0");
		txtTaxaPerdaQuadro.setText("0");
		setupCamadaFisica();
		setupTransmissor();
	}
	private void setupCamadaFisica(){
		try {
			long velocidadeCanal = Long.valueOf(txtTempoCamadaFisica.getText());
			CamadaFisica.getInstance().setVelocidadeCanal(velocidadeCanal);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelCamadaFisica,"Tempo inválido.");
		}
		try {
			int taxaPerdaAck = Integer.valueOf(txtTaxaPerdaAck.getText());
			CamadaFisica.getInstance().setTaxaErroAck(taxaPerdaAck);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelCamadaFisica,"Taxa de perda de ACK inválida.");
		}
		try {
			int taxaPerda = Integer.valueOf(txtTaxaPerdaQuadro.getText());
			CamadaFisica.getInstance().setTaxaErro(taxaPerda);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelCamadaFisica,"Taxa de perda de ACK inválida.");
		}
	}
	private void setupTransmissor(){
		try {
			long tempoTimeOut = Long.valueOf(txtTimeOut.getText());
			long intervalo = Long.valueOf(txtIntervalo.getText());
			int totalNumeros = (int) Math.pow(2, Integer.valueOf(txtBitsNumero.getText()));
			int maximoQuadrosCirculando = Integer.valueOf(txtMaxQuadroCirculando.getText());
			this.mainFrame.getTransmissor().setTotalNumeros(totalNumeros);
			this.mainFrame.getTransmissor().setMaximoQuadrosCirculando(maximoQuadrosCirculando);
			this.mainFrame.getTransmissor().setTempoTimeOut(tempoTimeOut);
			this.mainFrame.getTransmissor().setIntervaloEntreQuadros(intervalo);
			this.mainFrame.getReceptor().setTotalNumeros(totalNumeros);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	private void enviar() {
		try {
			setupTransmissor();
			int qtd = Integer.valueOf(txtQtd.getText());
			this.mainFrame.enviarQuadros(qtd);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	public synchronized void println(String arg) {
		textAreaOutPut.append(arg + "\n");
		textAreaOutPut.setCaretPosition(textAreaOutPut.getText().length());
		// textAreaOutPut.setText(textAreaOutPut.getText());
	}

}
