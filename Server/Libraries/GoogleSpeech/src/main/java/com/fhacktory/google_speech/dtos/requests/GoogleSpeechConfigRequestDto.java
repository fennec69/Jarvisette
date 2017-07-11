package com.fhacktory.google_speech.dtos.requests;

/**
 * Created by farid on 13/05/2017.
 */
public class GoogleSpeechConfigRequestDto {

    private String encoding;
    private Long sampleRateHertz;
    private String languageCode;

    public GoogleSpeechConfigRequestDto(String encoding, Long sampleRateHertz, String languageCode) {
        this.encoding = encoding;
        this.sampleRateHertz = sampleRateHertz;
        this.languageCode = languageCode;
    }


}
