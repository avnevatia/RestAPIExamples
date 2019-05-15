package com.ce.rest.processor;

import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ce.rest.Util.Constants;

public class FolderTransfer
{
  /**
   * Transfers a directory structure including files from GoogleDrive/OneDrive to GoogleDrive/OneDrive,
   * but before transfer a local copy of only file is created.
   * 
   * @param from:
   *          googledrive/onedrive value need to be given based on from where folder need to be be fetched
   * @param fromPath:
   *          the path of drive with file name. /Test
   * @param to:
   *          googledrive/onedrive value need to be given based on to where folder need to be uploaded
   * @param toPath:
   *          the path of drive to upload. /googledrive
   * @return
   */
  public boolean transferFolder( String from, String fromPath, String to, String toPath )
  {
    String fromToken = from.equalsIgnoreCase( "googledrive" ) ? Constants.GOOGLE_DRIVE_TOKEN : Constants.ONE_DRIVE_TOKEN;
    String toToken = to.equalsIgnoreCase( "googledrive" ) ? Constants.GOOGLE_DRIVE_TOKEN : Constants.ONE_DRIVE_TOKEN;
    
    Boolean isDirectory;
    String folderPath;
    JSONObject jObj;
    FolderDetails tempFD;

    try
    {
      LinkedList<FolderDetails> directoryList = new LinkedList<>();
      directoryList.add( new FolderDetails( fromPath, true ) );

      for( int i = 0; i < directoryList.size(); i++ )
      {
        tempFD = directoryList.get( i );
        if( tempFD.isDirectory() )
        {
          JSONArray jArray = new FetchDirectoryStructure().fetch( Constants.ELEMENTS_GET_FOLDER_URL + tempFD.getPath(), fromToken );

          for( int j = 0; j < jArray.size(); j++ )
          {
            jObj = (JSONObject) jArray.get( j );
            folderPath = (String) jObj.get( "path" );
            isDirectory = (Boolean) jObj.get( "directory" );
//            System.out.println( folderPath + " ## " + isDirectory );
            directoryList.add( new FolderDetails( folderPath, isDirectory ) );
          }
        }
      }
      
      for( FolderDetails directory : directoryList )
      {
        if( directory.isDirectory() )
        {
//          System.out.println( directory.getPath() + " ## " + directory.isDirectory() );
          new CreateDirectoryStructure().createInCloud( Constants.ELEMENTS_POST_FOLDER_URL, toToken, toPath + directory.getPath() );
        }
      }

      //Using FileTransfer for transfer of files
      FileTransfer fileTransfer = new FileTransfer();
      for( FolderDetails directory : directoryList )
      {
        if( !directory.isDirectory() )
        {
//          System.out.println( directory.getPath() + " ## " + directory.isDirectory() );
          fileTransfer.transferFile( from, directory.getPath(), to, toPath + directory.getPath() );
        }
      }

      return true;
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }

    return false;

  }

}
