package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class InputParser {

	private ArrayList<ArrayList<String>> FileContent = new ArrayList<ArrayList<String>>();

	public ArrayList<ArrayList<String>> getFileContent() {
		return FileContent;
	}
	private String FileName;
	
	public String getFileName() {
		return FileName;
	}
	
	public Boolean getIsHeader() {
		return isHeader;
	}

	public void setIsHeader(Boolean isHeader) {
		this.isHeader = isHeader;
	}

	private Boolean isHeader;

	public InputParser() {

		// zrobic jakis check box do wyboru czy jest header czy nie ma
		isHeader = true;

	}

	public ArrayList<ArrayList<String>> ParseFile(String File) throws IOException {
		
		this.FileName = File.substring(File.lastIndexOf("\\") + 1);
		
		String fileType = File.substring(File.lastIndexOf(".") + 1);

		switch (fileType.toUpperCase()) {

		case "TXT":
			FileContent = parseTxt(File);
			break;
		case "CSV":
			FileContent = ParseCsv(File);
			break;
		case "XLSX":
			FileContent = ParseXlsx(File);
			break;
		case "XLS":
			FileContent = Parsexls(File);
			break;
		default:
			System.out.println("pliku nie jest obslugiwany");
			// tutaj wywolac nowe okno z exceptionem, ze foramt pliku nie jest obslugiwany
		}

		return FileContent;
	}

	private ArrayList<ArrayList<String>> ParseCsv(String File) throws IOException {

		BufferedReader reader = null;
		// ArrayList will contain all file data
		ArrayList<String> SingleLine = new ArrayList<String>();

		String line = "";

		try {

			reader = new BufferedReader(new FileReader(File));

			// iterate over file lines
			while ((line = reader.readLine()) != null) {

				String TempLine = "";
				// if semicolon exist, changes them to comma
				TempLine = line.replaceAll(";", ",");
				// Adds file content to Array
				String[] items = TempLine.split(",");
				SingleLine = new ArrayList<String>(Arrays.asList(items));

				// each line will be added as new array
				FileContent.add(SingleLine);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {

			// closing reader
			reader.close();

		}

		return FileContent;
	}

	private ArrayList<ArrayList<String>> ParseXlsx(String File) {

		try {

			// Creating a Workbook from an Excel file (.xlsx)
			Workbook workbook = WorkbookFactory.create(new FileInputStream(File));

			Sheet sheet = workbook.getSheetAt(0);

			// we iterate on rows
			Iterator<Row> rowIt = sheet.iterator();

			// iterate whole row
			while (rowIt.hasNext()) {
				Row row = rowIt.next();

				// iterate on cells for the current row
				Iterator<Cell> cellIterator = row.cellIterator();
				ArrayList<String> SingleRowContent = new ArrayList<String>();

				// iterate cell by cell
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// SingleRowContent.add(cell.toString() + ";");

					// reading cells depending on cell content value
					switch (cell.getCellTypeEnum()) {

					// for numeric values
					case NUMERIC: {

						// Using HSSFDateUtil to check if a cell contains a date.
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

							SingleRowContent.add(sdf.format(cell.getDateCellValue()).trim());
							// typical numeric values
						} else {
							String value = "";
							value = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)
									.toPlainString();
							// System.out.print(value + ";");

							SingleRowContent.add(value.trim());
						}

						break;
					}
					// typical string cell
					case STRING:
						String Temp = cell.getRichStringCellValue().toString().replace('\r', ' ').replace('\n', ' ');
						// System.out.print(cell.getCellTypeEnum()+"_"+Temp + "|");
						SingleRowContent.add(Temp.trim());
						break;
					// empty cell

					case BLANK:
						SingleRowContent.add("");
						break;

					// Unknown cell type
					default:
						break;
					// SingleRowContent.add("");
					}
					// -------------------End of switch------------------------------\\

				}
				// -------------------End of cell iteration------------------------------\\

				int emptycells = 0;
				int RowSize = SingleRowContent.size();
				// check if whole row is containing empty strings
				for (int i = 0; i < SingleRowContent.size(); i++) {
					if (SingleRowContent.get(i) == null || SingleRowContent.get(i).isEmpty()) {
						emptycells++;
					} else
						continue;

				}

				// System.out.println("row size: "+RowSize+" emptycells: "+emptycells);

				// if whole row is empty skip it
				if (emptycells == RowSize)
					continue;
				// if not empty add to FileContent
				else {
					// System.out.println();
					FileContent.add(SingleRowContent);
				}

			}
			// -------------------End of row iteration------------------------------\\

			// closing file stream
			workbook.close();

			// exception handling
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return FileContent;

	}

	private ArrayList<ArrayList<String>> Parsexls(String File) {

		try {

			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(File));
			HSSFSheet sheet = workbook.getSheetAt(0);

			// iterate on rows
			Iterator<Row> rowIt = sheet.iterator();

			// iterate whole row
			while (rowIt.hasNext()) {
				HSSFRow row = (HSSFRow) rowIt.next();

				// iterate on cells for the current row
				Iterator<Cell> cellIterator = row.cellIterator();

				ArrayList<String> SingleRowContent = new ArrayList<String>();

				// iterate cell by cell
				while (cellIterator.hasNext()) {

					HSSFCell cell = (HSSFCell) cellIterator.next();

					// SingleRowContent.add(cell.toString() + ";");

					// reading cells depending on cell content value
					switch (cell.getCellTypeEnum()) {

					// for numeric values
					case NUMERIC: {

						// Using HSSFDateUtil to check if a cell contains a date.
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

							SingleRowContent.add(sdf.format(cell.getDateCellValue()).trim());

							// typical numeric values
						} else {
							String value = "";
							value = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)
									.toPlainString().trim();
							// System.out.print(value + ";");

							SingleRowContent.add(value.trim());
						}

						break;
					}
					// typical string cell
					case STRING:

						String Temp = cell.getRichStringCellValue().toString().replace('\r', ' ').replace('\n', ' ');
						// System.out.print(cell.getCellTypeEnum()+"_"+Temp + "|");
						SingleRowContent.add(Temp.trim());
						break;

					// empty cell

					case BLANK:
						SingleRowContent.add("");
						break;

					// Unknown cell type
					default:
						break;
					// SingleRowContent.add("");
					}
					// -------------------End of switch------------------------------\\

				}
				// -------------------End of cell iteration------------------------------\\

				int emptycells = 0;
				int RowSize = SingleRowContent.size();
				// check if whole row is containing empty strings
				for (int i = 0; i < SingleRowContent.size(); i++) {
					if (SingleRowContent.get(i) == null || SingleRowContent.get(i).isEmpty()) {
						emptycells++;
					} else
						continue;

				}

				// System.out.println("row size: "+RowSize+" emptycells: "+emptycells);

				// if whole row is empty skip it
				if (emptycells == RowSize)
					continue;
				// if not empty add to FileContent
				else {
					// System.out.println();
					FileContent.add(SingleRowContent);
				}

			}
			// -------------------End of row iteration------------------------------\\

			// closing file stream
			workbook.close();

			// exception handling
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return FileContent;

	}

	public void printFileContent() {

		for (ArrayList<String> lines : FileContent) {
			for (String rows : lines) {
				System.out.print(rows + "\t");
			}
			System.out.print("\n");
		}

	}

