package pl.javastart.library.io.file;

import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager{
    private final static String FILE_NAME = "Library.o";


    @Override
    public void exportData(Library library) {
        try(var fos = new FileOutputStream(FILE_NAME);
            var oos = new ObjectOutputStream(fos)) {
            oos.writeObject(library);
        }catch (FileNotFoundException e){
            throw new DataExportException("Brak Pliku "+FILE_NAME);
        }catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych "+FILE_NAME);
        }
    }
    @Override
    public Library importData() {
        try (
                var fis = new FileInputStream(FILE_NAME);
                var ois = new ObjectInputStream(fis)
                ){
            return (Library) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Brak pliku "+FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Błąd odczytu pliku "+FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Niezgodny typ danych w pliku "+FILE_NAME);
        }
    }
}
