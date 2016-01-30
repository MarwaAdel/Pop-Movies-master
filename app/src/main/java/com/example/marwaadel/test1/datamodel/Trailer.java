package com.example.marwaadel.test1.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marwa Adel on 1/13/2016.
 */
public class Trailer {



    private String id;
    private String key;
    private String name;
    private String site;
    private String type;

    public Trailer(String id, String key, String name, String site, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    public Trailer(JSONObject trailer)  throws JSONException {
        this.id = trailer.getString("id");
        this.key = trailer.getString("key");
        this.name = trailer.getString("name");
        this.site = trailer.getString("site");
        this.type = trailer.getString("type");
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}