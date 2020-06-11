package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class StaticTree {

    private static void createAndShowGUI() {

        JFrame frame = new JFrame("Static tree");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");
        createNodes(top);
        JTree tree = new JTree(top);
        tree.addTreeSelectionListener((TreeSelectionEvent e) -> System.out.println(e.getPath()));
        pane.add(new JScrollPane(tree));

        frame.pack();
        frame.setVisible(true);
    }

    private static void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode node;
        DefaultMutableTreeNode leaf;

        node = new DefaultMutableTreeNode("Node1");
        top.add(node);

        leaf = new DefaultMutableTreeNode("Leaf1");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Leaf2");
        node.add(leaf);

        node = new DefaultMutableTreeNode("Node2");
        top.add(node);

        leaf = new DefaultMutableTreeNode("Leaf1");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Leaf2");
        node.add(leaf);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(StaticTree::createAndShowGUI);
    }
}