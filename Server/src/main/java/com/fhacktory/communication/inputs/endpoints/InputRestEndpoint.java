package com.fhacktory.communication.inputs.endpoints;

import com.fhacktory.communication.inputs.dtos.AudioSignalDto;
import com.fhacktory.processor.AudioMessageProcessor;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by farid on 14/05/2017.
 */
@Path("/input")
public class InputRestEndpoint {

    @POST
    @Path("/{uuid}")
    @Consumes("application/json")
    public Response postNewCommand(@PathParam("uuid") String uuid, AudioSignalDto audioSignalDto) {
        AudioMessageProcessor.getInstance().onAudioMessageReceived(uuid, audioSignalDto.getSignal());
        return Response.ok().build();
    }

}
