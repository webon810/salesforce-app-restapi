package com.sb.sfdcrest.demo01.comsbsfdcrestdemo01;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//@RestController
public class SfdcRESTController {
    
    static final String USERNAME = "myname@whatevermail.com";
    //password should not include special charactor or has 404 error
    //password value is [realpassword][token]
    static final String PASSWORD = "SiyuyuiyWNMYYzucDvEDmHdGtuououio";
    
    //SFDC to create the connect app
    //under - edit policies - change IP relaxation for local test
    //static final String LOGINURL = "https://ap5.salesforce.com";
    static final String LOGINURL = "https://na51.salesforce.com";
    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
    //Consumer key
    static final String CLIENTID = "3MVG9rFJvQRVOvk7yX6hLtPqKdcB5xFqMgyMew4MzRylkbfGV8Ay2hsxhHK4SOJOE58DUvPMTa1Wxu......uiu";
    //Consumer Secret
    static final String CLIENTSECRET = "425464326052934786";
    
    private static String REST_ENDPOINT = "/services/data";
    private static String API_VERSION = "/v43.0";
    
    private static String baseUri;
    private static Header oauthHeader;
    private static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
    
    private static String leadId;
    private static String leadFirstName;
    private static String leadLastName;
    private static String leadCompany;
    
    public static void main(String[] args) {
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        //Assemble the login request url
        String loginURL= LOGINURL +
                GRANTSERVICE + 
                "&client_id=" + CLIENTID +
                "&client_secret=" + CLIENTSECRET +
                "&username=" + USERNAME +
                "&password=" + PASSWORD;

        System.out.println(loginURL);
        
        //Login request must be POST
        HttpPost httpPost = new HttpPost(loginURL);
        
        HttpResponse response = null;
        
        try{
            //Exceute the login POST request
            response = httpClient.execute(httpPost);
            
        }catch(ClientProtocolException cpException){
            cpException.printStackTrace();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
        
        final int statusCode = response.getStatusLine().getStatusCode();
        
        if(statusCode != HttpStatus.SC_OK){
            System.out.println("Error authenticating to Force.com: " + statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return;
        }
        
        String getResult = null;
        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        JSONObject jsonObject = null;
        String loginAccessToken = null;
        String loginInstanceUrl = null;

        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        //baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION ;
        //oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken) ;
        //System.out.println("oauthHeader1: " + oauthHeader);
        
        System.out.println(response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: "+loginInstanceUrl);
        System.out.println("access token/session ID: "+loginAccessToken);
        //System.out.println("baseUri: "+ baseUri);

        //release connection
        httpPost.releaseConnection();
        
        //System.out.println("hello sfdc rest api");
    }
    
    //@RequestMapping("/sfdctest01")
    //public String sfdcmain() {
      //  return "hello sfdc api test";
    //}
    
}
