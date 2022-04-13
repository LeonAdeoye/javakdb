package kx.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import kx.Connection;

public class GridViewer {
    public static class KxTableModel extends AbstractTableModel {
        private Connection.Flip flip;
        public void setFlip(Connection.Flip data) {
            this.flip = data;
        }

        public int getRowCount() {
            return Array.getLength(flip.y[0]);
        }

        public int getColumnCount() {
            return flip.y.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return Connection.at(flip.y[columnIndex], rowIndex);
        }

        @Override
        public String getColumnName(int columnIndex) {
            return flip.x[columnIndex];
        }
    }

    public static void main(String[] args) {
        KxTableModel model = new KxTableModel();
        Connection Connection = null;
        try {
            Connection = new Connection("localhost", 5001,System.getProperty("user.name")+":mypassword");
            String query="([]date:.z.D;time:.z.T;sym:10?`8;price:`float$10?500.0;size:10?100)";
            model.setFlip((Connection.Flip) Connection.k(query));
            JTable table = new JTable(model);
            table.setGridColor(Color.BLACK);
            String title = "kdb+ Example - "+model.getRowCount()+" Rows";
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
            frame.setSize(300, 300);
            frame.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(GridViewer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (Connection != null) {
                try {
                    Connection.close();
                }
                catch (IOException ex) {
                    // ingnore exception
                }
            }
        }
    }
}
