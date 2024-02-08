package com.authenticate.controller;

import java.util.Collections;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
// API Call
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class VerifyController {

	@PostMapping("/verify")
	
	public String verifyToken(@RequestBody String idToken ){
		/**
		//verifying an ID token signature using the tokeninfo endpoint
		String res=null;
		
		try {
            // Specify the URL of the API endpoint
            String apiUrl = "https://oauth2.googleapis.com/tokeninfo";

            // Define the parameters to be sent in the GET request
            String params = "id_token=" + URLEncoder.encode(idToken, "UTF-8") ;
            // Concatenate parameters to the URL
            String urlWithParams = apiUrl + "?" + params;

            // Create a URL object
            URL url = new URL(urlWithParams);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code from the server
            int responseCode = connection.getResponseCode();
           
            // Read the response from the server
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Print the response from the server
                System.out.println("Response Code: " + responseCode);
                System.out.println("Response Data: " + response.toString());
                System.out.println("Response email"+response);
                res=response.toString();
                //===temp====
                // Create an ObjectMapper
                   ObjectMapper objectMapper = new ObjectMapper();
                   // Convert API response string to JsonNode
                   JsonNode jsonResponse = objectMapper.readTree(res);
                // Access values from the JsonNode
                   //this data can be saved in the data base after checking if the user already exists
                   String given_name = jsonResponse.get("given_name").asText();
                   System.out.println("name>>"+given_name);
                   
                   //===temp====
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return res;
		**/
		// Verifying the Google ID token on server side
		try {
			NetHttpTransport transport = new NetHttpTransport();
			GsonFactory jsonFactory = new GsonFactory();
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
			    .setAudience(Collections.singletonList("741191046977-j8iempc1nag2p03g7jultlbfeuhee4rr.apps.googleusercontent.com"))
			    .build();
			
		    GoogleIdToken idToken1 = verifier.verify(idToken);
			if(idToken1!=null ) {
		        System.out.println("Token Verified : "+idToken1);
		        return "token Verified";
			}
		
			else {
				System.out.println("TOKEN IS NULL");
			}
		}catch(Exception ex) {
		ex.printStackTrace();
		}
		
        // Return a response
        return "Token is null ";
	}
	
}
	
