package com.dto;


import com.dualfual.google.Geocode;

import java.util.HashMap;
import java.util.Map;

public class ConEdRegion {

    private String _ZONE;
    private String _NORTH;
    private String _SOUTH;
    private String _EAST;
    private String _WEST;

    private Map<String, Geocode> border = new HashMap<>();

    public ConEdRegion(String _ZONE, String _NORTH, String _SOUTH, String _EAST, String _WEST, Map<String, Geocode> border) {
        this._ZONE = _ZONE;
        this._NORTH = _NORTH;
        this._SOUTH = _SOUTH;
        this._EAST = _EAST;
        this._WEST = _WEST;
        this.border = border;
    }

    public String get_ZONE() {
        return _ZONE;
    }

    public String get_NORTH() {
        return _NORTH;
    }

    public String get_SOUTH() {
        return _SOUTH;
    }

    public String get_EAST() {
        return _EAST;
    }

    public String get_WEST() {
        return _WEST;
    }

    public Map<String, Geocode> getBorder() {
        return border;
    }
}
