package com.ce.rest.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class FetchDirectoryStructure
{

  public JSONArray fetch(String requestURL, String authorization) throws Exception
  {
    URL url = new URL( requestURL );
    HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
    httpsConn.setRequestProperty( "authorization", authorization );
    httpsConn.setRequestProperty( "accept", "application/json" );
    httpsConn.setDoOutput( true );
    httpsConn.setRequestMethod( "GET" );

    BufferedReader reader;
    int responseCode = httpsConn.getResponseCode();
    if( responseCode == HttpsURLConnection.HTTP_OK )
    {
      reader = new BufferedReader( new InputStreamReader( httpsConn.getInputStream() ) );

      StringBuilder result = new StringBuilder();
      String line;

      while( (line = reader.readLine()) != null )
      {
        result.append( line );
      }
//      System.out.println( result.toString() );
      JSONParser parser = new JSONParser();
      JSONArray jArray = (JSONArray) parser.parse( result.toString() );
      
      httpsConn.disconnect();
      reader.close();
      
      return jArray;
    }
    else
    {
      System.out.println( "Something went wrong. Server replied HTTP code: " + responseCode );
    }
    httpsConn.disconnect();

    return null;

  }

}
