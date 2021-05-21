package arboldesicion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebastian I
 */
public class Test {

    public static void main(String args[]) {
        String child = null;
        String parent = null;
        String node = null;
        String agree = null;
        String command = null;
        System.out.println("Welcome\n___________\n");
        Tree tree = new Tree();
        Leaf root = tree.sendRootNode();
        do {
//las opciones son añadir, eliminar, guardar, imprimir y salir
            System.out.println(
                    "Insert a command(add,remove,print,save,exit):");
            command = Teclado.readString();
            if (command.equals("add")) {
                System.out.println("Insert the child's name: ");
                child = Teclado.readString();
                System.out.println("Insert the parent's name: ");
                parent = Teclado.readString();
                if (parent.equals("null")) {
//si el archivo readText.txt esta vacio, entonces
//se inserta un hijo
                    root.insertChild(new Leaf(child, root));
                    tree.setControl(true);
                } else {
//si el archivo readText.txt no esta vacio, entonces
//el programa ha de buscar al padre e insertar al hijo
                    root = tree.addNewChild(child, parent, root);
                }
                if ((tree.isControl()) & (tree.metodoyaexiste() == false)) {
//control=true
                    System.out.println("The new member was sucessfully added");
                    tree.setControl(false);
                } else if ((tree.isControl() == false) & (tree.metodoyaexiste() == false)) {
                    System.out.println("\n");
                } else if (tree.metodoyaexiste()) {
                    tree.setmetodoyaexiste(false);
                    tree.setControl(false);
                    System.out.println("This node already exist");
                }
            } else if (command.equals("print")) {
// me muevo a la primera linea
                tree.getTextFile().setLength(0);
                tree.getFirstFile().setLength(0);
                tree.printChildren(root, false);
            } else if (command.equals("save")) {
                tree.getTextFile().setLength(0);
                tree.getFirstFile().setLength(0);
                tree.printChildren(root, true);
                tree.saveTreeStructureToFile(root, "out.txt", tree.getTextFile());
                //escribe el arbol estructura en el archivo out.txt
                tree.saveTreeStructureToFile(root, "readText.txt",
                        tree.getFirstFile());
//escribe el arbol estructura en el archivo readText.txt
            } else if (command.equals("remove")) {
                System.out.println("Insert the name of the node to be delete: ");
                node = Teclado.readString();
                System.out.println("All the childs of this node will be remove. Are you agree? (yes/no)");
                agree = Teclado.readString();
                if (agree.equals("no")) {
                    System.out.println("The node was no removed");
                }
                if (agree.equals("yes")) {
                    tree.removeChild(node, root);
                    if (!tree.isControl()) {
                        System.out.println("The node was remove");
                        tree.setControl(false);
                    } else {
                        System.out.println("The node was not found");
                    }
                }
            }
        } while ((!command.equals("exit")) && (!command.equals("" + "")));
        System.out.println("The program was closed. Thank you for use this program");
    }
}
