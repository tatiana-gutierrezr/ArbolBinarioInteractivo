import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinaryTreeGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JTextField textField;
    private JButton addButton, deleteButton, searchButton, editButton;
    private BinaryTree binaryTree;
    private TreePanel treePanel;
    private JScrollPane treeView;

    public BinaryTreeGUI() {
        binaryTree = new BinaryTree();
        treePanel = new TreePanel(binaryTree);

        setTitle("Árbol binario interactivo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        textField = new JTextField(10);
        addButton = new JButton("Agregar");
        deleteButton = new JButton("Eliminar");
        searchButton = new JButton("Buscar");
        editButton = new JButton("Editar");

        addButton.setBackground(new Color(128, 0, 128)); //Morado
        addButton.setForeground(Color.WHITE); //Texto blanco

        deleteButton.setBackground(new Color(128, 0, 128)); //Morado
        deleteButton.setForeground(Color.WHITE); //Texto blanco

        searchButton.setBackground(new Color(128, 0, 128)); //Morado
        searchButton.setForeground(Color.WHITE); //Texto blanco

        editButton.setBackground(new Color(128, 0, 128)); //Morado
        editButton.setForeground(Color.WHITE); //Texto blanco

        treeView = new JScrollPane(treePanel);
        treeView.setPreferredSize(new Dimension(600, 400));

        //Establecer scrollbars
        treeView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        treeView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(textField);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(searchButton);
        panel.add(editButton);

        add(panel, BorderLayout.NORTH);
        add(treeView, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(textField.getText().trim());
                    binaryTree.insert(value);
                    treePanel.revalidate();
                    treePanel.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BinaryTreeGUI.this, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(textField.getText().trim());
                    binaryTree.delete(value);
                    treePanel.revalidate();
                    treePanel.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BinaryTreeGUI.this, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(textField.getText().trim());
                    boolean found = binaryTree.search(value);
                    if (found) {
                        JOptionPane.showMessageDialog(BinaryTreeGUI.this, "El número " + value + " está en el árbol.", "Búsqueda Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(BinaryTreeGUI.this, "El número " + value + " no está en el árbol.", "Búsqueda Fallida", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BinaryTreeGUI.this, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int oldValue = Integer.parseInt(textField.getText().trim());
                    String newValueStr = JOptionPane.showInputDialog(BinaryTreeGUI.this, "Inserte el nuevo valor para el nodo " + oldValue + ":");
                    if (newValueStr != null) {
                        int newValue = Integer.parseInt(newValueStr.trim());
                        binaryTree.edit(oldValue, newValue);
                        treePanel.revalidate();
                        treePanel.repaint();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BinaryTreeGUI.this, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BinaryTreeGUI();
            }
        });
    }

    class TreePanel extends JPanel {

        private static final long serialVersionUID = 1L;
        private BinaryTree tree;
        private static final int NODE_RADIUS = 15;
        private static final int VERTICAL_SPACE = 50; //Espacio vertical entre nodos
        private static final int HORIZONTAL_SPACE = 50; //Espacio horizontal constante entre nodos
        private int treeWidth, treeHeight;

        public TreePanel(BinaryTree tree) {
            this.tree = tree;
            treeWidth = 0;
            treeHeight = 0;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            calculateTreeDimensions(tree.getRoot());
            if (tree.getRoot() != null) {
                drawTree(g, getWidth() / 2, 30, tree.getRoot(), Integer.MAX_VALUE, Integer.MIN_VALUE);
            }
        }

        private void calculateTreeDimensions(TreeNode node) {
            if (node != null) {
                treeWidth = Math.max(treeWidth, nodeCount(node) * HORIZONTAL_SPACE);
                treeHeight = Math.max(treeHeight, depth(node) * VERTICAL_SPACE);
            }
        }

        private int nodeCount(TreeNode node) {
            if (node == null) return 0;
            return 1 + nodeCount(node.left) + nodeCount(node.right);
        }

        private int depth(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(depth(node.left), depth(node.right));
        }

        private void drawTree(Graphics g, int x, int y, TreeNode node, int minX, int maxX) {
            if (node != null) {
                int childY = y + VERTICAL_SPACE;

                //Dibujar la ruta antes de cambiar el color
                if (node.left != null) {
                    int childX = x - HORIZONTAL_SPACE * (node.left.right != null ? 2 : 1);
                    g.setColor(new Color(128, 0, 128)); //Restaurar el color de la línea a morado
                    g.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
                    drawTree(g, childX, childY, node.left, minX, x - HORIZONTAL_SPACE);
                }
                if (node.right != null) {
                    int childX = x + HORIZONTAL_SPACE * (node.right.left != null ? 2 : 1);
                    g.setColor(new Color(128, 0, 128)); //Restaurar el color de la línea a morado
                    g.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
                    drawTree(g, childX, childY, node.right, x + HORIZONTAL_SPACE, maxX);
                }

                //Establecer el color de relleno del círculo (morado)
                g.setColor(new Color(128, 0, 128)); // Morado
                g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

                //Establecer el color del borde del círculo (morado)
                g.setColor(new Color(128, 0, 128)); // Morado
                g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

                //Establecer el color del texto (blanco)
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(node.data), x - 6, y + 4);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(treeWidth + 2 * NODE_RADIUS, treeHeight + 2 * NODE_RADIUS);
        }
    }
}