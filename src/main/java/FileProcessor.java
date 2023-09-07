import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Comparator.comparingLong;

/**
 * Class that is responsible for file manipulations.
 */
public class FileProcessor {

    public void process() throws NoSuchFileException, FileNotFoundException {
        var folder = new File(getDefaultSystemPath("Videos", "Captures").toString());
        if (!folder.exists() || !folder.isDirectory()) {
            throw new NoSuchFileException("Folder does not exist or it is not a directory");
        }
        var lastCreatedFile = lastCreatedFileIn(folder).orElseThrow(
                () -> new FileNotFoundException("No file was found by given characteristics")
        );
        System.out.println("The last file that was created: " + lastCreatedFile.getName());
    }

    private Optional<File> lastCreatedFileIn(File folder) throws FileNotFoundException {
        var files = folder.listFiles();
        if (files == null) {
            throw new FileNotFoundException("No files is present under given path");
        }

        return Arrays.stream(files)
                .filter(file -> hasMP4Format(file.getName()))
                .max(comparingLong(file -> creationTimeInMills(file.toPath())));
    }

    private boolean hasMP4Format(String fileName) {
        return FilenameUtils.getExtension(fileName).equals("mp4");
    }

    private long creationTimeInMills(Path path) {
        try {
            var creationTime = (FileTime) Files.getAttribute(path, "creationTime");
            return creationTime.toMillis();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to get file creation time due to the following reason: " + ex);
        }
    }

    private Path getDefaultSystemPath(String... customPath) {
        return FileSystems.getDefault().getPath(System.getProperty("user.home"), customPath);
    }
}
