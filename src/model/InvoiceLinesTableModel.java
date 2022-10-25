package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mahmoud.magdy
 */
public class InvoiceLinesTableModel extends AbstractTableModel {

  private ArrayList<InvoiceLine> invoicesLines;
  private String[] colummns = {
    "No.",
    "Item Name",
    "Item Price",
    "Count",
    "Item Total",
  };

  public InvoiceLinesTableModel(ArrayList<InvoiceLine> lines) {
    this.invoicesLines = lines;
  }

  public ArrayList<InvoiceLine> getItemsList() {
    return invoicesLines;
  }

  @Override
  public int getRowCount() {
    return invoicesLines.size();
  }

  @Override
  public int getColumnCount() {
    return colummns.length;
  }

  @Override
  public String getColumnName(int cols1) {
    return colummns[cols1];
  }

  @Override
  public Object getValueAt(int row, int column) {
    InvoiceLine line = invoicesLines.get(row);
    switch (column) {
      case 0:
        return line.getInvoiceHeader().getInvoiceNumber();
      case 1:
        return line.getItemName();
      case 2:
        return line.getItemPrice();
      case 3:
        return line.getCount();
      case 4:
        return line.getTotalAmount();
      default:
        return "";
    }
  }
}
