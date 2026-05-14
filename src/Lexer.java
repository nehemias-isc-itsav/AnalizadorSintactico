import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String texto;
    private int posicion;

    public Lexer(String texto) {
        this.texto    = texto;
        this.posicion = 0;
    }

    public List<Token> tokenizar() {
        List<Token> tokens = new ArrayList<>();

        while (posicion < texto.length()) {
            char c = texto.charAt(posicion);

            // Ignorar espacios
            if (Character.isWhitespace(c)) {
                posicion++;
                continue;
            }

            // Números
            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (posicion < texto.length()
                       && Character.isDigit(texto.charAt(posicion))) {
                    num.append(texto.charAt(posicion));
                    posicion++;
                }
                tokens.add(new Token(Token.Tipo.NUMERO, num.toString()));
                continue;
            }

            // Identificadores (variables)
            if (Character.isLetter(c)) {
                StringBuilder id = new StringBuilder();
                while (posicion < texto.length()
                       && Character.isLetterOrDigit(texto.charAt(posicion))) {
                    id.append(texto.charAt(posicion));
                    posicion++;
                }
                tokens.add(new Token(Token.Tipo.IDENTIFICADOR, id.toString()));
                continue;
            }

            // Operadores y paréntesis
            switch (c) {
                case '+': tokens.add(new Token(Token.Tipo.MAS,       "+")); break;
                case '-': tokens.add(new Token(Token.Tipo.MENOS,     "-")); break;
                case '*': tokens.add(new Token(Token.Tipo.MULT,      "*")); break;
                case '/': tokens.add(new Token(Token.Tipo.DIV,       "/")); break;
                case '(': tokens.add(new Token(Token.Tipo.PAREN_IZQ, "(")); break;
                case ')': tokens.add(new Token(Token.Tipo.PAREN_DER, ")")); break;
                default:
                    throw new RuntimeException(
                        "Carácter desconocido: '" + c + "'");
            }
            posicion++;
        }

        // Token de fin generado automáticamente
        tokens.add(new Token(Token.Tipo.FIN, ""));
        return tokens;
    }
}