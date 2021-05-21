/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboldesicion;

/**
 *
 * @author Sebastian I
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Tree {

    private boolean control = false;
// se escribe la estructura arbol en el archivo out.txt
    private final StringBuffer textFile = new StringBuffer();
//escribe la estructura arbol en el archivo readText.txt
    private final StringBuffer firstFile = new StringBuffer();
    boolean yaexiste = false;

    public Tree() {
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public StringBuffer getTextFile() {// se da el archivo out.txt
        return textFile;
    }

    public StringBuffer getFirstFile() {// se da el archivo read.txt
        return firstFile;
    }

    public boolean metodoyaexiste() {
        return yaexiste;
    }

    public void setmetodoyaexiste(boolean yaexiste) {
        this.yaexiste = yaexiste;
    }

    public Leaf addNewChild(String childName, String parentName, Leaf rootNode) {
        for (int i = 0; i < rootNode.getChildren().size(); i++) {
            Leaf parentNode = rootNode.getChildren().get(i);
            for (int j = 0; j < parentNode.getChildren().size(); j++) {
                Leaf hijo = parentNode.getChildren().get(j);
                if (hijo.getName().equals(childName)) {
                    control = true;
                    yaexiste = true;
                    j = parentNode.getChildren().size();
                    i = rootNode.getChildren().size();
                }
            }
            //se recorren todos los hijos de esa hoja y el nodo en el que este
            //en ese momento sera el padre

            if (parentNode.getName().equals(parentName) && control == false
                    && yaexiste == false) {
// se comparan los nombres
                if (!control) { //if(control=false){
                    System.out.println("Adding...");
                    parentNode.insertChild(new Leaf(childName,
                            parentNode));
// si es el nombre que el programa está buscando,
//inserta el nodo
                    control = false;
                }
            }
            if (!parentNode.getChildren().isEmpty()) {
//si el nodo tiene hijos, el programa llama al metodo recursivo
                addNewChild(childName, parentName, parentNode);
            }
        }
        return rootNode;
    }

    public void removeChild(String Node, Leaf rootNode) {
        if (Node.equals("Root")) {
//este if borra la primera rama que cuelga de root si elijo borrar //root
            while (!rootNode.getChildren().isEmpty()) {
                int n = 0;
                rootNode.getChildren().remove(n);
            }
        } else {
            for (int i = 0; i < rootNode.getChildren().size(); i++) {
                Leaf parentNode = rootNode.getChildren().get(i);
//se recorren uno a uno los nodos del arbol
                if (parentNode.getName().equals(Node)) {
// se comparan los nombres
                    rootNode.getChildren().remove(i);
// si es el nombre que el programa está buscando,
// elimina el nodo
                    control = true;
// se encuentra un nodo con el mismo nombre que el
// usuario ha introducido
                }
                removeChild(Node, parentNode);//llamada metodo recursivo
            }
        }
        control = false;
    }

    public void printChildren(Leaf rootNode, Boolean command) {
        String tab = "";
        for (int i = 0; i < rootNode.getChildren().size(); i++) {
            tab = "";
            for (int j = 0; j < rootNode.rowofChild(rootNode); j++) {
//con este bucle se puede ver la profundidad que hay.
//el programa añade una nueva tabulacion para cada nivel
                tab += '\t';
            }
            String str = tab + rootNode.getChildren().get(i).getName();

            if (command) {
//true = actualiza el búfer que luego se utiliza para imprimir el
//archivo out.txt
                textFile.append(str).append(" ").append(System.getProperty("line.separator"));
//el programa añade las lineas al bufer que escribe el arbol
            } else {
//false = el programa actualiza el bufer que mas tarde muestra el
//arbol
                System.out.println(str);
            }
            if (!rootNode.getChildren().get(i).getChildren().isEmpty()) {
//si el nodo tiene hijos,el programa llama de nuevo al metodo
//recursivo
                printChildren(rootNode.getChildren().get(i), command);
            } else {
                firstFile.append(setLineOfFirstFile(rootNode.getChildren().get(i))).append(System.getProperty("line.separator"));
// el programa obtiene la línea para añadir en el búfer del archivo
//readText.txt
            }
        }
    }

    public void saveTreeStructureToFile(Leaf rootNode, String filename, StringBuffer strBuffer) {
        try {
            // se crea el archivo
            FileWriter fstream = new FileWriter(filename);
            try (BufferedWriter out = new BufferedWriter(fstream)) {
                out.write(strBuffer.toString());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public String setLineOfFirstFile(Leaf node) {
//se obtiene la linea que se añade en el archivo readText.txt con los
//predecesores del nodo
        String line = "";
//se crea un objeto ArrayList
        ArrayList<String> lineList = new ArrayList<>();
        while (!"RootNode".equals(node.getName())) {
//se continua mientras el bucle no obtenga todos los predecesores
            lineList.add(node.getName());
            node = node.getParent();
        }
        Collections.reverse(lineList);
//el bucle obtiene los nombres de los nodos desde el ultimo hasta el primero.
//Es necesario invertirlo.
        for (int i = 0; i < lineList.size(); i++) {
            line += lineList.get(i) + ",";
        }
        return line.substring(0, line.length() - 1);
//se coge la linea sin la ultima coma
    }

    public Leaf sendRootNode() {
        File file = new File("readText.txt");
        BufferedReader reader = null;
        //se crea una nueva hoja, que sera la raiz
        Leaf root = new Leaf("RootNode", null);
        Leaf broot = root; //se hace una copia para ejecutar el arbol
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            // se repite hasta que todas las lineas son leidas
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                for (String field : fields) {
                    Leaf leaf = new Leaf(field, broot);
                    //se crea una nueva hoja
                    int containsIndex = broot.ContainsNode(leaf);
                    //se comprueba si el padre contiene esta hoja
                    if (containsIndex != -1) {
                        //si el padre contiene este hijo, a continuación se busca la
//fila de ese hijo, y temporalmente hace que sea la nueva
//raíz
                        broot = broot.getChildren().get(containsIndex);
//containsIndex = obtiene el indice de la hoja hijo
                    } else {
                        broot.insertChild(leaf);
                        //si el padre no contiene esta hoja, entonces se añade
//esta hoja como hijo de este padre
                        broot = leaf;
//esta hoja es el nuevo padre
                    }
                }
                broot = root;
                //para la nueva línea del archivo, se comienza desde el principio a
//hacer el mismo proceso
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no ha sido encontrado");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return root;
    }
}
