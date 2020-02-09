package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Excel {
	

	ClientRequest clientRequest = new ClientRequest();
	//QueryData queryData = new QueryData();
	Controller controller;
	//Main main;
	public Excel() {};
	
	public Excel(ClientRequest clientRequest) {
		this.clientRequest = clientRequest;
	}

	private TableView<DoubleHeader> tabela;
	
    public TableColumn<Header, String> kolumna1;

    public TableColumn<Header, String> kolumna2;
	
	String FilePathXML = "C:\\Users\\10058058\\Desktop\\template.xml";
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
//	
//	public void setMainApp(Main main) {
//		this.main = main;
//	}

	
	public void createWorkbook(String CustomerDBName) throws FileNotFoundException {
		//cardidList = queryData.getCARDIDList();
		System.out.println(controller.numberOfRowsOfInputTable1());
		int numberOfRows = controller.numberOfRowsOfInputTable1();
		Templates templates = new Templates();
		List<Pull> TemplateContent = new ArrayList<>();
		Map<String, String> mapContent = new HashMap<String,String>();
		List <String> cardidList  = new ArrayList<String>(Arrays.asList("123","234","345","456"));
		List<String> comment = new ArrayList<String>(Arrays.asList("Maciek","Tomek","Kasia","Basia"));
		List<String> workorder = new ArrayList<String>(Arrays.asList("zlecenie","aaa","bbb", "ccc"));
		List<Pull> pullList = new ArrayList<>();
		List<String> dataFromZPalcaColumn = new ArrayList<>(); 
		int cellCounter = 0;
		int rowCounter = 2;
		int counter = 0;
		tabela = controller.getInputTable1();
		kolumna1 = controller.getFromInputColumn();
		kolumna2 = controller.getzPalcaColumn();
		
		for(int i = 0; i < controller.numberOfRowsOfInputTable1(); i++) {
			dataFromZPalcaColumn.add(kolumna2.getCellData(i));
		}
		
		
		//-----------------------------------------
		try {

			File Template = new File(FilePathXML);
			JAXBContext jaxbContext = JAXBContext.newInstance(Templates.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			templates = (Templates) jaxbUnmarshaller.unmarshal(Template);
			
			templates.getCustomerDBName();
			templates.getIsRenualChecked();
			pullList = templates.getPull();
			System.out.println(pullList.get(0).getCARDID());
	
			TemplateContent = templates.getPull();
			
		
	

		} catch (JAXBException e2) {
			e2.printStackTrace();
		}
		//-----------------------------------------------------------
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Persons");
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 4000);
		
		Row header = sheet.createRow(0);		
		
		CellStyle headerStyle = workbook.createCellStyle();
		
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);
		
		for(int i =0; i <controller.numberOfRowsOfInputTable1(); i++ ) {
			Cell headerCell = header.createCell(i);
			headerCell.setCellValue(dataFromZPalcaColumn.get(i));
			headerCell.setCellStyle(headerStyle);
		}
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		 

		//Adding data to Cells!
		for (String word : cardidList) {
			Row row = sheet.createRow(rowCounter);
			Cell cell = row.createCell(0);
			cell.setCellValue(cardidList.get(counter).toString());
			cell.setCellStyle(style);
			cell =row.createCell(1);
			cell.setCellValue(comment.get(counter).toString());
			cell = row.createCell(2);
			rowCounter++;
			counter++;
		}
		
	    Date date = new Date();
	    String strDateFormat = "dd-MM-yyyy";
	    String dateToFileName;
	    SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
	    System.out.println("Date:" + objSDF.format(date));
	    dateToFileName = objSDF.format(date);
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		System.out.println(path);
		String fileLocation = path.substring(0, path.length() - 1) + "PULL_Report" + CustomerDBName + dateToFileName +".xlsx";
		System.out.println(fileLocation); 
		
		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		try {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientRequest.getDBQuery().getCARDIDList().get(1);
		//clientRequest
		
	}

}
