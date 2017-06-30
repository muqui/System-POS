/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Component;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author mq12
 */
public class DerechaTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        setText(Objects.toString(obj, ""));
        setHorizontalAlignment(SwingConstants.RIGHT);
        return this;
    }
}
