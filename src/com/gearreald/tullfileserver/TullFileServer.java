package com.gearreald.tullfileserver;

import static spark.Spark.*;

import static spark.debug.DebugScreen.*;

import com.gearreald.tullfileserver.files.TullFileSystem;

public class TullFileServer {
	public static void main(String[] args) {
    	initialConfiguration();
	}
	public static void initialConfiguration(){
		enableDebugScreen();
		port(12345);
		Routing.setRouting();
		TullFileSystem.getTFS();
	}
}
