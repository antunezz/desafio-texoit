package it.texo.desafio.fixtures.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Javascript {
    
    private RemoteWebDriver driver;
    private boolean debug;
    
    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Javascript(RemoteWebDriver driver) {
        this.driver = driver;
    }
    
	public Object executeScript(String script, Object...objects) {
		if (debug) {
		    System.out.println(script);
		    System.out.println(objects);
		}
	    return ((JavascriptExecutor)driver).executeScript(script, objects);
	}
	
	public Object executeAsyncScript(String script, Object...objects) {
        if (debug) {
            System.out.println(script);
            System.out.println(objects);
        }
		return ((JavascriptExecutor)driver).executeAsyncScript(script, objects);
	}
	
}
