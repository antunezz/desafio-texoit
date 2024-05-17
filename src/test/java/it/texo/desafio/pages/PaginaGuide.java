package it.texo.desafio.pages;

import it.texo.desafio.fixtures.elementos.Elemento;
import it.texo.desafio.fixtures.pageobject.PaginaWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PaginaGuide extends PaginaWeb {

    public PaginaGuide(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean estaPronto() {
        return titulo().estaVisivel();
    }

    public Elemento titulo() {
        return body().findElement(By.cssSelector("main h2"));
    }

    public Elemento photoAlbumLink(int id) {
        return body().findElement(By.linkText("/albums/" + id + "/photos"));
    }

}
