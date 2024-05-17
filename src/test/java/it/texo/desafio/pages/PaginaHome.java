package it.texo.desafio.pages;

import it.texo.desafio.fixtures.elementos.Elemento;
import it.texo.desafio.fixtures.pageobject.PaginaWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PaginaHome extends PaginaWeb {

    public PaginaHome(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean estaPronto() {
        return titulo().estaVisivel();
    }

    public Elemento titulo() {
        return body().findElement(By.xpath("//span[text()='{JSON} Placeholder']"));
    }

    public Elemento menu(String texto) {
        return body().findElement(By.xpath("//a[text()='" + texto + "']"));
    }

}
