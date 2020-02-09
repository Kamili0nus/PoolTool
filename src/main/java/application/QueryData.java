/**
 * 
 */
package application;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kknyszyn
 *
 */
public class QueryData {

	// From IF file
	private ArrayList<String> CARDIDList = new ArrayList<String>();
	private ArrayList<String> CONTACT_NAME = new ArrayList<String>();
	private ArrayList<String> NEW_DELIVERY_ADRES = new ArrayList<String>();
	private ArrayList<String> PULLDESCRIPTION = new ArrayList<String>();
	private ArrayList<String> SPECIAL_INSTRUCTIONS = new ArrayList<String>();

	// From DB Query
	private ArrayList<String> PanFound = new ArrayList<String>();
	private ArrayList<String> duplicateFound = new ArrayList<String>();
	private ArrayList<String> Wostatus = new ArrayList<String>();
	private ArrayList<String> InsertSucces = new ArrayList<String>();
	private ArrayList<String> PoolExists = new ArrayList<String>();

	// Only for report data
	private ArrayList<String> ReportComment = new ArrayList<String>();
	private ArrayList<String> WorkorderHex = new ArrayList<String>();
	private ArrayList<String> CardSeq = new ArrayList<String>();

	// colummn indexes in IF
	private int idCARDID, idCONTACT_NAME, idPULLDESCRIPTION, idSPECIAL_INSTRUCTIONS;
	private int[] arrayidNEW_DELIVERY_ADRES;
	private int[] arrayidPULLDESCRIPTION;

	private boolean ConcatenatePull;

	// default constructor
	public QueryData() {
	}

	// constructor
	QueryData(ArrayList<ArrayList<String>> InputFileContent, List<Pull> TemplateContent) {

		String[] arrOfStr = null;

		ConcatenatePull = false;

		// check if Delivery address is using many IF fields
		if (TemplateContent.get(0).getNEW_DELIVERY_ADRES().contains("|")) {
			String Temp = TemplateContent.get(0).getNEW_DELIVERY_ADRES();
			arrOfStr = Temp.split("\\|");

			arrayidNEW_DELIVERY_ADRES = new int[arrOfStr.length];

			for (int i = 0; i <= arrOfStr.length - 1; i++) {

				arrayidNEW_DELIVERY_ADRES[i] = InputFileContent.get(0).indexOf(arrOfStr[i]);
			}
			// single row entry
		} else {
			arrayidNEW_DELIVERY_ADRES = new int[1];
			arrayidNEW_DELIVERY_ADRES[0] = InputFileContent.get(0)
					.indexOf(TemplateContent.get(0).getNEW_DELIVERY_ADRES());

		}

		// check if PULLDESCRIPTION is using many IF fields
		if (TemplateContent.get(0).getPULLDESCRIPTION().contains("|")) {
			String Temp = TemplateContent.get(0).getPULLDESCRIPTION();
			arrOfStr = Temp.split("\\|");

			ConcatenatePull = true;

			arrayidPULLDESCRIPTION = new int[arrOfStr.length];

			for (int i = 0; i <= arrOfStr.length - 1; i++) {

				arrayidPULLDESCRIPTION[i] = InputFileContent.get(0).indexOf(arrOfStr[i]);
			}
			// PULLDESCRIPTION single row entry
		} else {
			arrayidPULLDESCRIPTION = new int[1];
			arrayidPULLDESCRIPTION[0] = InputFileContent.get(0).indexOf(TemplateContent.get(0).getPULLDESCRIPTION());

		}

		// retrieving position of data in IF
		this.idCARDID = InputFileContent.get(0).indexOf(TemplateContent.get(0).getCARDID().trim());
		this.idCONTACT_NAME = InputFileContent.get(0).indexOf(TemplateContent.get(0).getCONTACT_NAME().trim());
		this.idPULLDESCRIPTION = InputFileContent.get(0).indexOf(TemplateContent.get(0).getPULLDESCRIPTION().trim());
		this.idSPECIAL_INSTRUCTIONS = InputFileContent.get(0).indexOf(TemplateContent.get(0).getSPECIAL_INSTRUCTIONS().trim());

		// adding data to containers
		for (int i = 1; i <= InputFileContent.size() - 1; i++) {
			
			this.CARDIDList.add(InputFileContent.get(i).get(idCARDID));
			this.CONTACT_NAME.add(InputFileContent.get(i).get(idCONTACT_NAME));

			String Adresstemp = "";
			for (int j = 0; j <= arrayidNEW_DELIVERY_ADRES.length - 1; j++) {
				Adresstemp += InputFileContent.get(i).get(arrayidNEW_DELIVERY_ADRES[j]) + " ";
			}
			this.NEW_DELIVERY_ADRES.add(Adresstemp.trim());

			//If PULLDESCRIPTION is concatenated from two fields
			if (ConcatenatePull) {
				String TempPULLDESCRIPTION = "";

				TempPULLDESCRIPTION += InputFileContent.get(i).get(arrayidPULLDESCRIPTION[0]);

				Pattern p = Pattern.compile("adresu"); // the pattern to search for
				Matcher m = p.matcher(InputFileContent.get(i).get(arrayidPULLDESCRIPTION[1]));

				if (m.find()) {
					TempPULLDESCRIPTION += "_Concatenate";
				}

				this.PULLDESCRIPTION.add(TempPULLDESCRIPTION);

			} else {

				this.PULLDESCRIPTION.add(InputFileContent.get(i).get(idPULLDESCRIPTION));
			}

			this.SPECIAL_INSTRUCTIONS.add(InputFileContent.get(i).get(idSPECIAL_INSTRUCTIONS));

		}

	}

