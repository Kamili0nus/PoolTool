package application;

import javax.xml.bind.annotation.XmlElement;

public class Header {

	private String headerName;
	
	//private Header() {};
	
	Header(String headerName) {
		this.headerName = headerName;
	}
	
	@XmlElement
	public String getHeaderName() {
		return headerName;
	}
	
	public void setHeaderName(String name) {
		this.headerName = name;
	}
	
}
