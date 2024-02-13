package pl.javastart.library.io.file;


import pl.javastart.library.model.Library;

import java.io.IOException;

public interface FileManager {
    Library importData();
    void exportData(Library library);
}