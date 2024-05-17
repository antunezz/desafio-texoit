package it.texo.desafio.fixtures.elementos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.texo.desafio.fixtures.enums.EventoHtml;
import it.texo.desafio.fixtures.enums.PropriedadeCss;
import it.texo.desafio.fixtures.enums.PseudoElementoCss;
import it.texo.desafio.fixtures.util.Espera;
import it.texo.desafio.fixtures.util.Javascript;

/**
 * Encapsula um WebElement e oferece algumas ações para executar nele,
 * principalmente baseadas em javascript.
 * 
 * Pode ser usado tanto para testes Web quanto Mobile, 
 * mas apenas para elementos dentro de uma Webview (html).
 * 
 */
public class Elemento implements IElemento<Elemento> {
	
	protected final String DATA_AUTOMATION_ID = "data-automation-id";
	protected RemoteWebDriver driver;
	protected WebElement el;
	protected Javascript javascript;
    protected Espera espera;
	
	/**
	 * Construtor a partir de um WebElement
	 * 
	 * @param element WebElement original
	 */
    public Elemento(WebElement element) {
		this.el = element;
		this.driver = (RemoteWebDriver) ((RemoteWebElement)el).getWrappedDriver();
		javascript = new Javascript(driver);
		espera = new Espera(driver);
	}
	
	/**
	 * Construtor estático a partir de um WebElement
	 * 
	 * @param el
	 * @return
	 */
    public static Elemento com(WebElement el) {
	    return new Elemento(el);
	}

    /**
     * Retorna o WebElement encapsulado.
     * 
     * @see org.openqa.selenium.WrapsElement#getWrappedElement()
     */
	@Override
	public WebElement getWrappedElement() {
		return el;
	}

	/**
	 * Procura um elemento dentro deste elemento.
	 * 
	 * @see IElemento#findElement(By)
	 */
	@Override
    public Elemento findElement(By by) {
        return new Elemento(el.findElement(by));
    }

	/**
	 * Procura elementos dentro deste elemento.
	 * 
	 * @see IElemento#findElements(By)
	 */
    @Override
    public List<Elemento> findElements(By by) {
        List<Elemento> result = new ArrayList<Elemento>();
        List<WebElement> found = el.findElements(by);
        for (WebElement wel : found) {
            result.add(new Elemento(wel));
        }
        return result;
    }
	
    /**
     * Verifica se o elemento está visível. 
     * 
     * @see IElemento#estaVisivel()
     */
	@Override
	public boolean estaVisivel() {
		return IElemento.super.estaVisivel() && obterPropriedade("offsetParent") != "null";
	}

	/**
	 * Define o atributo informado no elemento Html (com setAttribute) 
	 * 
	 * @param atributo
	 * @param valor
	 * @return o próprio elemento
	 */
	public Elemento definirAtributo(String atributo, String valor) {
		String script = String.format("arguments[0].setAttribute('%s', '%s')", atributo, valor);
		javascript.executeScript(script, el);
		return this;
	}
	
	/**
	 * Destaca o elemento visualmente (com uma borda verde).
	 * 
	 * @return o próprio elemento
	 */
    public Elemento destacar() {
        definirCss(PropriedadeCss.OUTLINE, "3px solid lightgreen");
        return this;
    }
	
    /**
     * Cria uma referência direta para o elemento, etiquetando-o com um
     * atributo de valor randômico (data-automation-id).
     * 
     * @return um locator único para o elemento
     */
	public By gerarAutomationLocator() {
	    String dataAutomationID = obterAtributo(DATA_AUTOMATION_ID);
        if (StringUtils.isEmpty(dataAutomationID)) {
            dataAutomationID = String.valueOf(Math.random());
            definirAtributo(DATA_AUTOMATION_ID, dataAutomationID);
        }
	    return By.cssSelector(String.format("[%s='%s']", DATA_AUTOMATION_ID, dataAutomationID));
	}
	
	/**
	 * Retorna o valor de uma propriedade CSS do elemento.
	 * 
	 * @param propriedade
	 * @return o próprio elemento
	 */
	public String obterCss(String propriedade) {
		return el.getCssValue(propriedade);
	}
	
	/**
	 * Retorna o valor de uma propriedade CSS do elemento
	 * 
	 * @param propriedade
	 * @return o próprio elemento
	 */
	public String obterCss(PropriedadeCss propriedade) {
	    return obterCss(propriedade.toString());
	}
	
