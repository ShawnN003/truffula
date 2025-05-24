import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TruffulaPrinterTest {

    /**
     * Checks if the current operating system is Windows.
     *
     * This method reads the "os.name" system property and checks whether it
     * contains the substring "win", which indicates a Windows-based OS.
     * 
     * You do not need to modify this method.
     *
     * @return true if the OS is Windows, false otherwise
     */
    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    /**
     * Creates a hidden file in the specified parent folder.
     * 
     * The filename MUST start with a dot (.).
     *
     * On Unix-like systems, files prefixed with a dot (.) are treated as hidden.
     * On Windows, this method also sets the DOS "hidden" file attribute.
     * 
     * You do not need to modify this method, but you SHOULD use it when creating hidden files
     * for your tests. This will make sure that your tests work on both Windows and UNIX-like systems.
     *
     * @param parentFolder the directory in which to create the hidden file
     * @param filename the name of the hidden file; must start with a dot (.)
     * @return a File object representing the created hidden file
     * @throws IOException if an I/O error occurs during file creation or attribute setting
     * @throws IllegalArgumentException if the filename does not start with a dot (.)
     */
    private static File createHiddenFile(File parentFolder, String filename) throws IOException {
        if(!filename.startsWith(".")) {
            throw new IllegalArgumentException("Hidden files/folders must start with a '.'");
        }
        File hidden = new File(parentFolder, filename);
        hidden.createNewFile();
        if(isWindows()) {
            Path path = Paths.get(hidden.toURI());
            Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
        }
        return hidden;
    }

    @Test
    public void testPrintTree_ExactOutput_WithCustomPrintStream(@TempDir File tempDir) throws IOException {
        // Build the example directory structure:
        // myFolder/
        //    .hidden.txt
        //    Apple.txt
        //    banana.txt
        //    Documents/
        //       images/
        //          Cat.png
        //          cat.png
        //          Dog.png
        //       notes.txt
        //       README.md
        //    zebra.txt

        // Create "myFolder"
        File myFolder = new File(tempDir, "myFolder");
        assertTrue(myFolder.mkdir(), "myFolder should be created");

        // Create visible files in myFolder
        File apple = new File(myFolder, "Apple.txt");
        File banana = new File(myFolder, "banana.txt");
        File zebra = new File(myFolder, "zebra.txt");
        apple.createNewFile();
        banana.createNewFile();
        zebra.createNewFile();

        // Create a hidden file in myFolder

        // Create subdirectory "Documents" in myFolder
        File documents = new File(myFolder, "Documents");
        assertTrue(documents.mkdir(), "Documents directory should be created");

        // Create files in Documents
        File readme = new File(documents, "README.md");
        File notes = new File(documents, "notes.txt");
        readme.createNewFile();
        notes.createNewFile();

        // Create subdirectory "images" in Documents
        File images = new File(documents, "images");
        assertTrue(images.mkdir(), "images directory should be created");

        // Create files in images
        File cat = new File(images, "cat.png");
        File dog = new File(images, "Dog.png");
        cat.createNewFile();
        dog.createNewFile();

        // Set up TruffulaOptions with showHidden = false and useColor = true
        TruffulaOptions options = new TruffulaOptions(myFolder, true, true);

        // Capture output using a custom PrintStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        // Instantiate TruffulaPrinter with custom PrintStream
        TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

        // Call printTree (output goes to printStream)
        printer.printTree();

        // Retrieve printed output
        String output = baos.toString();
        String nl = System.lineSeparator();

        // Build expected output with exact colors and indentation
        ConsoleColor reset = ConsoleColor.RESET;
        ConsoleColor white = ConsoleColor.WHITE;
        ConsoleColor purple = ConsoleColor.PURPLE;
        ConsoleColor yellow = ConsoleColor.YELLOW;

        StringBuilder expected = new StringBuilder();
        expected.append(white).append("myFolder/").append(nl).append(reset);
        expected.append(purple).append("   Apple.txt").append(nl).append(reset);
        expected.append(purple).append("   banana.txt").append(nl).append(reset);
        expected.append(purple).append("   Documents/").append(nl).append(reset);
        expected.append(yellow).append("      images/").append(nl).append(reset);
        expected.append(white).append("         cat.png").append(nl).append(reset);
        expected.append(white).append("         Dog.png").append(nl).append(reset);
        expected.append(yellow).append("      notes.txt").append(nl).append(reset);
        expected.append(yellow).append("      README.md").append(nl).append(reset);
        expected.append(purple).append("   zebra.txt").append(nl).append(reset);

        // Assert that the output matches the expected output exactly
        assertEquals(expected.toString(), output);
    }

    @Test
    public void testPrintTree_CustomNewTest(@TempDir File tempDir) throws IOException {
        // Build the example directory structure:
        // myFolder/
        //    Apple.txt
        //    banana.txt
        //    Documents/
        //       images/
        //          Cat.png
        //          cat.png
        //          Dog.png
        //       notes.txt
        //       README.md
        //    zebra.txt

        // Create "myFolder"
        File myFolder = new File(tempDir, "myFolder");
        assertTrue(myFolder.mkdir(), "myFolder should be created");

        // Create subdirectory "Documents" in myFolder
        File documents = new File(myFolder, "Documents");
        assertTrue(documents.mkdir(), "Documents directory should be created");

        // Create visible files in myFolder
        File apple = new File(documents, "Apple.txt");
        File banana = new File(documents, "banana.txt");
        File zebra = new File(myFolder, "zebra.txt");
        apple.createNewFile();
        banana.createNewFile();
        zebra.createNewFile();

        // Create a hidden file in myFolder


        // Create files in Documents
        File readme = new File(documents, "README.md");
        File notes = new File(documents, "notes.txt");
        
        readme.createNewFile();
        notes.createNewFile();

        // Create subdirectory "images" in Documents
        File images = new File(documents, "images");
        assertTrue(images.mkdir(), "images directory should be created");

        // Create files in images
        File cat = new File(images, "cat.png");
        File dog = new File(images, "Dog.png");
        cat.createNewFile();
        dog.createNewFile();

        // Set up TruffulaOptions with showHidden = false and useColor = true
        TruffulaOptions options = new TruffulaOptions(myFolder, false, true);

        // Capture output using a custom PrintStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        // Instantiate TruffulaPrinter with custom PrintStream
        TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

        // Call printTree (output goes to printStream)
        printer.printTree();

        // Retrieve printed output
        String output = baos.toString();
        String nl = System.lineSeparator();

        // Build expected output with exact colors and indentation
        ConsoleColor reset = ConsoleColor.RESET;
        ConsoleColor white = ConsoleColor.WHITE;
        ConsoleColor purple = ConsoleColor.PURPLE;
        ConsoleColor yellow = ConsoleColor.YELLOW;
        

        StringBuilder expected = new StringBuilder();
        expected.append(white).append("myFolder/").append(nl).append(reset);
        expected.append(purple).append("   Documents/").append(nl).append(reset);
        expected.append(yellow).append("      Apple.txt").append(nl).append(reset);
        expected.append(yellow).append("      banana.txt").append(nl).append(reset);
        expected.append(yellow).append("      images/").append(nl).append(reset);
        expected.append(white).append("         cat.png").append(nl).append(reset);
        expected.append(white).append("         Dog.png").append(nl).append(reset);
        expected.append(yellow).append("      notes.txt").append(nl).append(reset);
        expected.append(yellow).append("      README.md").append(nl).append(reset);
        expected.append(purple).append("   zebra.txt").append(nl).append(reset);

        // Assert that the output matches the expected output exactly
        assertEquals(expected.toString(), output);
    }
    @Test
    public void testPrintTree_OneFolderTest(@TempDir File tempDir) throws IOException {
        // Build the example directory structure:
        // myFolder/


        // Create "myFolder"
        File myFolder = new File(tempDir, "myFolder");
        assertTrue(myFolder.mkdir(), "myFolder should be created");

        // Set up TruffulaOptions with showHidden = false and useColor = true
        TruffulaOptions options = new TruffulaOptions(myFolder, false, true);

        // Capture output using a custom PrintStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        // Instantiate TruffulaPrinter with custom PrintStream
        TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

        // Call printTree (output goes to printStream)
        printer.printTree();

        // Retrieve printed output
        String output = baos.toString();
        String nl = System.lineSeparator();

        // Build expected output with exact colors and indentation
        ConsoleColor reset = ConsoleColor.RESET;
        ConsoleColor white = ConsoleColor.WHITE;

        StringBuilder expected = new StringBuilder();
        expected.append(white).append("myFolder/").append(nl).append(reset);

        // Assert that the output matches the expected output exactly
        assertEquals(expected.toString(), output);
    }
    @Test
    public void testPrintTree_NoFolderTest(@TempDir File tempDir) throws IOException {

        File myFolder = new File(tempDir, " ");
        

        // Set up TruffulaOptions with showHidden = false and useColor = true
        TruffulaOptions options = new TruffulaOptions(myFolder, false, true);

        // Capture output using a custom PrintStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        // Instantiate TruffulaPrinter with custom PrintStream
        TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

        // Call printTree (output goes to printStream)
        printer.printTree();

        // Retrieve printed output
        String output = baos.toString();
        String nl = System.lineSeparator();

        // Build expected output with exact colors and indentation
        ConsoleColor reset = ConsoleColor.RESET;
        ConsoleColor white = ConsoleColor.WHITE;

        StringBuilder expected = new StringBuilder();
        expected.append(white).append(" /").append(nl).append(reset);

        // Assert that the output matches the expected output exactly
        assertEquals(expected.toString(), output);
}

 @Test
    public void testPrintTree_TestingOnlyFilesOutput(@TempDir File tempDir) throws IOException {

        File testFile3 = new File(tempDir,"cat.txt" );
        File testFile2 = new File(tempDir,"dog.txt" );
        File testFile4 = new File(tempDir,"pig.txt" );

        testFile2.createNewFile();
        testFile3.createNewFile();
        testFile4.createNewFile();
        

        // Set up TruffulaOptions with showHidden = false and useColor = true
        TruffulaOptions options = new TruffulaOptions(tempDir, false, true);

        // Capture output using a custom PrintStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        // Instantiate TruffulaPrinter with custom PrintStream
        TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

        // Call printTree (output goes to printStream)
        printer.printTree();

        // Retrieve printed output
        String output = baos.toString();
        String nl = System.lineSeparator();

        // Build expected output with exact colors and indentation
        ConsoleColor reset = ConsoleColor.RESET;
        ConsoleColor white = ConsoleColor.WHITE;
        ConsoleColor purple = ConsoleColor.PURPLE;
        ConsoleColor yellow = ConsoleColor.YELLOW;
        StringBuilder expected = new StringBuilder();
       
        // Assert that the output matches the expected output exactly
    
        expected.append(white).append(tempDir.getName() + '/').append(nl).append(reset);
        expected.append(purple).append("   cat.txt").append(nl).append(reset);
        expected.append(purple).append("   dog.txt").append(nl).append(reset);
        expected.append(purple).append("   pig.txt").append(nl).append(reset);


        assertEquals(expected.toString(), output);
}
@Test
public void testPrintTree_HiddenFileTester(@TempDir File tempDir) throws IOException {

    // Create "myFolder"
    File myFolder = new File(tempDir, "myFolder");
    assertTrue(myFolder.mkdir(), "myFolder should be created");

    // Create visible files in myFolder
    File first = new File(myFolder, "Will.txt");
    File second = new File(myFolder, "This.txt");
    File third = new File(myFolder, "Work.txt");
    first.createNewFile();
    second.createNewFile();
    third.createNewFile();

    // Create a hidden file in myFolder
    createHiddenFile(myFolder, ".hidden.txt");


    // Create files in Documents

    // Set up TruffulaOptions with showHidden = false and useColor = true
    TruffulaOptions options = new TruffulaOptions(myFolder, true, true);

    // Capture output using a custom PrintStream
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);

    // Instantiate TruffulaPrinter with custom PrintStream
    TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

    // Call printTree (output goes to printStream)
    printer.printTree();

    // Retrieve printed output
    String output = baos.toString();
    String nl = System.lineSeparator();

    // Build expected output with exact colors and indentation
    ConsoleColor reset = ConsoleColor.RESET;
    ConsoleColor white = ConsoleColor.WHITE;
    ConsoleColor purple = ConsoleColor.PURPLE;
    ConsoleColor yellow = ConsoleColor.YELLOW;

    StringBuilder expected = new StringBuilder();
    expected.append(white).append("myFolder/").append(nl).append(reset);
    expected.append(purple).append("   This.txt").append(nl).append(reset);
    expected.append(purple).append("   Will.txt").append(nl).append(reset);
    expected.append(purple).append("   Work.txt").append(nl).append(reset);

    assertEquals(expected.toString(), output);
}

