package it.texo.desafio.steps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it.texo.desafio.pages.PaginaGuide;
import it.texo.desafio.pages.PaginaHome;
import it.texo.desafio.pages.PaginaJsonArray;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

public class StepDefinitions {

    private ChromeDriver driver;
    private JsonArray albums;
    private JsonObject album;
    private PaginaHome paginaHome;
    private PaginaGuide paginaGuide;
    private PaginaJsonArray paginaJsonArray;

    @Before
    public void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);

        paginaHome = new PaginaHome((RemoteWebDriver) driver);
        paginaGuide = new PaginaGuide((RemoteWebDriver) driver);
        paginaJsonArray = new PaginaJsonArray((RemoteWebDriver) driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am at the jsonplaceholder home page")
    public void iAmAtTheJsonplaceholderHomePage() {
        driver.get("https://jsonplaceholder.typicode.com/");
        paginaHome.aguardar(10);
        capturarTela("Home");
    }

    protected void capturarTela(String descricao) {
        Allure.addAttachment(descricao, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @When("I access the {string} menu")
    public void iAccessTheMenu(String menu) {
        paginaHome.menu(menu).clicar();
    }

    @And("I select the album with id={int}")
    public void iSelectTheAlbumWithId(int id) {
        paginaGuide.aguardar(10);
        capturarTela("Página Guide carregada");
        paginaGuide.photoAlbumLink(1).scrollIntoView().clicar();
    }

    @Then("the album information is displayed as a JSON array")
    public void theInformationIsDisplayedAsAJSONArray() {
        paginaJsonArray.aguardar(5);
        String raw = paginaJsonArray.pre().obterTexto();
        try {
            Gson gson = new Gson();
            albums = gson.fromJson(raw, JsonArray.class);
        } catch(Exception e) {
            Assertions.fail("Album information is an invalid JSON");
        }
    }

    @And("the photo with id={int} is displayed")
    public void theIdIsDisplayed(int id) {
        for (JsonElement oneAlbum : albums) {
            if (id == oneAlbum.getAsJsonObject().get("id").getAsInt()) {
                album = oneAlbum.getAsJsonObject();
                break;
            }
        }
        if (album == null) {
            Assertions.fail("Album " + id + "not found");
        } else {
            System.out.println(album);
        }
    }

    @And("the title is {string}")
    public void theTitleIs(String title) {
        Assertions.assertEquals(title, album.get("title").getAsString());
    }

    @And("the url is {string}")
    public void theUrlIs(String url) {
        Assertions.assertEquals(url, album.get("url").getAsString());
    }

    @And("the thumbnailUrl is {string}")
    public void theThumbnailUrlIs(String thumbnailUrl) {
        Assertions.assertEquals(thumbnailUrl, album.get("thumbnailUrl").getAsString());
    }

}
