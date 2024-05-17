package it.texo.desafio.fixtures.enums;

/**
 * Mapeamento dos eventos Html/JavaScript
 * 
 * @author B41351
 *
 */
public enum EventoHtml {
 
    BLUR("blur"),
    CHANGE("change"),
    CONTEXTMENU("contextmenu"),
    FOCUS("focus"),
    INPUT("input"),
    INVALID("invalid"),
    RESET("reset"),
    SEARCH("search"),
    SELECT("select"),
    SUBMIT("submit"),
    KEYDOWN("keydown"),
    KEYPRESS("keypress"),
    KEYUP("keyup"),
    CLICK("click"),
    DBLCLICK("dblclick"),
    MOUSE("mousedown"),
    MOUSEMOVE("mousemove"),
    MOUSEOUT("mouseout"),
    MOUSEOVER("mouseover"),
    MOUSEUP("mouseup"),
    MOUSEWHEEN("mousewheel"),
    WHEEL("wheel"),
    DRAG("drag"),
    DRAGEND("dragend"),
    DRAGENTER("dragenter"),
    DRAGLEAVE("dragleave"),
    DREAGSTART("dragstart"),
    DROP("drop"),
    SCROLL("scroll"),
    AFTERPRINT("afterprint"),
    BEFOREPRINT("beforeprint"),
    BEFOREUNLOAD("beforeunload"),
    ERROR("error"),
    HASHCHANGE("hashchange"),
    LOAD("load"),
    MESSAGE("message"),
    OFFLINE("offline"),
    ONLINE("online"),
    PAGEHIDE("pagehide"),
    PAGESHOW("onpageshow"),
    POPSTATE("popstate"),
    RESIZE("resize"),
    STORAGE("storage"),
    UNLOAD("unload");
    
    private final String text;

    EventoHtml(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
