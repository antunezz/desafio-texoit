package it.texo.desafio.fixtures.util;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

public class Espera {
    
    private RemoteWebDriver driver;
    
    public Espera(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public static void fixa(int segundos) {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(segundos));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
   public static void fixa(float segundos) {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofMillis((long) (segundos * 1000)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	public void dormir(int segundos) {
	    Espera.fixa(segundos);
	}

    public void dormir(float segundos) {
	    Espera.fixa(segundos);
	}
	
    public FluentWait<WebDriver> aguardarCondicao(int timeout) {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .ignoring(Exception.class);
    }
    
    public <V> V aguardarCondicao(Function<? super WebDriver, V> isTrue, int timeout, String mensagem) {
        return aguardarCondicao(timeout).withMessage(mensagem).until(isTrue);
    }
    
    public <V> V aguardarCondicao(Function<? super WebDriver, V> isTrue, int timeout) {
        return aguardarCondicao(timeout).until(isTrue);
    }
    
}
