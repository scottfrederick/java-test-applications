package controllers;

import play.api.modules.spring.Spring;

import play.*;
import play.mvc.*;

import views.html.*;
import util.Probe;

public class Application extends Controller {

	private static final String DRIVER_KEY = "db.default.driver";

	private static final String URL_KEY = "db.default.url";

	private static final String USER_KEY = "db.default.user";

	private static final String PASSWORD_KEY = "db.default.password";
 
    public static Result index() {
    	Probe probe = Spring.getBeanOfType(Probe.class);
    	String driverValue = Play.application().configuration().getString("DRIVER_KEY");
        String urlValue = Play.application().configuration().getString("URL_KEY");
        String userValue = Play.application().configuration().getString("USER_KEY");
        String passwordValue = Play.application().configuration().getString("PASSWORD_KEY");
    	
        return ok(index.render("Input arguments: " + probe.getInputArguments() + "\n" +
            "Database configuration:\n" + DRIVER_KEY + "=" + driverValue + "\n" +
            URL_KEY + "=" + urlValue + "\n" +
            USER_KEY + "=" + userValue + "\n" +
            PASSWORD_KEY + "=" + passwordValue + "\n" +
            "System properties:\n" + DRIVER_KEY + "=" + System.getProperty(DRIVER_KEY) + "\n" +
            URL_KEY + "=" + System.getProperty(URL_KEY) + "\n" +
            USER_KEY + "=" + System.getProperty(USER_KEY) + "\n" +
            PASSWORD_KEY + "=" + System.getProperty(PASSWORD_KEY)));
    }
  
}
