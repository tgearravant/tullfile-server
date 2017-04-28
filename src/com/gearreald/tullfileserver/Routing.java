package com.gearreald.tullfileserver;

import static spark.Spark.*;

import com.gearreald.tullfileserver.controllers.AuthController;

public final class Routing {
	public final static String INDEX = "/";
	public static void setRouting(){
		getRouting();
		postRouting();
		headRouting();
		
	}
	private static void getRouting(){
		
	}
	private static void postRouting(){
		
	}
	private static void headRouting(){
		head("*",AuthController.checkKey);
	}
}
