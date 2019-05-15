package com.ce.rest.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class MultipartUtility
{
  private final String boundary;
  private HttpsURLConnection httpsConn;
  private OutputStream outputStream;
  private PrintWriter writer;

  /**
   * This constructor initializes a new HTTP POST request with content type is set to multipart/form-data
   *
   * @param requestURL
   * @param charset
   * @throws IOException
   */
  public MultipartUtility( String requestURL, String charset, String authorization ) throws IOException
  {
    // creates a unique boundary based on time stamp
    boundary = "===" + System.currentTimeMillis() + "===";
    URL url = new URL( requestURL );
    httpsConn = (HttpsURLConnection) url.openConnection();
    httpsConn.setRequestProperty( "authorization", authorization );
    httpsConn.setUseCaches( false );
    httpsConn.setDoOutput( true ); // indicates POST method
    httpsConn.setDoInput( true );
    httpsConn.setRequestProperty( "Content-Type", "multipart/form-data; boundary=" + boundary );
    outputStream = httpsConn.getOutputStream();
    writer = new PrintWriter( new OutputStreamWriter( outputStream, charset ), true );
  }

  /**
   * Adds a upload file section to the request
   *
   * @param fieldName
   *          name attribute in <input type="file" name="..." />
   * @param uploadFile
   *          a File to be uploaded
   * @throws IOException
   */
  public void addFilePart( String fieldName, File uploadFile ) throws IOException
  {
    String fileName = uploadFile.getName();
    writer.append( "--" + boundary ).append( Constants.LINE_FEED );
    writer.append( "Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"" ).append( Constants.LINE_FEED );
    writer.append( "Content-Type: " + URLConnection.guessContentTypeFromName( fileName ) ).append( Constants.LINE_FEED );
    writer.append( "Content-Transfer-Encoding: binary" ).append( Constants.LINE_FEED );
    writer.append( Constants.LINE_FEED );
    writer.flush();

    FileInputStream inputStream = new FileInputStream( uploadFile );
    byte[] buffer = new byte[4096];
    int bytesRead = -1;
    while( (bytesRead = inputStream.read( buffer )) != -1 )
    {
      outputStream.write( buffer, 0, bytesRead );
    }
    outputStream.flush();
    inputStream.close();
    writer.append( Constants.LINE_FEED );
    writer.flush();
  }

  /**
   * Completes the request and receives response from the server.
   *
   * @return a list of Strings as response in case the server returned status OK, otherwise an exception is thrown.
   * @throws IOException
   */
  public int finish() throws IOException
  {
    writer.append( Constants.LINE_FEED ).flush();
    writer.append( "--" + boundary + "--" ).append( Constants.LINE_FEED );
    writer.close();
    outputStream.close();

    // checks server's status code first
    int status = httpsConn.getResponseCode();
    if( status == HttpsURLConnection.HTTP_OK )
    {
//      BufferedReader reader = new BufferedReader( new InputStreamReader( httpsConn.getInputStream() ) );
//      String line = null;
//      while( (line = reader.readLine()) != null )
//      {
//        System.out.println( line );
//      }
//      reader.close();
    }
    else
    {
      throw new IOException( "Server returned non-OK status: " + status );
    }
    
    httpsConn.disconnect();
    return status;
  }
  
}