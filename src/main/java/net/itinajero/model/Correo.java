package net.itinajero.model;

public class Correo {

	private String para;
	private String de;
	private String asunto;
	private String texto;
	
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
	public String getDe() {
		return de;
	}
	public void setDe(String de) {
		this.de = de;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	@Override
	public String toString() {
		return "Correo [para=" + para + ", de=" + de + ", asunto=" + asunto + ", texto=" + texto + "]";
	}
	
	
	
	
}
