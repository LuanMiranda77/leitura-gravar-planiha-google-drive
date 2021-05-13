package clienteAutentic;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.CopyPasteRequest;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;


public class GoogleSheetsLiveTest {

	private static Sheets sheetsService;
	private static String SPREADSHEET_ID = "106ipARcxuj2ZFl73g6Y-7swb3wDw0Hbv75tr8VOyaoc";

	    public static void setup() throws GeneralSecurityException, IOException {
	        sheetsService = SheetsServiceUtil.getSheetsService();
	        
	    }

	    public static void Gravar_Unick_Intevalo() throws IOException {
	        ValueRange body = new ValueRange().setValues(Arrays.asList(
	        		Arrays.asList("Expenses January"),
	        		Arrays.asList("books", "30"), 
	        		Arrays.asList("pens", "10"), 
	        		Arrays.asList("Expenses February"), 
	        		Arrays.asList("clothes", "20"), 
	        		Arrays.asList("shoes", "5")));
	        UpdateValuesResponse result = sheetsService.spreadsheets().values().update(SPREADSHEET_ID, "A2", body)
	        		.setValueInputOption("RAW")
	        		.execute();
	    }
	    
	    public static void GravarDadosPlanilha() throws IOException {

	        List<ValueRange> data = new ArrayList<>();
	        data.add(new ValueRange().setRange("D1").setValues(Arrays.asList(Arrays.asList("January Total", "=B2+B3"))));
	        data.add(new ValueRange().setRange("D4").setValues(Arrays.asList(Arrays.asList("February Total", "=B5+B6"))));

	        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest().setValueInputOption("USER_ENTERED").setData(data);
	        BatchUpdateValuesResponse batchResult = sheetsService.spreadsheets().values().batchUpdate(SPREADSHEET_ID, batchBody).execute();

	        List<String> ranges = Arrays.asList("E1", "E4");
	        BatchGetValuesResponse readResult = sheetsService.spreadsheets().values().batchGet(SPREADSHEET_ID).setRanges(ranges).execute();
	        
	       
	    }
	    public  static void GravarDadosFinal(String cnpj,String est,String razao,String fantazia) throws IOException {
	    	 ValueRange appendBody = new ValueRange().setValues(Arrays.asList(
	        		    Arrays.asList(cnpj,est,razao,fantazia)));
	        		AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
	        		  .append(SPREADSHEET_ID, "A1", appendBody)
	        		  .setValueInputOption("USER_ENTERED")
	        		  .setInsertDataOption("INSERT_ROWS")
	        		  .setIncludeValuesInResponse(true)
	        		  .execute();

	        		//ValueRange total = appendResult.getUpdates().getUpdatedData();
	        		//assertThat(total.getValues().get(0).get(1)).isEqualTo("65");
	    }
	    
	    public static void LerDadosPlanilha(String cnpj) throws IOException {
	 
	    	
	    	List<String> ranges = Arrays.asList("A2:Q1000");
	    	
	    	BatchGetValuesResponse readResult = sheetsService.spreadsheets().values().batchGet(SPREADSHEET_ID).setRanges(ranges).execute();
	    	List<List<Object>> lista = readResult.getValueRanges().get(0).getValues();
	    	
	    	for(int i=0; i<lista.size();i++) {
	    		
	    		if(cnpj.equals(lista.get(i).get(0))) {
	    			System.out.println("empresa :"
						    					+lista.get(i).get(0)
						    					+lista.get(i).get(1)
						    					+lista.get(i).get(2)
						    					+lista.get(i).get(3)
						    					+lista.get(i).get(4)
						    					+lista.get(i).get(5)
						    					+lista.get(i).get(6)
						    					+lista.get(i).get(7)
						    					+lista.get(i).get(8)
						    					+lista.get(i).get(9)
						    					+lista.get(i).get(10)
						    					+lista.get(i).get(11)
						    					+lista.get(i).get(12)
						    					+lista.get(i).get(13)
						    					+lista.get(i).get(14)
						    					+lista.get(i).get(15)
						    					+lista.get(i).get(16)
	    										);
	    		}
	    	}
	    		
       
	    }

	    @Test
	    public void whenUpdateSpreadSheetTitle_thenOk() throws IOException {

	        UpdateSpreadsheetPropertiesRequest updateRequest = new UpdateSpreadsheetPropertiesRequest().setFields("*").setProperties(new SpreadsheetProperties().setTitle("Expenses"));

	        CopyPasteRequest copyRequest = new CopyPasteRequest().setSource(new GridRange().setSheetId(0).setStartColumnIndex(0).setEndColumnIndex(2).setStartRowIndex(0).setEndRowIndex(1))
	                .setDestination(new GridRange().setSheetId(1).setStartColumnIndex(0).setEndColumnIndex(2).setStartRowIndex(0).setEndRowIndex(1)).setPasteType("PASTE_VALUES");

	        List<Request> requests = new ArrayList<>();

	        requests.add(new Request().setCopyPaste(copyRequest));
	        requests.add(new Request().setUpdateSpreadsheetProperties(updateRequest));

	        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);

	        sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();
	    }

	    @Test
	    public void whenCreateSpreadSheet_thenIdOk() throws IOException {
	        Spreadsheet spreadSheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle("KEY"));
	        Spreadsheet result = sheetsService.spreadsheets().create(spreadSheet).execute();

	        //assertThat(result.getSpreadsheetId()).isNotNull();
	    }
	    public static void main(String[] args) {
			try {
				setup();
				GravarDadosFinal("151515","15454","akak-me","kuabacana");
				
				
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
