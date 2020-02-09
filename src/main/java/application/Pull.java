package application;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Pull")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pull {
	
	@XmlElement
	private String CARDID;
	@XmlElement
	private String PULLDESCRIPTION;
	private String SPECIAL_INSTRUCTIONS;
	private String NEW_DELIVERY_ADRES;
	private String CONTACT_NAME;
	
	public Pull() {};

	public String getCARDID() {
		return CARDID;
	}

	public void setCARDID(String cARDID) {
		CARDID = cARDID;
	}
	
	public String getPULLDESCRIPTION() {
		return PULLDESCRIPTION;
	}

	public void setPULLDESCRIPTION(String pULLDESCRIPTION) {
		PULLDESCRIPTION = pULLDESCRIPTION;
	}
	
	public String getSPECIAL_INSTRUCTIONS() {
		return SPECIAL_INSTRUCTIONS;
	}

	public void setSPECIAL_INSTRUCTIONS(String sPECIAL_INSTRUCTIONS) {
		SPECIAL_INSTRUCTIONS = sPECIAL_INSTRUCTIONS;
	}
	
	public String getNEW_DELIVERY_ADRES() {
		return NEW_DELIVERY_ADRES;
	}

	public void setNEW_DELIVERY_ADRES(String nEW_DELIVERY_ADRES) {
		NEW_DELIVERY_ADRES = nEW_DELIVERY_ADRES;
	}
	
	public String getCONTACT_NAME() {
		return CONTACT_NAME;
	}

	public void setCONTACT_NAME(String cONTACT_NAME) {
		CONTACT_NAME = cONTACT_NAME;
	}

}
