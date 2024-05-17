package it.texo.desafio.pages;

import it.texo.desafio.fixtures.elementos.Elemento;
import it.texo.desafio.fixtures.pageobject.PaginaWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PaginaJsonArray extends PaginaWeb {

    public PaginaJsonArray(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean estaPronto() {
        return jsonFormatterContainer().estaVisivel();
    }

    public Elemento jsonFormatterContainer() {
        return body().findElement(By.cssSelector(".json-formatter-container"));
    }

    public Elemento pre() {
        return body().findElement(By.cssSelector("pre"));
    }

}
