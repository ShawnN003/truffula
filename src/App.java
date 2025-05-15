public class App {
  
  /**
   * Application for printing a directory tree
   * 
   * Options include:
   * - Whether to show hidden files.
   * - Whether to use colored output.
   * - The root directory from which to begin printing the tree.
   * 
   * Hidden files are identified by names that start with a dot (e.g., ".hidden.txt").
   * Color output is enabled by default, but can be disabled using flags.
   * 
   * Usage Example:
   * 
   * Arguments Format: [-h] [-nc] path
   * 
   * Flags:
   * - -h   : Show hidden files (defaults to false).
   * - -nc  : Do not use color (color is enabled by default).
   * 
   * Path:
   * - The absolute or relative path to the directory whose contents will be printed.
   * 
   * Behavior:
   * - If color is disabled, all text will be printed in white.
   * - The order of flags is unimportant.
   * - The path argument is mandatory.
   * 
   * Examples:
   * 
   * 1. ['-nc', '-h', '/path/to/directory']
   *    → Don't use color, do show hidden files.
   * 
   * 2. ['-h', '-nc', '/path/to/directory']
   *    → Don't use color, do show hidden files (order of flags is ignored).
   * 
   * 3. ['/path/to/directory']
   *    → Use color, don't show hidden files.
   * 
   * Error messages will be shown for illegal arguments or a not found file
   */
  public static void main(String[] args) throws Exception {
    // TODO: Implement this
    // You should create a TruffulaOptions object using the args and
    // pass it to a new TruffulaPrinter that uses System.out
    // Then, call printTree on the TruffulaPrinter
  }
}
