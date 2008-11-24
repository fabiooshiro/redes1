package br.unicarioca.ca.redes1.bo;

public abstract class FrameAction {
	private long frame = 0;
	public FrameAction(long frame) {
		this.frame = frame;
	}
	public abstract void executar();
	public long getFrame() {
		return frame;
	}
	public void setFrame(long frame) {
		this.frame = frame;
	}
	
}
