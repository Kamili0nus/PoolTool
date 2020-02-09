package application;

import javax.xml.bind.annotation.XmlElement;

public class DoubleHeader {

	private String fromInput;
	private String zPalca;
	
	//private Header() {};
	
	DoubleHeader(String fromInput, String zPalca) {
		this.fromInput = fromInput;
		this.zPalca = zPalca;
	}
	
	@XmlElement
	public String getFromInput() {
		return fromInput;
	}
	
	public void setFromInput(String fromInput) {
		this.fromInput = fromInput;
	}
	
	@XmlElement
	public String getZPalca() {
		return zPalca;
	}
	
	public void setZPalca(String zPalca) {
		this.zPalca = zPalca;
	}
	
}
