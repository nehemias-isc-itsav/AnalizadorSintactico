public class Token {

    public enum Tipo {
        NUMERO,
        IDENTIFICADOR,
        MAS, MENOS, MULT, DIV,
        PAREN_IZQ, PAREN_DER,
        FIN
    }

    public final Tipo tipo;
    public final String valor;

    public Token(Tipo tipo, String valor) {
        this.tipo  = tipo;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "[" + tipo + " : '" + valor + "']";
    }
}