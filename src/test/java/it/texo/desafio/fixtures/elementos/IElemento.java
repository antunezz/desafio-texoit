package it.texo.desafio.fixtures.elementos;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WrapsElement;

import it.texo.desafio.fixtures.util.Espera;

/**
 * Interface comum a elementos nativos e web.
 * 
 * @param <T>
 */
public interface IElemento<T extends IElemento<T>> extends WrapsElement {
    
    public T findElement(By by);

    public List<T> findElements(By by);
    
    /**
     * Verifica se o elemento está visível.
     * 
     * @return 
     */
    default public boolean estaVisivel() {
        return getWrappedElement().isDisplayed();
    }
    
    /**
     * Verifica se o elemento está habilitado (isEnabled)
     * 
     * @return
     */
    default public boolean estaHabilitado() {
        return getWrappedElement().isEnabled();
    }
    
    /**
     * Verifica se o elemento está selecionado (isSelected)
     * @return
     */
    default public boolean estaSelecionado() {
        return getWrappedElement().isSelected();
    }
    
    /**
     * Retorna o texto do elemento
     * 
     * @return
     */
    default public String obterTexto() {
        return getWrappedElement().getText();
    }

    /**
     * Retorna o valor de um atributo do elemento
     * 
     * @param atributo
     * @return
     */
    default public String obterAtributo(String atributo) {
        return getWrappedElement().getAttribute(atributo);
    }
    
    /**
     * Clia no elemento.
     * 
     * @return o próprio elemento.
     */
    @SuppressWarnings("unchecked")
    default public T clicar() {
        getWrappedElement().click();
        return (T) this;
    }
    
    /**
     * Limpa um elemento (de formulário)
     * 
     * @return o próprio elemento
     */
    @SuppressWarnings("unchecked")
    default public T limpar() {
        getWrappedElement().clear();
        return (T) this;
    }
    
    /**
     * Preenche um elemento de formulário (sendKeys)
     * 
     * Pode ser usado em qualquer elemento que aceite teclas.
     * 
     * @param string
     * @return o próprio elemento
     */
    @SuppressWarnings("unchecked")
    default public T preencher(String string) {
        getWrappedElement().sendKeys(string);
        return (T) this;
    }
    
    /**
     * Preenche com atraso entre cada tecla (default 50ms)
     * 
     * @param texto
     * @return o próprio elemento
     */
    public default T preencherLento(String texto) {
        return preencherLento(texto, 0.05f);
    }
    
    /**
     * Preenche com atraso entre cada tecla.
     * 
     * @param texto
     * @param atraso em segundos
     * @return o próprio elemento
     */
    @SuppressWarnings("unchecked")
    public default T preencherLento(String texto, float atraso) {
        texto.chars().forEach(c -> {
            preencher(String.valueOf((char)c));
            Espera.fixa(atraso);
        });
        return (T) this;
    }
    
    /**
     * Retorna informações da área ocupada pelo elemento (classe Rectangle)
     * 
     * @return
     */
    public default Rectangle obterRetangulo() {
        return getWrappedElement().getRect();
    }
    
    /**
     * Verifica se um elemento está contido dentro de outro (visualmente)
     * 
     * @param outro
     * @return
     */
    public default boolean estaContidoEm(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return r1.x >= r2.x && r1.y >= r2.y && 
            (r1.x + r1.width) <= (r2.x + r2.width) && (r1.y + r1.height) <= (r2.y + r2.height);
    }
    
    /**
     * Verifica se um elemento está acima de outro (visualmente)
     * 
     * @param outro
     * @return
     */
    public default boolean estaAcimaDe(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return r1.y + r1.height < r2.y + r2.height;
    }
    
    /**
     * Verifica se um elemento está à esquerda de outro (visualmente)
     * 
     * @param outro
     * @return
     */
    public default boolean estaEsquerdaDe(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return r1.x + r1.width < r2.x + r2.width;
    }
    
    /**
     * Verifica se um elemento está alinhado verticalmente a outro
     * 
     * @param outro
     * @return
     */
    public default boolean estaAlinhadoVerticalmenteA(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return Math.abs(((r1.x + (r1.width/2)) - (r2.x + (r2.width/2)))) <= 2;
    }
    
    
    /**
     * Verifica se um elementos está alinhado horizontalmente a outro.
     * 
     * @param outro
     * @return
     */
    public default boolean estaAlinhadoHorizontalmenteA(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return Math.abs(((r1.y + (r1.height/2)) - (r2.y + (r2.height/2)))) <= 2;
    }
    
    /**
     * Verifica se os elementos estáo alinhados à direita
     * 
     * @param outro
     * @return
     */
    public default boolean estaAlinhadoADireita(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return Math.abs(r1.x + r1.width - r2.x - r2.width) <= 2;
    }
    
    /**
     * Verifica se os elementoe estáo alinhados à esquerda
     * 
     * @param outro
     * @return
     */
    public default boolean estaAlinhadoAEsquerda(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return Math.abs(r1.x - r2.x) <= 2;
    }
    
    /**
     * Verifica se os elementos estáo alinhados ao topo
     * 
     * @param outro
     * @return
     */
    public default boolean estaAlinhadoAoTopo(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return Math.abs(r1.y - r2.y) <= 2;
    }
    
    /**
     * Verifica se os elementoe estáo alinhados abaixo
     * 
     * @param outro
     * @return
     */
    public default boolean estaAlinhadoAbaixo(Elemento outro) {
        Rectangle r1 = this.obterRetangulo();
        Rectangle r2 = outro.obterRetangulo();
        return Math.abs(r1.y + r1.height - r2.y - r2.height) <= 2;
    }
    
    public T destacar();
    
}
