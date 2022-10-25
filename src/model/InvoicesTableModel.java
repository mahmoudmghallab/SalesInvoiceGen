package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mahmoud.magdy
 */
public class InvoicesTableModel extends AbstractTableModel {

  private ArrayList<InvoiceHeader> invoicesList;
  private String[] columns = { "No.", "Date", "Customer", "Total" };

  public InvoicesTableModel(ArrayList<InvoiceHeader> invoices) {
    this.invoicesList = invoices;
  }

  @Override
  public int getRowCount() {
    return invoicesList.size();
  }

  @Override
  public int getColumnCount() {
    return columns.length;
  }

  @Override
  public String getColumnName(int column) {
    return columns[column];
  }

  @Override
  public Object getValueAt(int row, int column) {
    InvoiceHeader invoice = invoicesList.get(row);
    switch (column) {
      case 0:
        return invoice.getInvoiceNumber();
      case 1:
        return invoice.getInvoiceDate();
      case 2:
        return invoice.getCustomerName();
      case 3:
        return invoice.getInvoiceTotal();
      default:
        return "";
    }
  }
}
