//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jdatepicker;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.ComponentColorDefaults.Key;
import org.jdatepicker.constraints.DateSelectionConstraint;

public class JDatePicker extends JComponent implements DatePicker
{
    private static final long                serialVersionUID = 2814777654384974503L;
    private              String              dateFormat;
    private              JPopupMenu          popupMenu;
    private              JPopupMenu          popupWindow;
    private              JFormattedTextField formattedTextField;
    private              JButton             button;
    private              JDatePanel          datePanel;

    public JDatePicker()
    {
        this(new JDatePanel());
    }

    public JDatePicker(Calendar value)
    {
        this(new JDatePanel(value));
    }

    public JDatePicker(Date value)
    {
        this(new JDatePanel(value));
    }

    public JDatePicker(java.sql.Date value)
    {
        this(new JDatePanel(value));
    }

    public JDatePicker(DateModel<?> model)
    {
        this(new JDatePanel(model));
    }

    public JDatePicker(DateModel<?> model, String dateFormat)
    {
        this(new JDatePanel(model), dateFormat);
    }

    private JDatePicker(JDatePanel datePanel)
    {
        this((JDatePanel) datePanel, (String) null);
    }

    private JDatePicker(JDatePanel datePanel, String dateFormat)
    {
        this.datePanel = datePanel;
        this.popupWindow = null;
        datePanel.setBorder(BorderFactory.createLineBorder(getColors().getColor(Key.POPUP_BORDER)));
        InternalEventHandler internalEventHandler = new InternalEventHandler();
        SpringLayout                     layout               = new SpringLayout();
        this.setLayout(layout);
        this.dateFormat = dateFormat;
        Object formatter;
        if (dateFormat != null)
        {
            DateLabelFormatter f = new DateLabelFormatter();
            f.setDateFormatter(new SimpleDateFormat(dateFormat));
            formatter = f;
        }
        else
        {
            formatter = new DateComponentFormatter();
        }

        this.formattedTextField = new JFormattedTextField((AbstractFormatter) formatter);
        DateModel<?> model = datePanel.getModel();
        this.setTextFieldValue(this.formattedTextField, model.getYear(), model.getMonth(), model.getDay(), model.isSelected());
        this.formattedTextField.setEditable(false);
        this.add(this.formattedTextField);
        layout.putConstraint("West", this.formattedTextField, 0, "West", this);
        layout.putConstraint("South", this, 0, "South", this.formattedTextField);
        this.button = new JButton();
        this.button.setFocusable(true);
        Icon icon = ComponentIconDefaults.getInstance().getPopupButtonIcon();
        this.button.setIcon(icon);
        this.button.setMargin(new Insets(1, 2, 1, 1));
        if (icon == null)
        {
            this.button.setText("...");
        }
        else
        {
            this.button.setText("");
        }

        this.add(this.button);
        layout.putConstraint("West", this.button, 1, "East", this.formattedTextField);
        layout.putConstraint("East", this, 0, "East", this.button);
        layout.putConstraint("South", this, 0, "South", this.button);
        int h = (int) this.button.getPreferredSize().getHeight();
        int w = (int) datePanel.getPreferredSize().getWidth();
        this.button.setPreferredSize(new Dimension(h, h));
        this.formattedTextField.setPreferredSize(new Dimension(w - h - 1, h));
        this.addHierarchyBoundsListener(internalEventHandler);
        this.button.addActionListener(internalEventHandler);
        this.formattedTextField.addPropertyChangeListener("value", internalEventHandler);
        datePanel.addActionListener(internalEventHandler);
        datePanel.getModel().addChangeListener(internalEventHandler);
        long eventMask = 501L;
        Toolkit.getDefaultToolkit().addAWTEventListener(internalEventHandler, eventMask);
    }

    private static ComponentColorDefaults getColors()
    {
        return ComponentColorDefaults.getInstance();
    }

    public void addActionListener(ActionListener actionListener)
    {
        this.datePanel.addActionListener(actionListener);
    }

    public void removeActionListener(ActionListener actionListener)
    {
        this.datePanel.removeActionListener(actionListener);
    }

    public DateModel<?> getModel()
    {
        return this.datePanel.getModel();
    }

    public void setTextEditable(boolean editable)
    {
        this.formattedTextField.setEditable(editable);
    }

    public boolean isTextEditable()
    {
        return this.formattedTextField.isEditable();
    }

    public void setButtonFocusable(boolean focusable)
    {
        this.button.setFocusable(focusable);
    }

    public boolean getButtonFocusable()
    {
        return this.button.isFocusable();
    }

    public DatePanel getJDateInstantPanel()
    {
        return this.datePanel;
    }

    public JFormattedTextField getFormattedTextField()
    {
        return this.formattedTextField;
    }

    public JButton getButton()
    {
        return this.button;
    }

    public String getDateFormat()
    {
        return this.dateFormat;
    }

    private void showPopupWindow()
    {
        try
        {
            this.formattedTextField.commitEdit();
        }
        catch (ParseException var2)
        {
        }

        if (this.popupMenu == null)
        {
            JPopupMenu pop = new JPopupMenu();
            pop.setPreferredSize(new Dimension(250, 250));
            pop.add(this.datePanel);
            this.popupMenu = pop;
        }

        this.popupMenu.show(this, 0, this.getHeight());
        this.popupWindow = this.popupMenu;
    }

    private void hidePopupWindow()
    {
        if (this.popupMenu != null)
        {
            this.popupMenu.setVisible(false);
            this.popupWindow = null;
        }

    }

