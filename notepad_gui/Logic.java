package notepad_gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.undo.UndoManager;
import javax.swing.text.DefaultStyledDocument;

public class Logic extends JFrame implements ActionListener {

    JMenuBar menubar = new JMenuBar();

    UndoManager undoManager = new UndoManager();

    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenu help = new JMenu("Help");

    JMenuItem newfile = new JMenuItem("New File");
    JMenuItem openfile = new JMenuItem("Open File");
    JMenuItem savefile = new JMenuItem("Save File");
    JMenuItem print = new JMenuItem("Print");
    JMenuItem exit = new JMenuItem("Exit");

    JMenuItem cut = new JMenuItem("Cut");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");

    JMenuItem selectall = new JMenuItem("Select all");

    JMenuItem undo = new JMenuItem("Undo");

    JMenuItem redo = new JMenuItem("Redo");

    JMenuItem about = new JMenuItem("About");

    JTextArea textArea = new JTextArea();

    DefaultStyledDocument doc = new DefaultStyledDocument();

    public Logic() {

        setTitle("Notepad");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea.setDocument(doc);
        doc.addUndoableEditListener(undoManager);

        add(menubar);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);
        setJMenuBar(menubar);

        file.add(newfile);
        newfile.addActionListener(this);
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));

        file.add(openfile);
        openfile.addActionListener(this);
        openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));

        file.add(savefile);
        savefile.addActionListener(this);
        savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

        file.add(print);
        print.addActionListener(this);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));

        file.add(exit);
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));

        edit.add(copy);
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

        edit.add(paste);
        paste.addActionListener(this);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

        edit.add(cut);
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

        edit.add(selectall);
        selectall.addActionListener(this);
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

        help.add(about);
        about.addActionListener(this);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));

        edit.add(undo);
        undo.addActionListener(this);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));

        edit.add(redo);
        redo.addActionListener(this);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        setTitle("Untitled.txt - Notepad");

        // ImageIcon icon = new
        // ImageIcon(getClass().getResource("D:\\desktop\notepad.jpeg"));
        // setIconImage(icon.getImage());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equalsIgnoreCase("Undo")) {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Redo")) {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        }

        else if (e.getActionCommand().equalsIgnoreCase("New File")) {

            this.setTitle("Untitled.txt - Notepad");

            textArea.setText(null);

        } else if (e.getActionCommand().equalsIgnoreCase("Open File")) {

            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Only Text File(.txt)", "txt");

            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(textFilter);

            int action = fileChooser.showSaveDialog(null);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            } else {
                try {

                    BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                    textArea.read(reader, null);
                    File fyl = fileChooser.getSelectedFile();

                    this.setTitle(fyl.getName() + " - Notepad");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } else if (e.getActionCommand().equalsIgnoreCase("Save File")) {

            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Only Text File(.txt)", "txt");

            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(textFilter);

            int action = fileChooser.showSaveDialog(null);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            } else {

                String fileName = fileChooser.getSelectedFile().getAbsolutePath().toString();
                if (!fileName.contains(".txt")) {
                    fileName = fileName + ".txt";
                }

                try {

                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

                    File fyl = fileChooser.getSelectedFile();

                    this.setTitle(fyl.getName() + " - Notepad");

                    textArea.write(writer);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } else if (e.getActionCommand().equalsIgnoreCase("Print")) {
            try {
                textArea.print();
            } catch (PrinterException ex) {
                Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getActionCommand().equalsIgnoreCase("Exit")) {

            System.exit(0);

        } else if (e.getActionCommand().equalsIgnoreCase("Cut")) {

            textArea.cut();

        } else if (e.getActionCommand().equalsIgnoreCase("copy")) {

            textArea.copy();

        } else if (e.getActionCommand().equalsIgnoreCase("paste")) {
            textArea.paste();

        } else if (e.getActionCommand().equalsIgnoreCase("About")) {

            JOptionPane.showMessageDialog(this, "Created by: Hitesh Mori and Milap Patel", "Notepad",
                    JOptionPane.INFORMATION_MESSAGE);

        } else if (e.getActionCommand().equalsIgnoreCase("Select All")) {

            textArea.selectAll();

        }

    }
}