	/**
	 * Define uma propriedade css no elemento.
	 * 
	 * @param propriedade
	 * @param valor
	 * @return o próprio elemento
	 */
	public Elemento definirCss(PropriedadeCss propriedade, String valor) {
	    javascript.executeScript("arguments[0].style[arguments[1]] = arguments[2]" , el, propriedade.toString(), valor);
	    return this;
	}

	/**
	 * Define uma ou mais propriedades css no elemento.
	 * 
	 * @param estilos
	 * @return o próprio elemento
	 */
    public Elemento definirCss(Map<PropriedadeCss, String> estilos) {
        for (Entry<PropriedadeCss, String> entry : estilos.entrySet()) {
            definirCss(entry.getKey(), entry.getValue().toString());
        }
        return this;
    }
	
    /**
     * Adiciona uma class (css) no elemento
     * 
     * @param nomeClasse
     * @return o próprio elemento
     */
	public Elemento adicionarClass(String nomeClasse) {
	    javascript.executeScript("arguments[0].classList.add(arguments[1])", el, nomeClasse);
	    return this;
	}

	/**
	 * Remove uma class (css) do elemento
	 * 
	 * @param nomeClasse
	 * @return o próprio elemento
	 */
    public Elemento removerClass(String nomeClasse) {
        javascript.executeScript("arguments[0].classList.remove(arguments[1])", el, nomeClasse);
        return this;
    }
    
    /**
     * Verifica se o elemento possui uma determinada class (css)
     * 
     * @param nomeClasse
     * @return o próprio elemento
     */
    public boolean contemClass(String nomeClasse) {
        return (Boolean)javascript.executeScript("return arguments[0].classList.contains(arguments[1])", el, nomeClasse);
    }
	
    /**
     * Executa um toque no elemento (evento touchend)
     * 
     * @return o próprio elemento
     */
	public Elemento tocar() {
	    StringBuilder comando = new StringBuilder();
        comando.append("event = document.createEvent('HTMLEvents');");
        comando.append("event.initEvent('touchend', true, true);"); 
        comando.append("event.eventName = 'touchend';");
        comando.append("arguments[0].dispatchEvent(event);");
	    javascript.executeScript(comando.toString(), el);
	    return this;
	}
	
	/**
	 * Dispara um evento Html no elemento (Ex: focus, blur, keydown, etc...)
	 * 
	 * @param evento
	 * @return o próprio elemento
	 */
	public Elemento dispararEvento(EventoHtml evento) {
	    javascript.executeScript(String.format("arguments[0].dispatchEvent(new Event('%s'))", evento), el);
		return this;
	}

	/**
	 * Retorna todos estilos computados do elemento
	 * 
	 * @return um mapa chave/valor dos estilos
	 */
	public Map<PropriedadeCss, String> getComputedStyle() {
	    return getComputedStyle(PseudoElementoCss.NENHUM);
	}
    
	/**
     * Retorna os estilos computados do elemento, apenas para as propriedades informadas
     * 
     * @return um mapa chave/valor dos estilos
     */	
	public Map<PropriedadeCss, String> getComputedStyle(Set<PropriedadeCss> propriedades) {
	    return getComputedStyle(propriedades, PseudoElementoCss.NENHUM);
	}

	/**
	 * Retorna os estilos computados do elemento, para as propriedades informadas
	 * e para o pseudo-elemento informado.
	 * 
	 * @param propriedades
	 * @param pseudoElemento
	 * @return um mapa chave/valor dos estilos
	 */
	public Map<PropriedadeCss, String> getComputedStyle(Set<PropriedadeCss> propriedades, PseudoElementoCss pseudoElemento) {
	    Map<PropriedadeCss, String> styles = new HashMap<PropriedadeCss, String>();
	    Map<PropriedadeCss, String> computed = getComputedStyle(pseudoElemento);
	    for (PropriedadeCss prop : propriedades) {
	        if (computed.containsKey(prop)) {
	            styles.put(prop, computed.get(prop));
	        }
	    }
	    return styles;
	}
	
