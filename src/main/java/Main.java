import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, NoSuchFileException {
        FileProcessor processor = new FileProcessor();
        processor.process();
    }
}
