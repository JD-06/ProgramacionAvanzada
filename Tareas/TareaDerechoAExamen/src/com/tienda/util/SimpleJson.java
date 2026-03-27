package com.tienda.util;

import java.util.*;

/**
 * Parser/serializer JSON simple sin dependencias externas.
 * Soporta: null, boolean, número, string, array, object.
 */
public class SimpleJson {
    private final String s;
    private int i;

    private SimpleJson(String s) {
        this.s = s.trim();
        this.i = 0;
    }

    // ─── Parseo ────────────────────────────────────────────────

    public static Object parse(String json) {
        if (json == null || json.trim().isEmpty()) return null;
        return new SimpleJson(json.trim()).parseValue();
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> parseArray(String json) {
        Object r = parse(json);
        List<Map<String, Object>> result = new ArrayList<>();
        if (r instanceof List) {
            for (Object item : (List<?>) r) {
                if (item instanceof Map) result.add((Map<String, Object>) item);
            }
        }
        return result;
    }

    private Object parseValue() {
        skipWS();
        if (i >= s.length()) return null;
        char c = s.charAt(i);
        switch (c) {
            case '{': return parseObject();
            case '[': return parseList();
            case '"': return parseString();
            case 't': i += 4; return Boolean.TRUE;
            case 'f': i += 5; return Boolean.FALSE;
            case 'n': i += 4; return null;
            default: return parseNumber();
        }
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> map = new LinkedHashMap<>();
        i++; // '{'
        skipWS();
        while (i < s.length() && s.charAt(i) != '}') {
            skipWS();
            String key = parseString();
            skipWS();
            if (i < s.length() && s.charAt(i) == ':') i++;
            skipWS();
            Object val = parseValue();
            map.put(key, val);
            skipWS();
            if (i < s.length() && s.charAt(i) == ',') i++;
            skipWS();
        }
        if (i < s.length()) i++; // '}'
        return map;
    }

    private List<Object> parseList() {
        List<Object> list = new ArrayList<>();
        i++; // '['
        skipWS();
        while (i < s.length() && s.charAt(i) != ']') {
            list.add(parseValue());
            skipWS();
            if (i < s.length() && s.charAt(i) == ',') i++;
            skipWS();
        }
        if (i < s.length()) i++; // ']'
        return list;
    }

    private String parseString() {
        i++; // '"'
        StringBuilder sb = new StringBuilder();
        while (i < s.length() && s.charAt(i) != '"') {
            if (s.charAt(i) == '\\' && i + 1 < s.length()) {
                i++;
                switch (s.charAt(i)) {
                    case '"':  sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/':  sb.append('/'); break;
                    case 'n':  sb.append('\n'); break;
                    case 'r':  sb.append('\r'); break;
                    case 't':  sb.append('\t'); break;
                    default:   sb.append(s.charAt(i));
                }
            } else {
                sb.append(s.charAt(i));
            }
            i++;
        }
        if (i < s.length()) i++; // '"'
        return sb.toString();
    }

    private Object parseNumber() {
        int start = i;
        boolean isFloat = false;
        if (i < s.length() && s.charAt(i) == '-') i++;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '.' || c == 'e' || c == 'E') isFloat = true;
            if (Character.isDigit(c) || c == '.' || c == 'e' || c == 'E' || c == '+' || c == '-') i++;
            else break;
        }
        String num = s.substring(start, i);
        try {
            if (isFloat) return Double.parseDouble(num);
            return Long.parseLong(num);
        } catch (NumberFormatException e) {
            return num;
        }
    }

    private void skipWS() {
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) i++;
    }

    // ─── Acceso tipado seguro ───────────────────────────────────

    public static String str(Map<String, Object> m, String key) {
        Object v = m.get(key);
        return v == null ? "" : v.toString();
    }

    public static double dbl(Map<String, Object> m, String key) {
        Object v = m.get(key);
        if (v == null) return 0;
        if (v instanceof Number) return ((Number) v).doubleValue();
        try { return Double.parseDouble(v.toString()); } catch (Exception e) { return 0; }
    }

    public static int intVal(Map<String, Object> m, String key) {
        return (int) dbl(m, key);
    }

    // ─── Serialización ─────────────────────────────────────────

    public static String toJson(Object obj) {
        return toPretty(obj, 0);
    }

    private static String toPretty(Object obj, int depth) {
        String pad  = repeat("  ", depth);
        String pad1 = repeat("  ", depth + 1);
        String nl   = "\n";

        if (obj == null)    return "null";
        if (obj instanceof Boolean) return obj.toString();
        if (obj instanceof Number)  return formatNumber((Number) obj);
        if (obj instanceof String)  return "\"" + escape((String) obj) + "\"";

        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (list.isEmpty()) return "[]";
            StringBuilder sb = new StringBuilder("[" + nl);
            for (int j = 0; j < list.size(); j++) {
                sb.append(pad1).append(toPretty(list.get(j), depth + 1));
                if (j < list.size() - 1) sb.append(",");
                sb.append(nl);
            }
            sb.append(pad).append("]");
            return sb.toString();
        }

        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            if (map.isEmpty()) return "{}";
            StringBuilder sb = new StringBuilder("{" + nl);
            Iterator<? extends Map.Entry<?, ?>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> e = it.next();
                sb.append(pad1)
                  .append("\"").append(e.getKey()).append("\": ")
                  .append(toPretty(e.getValue(), depth + 1));
                if (it.hasNext()) sb.append(",");
                sb.append(nl);
            }
            sb.append(pad).append("}");
            return sb.toString();
        }

        return "\"" + escape(obj.toString()) + "\"";
    }

    private static String formatNumber(Number n) {
        if (n instanceof Double || n instanceof Float) {
            double d = n.doubleValue();
            if (d == Math.floor(d) && !Double.isInfinite(d)) return String.valueOf((long) d);
            return String.format("%.4f", d).replaceAll("0+$", "").replaceAll("\\.$", ".0");
        }
        return n.toString();
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    private static String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(s);
        return sb.toString();
    }
}
