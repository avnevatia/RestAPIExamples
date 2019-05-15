package com.ce.rest.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.net.ssl.HttpsURLConnection;

import com.ce.rest.Util.Constants;

public class DownloadFile
{
  private HttpsURLConnection httpsConn;
  private String fromPath;
  
  public DownloadFile( String fromPath, String authorization ) throws Exception {
    this.fromPath = fromPath;
    URL url = new URL( Constants.ELEMENTS_FILE_URL + URLEncoder.encode(fromPath, "UTF-8") );
    httpsConn = (HttpsURLConnection) url.openConnection();
    httpsConn.setRequestProperty( "authorization", authorization );
    httpsConn.setUseCaches( false );
    httpsConn.setDoOutput( true );
    httpsConn.setDoInput( true );
    httpsConn.setRequestMethod( "GET" );
  }
  
  public String download() throws Exception {
//    System.out.println( requestURL );
    
    int responseCode = httpsConn.getResponseCode();
    if( responseCode == HttpsURLConnection.HTTP_OK )
    {
      String fileName = "";
//      String disposition = httpsConn.getHeaderField( "Content-Disposition" );
//      String contentType = httpsConn.getContentType();
//      int contentLength = httpsConn.getContentLength();

      fileName = fromPath.substring( fromPath.lastIndexOf( "/" ) + 1, fromPath.length() );

//      System.out.println( "Content-Type = " + contentType );
//      System.out.println( "Content-Disposition = " + disposition );
//      System.out.println( "Content-Length = " + contentLength );
//      System.out.println( "fileName = " + fileName );

      ReadableByteChannel rbc = Channels.newChannel( httpsConn.getInputStream() );
      FileOutputStream fos = new FileOutputStream( new File( Constants.LOCAL_FILE_PATH+fileName ) );
      fos.getChannel().transferFrom( rbc, 0, Long.MAX_VALUE );
      fos.close();
      rbc.close();
      httpsConn.disconnect();

      System.out.println( "File downloaded at: " + Constants.LOCAL_FILE_PATH+fileName);
      
      return Constants.LOCAL_FILE_PATH+fileName;
    }
    else
    {
      System.out.println( "No file to download. Server replied HTTP code: " + responseCode );
    }
    
    return null;
    
  }

}
