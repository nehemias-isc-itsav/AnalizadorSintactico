public class NodoArbol {

    public final String valor;
    public final NodoArbol izquierdo;
    public final NodoArbol derecho;

    public NodoArbol(String valor, NodoArbol izquierdo, NodoArbol derecho) {
        this.valor     = valor;
        this.izquierdo = izquierdo;
        this.derecho   = derecho;
    }

    // Imprime el árbol visualmente con indentación
    public void imprimir(String sangria, boolean esRaiz) {
        System.out.println(sangria + (esRaiz ? "Raíz: " : "Nodo: ") + valor);
        if (izquierdo != null)
            izquierdo.imprimir(sangria + "  ├── ", false);
        if (derecho != null)
            derecho.imprimir(sangria + "  └── ", false);
    }
}