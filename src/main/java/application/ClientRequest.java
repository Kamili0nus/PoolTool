/**
 * Clinet request file
 */
package application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kknyszyn
 *
 */
public class ClientRequest {
	

	private String customerDBName, ClientFileName, isRenualChecked, templateName;
	
	private QueryData DBQuery;
	
	//default constructor
	public ClientRequest(){}
		
	/**
	 * @param customerDBName
	 * @param clientFileName
	 * @param isRenalChecked
	 * @param templateName
	 */
	public ClientRequest(String customerDBName, String clientFileName, String isRenalChecked,
							String templateName,ArrayList<ArrayList<String>> InputFileContent, List<Pull> TemplateContent ) {
		
		this.customerDBName = customerDBName;
		this.ClientFileName = clientFileName;
		this.isRenualChecked = isRenalChecked;
		this.templateName = templateName;
		
		//creating container for query data
		DBQuery = new QueryData(InputFileContent,TemplateContent);
	}

	/**
	 * @return the customerDBName
	 */
	public String getCustomerDBName() {
		return customerDBName;
	}


	/**
	 * @return the clientFileName
	 */
	public String getClientFileName() {
		return ClientFileName; }

	/**
	 * @return the IsRenualChecked
	 */
	public String getIsRenualChecked() {
		return isRenualChecked;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @return the dBQuery
	 */
	public QueryData getDBQuery() {
		return DBQuery;
	}
	

}
