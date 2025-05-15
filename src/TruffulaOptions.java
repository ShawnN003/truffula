import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents configuration options for controlling how a directory tree is displayed.
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
 * Exceptions:
 * - Throws IllegalArgumentException if:
 *     - Unknown flags are provided.
 *     - The path argument is missing.
 * 
 * - Throws FileNotFoundException if:
 *     - The specified directory does not exist.
 *     - The path points to a file instead of a directory.
 */
public class TruffulaOptions  {
  private final File root;
  private final boolean showHidden;
  private final boolean useColor;

  /**
   * Returns the root directory from which the directory tree will be printed.
   *
   * @return the root directory as a File object
   */
  public File getRoot() {
    return root;
  }

  /**
   * Indicates whether hidden files should be included when printing the directory tree.
   *
   * @return true if hidden files should be shown; false otherwise
   */
  public boolean isShowHidden() {
    return showHidden;
  }

  @Override
  public String toString() {
    return "TruffulaOptions [root=" + root + ", showHidden=" + showHidden + ", useColor=" + useColor + "]";
  }

  /**
   * Indicates whether color should be used when printing the directory tree.
   * 
   * If false, all output is printed in white.
   *
   * @return true if color should be used; false otherwise
   */
  public boolean isUseColor() {
    return useColor;
  }

  /**
   * Constructs a TruffulaOptions object based on command-line arguments.
   * 
   * Supported Flags:
   * - -h   : Show hidden files (defaults to false).
   * - -nc  : Do not use color (uses color by default).
   * 
   * The last argument must be the path to the directory.
   * 
   * @param args command-line arguments in the format [-h] [-nc] path
   * @throws IllegalArgumentException if unknown arguments are provided or the path is missing
   * @throws FileNotFoundException if the directory cannot be found or if the path points to a file
   */
  public TruffulaOptions(String[] args) throws IllegalArgumentException, FileNotFoundException {
    
    boolean hiddenCheck = false;
    boolean colorCheck = true;
    String location = "";


    for(int i = 0; i < args.length; i++)
    {
      if(args[i].equals("-h"))
      {
        hiddenCheck = true;
      }
      else if(args[i].equals("-nc"))
      {
        colorCheck = false;
      }      
      else if(location == "")
      {
         location = args[i];
      }
      else{
        throw new IllegalArgumentException("Invalid Argument Passed!");
      }
    }
    File roots = new File(location);

    
    if(!roots.exists())
    {
      throw new FileNotFoundException("No File Exists!");
    }
    if(!roots.isDirectory())
    {
      throw new FileNotFoundException("Invalid Directory!");
    }
    root = roots;
    showHidden = hiddenCheck;
    useColor = colorCheck;
  }

  /**
   * Constructs a TruffulaOptions object with explicit values.
   * 
   * @param root       the root directory for the directory tree
   * @param showHidden whether hidden files should be displayed
   * @param useColor   whether color should be used in the output
   */
  public TruffulaOptions(File root, boolean showHidden, boolean useColor) {
    this.root = root;
    this.showHidden = showHidden;
    this.useColor = useColor;
  }
}