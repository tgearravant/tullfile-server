package com.gearreald.tullfileserver.controllers;

import spark.Request;
import spark.Response;
import spark.Route;

public class AuthController {
	public static Route checkKey = (Request request, Response response) -> {
		String key = request.headers("Authorization");
		System.out.println(key);
		if(!key.equals("lol"))
			response.status(403);
		else
			response.status(200);
		return "";
	};
}
