package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.InvoiceHeader;
import model.InvoiceLine;
import model.InvoiceLinesTableModel;
import model.InvoicesTableModel;
import view.InvoicesManager;
import view.NewInvoiceDialog;
import view.NewItemDialog;

/**
 *
 * @author mahmoud.magdy
 */
public class InvoiceController
  implements ActionListener, ListSelectionListener {

  private InvoicesManager invoicesManager;
  private NewInvoiceDialog newInvoiceDialog;
  private NewItemDialog newItemDialog;

  public InvoiceController(InvoicesManager invoicesManager) {
    this.invoicesManager = invoicesManager;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    System.out.println("Action: " + actionCommand);
    switch (actionCommand) {
      case "Load File":
        loadInvoice();
        break;
      case "Save File":
        saveInvoice();
        break;
      case "Create New Invoice":
        openNewInvoiceDialog();
        break;
      case "Delete Invoice":
        deleteInvoice();
        break;
      case "Create New Item":
        openNewItemDialog();
        break;
      case "Delete Item":
        deleteItem();
        break;
      case "createInvoiceCancel":
        dismissNewInvoiceDialog();
        break;
      case "createInvoiceOK":
        createInvoice();
        break;
      case "createItemOK":
        createItem();
        break;
      case "createLineCancel":
        dismissNewItemDialog();
        break;
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    int selectedIndex = invoicesManager.getInvoiceTable().getSelectedRow();
    if (selectedIndex != -1) {
      System.out.println("row " + selectedIndex + " is selected.");
      InvoiceHeader currentInvoice = invoicesManager
        .getInvoicesList()
        .get(selectedIndex);
      invoicesManager
        .getInvoiceNumLabel()
        .setText("" + currentInvoice.getInvoiceNumber());
      invoicesManager
        .getInvoiceDateLabel()
        .setText(currentInvoice.getInvoiceDate());
      invoicesManager
        .getCustomerNameLabel()
        .setText(currentInvoice.getCustomerName());
      invoicesManager
        .getInvoiceTotalLabel()
        .setText("" + currentInvoice.getInvoiceTotal());
      InvoiceLinesTableModel linesTableModel = new InvoiceLinesTableModel(
        currentInvoice.getInvoicelines()
      );
      invoicesManager.getItemTable().setModel(linesTableModel);
      linesTableModel.fireTableDataChanged();
    }
  }

  // Invoice
  private void saveInvoice() {
    try {
      ArrayList<InvoiceHeader> invoices = invoicesManager.getInvoicesList();
      String headers = "";
      String lines = "";
      for (InvoiceHeader invoice : invoices) {
        String invCSV = invoice.getExcel();
        headers += invCSV;
        headers += "\n";
        for (InvoiceLine line : invoice.getInvoicelines()) {
          String lineCSV = line.getExcel();
          lines += lineCSV;
          lines += "\n";
        }
      }
      JFileChooser jfc = new JFileChooser();
      int result = jfc.showSaveDialog(invoicesManager);
      if (result == JFileChooser.APPROVE_OPTION) {
        File headerFile = jfc.getSelectedFile();
        FileWriter headerFileWriter = new FileWriter(headerFile);
        headerFileWriter.write(headers);
        headerFileWriter.flush();
        headerFileWriter.close();
        result = jfc.showSaveDialog(invoicesManager);
        if (result == JFileChooser.APPROVE_OPTION) {
          File lineFile = jfc.getSelectedFile();
          FileWriter lineFileWriter = new FileWriter(lineFile);
          lineFileWriter.write(lines);
          lineFileWriter.flush();
          lineFileWriter.close();
        }
      }
    } catch (Exception ex) {
      System.out.println("saveInvoice exception: " + ex.getMessage());
    }
  }

  private void loadInvoice() {
    try {
      JFileChooser fc = new JFileChooser();
      int result = fc.showOpenDialog(invoicesManager);
      if (result == JFileChooser.APPROVE_OPTION) {
        File headerFile = fc.getSelectedFile();
        Path headerPath = Paths.get(headerFile.getAbsolutePath());
        List<String> headerLines = Files.readAllLines(headerPath);
        System.out.println("Invoices read successfully.");
        ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
        for (String headerLine : headerLines) {
          String[] headerParts = headerLine.split(",");
          int invoiceNum = Integer.parseInt(headerParts[0]);
          String invoiceDate = headerParts[1];
          String customerName = headerParts[2];
          InvoiceHeader invoice = new InvoiceHeader(
            invoiceNum,
            invoiceDate,
            customerName
          );
          invoicesArray.add(invoice);
        }
        result = fc.showOpenDialog(invoicesManager);
        if (result == JFileChooser.APPROVE_OPTION) {
          File lineFile = fc.getSelectedFile();
          Path linePath = Paths.get(lineFile.getAbsolutePath());
          List<String> lineLines = Files.readAllLines(linePath);
          System.out.println("Items read successfully.");
          for (String lineLine : lineLines) {
            String lineParts[] = lineLine.split(",");
            int invoiceNum = Integer.parseInt(lineParts[0]);
            String itemName = lineParts[1];
            double itemPrice = Double.parseDouble(lineParts[2]);
            int count = Integer.parseInt(lineParts[3]);
            InvoiceHeader inv = null;
            for (InvoiceHeader invoice : invoicesArray) {
              if (invoice.getInvoiceNumber() == invoiceNum) {
                inv = invoice;
                break;
              }
            }
            InvoiceLine line = new InvoiceLine(itemPrice, itemName, count);
            line.setInvoiceHeader(inv);
            inv.getInvoicelines().add(line);
          }
        }
        invoicesManager.setInvoices(invoicesArray);
        InvoicesTableModel invoicesTableModel = new InvoicesTableModel(
          invoicesArray
        );
        invoicesManager.setInvoicesTableModel(invoicesTableModel);
        invoicesManager.getInvoiceTable().setModel(invoicesTableModel);
        invoicesManager.getInvoicesTableModel().fireTableDataChanged();
      }
    } catch (Exception ex) {
      System.out.println("loadInvoice exception: " + ex.getMessage());
    }
  }

  private void openNewInvoiceDialog() {
    newInvoiceDialog = new NewInvoiceDialog(invoicesManager);
    newInvoiceDialog.setVisible(true);
  }

  private void dismissNewInvoiceDialog() {
    newInvoiceDialog.setVisible(false);
    newInvoiceDialog.dispose();
    newInvoiceDialog = null;
  }

  private void createInvoice() {
    String date = newInvoiceDialog.getInvDateField().getText();
    int num = invoicesManager.getNextInvoiceNumber();
    InvoiceHeader invoice = new InvoiceHeader(num, date, date);
    invoicesManager.getInvoicesList().add(invoice);
    invoicesManager.getInvoicesTableModel().fireTableDataChanged();
    newInvoiceDialog.setVisible(false);
    newInvoiceDialog.dispose();
    newInvoiceDialog = null;
  }

  private void deleteInvoice() {
    int selectedInvoice = invoicesManager.getInvoiceTable().getSelectedRow();
    if (selectedInvoice != -1) {
      invoicesManager.getInvoicesList().remove(selectedInvoice);
      invoicesManager.getInvoicesTableModel().fireTableDataChanged();
    }
  }

  // Items
  private void openNewItemDialog() {
    newItemDialog = new NewItemDialog(invoicesManager);
    newItemDialog.setVisible(true);
  }

  private void dismissNewItemDialog() {
    newItemDialog.setVisible(false);
    newItemDialog.dispose();
    newItemDialog = null;
  }

  private void createItem() {
    String item = newItemDialog.getItemNameField().getText();
    String countString = newItemDialog.getItemCountField().getText();
    int count = Integer.parseInt(countString);

    String priceString = newItemDialog.getItemPriceField().getText();
    double price = Double.parseDouble(priceString);

    int selectedInvoice = invoicesManager.getInvoiceTable().getSelectedRow();
    if (selectedInvoice != -1) {
      InvoiceHeader invoice = invoicesManager
        .getInvoicesList()
        .get(selectedInvoice);
      InvoiceLine line = new InvoiceLine(price, item, count);
      invoice.getInvoicelines().add(line);
      InvoiceLinesTableModel invoiceLinesTableModel = (InvoiceLinesTableModel) invoicesManager
        .getItemTable()
        .getModel();
      invoiceLinesTableModel.fireTableDataChanged();
      invoicesManager.getInvoicesTableModel().fireTableDataChanged();
    }
    newItemDialog.setVisible(false);
    newItemDialog.dispose();
    newItemDialog = null;
  }

  private void deleteItem() {
    int selectedRow = invoicesManager.getItemTable().getSelectedRow();
    if (selectedRow != -1) {
      InvoiceLinesTableModel invoiceLinesTableModel = (InvoiceLinesTableModel) invoicesManager
        .getItemTable()
        .getModel();
      invoiceLinesTableModel.getItemsList().remove(selectedRow);
      invoiceLinesTableModel.fireTableDataChanged();
      invoicesManager.getInvoicesTableModel().fireTableDataChanged();
    }
  }
}
