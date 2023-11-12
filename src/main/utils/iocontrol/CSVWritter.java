package main.utils.iocontrol;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CSVWritter {
  private final StringBuilder builder = new StringBuilder();

  private CSVWritter() {}

  public static CSVWritter create(List<String> headers) {
    CSVWritter csv = new CSVWritter();
    return csv.addRow(headers);
  }

  /** Split a list by comma, and wrapped by quotations followed by "\n". */
  public CSVWritter addRow(List<String> row) {
    builder.append(row.stream().collect(Collectors.joining("\",\"", "\"", "\"")));
    builder.append("\n");
    return this;
  }

  public void writeAndClose(String filePath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    writer.write(builder.toString());
    writer.close();
  }

}