public ArrayList<ArrayList<String>> parseTxt(String file) throws IOException {

	ArrayList<String> headerLine = new ArrayList<String>();
	headerLine.add("IBAN");
	headerLine.add("Imie");
	headerLine.add("Nazwisko");

	ArrayList<String> singleLine = new ArrayList<String>();
	ArrayList<String> iban =new ArrayList<String>();
	String line = "";
	BufferedReader reader = null;
	Matcher match = null;
	Pattern pattern = Pattern.compile("\\S{4} \\S{4} \\S{4} \\S{4} \\S*[Aa¥¹BbCcÆæDdEeÊêFfGgHhIiJjKkLl£³MmNnÑñOoÓóPpRrSsŒœTtUuWwYyZz�Ÿ¯¿]* \\S*[Aa¥¹BbCcÆæDdEeÊêFfGgHhIiJjKkLl£³MmNnÑñOoÓóPpRrSsŒœTtUuWwYyZz�Ÿ¯¿]*");

    try {
    	reader = new BufferedReader(new FileReader(file));
    	FileContent.add(headerLine);
    	while ((line = reader.readLine()) != null) {
//        	String[] items = line.split(" ");
//        	singleLine = new ArrayList<String>(Arrays.asList(line.split(" ")));

        	match = pattern.matcher(line);
        	System.out.println(line);
        	while(match.find()) {
//        		String thisLine = match.group();
//        		System.out.println(thisLine);
        		singleLine = new ArrayList<String>(Arrays.asList(match.group().split(" ").toString()));
        		iban = singleLine;
        		FileContent.add(new ArrayList<String>(Arrays.asList(match.group())));
        	}
//            FileContent.add(singleLine);
        }


    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
    	reader.close();
    }
    return FileContent;
}
}
