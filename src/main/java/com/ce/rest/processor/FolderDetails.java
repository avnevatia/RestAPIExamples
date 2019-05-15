package com.ce.rest.processor;

public class FolderDetails
{
  private String path;
  private boolean isDirectory;
  
  public FolderDetails(String path, boolean isDirectory) {
    this.path = path;
    this.isDirectory = isDirectory;
  }
  
  public String getPath()
  {
    return path;
  }

  public boolean isDirectory()
  {
    return isDirectory;
  }
  
}
