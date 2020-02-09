package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Renewals {

	
	private List<String> RenList = new ArrayList<String>();
	
	
	
	public Renewals() {
		
		
		Properties prop = new Properties();
		InputStream input = null;
		String filename = "config\\Renewals.properties";
		try {

			input = new FileInputStream(filename);
			prop.load(input);

		String Temp ="";
		
		Temp = prop.getProperty("RenewalList");
			
		String str[] = Temp.split(",");
		
		RenList = Arrays.asList(str);
				
		} catch (IOException e) {
			System.out.println("Sorry, unable to find " + filename);
			e.printStackTrace();
		} finally {

			try {
				input.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	
	public boolean IsRenewals(String Value) {
		boolean IsRenewal = false;
		
		if(RenList.contains("Value")) IsRenewal= true;
		
		return IsRenewal;
	}
	
	
	
}