	/**
     * Retorna todos estilos computados do elemento, para um determinado pseudo elemento.
     * 
     * @return um mapa chave/valor dos estilos
     */
	public Map<PropriedadeCss, String> getComputedStyle(PseudoElementoCss pseudoElemento) {
	    Map<PropriedadeCss, String> styles = new HashMap<PropriedadeCss, String>();
        String stringfied = (String) javascript.executeScript(
            "return JSON.stringify(window.getComputedStyle(arguments[0], arguments[1]))", 
            el, 
            pseudoElemento.toString()
        );
        JsonObject json = new Gson().fromJson(stringfied, JsonObject.class);
        for (Entry<String, JsonElement> entry : json.entrySet()) {
               styles.put(PropriedadeCss.deString(entry.getKey()), entry.getValue().getAsString());
        }
        return styles;
    }
	
	/**
	 * Roda a tela até o elemento
	 * 
	 * @param alinharTopo passe true para alinhá-lo ao topo da tela, false para alinhar a base
	 * @return o próprio elemento
	 */
	public Elemento scrollIntoView(boolean alinharTopo) {
        javascript.executeScript("arguments[0].scrollIntoView(arguments[1])", el, alinharTopo);
	    return this;
	}
	
	/**
     * Roda a tela até o elemento, alinhando-o ao topo da tela.
     * 
     * @return o próprio elemento
     */
    public Elemento scrollIntoView() {
        return scrollIntoView(true);
    }
    
    /**
     * Retorna o shadowRoot de um elemento, através da propriedade .shadowRoot (javascript)
     * 
     * @return elemento shadowRoot
     */
    public Elemento shadowRoot() {
        WebElement el = (WebElement)obterPropriedade("shadowRoot");
        return Elemento.com(el);
    }
    
    /**
     * Retorna uma propriedade do DOMNode de um elemento.
     * 
     * Executa um comando javascript para acessar o dom node, e obter a propriedade da forma:
     * 
     *  return domNode['propriedade'];
     * 
     * ou o equivalente
     * 
     *  return domNome.propriedade;
     * 
     * @param propriedade
     * @return o valor encontrado
     */
    public Object obterPropriedade(String propriedade) {
        return javascript.executeScript("return arguments[0][arguments[1]]", el, propriedade);
    }
    
    /**
     * Define uma propriedade no DOMNode do elemento.
     * 
     * @param propriedade
     * @param valor
     * @return
     */
    public Elemento definirPropriedade(String propriedade, Object valor) {
        javascript.executeScript("arguments[0][arguments[1]] = arguments[2]", el, propriedade, valor);
        return this;
    }
    
    /**
     * Executa uma espera fixa antes de continuar para o próximo comando.
     * 
     * @param timeout
     * @return o próprio elemento
     */    
    public Elemento aguardarFixo(int timeout) {
        espera.dormir(timeout);
        return this;
    }

    /**
     * Executa uma espera fixa antes de continuar para o próximo comando.
     * 
     * @param timeout
     * @return o próprio elemento
     */    
    public Elemento aguardarFixo(float timeout) {
        espera.dormir(timeout);
        return this;
    }
    
    /**
     * Aguarda o elemento ficar habilitado.
     * 
     * Feito através da condição ExpectedConditions.elementToBeClickable(el)
     * do Selenium.
     * 
     * @param timeout
     * @return o próprio elemento
     */
    public Elemento aguardarHabilitar(int timeout) {
        espera.aguardarCondicao(timeout).until(ExpectedConditions.elementToBeClickable(el));
        return this;
    }

    /**
     * Aguarda o elemento desabilitar.
     * 
     * @param timeout
     * @return
     */
    public Elemento aguardarDesabilitar(int timeout) {
        espera.aguardarCondicao(timeout)
            .withMessage("Elemento " + toString() + " não desabilitou após " + timeout + " segundos")
            .until(driver -> !estaHabilitado());
        return this;
    }
    
    /**
     * Aguarda um atributo (html) existir no elemento.
     * 
     * @param atributo
     * @param timeout
     * @return
     */
    public Elemento aguardarAtributo(String atributo, int timeout) {
        espera.aguardarCondicao(timeout).until(driver -> obterAtributo(atributo) != null);
        return this;
    }

    /**
     * Aguarda um atributo (html) não existir no elemento.
     * 
     * @param atributo
     * @param timeout
     * @return
     */
    public Elemento aguardarAtributoAusente(String atributo, int timeout) {
        espera.aguardarCondicao(timeout).until(driver -> obterAtributo(atributo) == null);
        return this;
    }