	/**
	 * @return the cARDIDList
	 */
	public ArrayList<String> getCARDIDList() {
		return CARDIDList;
	}

	/**
	 * @return the cONTACT_NAME
	 */
	public ArrayList<String> getCONTACT_NAME() {
		return CONTACT_NAME;
	}

	/**
	 * @return the nEW_DELIVERY_ADRES
	 */
	public ArrayList<String> getNEW_DELIVERY_ADRES() {
		return NEW_DELIVERY_ADRES;
	}

	/**
	 * @return the pULLDESCRIPTION
	 */
	public ArrayList<String> getPULLDESCRIPTION() {
		return PULLDESCRIPTION;
	}


	/**
	 * @return the sPECIAL_INSTRUCTIONS
	 */
	public ArrayList<String> getSPECIAL_INSTRUCTIONS() {
		return SPECIAL_INSTRUCTIONS;
	}

	/**
	 * @return the panFound
	 */
	public ArrayList<String> getPanFound() {
		return PanFound;
	}

	/**
	 * @param panFound
	 *            the panFound to set
	 */
	public void setPanFound(String panFound) {
		this.PanFound.add(panFound);
	}

	/**
	 * @return the duplicateFound
	 */
	public ArrayList<String> getduplicateFound() {
		return duplicateFound;
	}

	/**
	 * @param duplicateFound
	 *            the duplicateFound to set
	 */
	public void setduplicateFound(String duplicateFound) {
		this.duplicateFound.add(duplicateFound);
	}

	/**
	 * @return the statusWarning
	 */
	public ArrayList<String> getWostatus() {
		return Wostatus;
	}

	/**
	 * @param statusWarning
	 *            the statusWarning to set
	 */
	public void setWostatus(String statusWarning) {
		Wostatus.add(statusWarning);
	}

	/**
	 * @return the insertSucces
	 */
	public ArrayList<String> getInsertSucces() {
		return InsertSucces;
	}

	/**
	 * @param insertSucces
	 *            the insertSucces to set
	 */
	public void setInsertSucces(String insertSucces) {
		InsertSucces.add(insertSucces);
	}

	/**
	 * @return the poolExists
	 */
	public ArrayList<String> getPoolExists() {
		return PoolExists;
	}

	/**
	 * @param poolExists
	 *            the poolExists to set
	 */
	public void setPoolExists(String poolExists) {
		PoolExists.add(poolExists);
	}

	/**
	 * @return the reportComment
	 */
	public ArrayList<String> getReportComment() {
		return ReportComment;
	}

	/**
	 * @param reportComment
	 *            the reportComment to set
	 */
	public void setReportComment(String reportComment) {
		ReportComment.add(reportComment);
	}

	/**
	 * @return the workorderHex
	 */
	public ArrayList<String> getWorkorderHex() {
		return WorkorderHex;
	}

	/**
	 * @param workorderHex
	 *            the workorderHex to set
	 */
	public void setWorkorderHex(String workorderHex) {
		WorkorderHex.add(workorderHex);
	}

	/**
	 * @return the cardSeq
	 */
	public ArrayList<String> getCardSeq() {
		return CardSeq;
	}

	/**
	 * @param cardSeq
	 *            the cardSeq to set
	 */
	public void setCardSeq(String cardSeq) {
		CardSeq.add(cardSeq);
	}

}
