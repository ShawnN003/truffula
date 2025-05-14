import java.io.PrintStream;

/**
 * A utility class for printing colored text to a PrintStream using ANSI escape codes.
 * 
 * The ColorPrinter allows setting a current color and printing messages in that color
 * to the specified output stream. The color can be reset after each print or kept active
 * based on the provided parameters.
 * 
 * Colors are managed using the ConsoleColor enum, which defines standard ANSI color codes.
 * 
 * Example Usage:
 * 
 * ColorPrinter printer = new ColorPrinter(System.out);
 * printer.setCurrentColor(ConsoleColor.RED);
 * printer.println("This is red text");
 * 
 * The printed text will appear in the terminal with the specified colors if the terminal supports ANSI codes.
 */
public class ColorPrinter {
  /**
   * The current color used for printing.
   */
  private ConsoleColor currentColor;

  /**
   * The PrintStream to which the colored output will be written.
   */
  private final PrintStream printStream;

  /**
   * Returns the current color set for the printer.
   * 
   * @return the current ConsoleColor
   */
  public ConsoleColor getCurrentColor() {
    return currentColor;
  }

  /**
   * Sets the current color for the printer. All subsequent print operations will use this color
   * until it is changed or reset.
   * 
   * @param color the ConsoleColor to set as the current color
   */
  public void setCurrentColor(ConsoleColor color) {
    this.currentColor = color;
  }

  /**
   * Prints the given message followed by a newline in the current color.
   * The color is reset to default after printing.
   * 
   * @param message the message to print
   */
  public void println(String message) {
    println(message, true);
  }

  /**
   * Prints the given message followed by a newline in the current color.
   * Optionally resets the color after printing based on the reset parameter.
   * 
   * @param message the message to print
   * @param reset   if true, resets the color after printing; if false, keeps the current color
   */
  public void println(String message, boolean reset) {
    print(message + System.lineSeparator(), reset);
  }

  /**
   * Prints the given message in the current color without appending a newline.
   * The color is reset to default after printing.
   * 
   * @param message the message to print
   */
  public void print(String message) {
    print(message, true);
  }

  /**
   * Prints the given message in the current color without appending a newline.
   * Optionally resets the color after printing based on the reset parameter.
   * 
   * @param message the message to print
   * @param reset   if true, resets the color after printing; if false, keeps the current color
   */
  public void print(String message, boolean reset) {
    // if(reset == true)
    // {
      
    // }
    // else if(reset == false)
    // {
      
    // }
  }

  /**
   * Constructs a ColorPrinter with the specified PrintStream.
   * The default color is set to ConsoleColor.WHITE.
   * 
   * @param printStream the PrintStream to which output will be written
   */
  public ColorPrinter(PrintStream printStream) {
    this(printStream, ConsoleColor.WHITE);
  }

  /**
   * Constructs a ColorPrinter with the specified PrintStream and initial color.
   * 
   * @param printStream the PrintStream to which output will be written
   * @param color       the initial ConsoleColor to use for printing
   */
  public ColorPrinter(PrintStream printStream, ConsoleColor color) {
    this.printStream = printStream;
    this.currentColor = color;
  }
}
