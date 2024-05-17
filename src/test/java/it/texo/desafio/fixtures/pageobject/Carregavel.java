package it.texo.desafio.fixtures.pageobject;

import java.util.Calendar;

import it.texo.desafio.fixtures.util.Espera;
import it.texo.desafio.fixtures.util.Formatador;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Uma abstração para um componente que demora para carregar.
 * 
 * <p>
 * Serve de base para p�ginas e widgets.
 * <p>
 * Inspirado no padr�o LoadableComponent do Selenium.
 * <p>
 * Por�m, corrige o funcionamento do m�todo abstrato de carga,
 * retornando boolean ao contr�rio do Selenium que dispara um java.lang.Error.
 * <p>
 * O uso foi simplificado, existindo apenas um m�todo abstrato.
 * 
 */
public abstract class Carregavel {

    protected final RemoteWebDriver driver;
    protected Espera espera;

    public Carregavel(RemoteWebDriver driver) {
        this.driver = driver;
        this.espera = new Espera(driver);
    }

    /**
     * Retornar verdadeiro quando o componente j� estiver carregado
     * 
     * @return verdadeiro ou falso
     */
    protected abstract boolean estaPronto();
    
    /**
     * Informa se o componente está carregado
     * 
     * @return verdadeiro ou falso informando que o componente foi carregado
     */
    public boolean carregado() {
    	try {
    		return estaPronto();
    	} catch(Exception e) {
    		return false;
    	}
    }

    /**
     * Aguarda o carregamento do componente até um determinado tempo limite.
     * Falha o teste se não for encontrado.
     * 
     * @param timeout tempo limite em segundos
     */
    public void aguardar(int timeout) {
        long momentoInicial = Calendar.getInstance().getTimeInMillis();
        System.out.print("Aguardando " + getClass().getSimpleName() + "...");
        espera.aguardarCondicao(timeout)
            .withMessage("Tempo esgotado aguardando " + getClass().getSimpleName())
            .ignoring(Exception.class)
            .until(driver -> {
                System.out.print(".");
                return estaPronto();
            });
        double tempoDecorridoMillis = Calendar.getInstance().getTimeInMillis() - momentoInicial;
        double tempoDecorridoSegundos = tempoDecorridoMillis / 1000;
        System.out.println("[" + Formatador.formatarNumerico(tempoDecorridoSegundos) + "s]");
    }
    
    /**
     * Aguarda o carregamento do componente até um determinado tempo limite.
     * não falha o teste se não for encontrado.
     * 
     * @param timeout
     */
    public void aguardarSemFalhar(int timeout) {
        try {
            aguardar(timeout);
        } catch(Exception e) {
            System.out.println(getClass().getSimpleName() + " não encontrado, continuando...");
        }        
    }

}
