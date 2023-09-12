//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.textfield;

import com.tool.ui.combobox.TableFilterCombobox;
import com.tool.ui.table.TableColumnMapping;
import com.tool.ui.table.TableRowFilterSet;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class TableFilterTextField extends JTextField {
    public TableFilterTextField() {
    }

    public void initializeEvent(final TableRowFilterSet tableRowFilterSet, final TableFilterCombobox combobox) {
        this.getDocument().addDocumentListener(new DocumentListener() {
            protected void changeFilter(DocumentEvent event) {
                Document document = event.getDocument();

                try {
                    String word = document.getText(0, document.getLength());
                    tableRowFilterSet.filter((TableColumnMapping)combobox.getSelectedItem(), word);
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }

            public void changedUpdate(DocumentEvent e) {
                this.changeFilter(e);
            }

            public void insertUpdate(DocumentEvent e) {
                this.changeFilter(e);
            }

            public void removeUpdate(DocumentEvent e) {
                this.changeFilter(e);
            }
        });
    }
}
