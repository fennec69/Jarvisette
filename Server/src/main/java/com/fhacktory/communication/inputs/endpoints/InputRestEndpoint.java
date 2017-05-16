package com.fhacktory.communication.inputs.endpoints;

import com.fhacktory.communication.inputs.dtos.AudioSignalDto;
import com.fhacktory.processor.inputs.AudioMessageProcessor;

import javax.ws.rs.*;
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

    @GET
    @Path("/")
    @Produces("application/json")
    public Response ping() {
        return Response.ok().entity(new AudioSignalDto()).build();
    }

}
