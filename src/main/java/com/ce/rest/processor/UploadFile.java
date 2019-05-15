package com.ce.rest.processor;

import java.io.File;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import com.ce.rest.Util.Constants;
import com.ce.rest.Util.MultipartUtility;

public class UploadFile
{
  public boolean upload( String token, String filePath, String uploadPath )
  {
    String charset = "UTF-8";
    boolean stat = false;
    
    File textFile = new File( filePath );
    try
    {
      MultipartUtility multipart = new MultipartUtility( Constants.ELEMENTS_FILE_URL + URLEncoder.encode(uploadPath, "UTF-8"), charset, token );

      multipart.addFilePart( "file", textFile );

      stat = multipart.finish() == HttpsURLConnection.HTTP_OK;
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
    
    return stat;
  }

}
