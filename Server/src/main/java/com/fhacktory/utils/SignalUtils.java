package com.fhacktory.utils;

import com.google.api.client.util.Base64;

/**
 * Created by farid on 13/05/2017.
 */
public class SignalUtils {

    public static double calculateRMS(String base64EncodedSignal) {
        byte[] signal = Base64.decodeBase64(base64EncodedSignal);
        return calculateRMS(byteArrayToShortArray(signal));
    }

    public static double calculateRMS(short[] signal) {
        double sum = 0d;
        if (signal.length==0) {
            return sum;
        } else {
            for (int i=0; i<signal.length; i++) {
                sum += signal[i] * signal[i];
            }
        }
        double average = sum/signal.length;

        return Math.sqrt(average);
    }

    public static short[] byteArrayToShortArray(byte[] bytes) {
        short[] samples = new short[bytes.length/2];
        int j = 0;
        for (int i = 44; i < bytes.length; i+=2) {
            byte lo = bytes[i];
            byte hi = bytes[i+1];

            short val=(short)( ((hi&0xFF)<<8) | (lo&0xFF) );
            samples[j] = val;
            j++;
        }

        return samples;
    }
}
