package com.ce.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.ce.rest.processor.FolderTransfer;

@Path("/transferFolder")
public class TransferFolderService {
 
  @GET
  @Path("/path")
  public Response getStatus(
      @QueryParam("from") String from,
      @QueryParam("fromPath") String fromPath,
      @QueryParam("to") String to,
      @QueryParam("toPath") String toPath) {
 
    FolderTransfer folderTransfer = new FolderTransfer();
    boolean stat = folderTransfer.transferFolder(from, fromPath, to, toPath);
    
    if(stat) {
      return Response.status(200).entity("Directory structure created.").build();
    }
 
    return Response.status(200).entity("Some issue came in directory creation.").build();
 
  }
 
}