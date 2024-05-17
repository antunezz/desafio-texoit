package it.texo.desafio.fixtures.enums;

/**
 * Mapeamento de pseudo elementos de CSS
 * 
 * @author B41351
 *
 */
public enum PseudoElementoCss {
    
    BEFORE(":before"),
    AFTER(":after"),
    NENHUM(null);
    
    private final String propriedade;

    PseudoElementoCss(final String propriedade) {
        this.propriedade = propriedade;
    }

    @Override
    public String toString() {
        return propriedade;
    }

}
