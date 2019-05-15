package com.ce.rest.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utillity
{
  public static String createTempFolder()
  {
    String tempPath = System.getProperty( "java.io.tmpdir" );
    tempPath += File.separator + "tempForTransfer\\";

//    System.out.println( tempPath );

    Path path = Paths.get( tempPath );

    try
    {
      deleteLocalFile( tempPath );
      Files.createDirectories( path );
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }

    return tempPath;
  }

  private static void deleteLocalFile( String path ) throws IOException
  {

    File file = new File( path );
    for( File childFile : file.listFiles() )
    {

      if( childFile.isDirectory() )
      {
        deleteLocalFile( childFile.getPath() );
      }
      else
      {
        if( !childFile.delete() )
        { throw new IOException(); }
      }
    }

    if( !file.delete() )
    { throw new IOException(); }
  }
}
