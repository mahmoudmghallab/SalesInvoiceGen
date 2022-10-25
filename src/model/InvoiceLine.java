package model;

/**
 *
 * @author mahmoud.magdy
 */
public class InvoiceLine {

  private double itemPrice;
  private String itemName;
  private int count;
  private InvoiceHeader invoiceHeader;

  public InvoiceLine(double itemPrice, String itemName, int count) {
    this.itemPrice = itemPrice;
    this.itemName = itemName;
    this.count = count;
  }

  public double getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(double itemPrice) {
    this.itemPrice = itemPrice;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getCount() {
    return count;
  }

  public double getTotalAmount() {
    return itemPrice * count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public InvoiceHeader getInvoiceHeader() {
    return invoiceHeader;
  }

  public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
    this.invoiceHeader = invoiceHeader;
  }

  @Override
  public String toString() {
    return (
      "InvoiceLine{" +
      "itemPrice=" +
      itemPrice +
      ", itemName=" +
      itemName +
      ", count=" +
      count +
      ", invoiceHeader=" +
      invoiceHeader +
      '}'
    );
  }

  public String getExcel() {
    return (
      invoiceHeader.getInvoiceNumber() +
      "," +
      itemName +
      "," +
      itemPrice +
      "," +
      count
    );
  }
}
