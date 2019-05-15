package com.ce.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.ce.rest.Util.Constants;
import com.ce.rest.processor.UploadFile;

@Path("/uploadFile")
public class UploadFileService {
 
  @GET
  @Path("/path")
  public Response getStatus(
      @QueryParam("fromPath") String fromPath,
      @QueryParam("to") String to,
      @QueryParam("toPath") String toPath) {
 
    String toToken = to.equalsIgnoreCase( "googledrive" ) ? Constants.GOOGLE_DRIVE_TOKEN :Constants.ONE_DRIVE_TOKEN; 
    UploadFile uploadFile = new UploadFile();
    boolean stat = uploadFile.upload( toToken, fromPath, toPath );
    
    if(stat) {
      return Response.status(200).entity("File transered.: ").build();
    }
 
    return Response.status(200).entity("File not transered.: ").build();
 
  }
 
}