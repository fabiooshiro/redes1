package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelControle extends JPanel{
	private static final long serialVersionUID = -1907642558005285055L;
	MainFrame mainFrame;
	JButton btnEnviarPacote;
	JButton btnEmitirOnda;
	JTextField txtTaxaPerdaQuadro;
	JTextField txtTaxaPerdaAck;
	JTextField txtTempo;
	JTextField txtQtd;
	JTextField txtIntervalo;
	JTextField txtAckDelay;
	JCheckBox chkMostrarHistorico;
	JLabel lblTaxaPerdaQuadro;
	JLabel lblTaxaPerdaAck;
	JLabel lblTempo;
	JLabel lblQtd;
	JLabel lblIntervalo;
	JLabel lblAckDelay;
	JPanel panelTop = new JPanel();
	JPanel panelCenter = new JPanel();
	public PanelControle(final MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		btnEnviarPacote=new JButton("Enviar");
		btnEmitirOnda=new JButton("Emitir Onda");
		txtTempo = new JTextField("45");
		txtQtd = new JTextField("3");
		txtIntervalo = new JTextField("20");
		txtAckDelay = new JTextField("5");
		txtTaxaPerdaAck = new JTextField("0");
		txtTaxaPerdaQuadro = new JTextField("0");
		lblTaxaPerdaAck = new JLabel("Erro ACK(%):");
		lblTaxaPerdaQuadro = new JLabel("Erro(%):");
		lblTempo = new JLabel("Tempo:");
		lblQtd = new JLabel("QTD.:");
		lblIntervalo = new JLabel("Intervalo:");
		lblAckDelay = new JLabel("Ack delay:");
		
		btnEmitirOnda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				enviarOnda();	
			}			
		});
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
		txtTaxaPerdaQuadro.setPreferredSize(txtDimension);
		txtTaxaPerdaAck.setPreferredSize(txtDimension);
		chkMostrarHistorico = new JCheckBox("Time Line",false);
		chkMostrarHistorico.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent arg0) {
				mainFrame.mostrarHistorico(chkMostrarHistorico.isSelected());
			}
		});
		panelCenter.add(chkMostrarHistorico);
		panelTop.add(lblQtd);
		panelTop.add(txtQtd);		
		panelTop.add(lblTempo);
		panelTop.add(txtTempo);
		
		panelTop.add(lblIntervalo);
		panelTop.add(txtIntervalo);
		
		panelTop.add(lblAckDelay);
		panelTop.add(txtAckDelay);
		
		panelTop.add(lblTaxaPerdaQuadro);
		panelTop.add(txtTaxaPerdaQuadro);
		
		panelTop.add(lblTaxaPerdaAck);
		panelTop.add(txtTaxaPerdaAck);
		
		panelTop.add(btnEnviarPacote);
		panelTop.add(btnEmitirOnda);
		this.add(panelTop,BorderLayout.NORTH);
		this.add(panelCenter,BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(800,330));
	}
	private void enviar(){
		
		try{
			int ackDelay = Integer.valueOf(txtAckDelay.getText());
			int intervalo = Integer.valueOf(txtIntervalo.getText());
			int qtd = Integer.valueOf(txtQtd.getText());
			int tempo = Integer.valueOf(txtTempo.getText());
			int taxaErro = Integer.valueOf(txtTaxaPerdaQuadro.getText());
			int taxaErroAck = Integer.valueOf(txtTaxaPerdaAck.getText());
			this.mainFrame.enviarPacote(qtd, tempo, intervalo, ackDelay,taxaErro,taxaErroAck);
			
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
	
	private void enviarOnda(){
		try{
			this.mainFrame.enviarOnda();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
}
