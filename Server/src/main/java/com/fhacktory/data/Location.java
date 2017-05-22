package com.fhacktory.data;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by farid on 14/05/2017.
 */
public class Location {

    private Map<String, Float> mLocations;

    public Location() {
        mLocations = new TreeMap<>();
    }

    public void addLocation(String uuid, Float intensity) {
        mLocations.put(uuid, intensity);
    }

    public Map<String, Float> getLocations() {
        return mLocations;
    }

    public double matching(Location location) {
        Map<String, Float> tmpMap = new TreeMap<String, Float>(location.getLocations());
        int sum = 0;
        for(String uuid : mLocations.keySet()) {
            if(tmpMap.containsKey(uuid)) {
                sum +=Math.abs(mLocations.get(uuid) - tmpMap.get(uuid));
                tmpMap.remove(uuid);
            }
            else {
                sum += mLocations.get(uuid);
            }
        }
        int nbLocation = mLocations.size() + tmpMap.size();
        for(String uuid : tmpMap.keySet()) {
            sum += tmpMap.get(uuid);
        }
        return 100d - (sum / nbLocation);
    }
}