@Test
public void testPrintTree_MultipleHiddenFileTester(@TempDir File tempDir) throws IOException {

    // Create "myFolder"
    File myFolder = new File(tempDir, "myFolder");
    assertTrue(myFolder.mkdir(), "myFolder should be created");

    // Create visible files in myFolder
    File first = new File(myFolder, "Hi.txt");
    File second = new File(myFolder, "There.txt");
    File third = new File(myFolder, "Summer.txt");
    first.createNewFile();
    second.createNewFile();
    third.createNewFile();

    // Create a hidden file in myFolder
    createHiddenFile(myFolder, ".hidden.txt");
    createHiddenFile(myFolder, ".unseen.txt");
    createHiddenFile(myFolder, ".hide.txt");




    // Create files in Documents

    // Set up TruffulaOptions with showHidden = false and useColor = true
    TruffulaOptions options = new TruffulaOptions(myFolder, true, true);

    // Capture output using a custom PrintStream
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);

    // Instantiate TruffulaPrinter with custom PrintStream
    TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

    // Call printTree (output goes to printStream)
    printer.printTree();

    // Retrieve printed output
    String output = baos.toString();
    String nl = System.lineSeparator();

    // Build expected output with exact colors and indentation
    ConsoleColor reset = ConsoleColor.RESET;
    ConsoleColor white = ConsoleColor.WHITE;
    ConsoleColor purple = ConsoleColor.PURPLE;
    ConsoleColor yellow = ConsoleColor.YELLOW;

    StringBuilder expected = new StringBuilder();
    expected.append(white).append("myFolder/").append(nl).append(reset);
    expected.append(purple).append("   Hi.txt").append(nl).append(reset);
     expected.append(purple).append("   Summer.txt").append(nl).append(reset);
    expected.append(purple).append("   There.txt").append(nl).append(reset);
   

    assertEquals(expected.toString(), output);
}


