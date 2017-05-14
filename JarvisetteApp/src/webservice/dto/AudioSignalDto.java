package webservice.dto;

/**
 * Created by Frederic on 13/05/2017.
 */

public class AudioSignalDto {

    private String signal;

    public AudioSignalDto(String signal) {
        this.signal = signal;
    }


    public String getSignal(){
        return signal;
    }
}
