package it.texo.desafio.fixtures.pageobject;

import it.texo.desafio.fixtures.elementos.Elemento;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Um page object carregável para uso em Web.
 * 
 *
 */
public abstract class PaginaWeb extends Carregavel {

    protected RemoteWebDriver driver;
    
	public PaginaWeb(RemoteWebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public Elemento body() {
		return Elemento.com(driver.findElement(By.cssSelector("body")));
	}
	
}
