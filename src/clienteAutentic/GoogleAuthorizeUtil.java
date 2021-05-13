package clienteAutentic;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GoogleAuthorizeUtil {

	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);

	public static Credential autoriza() throws FileNotFoundException, IOException {
		GoogleCredential credential = GoogleCredential
				.fromStream(new FileInputStream("src/clinicaserial-45b7851faf2d.json"))
				.createScoped(SCOPES);
		return credential;
	}
}
