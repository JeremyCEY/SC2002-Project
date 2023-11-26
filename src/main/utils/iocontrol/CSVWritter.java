package main.utils.iocontrol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for writing CSV files and returning their
 * contents as a list of rows, each represented as a list of
 * strings.
 */
public class CSVWritter {
  private final StringBuilder builder = new StringBuilder();

  private CSVWritter() {
  }

  /**
   * Creates a new instance of CSVWriter and adds a row of headers.
   *
   * This static factory method is used to conveniently initialize a CSVWriter
   * with a list of header values. The headers are added as the first row in the
   * CSV document.
   *
   * @param headers A list of strings representing the header values for the CSV.
   * @return A new CSVWriter instance with the provided headers added as the first row.
   */
  public static CSVWritter create(List<String> headers) {
    CSVWritter csv = new CSVWritter();
    return csv.addRow(headers);
  }

  /**
   * Appends a row to the CSVWriter. The provided list of strings is joined by commas,
   * wrapped in double quotations, and followed by a newline character.
   *
   * @param row A list of strings representing the values of a CSV row.
   * @return This CSVWriter instance to allow method chaining.
   */
  public CSVWritter addRow(List<String> row) {
    builder.append(row.stream().collect(Collectors.joining("\",\"", "\"", "\"")));
    builder.append("\n");
    return this;
  }

  /**
   * Writes the accumulated content to a specified file and closes the writer.
   *
   * This method takes a file path as a parameter, creates a BufferedWriter to
   * write the content of the CSV document to the specified file, and then closes
   * the writer. It is essential to handle IOException, which may occur during
   * file I/O operations.
   *
   * @param filePath The path to the file where the CSV content will be written.
   * @throws IOException If an I/O error occurs while writing to or closing the file.
   */
  public void writeAndClose(String filePath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    writer.write(builder.toString());
    writer.close();
  }

}