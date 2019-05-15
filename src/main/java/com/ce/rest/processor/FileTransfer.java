package com.ce.rest.processor;

import com.ce.rest.Util.Constants;

public class FileTransfer
{
  /**
   * Transfers a file from GoogleDrive/OneDrive to GoogleDrive/OneDrive, but before transfer a local copy is created.
   * 
   * @param from:
   *          googledrive/onedrive value need to be given based on from where file need to be be fetched
   * @param fromPath:
   *          the path of drive with file name. /Test/Test.txt
   * @param to:
   *          googledrive/onedrive value need to be given based on to where file need to be uploaded
   * @param toPath:
   *          the path of drive to upload.
   * @return
   */
  public boolean transferFile( String from, String fromPath, String to, String toPath )
  {
    String filePath = null;
    String fromToken = from.equalsIgnoreCase( "googledrive" ) ? Constants.GOOGLE_DRIVE_TOKEN : Constants.ONE_DRIVE_TOKEN;
    String toToken = to.equalsIgnoreCase( "googledrive" ) ? Constants.GOOGLE_DRIVE_TOKEN : Constants.ONE_DRIVE_TOKEN;

    try
    {
      DownloadFile downloadFile = new DownloadFile( fromPath, fromToken );
      filePath = downloadFile.download();

      UploadFile uploadFile = new UploadFile();
      return uploadFile.upload( toToken, filePath, toPath );
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }

    return false;

  }

}
