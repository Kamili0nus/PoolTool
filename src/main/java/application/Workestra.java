/**
 * 
 */
package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author kknyszyn
 *
 */
public class Workestra {
	
	// default constructor
	public Workestra() {}
	
	
	
	//Formatting PAN from IF to fit to DB Queries
	public static String PanReformater(String Pan) {
		String FormatedPan = null;
		
		if(Pan.length()==16 && StringUtils.isNumeric(Pan))FormatedPan=Pan+"%";
		else {
			String Tempe = Pan.replaceAll(" ", "");
			
			if(Tempe.length()>=16 && StringUtils.isNumeric(Tempe)) { 
				String Temp = Pan.replaceAll(" ", "");

				FormatedPan=Temp+"%";}
			else {
				  if(StringUtils.isNumericSpace(Pan)) {
					  FormatedPan= Pan.substring(0, Pan.indexOf(" "))+"%"+Pan.substring(Pan.lastIndexOf(" ")+1)+"%";
					  
				  }else {
					  String Temp = null;
					  Temp = Pan.replaceAll(" ", "");
					  String regex = "^[^0-9]*([0-9]*).*$";
					    Pattern pattern = Pattern.compile(regex);
					    Matcher matcher = pattern.matcher(Temp);
					    matcher.matches();
					    FormatedPan = matcher.group(1) +"%"+Temp.substring(Temp.length()-4)+"%";
				  }
			}
			
			
		}
		return FormatedPan;	
		
		
		
	}

}
