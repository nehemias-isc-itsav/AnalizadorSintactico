import java.util.ArrayList;
import java.util.List;

public class GeneradorCodigo {

    // Representa un cuádruplo: (operador, arg1, arg2, resultado)
    public static class Cuadruplo {
        public final String operador;
        public final String arg1;
        public final String arg2;
        public final String resultado;

        public Cuadruplo(String operador, String arg1, 
                         String arg2, String resultado) {
            this.operador  = operador;
            this.arg1      = arg1;
            this.arg2      = arg2;
            this.resultado = resultado;
        }

        @Override
        public String toString() {
            return "(" + operador + ", " + arg1 + 
                   ", " + arg2 + ", " + resultado + ")";
        }
    }

    private final List<Cuadruplo> cuadruplos = new ArrayList<>();
    private int contadorTemp = 1; // Para generar t1, t2, t3...

    // Genera un nuevo nombre de variable temporal único
    private String nuevaTemp() {
        return "t" + contadorTemp++;
    }

    // Recorre el árbol y genera código intermedio
    // Devuelve el nombre que representa el resultado del nodo
    public String generar(NodoArbol nodo) {

        // Caso base: nodo hoja (variable o número)
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return nodo.valor;
        }

        // Caso recursivo: nodo operador
        String resultadoIzq = generar(nodo.izquierdo);
        String resultadoDer = generar(nodo.derecho);

        String temp = nuevaTemp();
        cuadruplos.add(new Cuadruplo(
            nodo.valor,      // operador
            resultadoIzq,    // arg1
            resultadoDer,    // arg2
            temp             // resultado
        ));

        return temp;
    }

    // Genera notación postfija recorriendo el árbol
    public String generarPostfija(NodoArbol nodo) {
        if (nodo == null) return "";

        // Caso base: nodo hoja
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return nodo.valor;
        }

        // Primero izquierdo, luego derecho, luego operador
        String izq = generarPostfija(nodo.izquierdo);
        String der = generarPostfija(nodo.derecho);
        return izq + " " + der + " " + nodo.valor;
    }

    public List<Cuadruplo> getCuadruplos() {
        return cuadruplos;
    }
}