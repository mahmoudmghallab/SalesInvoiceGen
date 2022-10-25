package model;

import java.util.ArrayList;

/**
 *
 * @author mahmoud.magdy
 */
public class InvoiceHeader {

  private int invoiceNumber;
  private String invoiceDate;
  private String customerName;
  private ArrayList<InvoiceLine> invoiceLines;

  public InvoiceHeader(
    int invoiceNum,
    String invoicedate,
    String customerName
  ) {
    this.invoiceNumber = invoiceNum;
    this.invoiceDate = invoicedate;
    this.customerName = customerName;
    this.invoiceLines = new ArrayList<>();
  }

  public int getInvoiceNumber() {
    var invoiceNum = this.invoiceNumber;
    return invoiceNum;
  }

  public void setInvoiceNumber(int invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public String getInvoiceDate() {
    return invoiceDate;
  }

  public void setInvoiceDate(String invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public ArrayList<InvoiceLine> getInvoicelines() {
    return invoiceLines;
  }

  public void setInvoicelines(ArrayList<InvoiceLine> InvoiceLines) {
    this.invoiceLines = InvoiceLines;
  }

  public double getInvoiceTotal() {
    double total = 0.0;
    for (InvoiceLine line : getInvoicelines()) {
      total += line.getTotalAmount();
    }
    return total;
  }

  @Override
  public String toString() {
    return (
      "InvoiceHeader{" +
      "invoiceNum=" +
      invoiceNumber +
      ", invoicedate=" +
      invoiceDate +
      ", customerName=" +
      customerName +
      ", Invoicelines=" +
      invoiceLines +
      '}'
    );
  }

  public String getExcel() {
    return invoiceNumber + "," + invoiceDate + "," + customerName;
  }
}