    public void showPopup()
    {
        this.showPopupWindow();
    }

    public void hidePopup()
    {
        this.hidePopupWindow();
    }

    private Set<Component> getAllComponents(Component component)
    {
        Set<Component> children = new HashSet();
        children.add(component);
        if (component instanceof Container)
        {
            Container   container  = (Container) component;
            Component[] components = container.getComponents();

            for (int i = 0; i < components.length; ++i)
            {
                children.addAll(this.getAllComponents(components[i]));
            }
        }

        return children;
    }

    public boolean isDoubleClickAction()
    {
        return this.datePanel.isDoubleClickAction();
    }

    public boolean isShowYearButtons()
    {
        return this.datePanel.isShowYearButtons();
    }

    public void setDoubleClickAction(boolean doubleClickAction)
    {
        this.datePanel.setDoubleClickAction(doubleClickAction);
    }

    public void setShowYearButtons(boolean showYearButtons)
    {
        this.datePanel.setShowYearButtons(showYearButtons);
    }

    private void setTextFieldValue(JFormattedTextField textField, int year, int month, int day, boolean isSelected)
    {
        if (!isSelected)
        {
            textField.setValue((Object) null);
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            calendar.set(14, 0);
            textField.setValue(calendar);
        }

    }

    public void addDateSelectionConstraint(DateSelectionConstraint constraint)
    {
        this.datePanel.addDateSelectionConstraint(constraint);
    }

    public void removeDateSelectionConstraint(DateSelectionConstraint constraint)
    {
        this.datePanel.removeDateSelectionConstraint(constraint);
    }

    public void removeAllDateSelectionConstraints()
    {
        this.datePanel.removeAllDateSelectionConstraints();
    }

    public Set<DateSelectionConstraint> getDateSelectionConstraints()
    {
        return this.datePanel.getDateSelectionConstraints();
    }

    public int getTextfieldColumns()
    {
        return this.formattedTextField.getColumns();
    }

    public void setTextfieldColumns(int columns)
    {
        this.formattedTextField.setColumns(columns);
    }

    public void setVisible(boolean aFlag)
    {
        if (!aFlag)
        {
            this.hidePopup();
        }

        super.setVisible(aFlag);
    }

    public void setEnabled(boolean enabled)
    {
        this.button.setEnabled(enabled);
        this.datePanel.setEnabled(enabled);
        this.formattedTextField.setEnabled(enabled);
        super.setEnabled(enabled);
    }

    private class InternalEventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener, PropertyChangeListener, AWTEventListener
    {
        private InternalEventHandler()
        {
        }

        public void ancestorMoved(HierarchyEvent arg0)
        {
            JDatePicker.this.hidePopupWindow();
        }

        public void ancestorResized(HierarchyEvent arg0)
        {
            JDatePicker.this.hidePopupWindow();
        }

        public void actionPerformed(ActionEvent arg0)
        {
            if (arg0.getSource() == JDatePicker.this.button)
            {
                if (JDatePicker.this.popupWindow != null && JDatePicker.this.popupWindow.isVisible())
                {
                    JDatePicker.this.hidePopupWindow();
                }
                else
                {
                    JDatePicker.this.formattedTextField.requestFocus();
                    JDatePicker.this.showPopupWindow();
                }
            }
            else if (arg0.getSource() == JDatePicker.this.datePanel)
            {
                JDatePicker.this.hidePopupWindow();
            }

        }

        public void stateChanged(ChangeEvent arg0)
        {
            if (arg0.getSource() == JDatePicker.this.datePanel.getModel())
            {
                DateModel<?> model = JDatePicker.this.datePanel.getModel();
                JDatePicker.this.setTextFieldValue(JDatePicker.this.formattedTextField, model.getYear(), model.getMonth(), model.getDay(), model.isSelected());
            }

        }

        public void propertyChange(PropertyChangeEvent evt)
        {
            if (evt.getOldValue() != null || evt.getNewValue() != null)
            {
                if (evt.getOldValue() == null || !evt.getOldValue().equals(evt.getNewValue()))
                {
                    if (JDatePicker.this.formattedTextField.isEditable())
                    {
                        if (evt.getNewValue() != null)
                        {
                            Calendar  value = (Calendar) evt.getNewValue();
                            DateModel model = new UtilCalendarModel(value);
                            if (!JDatePicker.this.datePanel.checkConstraints(model))
                            {
                                JDatePicker.this.formattedTextField.setValue(evt.getOldValue());
                                return;
                            }

                            JDatePicker.this.datePanel.getModel().setDate(value.get(1), value.get(2), value.get(5));
                            JDatePicker.this.datePanel.getModel().setSelected(true);
                        }

                        if (evt.getNewValue() == null)
                        {
                            JDatePicker.this.getModel().setSelected(false);
                        }

                    }
                }
            }
        }

        public void eventDispatched(AWTEvent event)
        {
            if (500 == event.getID() && event.getSource() != JDatePicker.this.button)
            {
                Set<Component> components   = JDatePicker.this.getAllComponents(JDatePicker.this.datePanel);
                boolean        clickInPopup = false;
                Iterator       var4         = components.iterator();

                while (var4.hasNext())
                {
                    Component component = (Component) var4.next();
                    if (event.getSource() == component)
                    {
                        clickInPopup = true;
                    }
                }

                if (!clickInPopup)
                {
                    JDatePicker.this.hidePopup();
                }
            }

        }
    }
}
