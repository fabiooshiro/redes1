package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.unicarioca.ca.redes1.bo.Tela;

/**
 * Contém os elementos para serem animados<br>
 * Transmissor <br>
 * Receptor<br>
 * Quadro<br>
 * ACK<br>
 * NACK<br>
 * TimeOut<br>
 * @author Fabio Issamu Oshiro
 *
 */
public class PanelAnimacao extends JPanel implements Tela{
	private static final long serialVersionUID = -2356221509796590503L;
	private JLabel imagem;
	private JLabel lblEnviar 	=	new JLabel(" Mensagem: ");
	private JLabel lblRecebida 	= 	new JLabel(" Mensagem: ");
	private JButton btnEnviar = new JButton("Enviar");
	private JTextField mensagemEnviada = new JTextField();
	private JTextField mensagemRecebida = new JTextField();
	MainFrame mainFrame;
	public PanelAnimacao(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout());
		imagem = new JLabel();
		
		mensagemEnviada.setText("Olá Mundo!");
		JPanel trans = new JPanel(new BorderLayout());
		trans.setBorder(BorderFactory.createTitledBorder("Transmissor"));
		btnEnviar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				enviarMensagem();
			}
		});
		
		trans.add(lblEnviar,BorderLayout.WEST);
		trans.add(mensagemEnviada,BorderLayout.CENTER);
		trans.add(btnEnviar,BorderLayout.EAST);
		
		btnEnviar.setBounds(new Rectangle(100,10));
		
		JPanel receptor = new JPanel(new BorderLayout());
		receptor.add(lblRecebida,BorderLayout.WEST);
		receptor.add(mensagemRecebida,BorderLayout.CENTER);
		receptor.setBorder(BorderFactory.createTitledBorder("Receptor"));
		
		add(trans,BorderLayout.NORTH);
		add(imagem,BorderLayout.CENTER);
		add(receptor,BorderLayout.SOUTH);
		add(new JLabel(),BorderLayout.WEST);
		add(new JLabel(),BorderLayout.EAST);
	}
	protected void enviarMensagem() {
		this.mainFrame.enviarMensagem(mensagemEnviada.getText());
	}
	public String getMensagemEnviada(){
		return mensagemEnviada.getText();
	}
	public void setMensagemRecebida(String texto){
		mensagemRecebida.setText(texto);
	}
	public void setBufferedImage(BufferedImage bi) {
		ImageIcon imageIcon = new ImageIcon(bi.getSubimage(0, 0, 770, bi.getHeight()));
		imagem.setIcon(imageIcon);
	}
}
