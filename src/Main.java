public class Main {

    public static void main(String[] args) {
        String[] expresiones = {
            "a + b * c",
            "a + (b * c)",
            "(a + b) * c",
            "42 + x * 3"
        };

        for (String expr : expresiones) {
            System.out.println("==========================================");
            System.out.println("Expresión:  " + expr);
            System.out.println("------------------------------------------");

            try {
                // FASE 1: Análisis Léxico
                Lexer lexer  = new Lexer(expr);
                var   tokens = lexer.tokenizar();

                // FASE 2: Análisis Sintáctico
                Parser    parser = new Parser(tokens);
                NodoArbol arbol  = parser.analizar();
                System.out.println("Árbol:");
                arbol.imprimir("", true);

                // FASE 3: Generación de Código Intermedio
                GeneradorCodigo gen = new GeneradorCodigo();

                String postfija = gen.generarPostfija(arbol);
                System.out.println("\nNotación Postfija: " + postfija);

                gen.generar(arbol);
                System.out.println("Cuádruplos:");
                for (GeneradorCodigo.Cuadruplo c : gen.getCuadruplos()) {
                    System.out.println("  " + c);
                }

            } catch (RuntimeException e) {
                System.out.println("ERROR: " + e.getMessage());
            }

            System.out.println();
        }
    }


}