package application;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="Template")
@XmlAccessorType(XmlAccessType.FIELD)
public class Templates {
	
	@XmlElement
	private String customerDBName;
	
	@XmlElement
	private String isRenualChecked;
	
	@XmlElement(name="Pull")
	private List<Pull> pulls = null;

	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, String> Report;
	
	public Map<String, String> getReport() {
		return Report;
	}

	public void setReport(Map<String, String> mapProperty) {
		this.Report = mapProperty;
	}

	public List<Pull> getPull() {
		return pulls;
	}
	
	public void setPull(List<Pull> templateList) {
		this.pulls = templateList;
	}
	
	public String getCustomerDBName() {
		return customerDBName;
	}

	public void setCustomerDBName(String customerDBName) {
		this.customerDBName = customerDBName;
	}

	public String getIsRenualChecked() {
		return isRenualChecked;
	}

	public void setIsRenualChecked(String isRenualChecked) {
		this.isRenualChecked = isRenualChecked;
	}


	
}
