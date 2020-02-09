package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main extends Application {
	
	// GUI function
	
	Controller controller;
	
	
	
	public Controller getController() {
		return controller;
	}


	public void setController(Controller controller) {
		this.controller = controller;
	}


	@Override
	public void start(Stage primaryStage) {
		try {
					
			String Version = "1.0";
			
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("PullTool.fxml"));
			
			Scene scene = new Scene(root,1024,768);
			scene.getStylesheets().add(getClass().getResource("application/application.css").toExternalForm());
			primaryStage.setResizable(false); 
			primaryStage.setScene(scene);
			primaryStage.setTitle("PoolTool v."+ Version);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// Command Line
	public static void main(String[] args) throws IOException, SQLException, ParseException {

		
		
		if (args.length == 0) {
			launch(args);
			 System.exit(0);
		}
		   
		     String Name = "Pull Management Application";
		     String Version = "1.0";
		     String filepatch = "config\\log4j2.xml";
		     String FileName = "log4j2.xml";

	         InputStream inputStream;
	         
				inputStream = new FileInputStream(filepatch);
		         ConfigurationSource source = new ConfigurationSource(inputStream);
		         Configurator.initialize(null, source);
		         
		         Logger logger = LogManager.getLogger();
				
		         logger.info(FileName + ": file loaded successfully");  
		         logger.info("Starting: "+ Name+" "+Version);
		   
		// Console arguments count check

		  if (args.length == 1) { logger.
		  error("insufficient parameters passed to file. Only one param was passed.");
		  System.exit(1); } else if (args.length == 2) {
		  logger.info("Correct number of parameters passed."); } else { logger.
		  error("Too many parameters passed, only client IF and Matrix file needs to be passes"
		  ); System.exit(1); }

		    logger.info("Loading Property file");
		    Property Props = new Property();

		logger.info("Loading Renewals file");
		Renewals RenList = new Renewals();

		InputParser Plik = new InputParser();

		 String FilePath = Props.getInputFilePath() + args[0].toString();
		//String FilePath = "C:\\Users\\kknyszyn\\Desktop\\toolek\\odpalanie\\inputfile\\pull_2018-09-11.xls";

		 logger.info("Loading File: " + args[0].toString() + " form directory: " +	 Props.getInputFilePath());
		 ArrayList<ArrayList<String>> FileContent = new ArrayList<ArrayList<String>>();
		try {
			// reading Client IF
			Plik.ParseFile(FilePath);
			FileContent = Plik.getFileContent();

		} catch (Exception e1) {
			logger.error("fail to open file: " + args[0].toString());
			e1.printStackTrace();
		}

		 logger.info("Loading File: " + args[1].toString() + " form directory: " + Props.getMatchingMatrixFilePath());
		 
		Templates templates = new Templates();
		List<Pull> TemplateContent = new ArrayList<>();
		Map<String, String> mapContent = new HashMap<String,String>();

		 String FilePathXML = Props.getMatchingMatrixFilePath() + args[1].toString();
		//String FilePathXML = "C:\\Users\\kknyszyn\\Desktop\\toolek\\odpalanie\\matrix\\TemplateOK.xml";

		try {

			File Template = new File(FilePathXML);
			JAXBContext jaxbContext = JAXBContext.newInstance(Templates.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			templates = (Templates) jaxbUnmarshaller.unmarshal(Template);

			templates.getCustomerDBName();
			templates.getIsRenualChecked();

			TemplateContent = templates.getPull();
			mapContent = templates.getReport();

		} catch (JAXBException e2) {
			logger.error("fail to open file: " + args[1].toString());
			e2.printStackTrace();
		}

		logger.info("matching input file data with pull template");

		ClientRequest MatchedData = new ClientRequest(templates.getCustomerDBName(), Plik.getFileName(),
				templates.getIsRenualChecked(), FilePathXML.substring(FilePathXML.lastIndexOf("\\") + 1), FileContent,
				TemplateContent);

		logger.info("Loading pull_matrix file");

		String MatrixFilePath = Props.getMatchingMatrixFilePath() + "pull_matrix.csv";
		//String MatrixFilePath = "C:\\Users\\kknyszyn\\Desktop\\toolek\\odpalanie\\matrix\\pull_matrix.csv";

		InputParser PullMatrixFile = new InputParser();

		Map<String, String> Matrix = new HashMap<String, String>();

		PullMatrixFile.ParseFile(MatrixFilePath);

		for (int i = 1; i <= PullMatrixFile.getFileContent().size() - 1; i++) {
			Matrix.put(PullMatrixFile.getFileContent().get(i).get(0), PullMatrixFile.getFileContent().get(i).get(1));
		}

		// DB connection sets
		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement insert = null;
		ResultSet rs = null;

		// tns system file path
		// System.setProperty("oracle.net.tns_admin","C:/app/product/11.2.0/client_1/NETWORK/ADMIN");

		// // ORCL is net service name from the TNSNAMES.ORA file
		String dbURL = "jdbc:oracle:thin:@";
		String TNS = Props.getDB_URL();

		String CON = dbURL + TNS;

		logger.info("Setting db connection for: " + CON);

		// Prepared Select Query
		String SelectSTM = "select c.originalworkorderid, to_char(c.originalworkorderid, 'XXXXXX')as WOInHex, c.initindexnumber, c.cardid,"
				+ " w.status,p.deliverydelay,c.primarykeydisplay,c.primarykeyvalue, co.customername, count (*) over (partition by c.primarykeyvalue) as CNT,"
				+ " CASE WHEN (select pu.pullid FROM card ca, workorder w, product p, customerorder co, pull pu WHERE ca.originalworkorderid = w.workorderid"
				+ " and w.customerorderid = co.customerorderid and w.productid = p.productid and co.customername like ? and pu.cardid = ca.cardid and ca.primarykeyvalue like ? )"
				+ " is not null THEN 'True' ELSE 'False' END as \"PULL_DONE\" FROM card c, workorder w, product p, customerorder co WHERE c.originalworkorderid = w.workorderid"
				+ " and w.customerorderid = co.customerorderid and w.productid = p.productid and co.customername like ? and   c.primarykeyvalue like ? ";

		// Prepared Insert
		String InsertSTM = "INSERT into PULL values('',?,'0','','',?,sysdate,'',?,?,'','',?,'','','',?,'','0','0','0','','','','','','','','','','','','0')";

		// formating Date for DB Column
		String PULLDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(PULLDate);
		// because PreparedStatement#setDate(..) expects a java.sql.Date argument
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection(CON, Props.getDB_USER(), Props.getDB_PASS());
			con.setAutoCommit(false);

			stmt = con.prepareStatement(SelectSTM);

			for (int i = 0; i < MatchedData.getDBQuery().getCARDIDList().size(); i++) {
				boolean Insert = true;
				int CardId = 0;
				String TempReportComment = "";
				
				stmt.setString(1, MatchedData.getCustomerDBName());
				stmt.setString(2, Workestra.PanReformater(MatchedData.getDBQuery().getCARDIDList().get(i)));
				stmt.setString(3, MatchedData.getCustomerDBName());
				stmt.setString(4, Workestra.PanReformater(MatchedData.getDBQuery().getCARDIDList().get(i)));

				rs = stmt.executeQuery();

				if (!rs.next()) {
					logger.info("No data found in DB for PAN: " + MatchedData.getDBQuery().getCARDIDList().get(i));
					MatchedData.getDBQuery().setPanFound("false");
					MatchedData.getDBQuery().setduplicateFound("False");

					TempReportComment += "Brak numeru PAN w bazie danych ";

				} else {

				// get query result
					do {
						logger.info("Data found in DB for PAN: " + MatchedData.getDBQuery().getCARDIDList().get(i));
						MatchedData.getDBQuery().setPanFound("True");


						// Count of record for the same PAN
						if (rs.getInt("CNT") > 1) {
							logger.info("Duplicated PAN's records  found");
							MatchedData.getDBQuery().setduplicateFound("True");
							TempReportComment += "Wiecej niz jedno wystapienie numeru PAN w bazie danych ";
						} else {
							MatchedData.getDBQuery().setduplicateFound("False");
						}

						// check if poll was already done
						if (rs.getString("PULL_DONE").equalsIgnoreCase("True")) {
							logger.info(
									"Pull  already done for PAN: " + MatchedData.getDBQuery().getCARDIDList().get(i));
							MatchedData.getDBQuery().setPoolExists("True");
							TempReportComment += "Pull juz zaznaczony w bazie danych ";
						} else {
							MatchedData.getDBQuery().setPoolExists("False");
						}

						// ING Priority Pass
						if (rs.getString("primarykeyvalue").length() <= 13
								&& MatchedData.getCustomerDBName().equalsIgnoreCase("ING_BANK_SLASKI")) {
							Insert = false;
							TempReportComment += "ING Priority Pass – konieczna operacja manualna ";

						}

						// Renual Check
						if (MatchedData.getIsRenualChecked().equalsIgnoreCase("Enabled")) {

							// SkipRenual = true;
							String queryDelay = rs.getString("deliverydelay");
							if (RenList.IsRenewals(queryDelay)) {
								Insert = false;
								TempReportComment += "Karta Renewal – konieczna operacja manualna ";
							}
						}

						// Workorder Status
						MatchedData.getDBQuery().setWostatus(rs.getString("status"));

						MatchedData.getDBQuery().setWorkorderHex(rs.getString("WOInHex"));

						MatchedData.getDBQuery().setCardSeq(rs.getString("initindexnumber"));

						CardId = rs.getInt("cardid");

					} while (rs.next());
				}
				// End of Select Result

				boolean MatrixMatch = true;
				if (Matrix.get(MatchedData.getDBQuery().getPULLDESCRIPTION().get(i)) == null) {
					MatrixMatch = false;
					logger.info("No data match in pull_matrix");
					TempReportComment += "Typ pulla nie zdefiniowany ";
				}

				MatchedData.getDBQuery().setReportComment(TempReportComment);

				// IF pull was already done then skip insert
				if ((MatchedData.getDBQuery().getPoolExists().get(i).equalsIgnoreCase("False")) && MatrixMatch
						&& Insert) {

					// set empty string if no data found in IF
					String SPECIAL_INSTRUCTIONS = null;
					if (MatchedData.getDBQuery().getSPECIAL_INSTRUCTIONS().get(i).isEmpty()) {
						SPECIAL_INSTRUCTIONS = "";
					} else {
						SPECIAL_INSTRUCTIONS = MatchedData.getDBQuery().getSPECIAL_INSTRUCTIONS().get(i);
					}
					String NEW_DELIVERY_ADRES = null;
					if (MatchedData.getDBQuery().getNEW_DELIVERY_ADRES().get(i).isEmpty()) {
						NEW_DELIVERY_ADRES = "";
					} else {
						NEW_DELIVERY_ADRES = MatchedData.getDBQuery().getNEW_DELIVERY_ADRES().get(i);
					}
					String CONTACT_NAME = null;
					if (MatchedData.getDBQuery().getCONTACT_NAME().get(i).isEmpty()) {
						CONTACT_NAME = "";
					} else {
						CONTACT_NAME = MatchedData.getDBQuery().getCONTACT_NAME().get(i);
					}

					insert = con.prepareStatement(InsertSTM);

					// setting Data for Insert Statement
					insert.setInt(1, CardId);
					insert.setString(2, Props.getDB_USER().toString().trim());
					//insert.setDate(3, sqlDate);
					insert.setString(3, SPECIAL_INSTRUCTIONS);
					insert.setString(4, NEW_DELIVERY_ADRES);
					insert.setInt(5,
							Integer.parseInt(Matrix.get(MatchedData.getDBQuery().getPULLDESCRIPTION().get(i))));
					insert.setString(6, CONTACT_NAME);
					// Executing insert
					int rowsInserted = insert.executeUpdate();

					if (rowsInserted > 0) {
						logger.info("Pull was inserted successfully for PAN: "
								+ MatchedData.getDBQuery().getCARDIDList().get(i));
						MatchedData.getDBQuery().setInsertSucces("True");
					} else {
						logger.info("Fail to insert Pull  PAN: " + MatchedData.getDBQuery().getCARDIDList().get(i));
						MatchedData.getDBQuery().setInsertSucces("False");
					}

					MatchedData.getDBQuery().getWostatus().get(i);

				}
				// end of query loop
			}

		} catch (ClassNotFoundException e) {
			logger.error("DB error - fail to Connect to DB..");
			e.printStackTrace();
			System.exit(1);
		} finally {
			// Closing DB connections
			rs.close();
			stmt.close();
			con.close();
			logger.info("Closing DB connection");
		}
		System.exit(0);
	}
}