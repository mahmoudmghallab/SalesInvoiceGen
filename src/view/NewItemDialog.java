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
public class NewItemDialog extends JDialog {

  private JTextField itemNameField;
  private JTextField itemCountField;
  private JTextField itemPriceField;
  private JLabel itemNameLable;
  private JLabel itemCountLable;
  private JLabel itemPriceLable;
  private JButton okButton;
  private JButton cancelButton;

  public NewItemDialog(InvoicesManager invoiceManager) {
    itemNameField = new JTextField(20);
    itemNameLable = new JLabel("Item Name");

    itemCountField = new JTextField(20);
    itemCountLable = new JLabel("Item Count");

    itemPriceField = new JTextField(20);
    itemPriceLable = new JLabel("Item Price");

    okButton = new JButton("OK");
    okButton.setActionCommand("createItemOK");
    okButton.addActionListener(invoiceManager.getController());

    cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand("createLineCancel");
    cancelButton.addActionListener(invoiceManager.getController());

    setLayout(new GridLayout(6, 2));

    add(itemNameLable);
    add(itemNameField);
    add(itemCountLable);
    add(itemCountField);
    add(itemPriceLable);
    add(itemPriceField);
    add(okButton);
    add(cancelButton);
    pack();
  }

  public JTextField getItemNameField() {
    return itemNameField;
  }

  public JTextField getItemCountField() {
    return itemCountField;
  }

  public JTextField getItemPriceField() {
    return itemPriceField;
  }
}
