import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int posicion;

    public Parser(List<Token> tokens) {
        this.tokens   = tokens;
        this.posicion = 0;
    }

    // Mira el token actual sin consumirlo
    private Token actual() {
        return tokens.get(posicion);
    }

    // Consume el token actual y avanza al siguiente
    private Token consumir(Token.Tipo tipoEsperado) {
        Token t = actual();
        if (t.tipo != tipoEsperado) {
            throw new RuntimeException(
                "Error sintáctico: se esperaba " + tipoEsperado +
                " pero se encontró " + t);
        }
        posicion++;
        return t;
    }

    // -------------------------------------------------------
    // E → T + E | T - E | T
    // Maneja + y - (menor precedencia)
    // -------------------------------------------------------
    public NodoArbol parseExpresion() {
        NodoArbol izquierdo = parseTerm();

        if (actual().tipo == Token.Tipo.MAS) {
            consumir(Token.Tipo.MAS);
            NodoArbol derecho = parseExpresion();
            return new NodoArbol("+", izquierdo, derecho);
        }

        if (actual().tipo == Token.Tipo.MENOS) {
            consumir(Token.Tipo.MENOS);
            NodoArbol derecho = parseExpresion();
            return new NodoArbol("-", izquierdo, derecho);
        }

        return izquierdo;
    }

    // -------------------------------------------------------
    // T → F * T | F / T | F
    // Maneja * y / (mayor precedencia)
    // -------------------------------------------------------
    private NodoArbol parseTerm() {
        NodoArbol izquierdo = parseFactor();

        if (actual().tipo == Token.Tipo.MULT) {
            consumir(Token.Tipo.MULT);
            NodoArbol derecho = parseTerm();
            return new NodoArbol("*", izquierdo, derecho);
        }

        if (actual().tipo == Token.Tipo.DIV) {
            consumir(Token.Tipo.DIV);
            NodoArbol derecho = parseTerm();
            return new NodoArbol("/", izquierdo, derecho);
        }

        return izquierdo;
    }

    // -------------------------------------------------------
    // F → ( E ) | número | identificador
    // Maneja valores base y paréntesis
    // -------------------------------------------------------
    private NodoArbol parseFactor() {
        Token t = actual();

        if (t.tipo == Token.Tipo.NUMERO) {
            consumir(Token.Tipo.NUMERO);
            return new NodoArbol(t.valor, null, null);
        }

        if (t.tipo == Token.Tipo.IDENTIFICADOR) {
            consumir(Token.Tipo.IDENTIFICADOR);
            return new NodoArbol(t.valor, null, null);
        }

        if (t.tipo == Token.Tipo.PAREN_IZQ) {
            consumir(Token.Tipo.PAREN_IZQ);
            NodoArbol nodo = parseExpresion();
            consumir(Token.Tipo.PAREN_DER);
            return nodo;
        }

        throw new RuntimeException(
            "Error sintáctico: token inesperado " + t);
    }

    // Punto de entrada del análisis
    public NodoArbol analizar() {
        NodoArbol raiz = parseExpresion();
        consumir(Token.Tipo.FIN);
        return raiz;
    }
}