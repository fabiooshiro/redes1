package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JLabel imagem;
	public PanelAnimacao(){
		imagem = new JLabel();
		add(imagem,BorderLayout.CENTER);
	}

	public void setBufferedImage(BufferedImage bi) {
		imagem.setIcon(new ImageIcon(bi));
	}
}
