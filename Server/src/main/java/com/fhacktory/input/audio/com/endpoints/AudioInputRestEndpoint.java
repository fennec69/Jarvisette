package com.fhacktory.input.audio.com.endpoints;

import com.fhacktory.input.audio.com.dtos.AudioSignalDto;
import com.fhacktory.input.audio.AudioMessageProcessor;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by farid on 14/05/2017.
 */
@Path("/input/audio")
public class AudioInputRestEndpoint {

    @Inject
    private AudioMessageProcessor mAudioMessageProcessor;

    @POST
    @Path("/{uuid}")
    @Consumes("application/json")
    public Response postNewCommand(@PathParam("uuid") String uuid, AudioSignalDto audioSignalDto) {
        mAudioMessageProcessor.onAudioMessageReceived(uuid, audioSignalDto.getSignal());
        return Response.ok().build();
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response ping() {
        return Response.ok().entity(new AudioSignalDto()).build();
    }

}
