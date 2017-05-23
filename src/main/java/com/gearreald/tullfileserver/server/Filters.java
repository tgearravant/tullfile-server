package com.gearreald.tullfileserver.server;

import com.gearreald.tullfileserver.utils.SystemUtils;

import static spark.Spark.halt;

import spark.Filter;
import spark.Request;
import spark.Response;

public class Filters {
    public static Filter ensureLoggedIn = (Request request, Response response) -> {
    	String key = request.headers("Authorization");
    	if(!key.equals(SystemUtils.getProperty("api_key")))
    		halt(403);
    };
}
