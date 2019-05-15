package com.ce.rest.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CreateDirectoryStructure
{

  public boolean createInCloud( String requestURL, String authorization, String path ) throws Exception
  {
    URL url = new URL( requestURL );
    HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
    httpsConn.setRequestProperty( "authorization", authorization );
    httpsConn.setDoOutput( true ); // indicates POST method
    httpsConn.setRequestProperty( "Content-Type", "application/json; utf-8" );
    httpsConn.setRequestProperty( "Accept", "application/json" );

    String jsonInputString = "{\"path\":\"" + path + "\"}";

    OutputStream os = httpsConn.getOutputStream();
    byte[] input = jsonInputString.getBytes( "utf-8" );
    os.write( input, 0, input.length );
    os.close();

//    BufferedReader reader;
    int responseCode = httpsConn.getResponseCode();
    if( responseCode == HttpsURLConnection.HTTP_OK )
    {
//      reader = new BufferedReader( new InputStreamReader( httpsConn.getInputStream() ) );
//
//      StringBuilder result = new StringBuilder();
//      String line;
//
//      while( (line = reader.readLine()) != null )
//      {
//        result.append( line );
//      }
//      System.out.println( result.toString() );
//      reader.close();
      httpsConn.disconnect();
      return true;

    }
    else
    {
      System.out.println( "Something went wrong. Server replied HTTP code: " + responseCode );
    }
    
    httpsConn.disconnect();
    return false;
  }

}
