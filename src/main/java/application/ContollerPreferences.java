package application;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ContollerPreferences implements Initializable {
	
	
	@FXML
	private Button PreferencesCancel; // PreferencesApply;
		
	@FXML
	private  TextField UserText,Passtext,UrlText,inputText,OutputText,MatrixTest;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

    	Properties prop = new Properties();
    	InputStream input = null;
    	String filename = "config\\config.properties";
    	try {
            		
    		input = new FileInputStream(filename);
    		    		
    		prop.load(input);
    		
    		UserText.setText(prop.getProperty("DB_USER"));
    		Passtext.setText(prop.getProperty("DB_PASS"));
    		UrlText.setText(prop.getProperty("DB_URL"));
    		inputText.setText(prop.getProperty("InputfilesDirectories"));
    		OutputText.setText(prop.getProperty("OutputDirectories"));
    		MatrixTest.setText(prop.getProperty("MatchingMatrix"));
		
    	} catch (IOException e) {
    		 System.out.println("Sorry, unable to find " + filename);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{

				try {
					if (input != null) {
						input.close();
					}
				} catch (IOException e) {

					e.printStackTrace();
				}
        }
		

	}
	
	@FXML
	private void CancelPref() {
		
		// get a handle to the stage
		Stage stage = (Stage) PreferencesCancel.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
	
	@FXML
	private void ApplyPref() {
		
		Properties prop = new Properties();
		PrintWriter writer;
		try {

		  writer = new PrintWriter(new File("config\\config.properties")); 
		  
	
			// set the properties value
			prop.setProperty("DB_USER", UserText.getText());
			prop.setProperty("DB_PASS", Passtext.getText());
			prop.setProperty("DB_URL", UrlText.getText());
			prop.setProperty("InputfilesDirectories", inputText.getText());
			prop.setProperty("OutputDirectories", OutputText.getText());
			prop.setProperty("MatchingMatrix", MatrixTest.getText());


			// save properties to project resources
			prop.store(writer, null);

		} catch (IOException io) {
			io.printStackTrace();
		} 
		
		
		
	}
	
}
