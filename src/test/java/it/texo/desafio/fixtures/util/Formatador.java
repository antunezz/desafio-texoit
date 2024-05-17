package it.texo.desafio.fixtures.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class Formatador {

    public static String formatarCPF(String cpf) {
        cpf = Formatador.preencherAEsquerda(cpf, 11, "0");
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
    
    public static String formatarCNPJ(String cnpj) {
        cnpj = Formatador.preencherAEsquerda(cnpj, 14, "0");
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }
    
    public static String formatarCelular(String celular) {
        return celular.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "\\($1\\) $2-$3");
    }
    
    public static String truncarTexto(String valor, int max) {
        return valor.substring(0, Math.min(max, valor.length()));
    }
    
    public static String preencherAEsquerda(String valor, int tamanho, String caractere) {
        return StringUtils.leftPad(valor, tamanho, caractere);
    }
    
    public static String preencherADireita(String valor, int tamanho, String caractere) {
        return StringUtils.rightPad(valor, tamanho, caractere);
    }
    
    public static String filtrarNaoNumericos(String valor) {
        return valor.replaceAll("[^\\d.]", "");
    }
    
    public static String filtrarNaoDigitos(String valor) {
        return valor.replaceAll("[^\\d]", "");
    }

    public static String formatarNumerico(float valor) {
        return formatarNumerico((double)valor);
    }
     
    public static String formatarNumerico(double valor) {
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return nf.format(valor);
    }
    
    public static String formatarNumerico(float valor, Locale locale) {
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(locale));
        return nf.format(valor);
    }
    
    public static String formatarNumerico(float valor, Locale locale, String pattern) {
        NumberFormat nf = new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
        return nf.format(valor);
    }

    public static String formatarNumerico(double valor, Locale locale, String pattern) {
        NumberFormat nf = new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
        return nf.format(valor);
    }
    
    public static String removerQuebrasDeLinha(String texto) {
        return texto.replaceAll("[\\r\\n]+", " ");
    }
    
    public static String formatarMoeda(float valor) {
        return formatarMoeda((double)valor);
    }
    
    public static String formatarMoeda(double valor) {
        return Formatador.formatarMoeda(valor, "R$ ");
    }    
    
    public static String formatarMoeda(float valor, String moeda) {
        return formatarMoeda((double)valor, moeda);
    }
    
    public static String formatarMoeda(double valor, String moeda) {
        return moeda + Formatador.formatarNumerico(valor);
    }
    
    public static String formatarPorcentagem(double valor) {
        NumberFormat nf = new DecimalFormat("#,##0.00'%'", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return nf.format(valor);
    }

    public static String formatarData(Calendar calendar, String formato) {
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(calendar.getTime());
    }

    public static String formatarData(Calendar calendar) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(calendar.getTime());
    }
    
    public static String formatarDataHora(Calendar calendar) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        return formatter.format(calendar.getTime());
    }

    public static String normalizarTexto(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).toLowerCase().replaceAll("[^\\p{ASCII}]", "");
    }
	
    public static String formatarContaCorrente(String conta) {
        String contaSemPontos = filtrarNaoNumericos(conta);
        return contaSemPontos.replaceAll("(\\d{2})(\\d{6})(\\d{1})(\\d{1})", "$1.$2.$3.$4");
    }
    
    public static String formatarContaCorrenteHifen(String conta) {
        String contaSemPontos = filtrarNaoNumericos(conta);
        return contaSemPontos.replaceAll("(\\d{2})(\\d{6})(\\d{1})(\\d{1})", "$1.$2.$3-$4");
    }

}
