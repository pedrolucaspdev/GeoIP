import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Scanner;

import com.google.gson.Gson;

public class App {
	public static String responseError = "\nUrl Invalid or service unknown, try again..\n\n-> Url Example: www.google.com\n-> IP Example: 142.250.79.36";
	
	// Class InfoIP
	class InfoIP {
		// Variables of API
		String status, query, country, countryCode, region, regionName,city, zip, timezone;
		double lat, lon;
	}
	
	// Function Request
	public static void Request (String ip) {
		try {
			// Instance of request
			HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(new URI("http://ip-api.com/json/"+ip))
				.timeout(Duration.ofSeconds(2))
				.build();
			
			// Get a Response of request
			HttpResponse<String> response = HttpClient
					.newBuilder()
					.build()
					.send(request, BodyHandlers.ofString());
				
			// Constructs a GSON object 
			Gson gson = new Gson();
			
			// Deserializes the response variable JSON into an object of the class InfoIP.
			InfoIP info = gson.fromJson(response.body(), InfoIP.class);
	
			if(response.statusCode() != 200 || info.status.contains("fail")) {
				System.out.println(responseError);
				System.exit(0);
			}
			
			System.out.printf("\nStatus: %s\nQuery: %s\nCountry: %s\nCountry Code: %s\nRegion: %s\nRegion Name: %s\nCity: %s\nZIP: %s\nTimezone: %s\nLat: %f\nLon: %f\n", info.status, info.query, info.country, info.countryCode, info.region, info.regionName, info.city, info.zip, info.timezone, info.lat, info.lon);
			System.exit(0);
	
		} catch (Exception e) {
			System.err.println(responseError);
		}
	}

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			
			System.out.printf("IP Address or URL: ");
			String option = sc.next();
			
			sc.close();
			
			// Verify if contains protocol in URL
			if(option.isEmpty() || option.contains("http://") || option.contains("https://")) {
				System.err.println(responseError);
				System.exit(0);
			}
			
			// Call to Request function
			Request(option);
			
			
		} catch (Exception e) {
			System.err.println(responseError);
		}
	}
}
