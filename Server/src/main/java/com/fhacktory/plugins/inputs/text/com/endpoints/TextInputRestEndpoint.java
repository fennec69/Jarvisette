package com.fhacktory.plugins.inputs.text.com.endpoints;

import com.fhacktory.plugins.inputs.text.TextMessageProcessor;
import com.fhacktory.plugins.inputs.text.com.dtos.TextCommandDto;
import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by farid on 22/05/2017.
 */
@Path("/input/command")
public class TextInputRestEndpoint {

    @Inject
    private TextMessageProcessor mTextMessageProcessor;

    @POST
    @Path("/{uuid}")
    @Consumes("application/json")
    public Response postNewCommand(@PathParam("uuid") String uuid, TextCommandDto textCommandDto) {
        mTextMessageProcessor.processMessage(textCommandDto, uuid);
        return Response.ok().build();
    }

}