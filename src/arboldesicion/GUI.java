/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboldesicion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

/**
 *
 * @author Sebastian I
 */
public class GUI extends JFrame implements ActionListener {

    private JPanel principal, arbol, botones, h, pad, n, panelsouthAdd, panelsouthClean;
//las opciones del menu principal son add_member, remove, print y exit
    private JButton add_member, remove, Save_arbol, Print_tree, Exit;
    private JFrame addchild, clearNode;
    private JButton add, clear, cancelAdd, cancelClean;
    private JComboBox parents, nodes; //desplegable con todos los nodos del arbol
    private JTextArea area;
    private JTextField child, node;
    private JLabel lh, lp, ln;
    private int res;
    private String nameH, nameP, nameN;
    private Tree tree;
    private Leaf root;

    public GUI() {
        super("Organigrama de la empresa");
        initialize();
    }

    public static void main(String args[]) {
        GUI frame = new GUI();
        frame.setSize(700, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initialize() {
//se obtiene el contenedor asociado a la ventana
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.setBackground(Color.black);
//se crean los diferentes paneles
        principal = new JPanel();
        arbol = new JPanel();
//se crea el bordeado del área de texto,
//el área central donde se imprimen los arboles
        Box cuadro = Box.createHorizontalBox();
        area = new JTextArea(25, 50);
        area.setEditable(false);
        cuadro.add(new JScrollPane(area));
        arbol.add(cuadro);
//se crean los botones y se añaden los botones a los escuchadores
        botones = new JPanel(new GridLayout(4, 1));
        add_member = new JButton("add_member");
        add_member.addActionListener(this);
        botones.add(add_member);
        remove = new JButton("remove");
        remove.addActionListener(this);
        botones.add(remove);
        Print_tree = new JButton("Print");
        Print_tree.addActionListener(this);
        botones.add(Print_tree);
        Exit = new JButton("Exit");
        Exit.addActionListener(this);
        botones.add(Exit);
        principal.add(arbol);
        principal.add(botones);
        c.add(principal);
        principal.setBackground(Color.black);
        tree = new Tree();
        root = tree.sendRootNode();
    }

    public void add_member() {
        addchild = new JFrame("add_member_child");
        addchild.setSize(450, 350);
        addchild.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addchild.setVisible(true);
        addchild.setLayout(new GridLayout(3, 1));
        addchild.setBackground(Color.black);
        h = new JPanel();
        lh = new JLabel("Child’s name");
        child = new JTextField(20);
        h.add(lh);
        h.add(child);
        h.setBackground(Color.white);
//SE OBTIENEN LOS NODOS EXISTENTES
//se crea un array con todos los nodos que cuelgan de la raiz
        ArrayList<String> p = root.getChildren2();
//se crea un iterador para poder ir recorriendo todos los elementos
//del array
        Iterator pi = p.iterator();
        String[] ps = new String[p.size() + 1];
        ps[0] = "Root";
        int i = 1;
        while (pi.hasNext()) {
            ps[i] = ((String) pi.next());
            i++;
        }
        pad = new JPanel();
        parents = new JComboBox(ps);
        lp = new JLabel("Parent's name");
        pad.add(lp);
        pad.add(parents);
        addchild.add(h);
        addchild.add(pad);
        pad.setBackground(Color.LIGHT_GRAY);
        //CREAR BOTON DE AÑADIR EN EL SUBMENU DE AÑADIR A UN HIJO

        add = new JButton("Add");
        add.addActionListener(this);
        addchild.add(add);
//CREAR BOTON DE CANCELAR EN EL SUBMENU DE AÑADIR A UN HIJO
        cancelAdd = new JButton("Cancel");
        cancelAdd.addActionListener(this);
        addchild.add(cancelAdd);
        panelsouthAdd = new JPanel();
        panelsouthAdd.setLayout(new FlowLayout());
        panelsouthAdd.add(add);
        panelsouthAdd.add(cancelAdd);
        addchild.add(panelsouthAdd, BorderLayout.SOUTH);
        panelsouthAdd.setBackground(Color.darkGray);
    }

    public void borrar() {
        clearNode = new JFrame("Remove node");
        clearNode.setSize(350, 150);
        clearNode.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clearNode.setVisible(true);
        clearNode.setVisible(true);
        clearNode.setLayout(new GridLayout(2, 1));
        n = new JPanel();
//SE OBTIENEN LOS NODOS EXISTENTES
//para mostrar las opciones de los nodos que es posible borrar
        ArrayList<String> p = root.getChildren2();
//se crea un iterador para poder ir recorriendo todos los elementos
//del array
        Iterator pi = p.iterator();
        String[] ps = new String[p.size() + 1];
        ps[0] = "Root";
        int i = 1;
        while (pi.hasNext()) {//se pasa al siguiente
            ps[i] = ((String) pi.next());
            i++;
        }
        nodes = new JComboBox(ps);
        ln = new JLabel("Node's name");
        n.add(ln);
        n.add(nodes);
        clearNode.add(n);
        n.setBackground(Color.LIGHT_GRAY);
        clear = new JButton("Clear");
        clear.addActionListener(this);
        clearNode.add(clear);
        panelsouthClean = new JPanel();
//AGREGAR BOTON DE CANCELAR BORRAR UN NODO
        cancelClean = new JButton("Cancel");
        cancelClean.addActionListener(this);
        clearNode.add(cancelClean);
        panelsouthClean.setLayout(new FlowLayout());

        panelsouthClean.add(clear);
        panelsouthClean.add(cancelClean);
        clearNode.add(panelsouthClean, BorderLayout.SOUTH);
        panelsouthClean.setBackground(Color.DARK_GRAY);
    }

    public void crearArbol(Leaf root, Boolean command) {
//metodo Print_tree de la clase Tree.java
        String tab = "";
        for (int i = 0; i < root.getChildren().size(); i++) {
            tab = "";
            for (int j = 0; j < root.rowofChild(root); j++) {
//con este bucle se puede ver el nivel de profundidad del
//nodo.
//el programa añade una nueva tabulacion para cada nivel
                tab += '\t';
            }
            String str = tab + root.getChildren().get(i).getName();
            if (command) {
//true = actualiza el búfer que luego se utiliza para
//Print_arbol el archivo out.txt
tree.getTextFile().append(str).append(" ").append(System.getProperty("line.separator"));
//el programa añade las lineas al bufer que escribe el arbol
            } else {
//false = el programa actualiza el bufer que mas tarde
//muestra el arbol
                area.append(str + "\n");
            }
            if (!root.getChildren().get(i).getChildren().isEmpty()) {
//si el nodo tiene hijos, el programa llama de nuevo al
//metodo recursivo
                crearArbol(root.getChildren().get(i), command);
            } else {
                tree.getFirstFile().append(tree.setLineOfFirstFile(root.
                        getChildren().get(i))).append(System.getProperty("line.separator"));
// el programa obtiene la línea para add_member en el
//búfer del archivo readText.txt
            }
        }
    }

    public void Save_arbol() {
        System.out.println("saving...");
        tree.getTextFile().setLength(0);
        tree.getFirstFile().setLength(0);
        tree.printChildren(root, true);
        area.append("\n\n\n\n");
        tree.saveTreeStructureToFile(root, "out.txt", tree.getTextFile());
//escribe el arbol estructura en el archivo out.txt
        tree.saveTreeStructureToFile(root, "readText.txt", tree.getFirstFile());
//escribe el arbol estructura en el archivo readText.txt
    }

    public void Exit() {
        res = JOptionPane.showConfirmDialog(null, "¿Would you like Exit?",
                "Exit", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void exist() {
        addchild.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addchild.setVisible(false);
        JOptionPane.showMessageDialog(addchild, "This node already exists");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == Print_tree) {
            crearArbol(root, false);
            area.append("\n\n\n\n");
        }
        if (ae.getSource() == add_member) {
            add_member();
        }
        if (ae.getSource() == add) {
//este boton es el "ADD" de la ventana secundaria
            nameH = child.getText();
            nameP = parents.getSelectedItem().toString();
            boolean comprobar = false;
            //se obtienen los nodos existentes
//se crea un array con todos los nodes que cuelgan de la raiz
            ArrayList<String> p = root.getChildren2();
//se crea un iterador para poder ir recorriendo todos los elementos
//del array
            Iterator pi = p.iterator();
            String[] ps = new String[p.size() + 1];
            ps[0] = "Root";
            int i = 1;
            while (pi.hasNext()) {//se pasa al siguiente
                ps[i] = ((String) pi.next());
                i++;
            }
//compruebo si el hijo ya existia
            for (int j = 0; j < i; j++) {
                if (ps[j].equals(nameH)) {
                    comprobar = true;
                    exist();
                }
            }
            if ("Root".equals(nameP)) {
                root.insertChild(new Leaf(nameH, root));
            } else {
                root = tree.addNewChild(nameH, nameP, root);
            }
            Save_arbol();
            crearArbol(root, false);
            addchild.dispose();
//cuando se pulsa el boton "ADD" se guarda el hijo en el padre
//seleccionado
            if (comprobar == false) {
                JOptionPane.showMessageDialog(addchild, "The element was insert succesfully");
            }
        }

        if (ae.getSource() == cancelAdd) { //Accion de cerrar ventana secundaria
            addchild.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            addchild.setVisible(false);
            crearArbol(root, false);
            JOptionPane.showMessageDialog(addchild, "The tree was not modified");
        }

        if (ae.getSource() == cancelClean) { //Accion de cerrar ventana secundaria
            clearNode.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            clearNode.setVisible(false);
            crearArbol(root, false);
            JOptionPane.showMessageDialog(addchild, "The tree was not modified");
        }

        if (ae.getSource() == remove) { //pantalla principal
            borrar();
        }
        if (ae.getSource() == clear) {//este boton es el "Clear" de la segunda ventana
            nameN = nodes.getSelectedItem().toString();//nombre del nodo seleccionado
            tree.removeChild(nameN, root);
            Save_arbol();
            crearArbol(root, false);
            clearNode.dispose();
//cuando se pulsa el boton "Clear" se elimina el nodo introducido
            if (nameN.equals("Root")) { //se comprueba si el elemento eliminado es root
                JOptionPane.showMessageDialog(clearNode, "All the elements were removed");
            } else {
                JOptionPane.showMessageDialog(clearNode, "The element was remove succesfully");
            }
        }
        if (ae.getSource() == Exit) {
            Exit();
        }
    }//cierra metodo actionPerformed

}//cierra GUI