    /**
     * Aguarda um atributo (html) ficar com um determinado valor.
     * 
     * @param atributo
     * @param valor
     * @param timeout
     * @return
     */
    public Elemento aguardarAtributo(String atributo, String valor, int timeout) {
        espera.aguardarCondicao(timeout).until(driver -> obterAtributo(atributo).equals(valor));
        return this;
    }
    
    /**
     * Aguarda um atributo (html) ficar com um valor diferente de um dado valor.
     * 
     * @param atributo
     * @param valor
     * @param timeout
     * @return
     */
    public Elemento aguardarAtributoDiferente(String atributo, String valor, int timeout) {
        espera.aguardarCondicao(timeout).until(driver -> !obterAtributo(atributo).equals(valor));
        return this;
    }
    
    /**
     * Aguarda o elemento ter uma determinada classe (css).
     * 
     * @param nomeClasse
     * @param timeout
     * @return
     */
    public Elemento aguardarClass(String nomeClasse, int timeout) {
        espera.aguardarCondicao(timeout).until(driver -> contemClass(nomeClasse));
        return this;
    }
    
    
    /**
     * Aguarda o elemento não ter uma determinada classe (css)
     * 
     * @param nomeClasse
     * @param timeout
     * @return
     */
    public Elemento aguardarClassAusente(String nomeClasse, int timeout) {
        espera.aguardarCondicao(timeout).until(driver -> !contemClass(nomeClasse));
        return this;
    }
    
    /**
     * Aguarda o elemento ter uma determinada propriedade de css
     * 
     * @param propriedade
     * @param timeout
     * @return
     */
    public Elemento aguardarCss(String propriedade, int timeout) {
        aguardarCondicao(el -> el.obterCss(propriedade) != null, timeout);
        return this;
    }
    
    /**
     * Aguarda que a propriedade de css tenha um determinado valor.
     * 
     * @param propriedade
     * @param valor
     * @param timeout
     * @return
     */
    public Elemento aguardarCss(String propriedade, String valor, int timeout) {
        aguardarCondicao(el -> el.obterCss(propriedade).equals(valor), timeout);
        return this;
    }
    
    /**
     * Aguarda que a propriedade de css tenha um valor diferente de algum determinado valor.
     * 
     * @param propriedade
     * @param valor
     * @param timeout
     * @return
     */
    public Elemento aguardarCssDiferente(String propriedade, String valor, int timeout) {
        aguardarCondicao(el -> !el.obterCss(propriedade).equals(valor), timeout);
        return this;
    }
    
    /**
     * Aguarda o elemento não ter uma determinada propriedade de css.
     * 
     * @param propriedade
     * @param timeout
     * @return
     */
    public Elemento aguardarCssAusente(String propriedade, int timeout) {
        aguardarCondicao(el -> el.obterCss(propriedade) == null, timeout);
        return this;
    }
    
    /**
     * Aguarda uma condição específica (personalizada) no elemento.
     * 
     * @param isTrue uma lambda function que retorne boolean
     * @param timeout
     * @return
     */
    public Elemento aguardarCondicao(Function<? super Elemento, Boolean> isTrue, int timeout) {
        espera.aguardarCondicao(timeout).until(driver -> isTrue.apply(this));
        return this;
    }
    
    /**
     * Verifica se o elemento tem ao menos um event listener para um dado evento.
     * 
     * @param evento
     * @return
     */
    public boolean temEventListener(String evento) {
        return (boolean) javascript.executeScript(""
                + "var listeners = jQuery._data(arguments[0], 'events'); "
                + "var evento = arguments[1]; "
                + "return listeners != undefined && listeners[evento].length > 0", el, evento);
    }
    
    /**
     * Retorna uma representação String do elemento
     * 
     * @see Object#toString()
     */
    @Override
    public String toString() {
        String tagName = getWrappedElement().getTagName();
        String id = obterAtributo("id");
        String clazz = obterAtributo("class");
        StringBuilder sb = new StringBuilder();
        sb.append(tagName);
        if (id != null) {
            sb.append("#" + id);
        }
        if (clazz != null) {
            String[] classes = clazz.split(" ");
            sb.append("." + String.join(".", classes));
        }
        return sb.toString();
    }
    
}