@Test
public void testPrintTree_onlyHiddenFileTester(@TempDir File tempDir) throws IOException {

    // Create "myFolder"

    // Create a hidden file in myFolder
    createHiddenFile(tempDir, ".hidden.txt");
    createHiddenFile(tempDir, ".unseen.txt");
    createHiddenFile(tempDir, ".hide.txt");

    // Create files in Documents

    // Set up TruffulaOptions with showHidden = false and useColor = true
    TruffulaOptions options = new TruffulaOptions(tempDir, true, true);

    // Capture output using a custom PrintStream
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);

    // Instantiate TruffulaPrinter with custom PrintStream
    TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

    // Call printTree (output goes to printStream)
    printer.printTree();

    // Retrieve printed output
    String output = baos.toString();
    String nl = System.lineSeparator();

    // Build expected output with exact colors and indentation
    ConsoleColor reset = ConsoleColor.RESET;
    ConsoleColor white = ConsoleColor.WHITE;


    StringBuilder expected = new StringBuilder();
    
   
    expected.append(white).append(tempDir.getName() + ("/")).append(nl).append(reset);
    assertEquals(expected.toString(), output);
}


@Test
    public void testPrintTree_colorOutputDeepFolder(@TempDir File tempDir) throws IOException {

        // Build the example directory structure:
        //    Documents/
        //       images/
        //          deep1/
        //              deep2/
        //                  deep3/
        //                      deep4/
        //                         deep5/
        //                              deep6/
        //                                  hi.txt

        File documents = new File(tempDir, "documents");
        File images = new File(documents, "images");
        File deep1 = new File(images, "deep1");
        File deep2 = new File(deep1, "deep2");
        File deep3 = new File(deep2, "deep3");
        File deep4 = new File(deep3, "deep4");
        File deep5 = new File(deep4, "deep5");
        File deep6 = new File(deep5, "deep6");
        

        assertTrue(deep6.mkdirs(), "Deep folder structure should be created");

    // Create file at deepest level
    File hi = new File(deep6, "hi.txt");
    assertTrue(hi.createNewFile(), "hi.txt should be created");

    // Set up TruffulaOptions with showHidden = false and useColor = true
    TruffulaOptions options = new TruffulaOptions(tempDir, false, true);

    // Capture output using a custom PrintStream
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);

    // Instantiate TruffulaPrinter with custom PrintStream
    TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

    // Call printTree (output goes to printStream)
    printer.printTree();

    // Retrieve printed output
    String output = baos.toString();
    String nl = System.lineSeparator();

    // Build expected output with exact colors and indentation
    ConsoleColor reset = ConsoleColor.RESET;
    ConsoleColor white = ConsoleColor.WHITE;
    ConsoleColor purple = ConsoleColor.PURPLE;
    ConsoleColor yellow = ConsoleColor.YELLOW;
    StringBuilder expected = new StringBuilder();
       
        // Assert that the output matches the expected output exactly
        // Build the example directory structure:
        //    Documents/
        //       images/
        //          deep1/
        //              deep2/
        //                  deep3/
        //                      deep4/
        //                         deep5/
        //                              deep6/
        //                                  hi.txt

    
        expected.append(white).append(tempDir.getName() + '/').append(nl).append(reset);
        expected.append(purple).append("   documents/").append(nl).append(reset);
        expected.append(yellow).append("      images/").append(nl).append(reset);
        expected.append(white).append("         deep1/").append(nl).append(reset);
        expected.append(purple).append("            deep2/").append(nl).append(reset);
        expected.append(yellow).append("               deep3/").append(nl).append(reset);
        expected.append(white).append("                  deep4/").append(nl).append(reset);
        expected.append(purple).append("                     deep5/").append(nl).append(reset);
        expected.append(yellow).append("                        deep6/").append(nl).append(reset);
        expected.append(white).append("                           hi.txt").append(nl).append(reset);


        assertEquals(expected.toString(), output);
}

