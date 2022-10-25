package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author mahmoud.magdy
 */
public class NewInvoiceDialog extends JDialog {

  private JTextField customerNameField;
  private JTextField invalidDateField;
  private JLabel customerNameLable;
  private JLabel invalidDateLable;
  private JButton okButton1;
  private JButton cancelButton1;

  public NewInvoiceDialog(InvoicesManager frame) {
    customerNameLable = new JLabel("Customer Name : ");
    customerNameField = new JTextField(20);

    invalidDateLable = new JLabel("Invoice Date : ");
    invalidDateField = new JTextField(20);

    okButton1 = new JButton("OK");
    okButton1.setActionCommand("createInvoiceOK");
    okButton1.addActionListener(frame.getController());

    cancelButton1 = new JButton("Cancel");
    cancelButton1.setActionCommand("createInvoiceCancel");
    cancelButton1.addActionListener(frame.getController());

    setLayout(new GridLayout(3, 2));

    add(invalidDateLable);
    add(invalidDateField);
    add(customerNameLable);
    add(customerNameField);
    add(okButton1);
    add(cancelButton1);
    pack();
  }

  public JTextField getCustNameField() {
    return customerNameField;
  }

  public JTextField getInvDateField() {
    return invalidDateField;
  }
}
