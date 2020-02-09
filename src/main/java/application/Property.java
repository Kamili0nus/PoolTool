package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @author kknyszyn
 *
 */
public class Property {

	private String DB_USER, DB_PASS, DB_URL, InputFilePath, OutputFilePath, MatchingMatrixFilePath;

	public Property() {

		Properties prop = new Properties();
		InputStream input = null;
		String filename = "config\\config.properties";
		try {

			input = new FileInputStream(filename);

			prop.load(input);

			DB_USER = prop.getProperty("DB_USER");
			DB_PASS = prop.getProperty("DB_PASS");
			DB_URL = prop.getProperty("DB_URL");
			InputFilePath = prop.getProperty("InputfilesDirectories");
			OutputFilePath = prop.getProperty("OutputDirectories");
			MatchingMatrixFilePath = prop.getProperty("MatchingMatrix");

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

	/**
	 * @return the dB_USER
	 */
	public String getDB_USER() {
		return DB_USER;
	}

	/**
	 * @return the dB_PASS
	 */
	public String getDB_PASS() {
		return DB_PASS;
	}

	/**
	 * @return the dB_URL
	 */
	public String getDB_URL() {
		return DB_URL;
	}

	/**
	 * @return the inputFilePath
	 */
	public String getInputFilePath() {
		return InputFilePath;
	}

	/**
	 * @return the outputFilePath
	 */
	public String getOutputFilePath() {
		return OutputFilePath;
	}

	/**
	 * @return the matchingMatrixFilePath
	 */
	public String getMatchingMatrixFilePath() {
		return MatchingMatrixFilePath;
	}

}