@Test
    public void testPrintTree_AlphabeticalTesting(@TempDir File tempDir) throws IOException {

        // Build the example directory structure:
        //    Documents/
        //       images.txt
        //          Alphabet.txt
        //          Bees.txt
        //          Center.txt
        //          Deep.txt
        //          Eggs.txt
        //          Found.txt


            // Create "myFolder"
            File myFolder = new File(tempDir, "Document");
            assertTrue(myFolder.mkdir(), "Document should be created");
                        
            // Create visible files in myFolder
            File first = new File(myFolder, "Alphabet.txt");
            File second = new File(myFolder, "Bees.txt");
            File third = new File(myFolder, "Center.txt");
            File fourth = new File(myFolder, "Deep.txt");
            File fifth = new File(myFolder, "Eggs.txt");
            File sixth = new File(myFolder, "Found.txt");

            first.createNewFile();
            second.createNewFile();
            third.createNewFile();
            fourth.createNewFile();
            fifth.createNewFile();
            sixth.createNewFile();

    // Set up TruffulaOptions with showHidden = false and useColor = true
    TruffulaOptions options = new TruffulaOptions(myFolder, false, true);

    // Capture output using a custom PrintStream
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);

    // Instantiate TruffulaPrinter with custom PrintStream
    TruffulaPrinter printer = new TruffulaPrinter(options, printStream);

    // Call printTree (output goes to printStream)
    printer.printTree();

    // Retrieve printed output
    String output = baos.toString();
    String nl = System.lineSeparator();

    // Build expected output with exact colors and indentation
    ConsoleColor reset = ConsoleColor.RESET;
    ConsoleColor white = ConsoleColor.WHITE;
    ConsoleColor purple = ConsoleColor.PURPLE;
    StringBuilder expected = new StringBuilder();
       
        // Assert that the output matches the expected output exactly
        //    Documents/
        //       images.txt
        //          Alphabet.txt
        //          Bees.txt
        //          Center.txt
        //          Deep.txt
        //          Eggs.txt
        //          Found.txt

    
        expected.append(white).append("Document/").append(nl).append(reset);
        expected.append(purple).append("   Alphabet.txt").append(nl).append(reset);
        expected.append(purple).append("   Bees.txt").append(nl).append(reset);
        expected.append(purple).append("   Center.txt").append(nl).append(reset);
        expected.append(purple).append("   Deep.txt").append(nl).append(reset);
        expected.append(purple).append("   Eggs.txt").append(nl).append(reset);
        expected.append(purple).append("   Found.txt").append(nl).append(reset);

        assertEquals(expected.toString(), output);
}


}






