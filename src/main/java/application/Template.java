package application;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Pull")
public class Template {

	private String headerName;
	private String templateName;
	private String customerDBName;
	private String isRenualChecked;
		
	public Template(String headerName, String templateName, String customerDBName, String isRenualChecked) {
		super();
		this.headerName = headerName;
		this.templateName = templateName;
		this.customerDBName = customerDBName;
		this.isRenualChecked = isRenualChecked;
	}
	
	public Template() {
		// TODO Auto-generated constructor stub
	};
	@XmlElement(name="FirstColumn")
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	@XmlElement
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	@XmlElement
	public String getCustomerDBName() {
		return customerDBName;
	}
	public void setCustomerDBName(String customerDBName) {
		this.customerDBName = customerDBName;
	}
	@XmlElement
	public String getIsRenualChecked() {
		return isRenualChecked;
	}
	public void setIsRenualChecked(String isRenualChecked) {
		this.isRenualChecked = isRenualChecked;
	}
	
	
	
	
}
