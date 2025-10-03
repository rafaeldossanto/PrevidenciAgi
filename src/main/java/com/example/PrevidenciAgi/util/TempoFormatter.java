package com.example.PrevidenciAgi.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TempoFormatter {

    private static final Map<String, Integer> UNIDADES = createUnidades();
    private static final Map<String, Integer> DEZENAS = createDezenas();

    private static Map<String, Integer> createUnidades() {
        Map<String, Integer> m = new HashMap<>();
        m.put("zero", 0);
        m.put("um", 1); m.put("uma", 1);
        m.put("dois", 2); m.put("duas", 2);
        m.put("tres", 3); m.put("três", 3);
        m.put("quatro", 4);
        m.put("cinco", 5);
        m.put("seis", 6);
        m.put("sete", 7);
        m.put("oito", 8);
        m.put("nove", 9);
        m.put("dez", 10);
        m.put("onze", 11);
        m.put("doze", 12);
        m.put("treze", 13);
        m.put("quatorze", 14); m.put("catorze", 14);
        m.put("quinze", 15);
        m.put("dezesseis", 16); m.put("dezasseis", 16);
        m.put("dezessete", 17); m.put("dezassete", 17);
        m.put("dezoito", 18);
        m.put("dezenove", 19);
        return m;
    }

    private static Map<String, Integer> createDezenas() {
        Map<String, Integer> m = new HashMap<>();
        m.put("vinte", 20);
        m.put("trinta", 30);
        m.put("quarenta", 40);
        m.put("cinquenta", 50);
        m.put("sessenta", 60);
        m.put("setenta", 70);
        m.put("oitenta", 80);
        m.put("noventa", 90);
        m.put("cem", 100);
        return m;
    }

    /**
     * Retorna uma representação canônica do tempo.
     * Exemplos: "10", "10 anos", "dez anos" -> "10 anos"
     * "120 meses" -> "10 anos"
     * "18 meses" -> "1 ano e 6 meses"
     */
    public static String formatar(String entrada) {
        if (entrada == null || entrada.isBlank()) return "Não informado";

        String s = entrada.trim().toLowerCase();

        // tenta encontrar padrões "X anos" e "Y meses" (com números)
        Integer anos = extractNumberForUnit(s, "(\\d+)\\s*(anos?|ano)");
        Integer meses = extractNumberForUnit(s, "(\\d+)\\s*(mes(es)?|m[eê]s)");

        // se não tiver números, tenta palavras por extenso próximas às unidades
        if (anos == null) anos = extractWordsNumberForUnit(s, "(.*?)\\s*(anos?|ano)");
        if (meses == null) meses = extractWordsNumberForUnit(s, "(.*?)\\s*(mes(es)?|m[eê]s)");

        // se não foi encontrado explicitamente "anos" ou "meses", tenta interpretar o todo
        if (anos == null && meses == null) {
            // se for apenas dígitos "10"
            if (s.matches("\\d+")) {
                anos = Integer.parseInt(s);
            } else {
                // tenta converter palavra inteira "dez"
                Integer n = parseNumberWords(s);
                if (n != null) anos = n;
            }
        }

        // Monta a string canônica
        if (anos != null && meses != null) {
            // ambos presentes (ex: "1 ano e 6 meses")
            if (meses % 12 == 0) {
                anos += meses / 12;
                meses = 0;
            }
        }

        if (anos != null && (meses == null || meses == 0)) {
            if (anos == 1) return "1 ano";
            return anos + " anos";
        }

        if (meses != null && anos == null) {
            if (meses < 12) {
                if (meses == 1) return "1 mês";
                return meses + " meses";
            } else {
                int a = meses / 12;
                int m = meses % 12;
                if (m == 0) return a + (a == 1 ? " ano" : " anos");
                return a + (a == 1 ? " ano e " : " anos e ") + m + (m == 1 ? " mês" : " meses");
            }
        }

        if (anos != null && meses != null && meses > 0) {
            return anos + (anos == 1 ? " ano e " : " anos e ") + meses + (meses == 1 ? " mês" : " meses");
        }

        return "Não informado";
    }

    /**
     * Converte uma string de tempo para o total em meses.
     * Lança IllegalArgumentException se não conseguir interpretar.
     */
    public static int converterParaMeses(String entrada) {
        if (entrada == null || entrada.isBlank()) {
            throw new IllegalArgumentException("Tempo inválido: vazio ou nulo");
        }
        String s = entrada.trim().toLowerCase();

        Integer anos = extractNumberForUnit(s, "(\\d+)\\s*(anos?|ano)");
        Integer meses = extractNumberForUnit(s, "(\\d+)\\s*(mes(es)?|m[eê]s)");

        if (anos == null) anos = extractWordsNumberForUnit(s, "(.*?)\\s*(anos?|ano)");
        if (meses == null) meses = extractWordsNumberForUnit(s, "(.*?)\\s*(mes(es)?|m[eê]s)");

        // se nada explicitado, tenta interpretar todo o texto
        if (anos == null && meses == null) {
            if (s.matches("\\d+")) {
                anos = Integer.parseInt(s);
            } else {
                Integer n = parseNumberWords(s);
                if (n != null) anos = n;
            }
        }

        int totalMeses = 0;
        if (anos != null) totalMeses += anos * 12;
        if (meses != null) totalMeses += meses;

        if (totalMeses <= 0) {
            throw new IllegalArgumentException("Não foi possível interpretar o tempo: '" + entrada + "'. Formatos aceitos: '10', '10 anos', 'dez anos', '120 meses', '1 ano e 6 meses'.");
        }
        return totalMeses;
    }

    // ---- helpers ----

    private static Integer extractNumberForUnit(String s, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(s);
        if (m.find()) {
            try {
                return Integer.parseInt(m.group(1));
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }

    private static Integer extractWordsNumberForUnit(String s, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(s);
        if (m.find()) {
            String grupo = m.group(1).replaceAll("(^\\s+|\\s+$)", "");
            if (grupo.isBlank()) return null;
            Integer n = parseNumberWords(grupo);
            return n;
        }
        return null;
    }

    /**
     * Converte palavras por extenso (pt-BR) para inteiro quando possível.
     * Suporta composições simples como "vinte e três", "trinta e cinco", "dez", "um".
     * Retorna null se não conseguir interpretar.
     */
    private static Integer parseNumberWords(String texto) {
        if (texto == null) return null;
        String t = texto.toLowerCase().replaceAll("[^a-z\\s-]", " ").replace("-", " ").trim();
        if (t.isBlank()) return null;

        String[] tokens = t.split("\\s+");
        int total = 0;
        for (String tok : tokens) {
            if (tok.isBlank() || tok.equals("e")) continue;
            if (UNIDADES.containsKey(tok)) {
                total += UNIDADES.get(tok);
                continue;
            }
            if (DEZENAS.containsKey(tok)) {
                total += DEZENAS.get(tok);
                continue;
            }
            // palavras como "anos", "ano", "meses", "mes" podem aparecer — ignorar
            if (tok.matches("anos?|mes(es)?|m[eê]s")) continue;
            // se token não reconhecido, aborta (retorna null)
            return null;
        }
        return total == 0 ? null : total;
    }
}