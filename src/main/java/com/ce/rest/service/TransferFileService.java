package com.ce.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.ce.rest.processor.FileTransfer;

/**
 * TransferFileService is used to transfer a file from one cloud service to other.
 *
 */
@Path("/transferFiles")
public class TransferFileService {
 
  @GET
  @Path("/path")
  public Response getStatus(
      @QueryParam("from") String from,
      @QueryParam("fromPath") String fromPath,
      @QueryParam("to") String to,
      @QueryParam("toPath") String toPath) {
 
    FileTransfer fileTransfer = new FileTransfer();
    boolean stat = fileTransfer.transferFile(from, fromPath, to, toPath);
    
    if(stat) {
      return Response.status(200).entity("File transered.: ").build();
    }
 
    return Response.status(200).entity("File not transered.: ").build();
 
  }
 
}