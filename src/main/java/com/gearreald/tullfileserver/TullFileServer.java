package com.gearreald.tullfileserver;

import static spark.Spark.*;

import static spark.debug.DebugScreen.*;

import com.gearreald.tullfileserver.files.TullFileSystem;
import com.gearreald.tullfileserver.server.Routing;
import com.gearreald.tullfileserver.utils.SystemUtils;

public class TullFileServer {
	public static void main(String[] args) {
    	initialConfiguration();
	}
	public static void initialConfiguration(){
		if(SystemUtils.getProperty("use_ssl").equals("true")) {
			secure(SystemUtils.getProperty("keystore_location"), SystemUtils.getProperty("keystore_password"), null, null);
		}
		if(SystemUtils.getProperty("environment").equals("development")){
			enableDebugScreen();
		}
		port(Integer.parseInt(SystemUtils.getProperty("port")));
		Routing.setRouting();
		TullFileSystem.getTFS();
	}
}
