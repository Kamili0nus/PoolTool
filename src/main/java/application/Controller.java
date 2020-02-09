package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;


public class Controller  {
	Main main;
		
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}

	public ArrayList<String> headerList = new ArrayList<>();

	public ArrayList<String> headerList1 = new ArrayList<>();
	
	public ArrayList<String> contentList = new ArrayList<>();
			
	private ArrayList<ArrayList<String>> mainFileContent = new ArrayList<ArrayList<String>>();
		
	public ObservableList<Header> headerData = FXCollections.observableArrayList();	
	
	public ObservableList<FileContent> contentData = FXCollections.observableArrayList();	
	
	public ObservableList<Header> clearData = FXCollections.observableArrayList();
	
    @FXML
    void generateExcel(ActionEvent event) {
	    Excel file = new Excel();
	    file.setController(this);
	    
	    try {
			file.createWorkbook("Citi");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@FXML
	private javafx.scene.control.Button fileOpenButton;
	
	@FXML
	private  TextField filePatchArea;
	@FXML
	private Label Testowy;
	
    @FXML
    private TextField DragTextBox1;

    @FXML
    private TextField DragTextBox2;

    @FXML
    private TextField DragTextBox3;

    @FXML
    private TextField DragTextBox4;

    @FXML
    private TextField DragTextBox5;

    
    @FXML
    private TextField TemplateTextBox1;
    
    @FXML
    private TextField TemplateTextBox2;

    @FXML
    private TextField TemplateTextBox3;

    @FXML
    private TextField TemplateTextBox4;

    @FXML
    private TextField TemplateTextBox5;

    @FXML
    private TextField TemplateTextBox6;

    @FXML
    private TextField TemplateTextBox7;

    @FXML
    private TextField TemplateTextBox8;

    @FXML
    private TextField TemplateTextBox9;
    
    @FXML
    private CheckBox renualCheckBox;

    @FXML
    private TextField customerDBName;
	
	@FXML ImageView obrazek;
	
	private InputParser Plik;

	private String FilePath;
	
    @FXML
    private Button reportButton;
	
    @FXML
    private TableView<Header> inputFileHeader;
    
    @FXML
    private TableView<Header> inputFileHeader1;

    @FXML
    private TableColumn<Header, String> headerColumn;

    @FXML
    private TableView<FileContent> tableContent;

    @FXML
    private TableColumn<FileContent, String> contentColumn;
	    
	@FXML
	private javafx.scene.control.Button closeButton;
	
    @FXML
    private MenuItem openXMLMenuItem;   
    
    @FXML
    private Button clearButton;
    //______________________________________________________
    //in progress
	@FXML
    private CheckBox checkedButton_1;
	@FXML
    private CheckBox checkedButton_2;
	@FXML
    private CheckBox checkedButton_3;
	@FXML
    private CheckBox checkedButton_4;
	@FXML
    private CheckBox checkedButton_5;
	@FXML
    private CheckBox checkedButton_6;
	@FXML
    private CheckBox checkedButton_7;
	@FXML
    private CheckBox checkedButton_8;
	@FXML
    private CheckBox checkedButton_9;
	
	@FXML
    private TableView<?> reportTable_2;
	@FXML
    private TableColumn<Header, String> headerColumn1;
	
	private ObservableList<Header> headerData1 = FXCollections.observableArrayList();
	
	private ObservableList<DoubleHeader> secondTable1 = FXCollections.observableArrayList();
	
	private List<TextField> fromInputList = new ArrayList<TextField>();
	
	private List<TextField> zPalcaList= new ArrayList<TextField>();
	
	
		
	public TextField getTemplateTextBox1() {
		return TemplateTextBox1;
	}

	public TableView<DoubleHeader> getInputTable1() {
		return inputTable1;
	}
	public void setInputTable1(TableView<DoubleHeader> inputTable1) {
		this.inputTable1 = inputTable1;
	}
	public TableColumn<Header, String> getzPalcaColumn() {
		return zPalcaColumn;
	}
	public void setzPalcaColumn(TableColumn<Header, String> zPalcaColumn) {
		this.zPalcaColumn = zPalcaColumn;
	}
			
	public TableColumn<Header, String> getFromInputColumn() {
		return fromInputColumn;
	}
	public void setFromInputColumn(TableColumn<Header, String> fromInputColumn) {
		this.fromInputColumn = fromInputColumn;
	}
	@FXML
	void pressed_1(ActionEvent event)  {
		System.out.println("1 pressed");
		zPalcaList.add(TemplateTextBox1);
		fromInputList.add(DragTextBox1);
	}
	@FXML
	void pressed_2(ActionEvent event)  {
		System.out.println("2 pressed");
		zPalcaList.add(TemplateTextBox2);
		fromInputList.add(DragTextBox2);
	}
	@FXML
	void pressed_3(ActionEvent event)  {
		System.out.println("3 pressed");
		zPalcaList.add(TemplateTextBox3);
		fromInputList.add(DragTextBox3);
	}
	@FXML
	void pressed_4(ActionEvent event)  {
		System.out.println("4 pressed");
		zPalcaList.add(TemplateTextBox4);
		fromInputList.add(DragTextBox4);
	}
	@FXML
	void pressed_5(ActionEvent event)  {
		System.out.println("5 pressed");
		zPalcaList.add(TemplateTextBox5);
		fromInputList.add(DragTextBox5);
	}
	
	@FXML
	void sendReport(ActionEvent event)  {
		displayTable2();
	}
	
	@FXML
    private TableView<DoubleHeader> inputTable1;
	
	@FXML
    private TableColumn<Header, String> fromInputColumn;

    @FXML
    private TableColumn<Header, String> zPalcaColumn;
    
    public int numberOfRowsOfInputTable1() {
    	int count = inputTable1.getItems().size();
    	
		return count;
    	
    }
    
    public void displayTable2() {
		fromInputColumn.setCellValueFactory(new PropertyValueFactory<>("fromInput"));
		zPalcaColumn.setCellValueFactory(new PropertyValueFactory<>("zPalca"));
		for (int i = 0; i < fromInputList.size(); i++) {
			String headerFromInput = fromInputList.get(i).getText();
			String headerZPalca = zPalcaList.get(i).getText();
			System.out.println(headerZPalca );
			System.out.println(headerFromInput);
			secondTable1.add(new DoubleHeader(headerFromInput,headerZPalca));
		}
		inputTable1.setItems(secondTable1);

    }
    

	public void displayHeaders() {
		headerColumn.setCellValueFactory(new PropertyValueFactory<>("headerName"));
		headerColumn1.setCellValueFactory(new PropertyValueFactory<>("headerName"));
		headerList = mainFileContent.get(0);
	    for (String headers : headerList) {
	    	headerData.add(new Header(headers));
	    	headerData1.add(new Header(headers));
	    }
	    inputFileHeader.setItems(headerData);
	    inputFileHeader1.setItems(headerData1);
	    
	   	}
	
    @FXML
    void clearTable(ActionEvent event) {
	    List<TextField> dragFields = new ArrayList<>(Arrays.asList(DragTextBox1,DragTextBox2,DragTextBox3,DragTextBox4,DragTextBox5));
	    for (TextField textField : dragFields) {
				textField.clear();
		}
	    
	    
	    clearDataFromInput();
    }


    @FXML
    void openXMLFile(ActionEvent event) {
    	//File file = new File("config\\jaxb.xml");
    	try {
    		Templates templates = new Templates();
			JAXBContext jaxbContext = JAXBContext.newInstance(Templates.class);
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML file", "*.xml"));
			File file = fileChooser.showOpenDialog(null);
			
			if (file != null) {
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			templates = (Templates)jaxbUnmarshaller.unmarshal(file);
			}
			
			List<Pull> list = new ArrayList<>();
			list = templates.getPull();
			
			customerDBName.setText(templates.getCustomerDBName());
			if(templates.getIsRenualChecked().equals("Enabled")) {
				renualCheckBox.setSelected(true);
			}
			else
				renualCheckBox.setSelected(false);
			
			DragTextBox1.setText(list.get(0).getCARDID());
			DragTextBox2.setText(list.get(0).getCONTACT_NAME());
			DragTextBox3.setText(list.get(0).getNEW_DELIVERY_ADRES());
			DragTextBox4.setText(list.get(0).getPULLDESCRIPTION());
			DragTextBox5.setText(list.get(0).getSPECIAL_INSTRUCTIONS());

		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void createReport(ActionEvent event) throws IOException {
     	ObservableList<DoubleHeader> obsList = FXCollections.observableArrayList();
	    List<TextField> dragFields = new ArrayList<>(Arrays.asList(DragTextBox1,DragTextBox2,DragTextBox3,DragTextBox4,DragTextBox5));
    	List<String> reportList = new ArrayList<>(Arrays.asList("osoba","numer"));
	    Templates templates = new Templates();
	    Map<String, String> reportMap = new HashMap<>();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select file to save");
    	fileChooser.setInitialFileName("template.xml");
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML file", "*.xml"));
    	File file = fileChooser.showSaveDialog(null);
	    Pull pull = new Pull();

	    FileOutputStream fileOutputStream = new FileOutputStream(file);
    	OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
    	String selected = "";
 	  	
    	boolean isSelected = renualCheckBox.isSelected();
    	if(isSelected) {
    		selected = "Enabled";
    	}
    	else
    		selected = "Disabled";
    	
    	templates.setCustomerDBName(customerDBName.getText());
    	templates.setIsRenualChecked(selected);
    	
    	pull.setCARDID(dragFields.get(0).getText());
    	pull.setCONTACT_NAME(dragFields.get(1).getText());
    	pull.setNEW_DELIVERY_ADRES(dragFields.get(2).getText());
    	pull.setPULLDESCRIPTION(dragFields.get(3).getText());
    	pull.setSPECIAL_INSTRUCTIONS(dragFields.get(4).getText());
         	
   	 
   	  	obsList =	inputTable1.getItems();
   	  	int sizeOfFromInputColumn = obsList.size();
    	 
 	 
   	  	for(int i =0; i< sizeOfFromInputColumn; i++) {
   	  		reportMap.put(zPalcaColumn.getCellData(i),fromInputColumn.getCellData(i) );
   	  	}
    	  	
    	templates.setPull(new ArrayList<Pull>());
    	templates.getPull().add(pull);
    	templates.setReport(reportMap);
  	
    	JAXBContext context;
		try {
			context = JAXBContext.newInstance(Templates.class);
	    	Marshaller m = context.createMarshaller();
	    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    	m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    	m.marshal(templates, writer);
		} catch (JAXBException e) { 
			System.out.println("Can't save the file!!");
			e.printStackTrace();
		}

    	writer.flush();
    	writer.close();

    }
	
	@FXML
	private void closeButtonAction() {
		Platform.exit();
		System.exit(0);
	}
	

    @FXML
    void handleDragDetection(MouseEvent event) {
    	String headerName;
    	headerName = inputFileHeader.getSelectionModel().getSelectedItem().getHeaderName();
    	Dragboard db = inputFileHeader.startDragAndDrop(TransferMode.ANY);
    	ClipboardContent content = new ClipboardContent();
    	content.putString(headerName);//idx.toString());
    	db.setContent(content);

    	event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
    	event.acceptTransferModes(TransferMode.ANY);
    	event.consume();
    }

    @FXML
    void textBoxDroppedHandle(DragEvent event) {
    	System.out.println("DragDropped");
    	String text = "" ;
    	event.acceptTransferModes(TransferMode.ANY);
    	Dragboard db = event.getDragboard();
    	TextField temp = (TextField)event.getGestureTarget();
    	boolean success = false;
    	if (db.hasString()) { 		
    		if(!temp.getText().isEmpty()) {
    			text = temp.getText() + "|" + db.getString();
    			temp.setText(text);
    		}
    		else {
    			text = db.getString();
    			temp.setText(text);
    		}		
    	}    	
    	success = true;
    	event.setDropCompleted(success);
    	event.consume();
    }
	
	

	
	public void displayContent() {
		
		contentList.clear(); 
		
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("rawLine"));
		//tableContent.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);		
		String oneRaw;
		
		for (ArrayList<String> lines : mainFileContent) {
			oneRaw = "";
			for (String rows : lines) {
				oneRaw = oneRaw + rows;
				oneRaw = oneRaw + " ";
			}
			
			contentList.add(oneRaw);
			
		}

		contentList.remove(0);
		
		for (String content : contentList) {
			contentData.add(new FileContent(content));
			}
			
			tableContent.setItems(contentData);	
	}
	
	@FXML
	private void OpeningFile() throws IOException{
		
				
		Plik = new InputParser();
		
		mainFileContent = Plik.getFileContent();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");		

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Supported files:", new String[] {"*.txt", "*.csv", "*.xls","*.xlsx"}) );
		

		 File file = fileChooser.showOpenDialog(null);

		
         if (file != null) {
     		FilePath = file.toString();
   		 
   			Plik.ParseFile(FilePath);
   			 			
   			clearDataFromTable();
   			displayHeaders();
   			displayContent();
   												

   			
   		filePatchArea.setText(FilePath);
         } else {
        	 filePatchArea.setText(null);
        	 
         }
		
	}
	@FXML
	public void QuitPool(ActionEvent event) {
		
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	public void PreferencesAction(ActionEvent event) {
		
		
	      try{
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Preferences.fxml"));
	            Parent root1 = (Parent) fxmlLoader.load();
	            Stage stage = new Stage();
	            stage.initModality(Modality.APPLICATION_MODAL);
	            stage.initStyle(StageStyle.UNDECORATED);
	            stage.setTitle("Preferences");
	            stage.setScene(new Scene(root1));  
	            stage.show();
	          }catch (Exception e) {
	        	  
	        	  //error ze nie ma pliku properties
	        	  System.out.println("can't open pref window");
	        	  
	          }
	     
		
	}
	
	public void clearDataFromInput( ) {
		tableContent.getItems().clear();
		tableContent.refresh();
	}
	
	
	
	@FXML
	public void AboutAction(ActionEvent event) {
		
		
	      try{
	            FXMLLoader AboutLoader = new FXMLLoader(getClass().getResource("About.fxml"));
	            Parent root1 = (Parent) AboutLoader.load();
	            Stage stage = new Stage();
	            //stage.initModality(Modality.APPLICATION_MODAL);
	           // stage.initStyle(StageStyle.UNDECORATED);
	            stage.setTitle("About");
	            stage.setScene(new Scene(root1));  
	            stage.show();

	          }catch (Exception e) {
	        	  
	        	  System.out.println("can't open pref window");
	        	  
	          }
	     
		
	}


	public void clearDataFromTable() {
	    	inputFileHeader.getItems().clear();
	    	inputFileHeader.refresh();
	    	
   			tableContent.getItems().clear();
   			tableContent.refresh();
   			
   	
	}
	

	
	
}
