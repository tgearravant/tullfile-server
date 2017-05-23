package com.gearreald.tullfileserver.controllers;

import org.json.JSONObject;

import com.gearreald.tullfileserver.utils.SystemUtils;

import spark.Request;
import spark.Response;
import spark.Route;

public class AuthController {
	public static Route checkKey = (Request request, Response response) -> {
		String key = request.headers("Authorization");
		String expectedKey = SystemUtils.getProperty("api_key");
		if(!key.equals(expectedKey))
			response.status(403);
		else
			response.status(200);
		JSONObject output = new JSONObject();
		output.put("authenticated", "true");
		return output;
	};
}
