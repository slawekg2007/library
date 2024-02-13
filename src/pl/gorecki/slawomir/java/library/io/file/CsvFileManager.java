package pl.javastart.library.io.file;

import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.InvalidDataException;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

import java.io.*;
import java.util.Scanner;

public class CsvFileManager implements FileManager{
    private static final String FILE_NAME = "Library.csv";
    @Override
    public Library importData() {
        Library library = new Library();
        try(Scanner fileReader = new Scanner(new File(FILE_NAME))) {
            while (fileReader.hasNextLine()){
                String line= fileReader.nextLine();
                Publication publication = createObjectFromString(line);
                library.addPublication(publication);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + FILE_NAME);
        }
        return library;
    }

    private Publication createObjectFromString(String line) {
        String[] split=line.split(";");
        String type = split[0];
        if (Book.TYPE.equalsIgnoreCase(type)){
            return createBook(split);
        }else if (Magazine.TYPE.equalsIgnoreCase(type)){
            return createMagazine(split);
        }throw new InvalidDataException("Nieznany typ publikacji "+type);
    }

    private Magazine createMagazine(String[] data) {
        String title = data[1];
        String publisher = data [2];
        int year = Integer.parseInt(data[3]);
        int month = Integer.parseInt(data[4]);
        int day = Integer.parseInt(data[5]);
        String language = data[6];
        return new Magazine(title, publisher,language,year,month,day);

    }

    private Book createBook(String[] data) {
        String title = data[1];
        String publisher = data[2];
        int year = Integer.parseInt(data[3]);
        String author = data[4];
        int pages = Integer.parseInt(data[5]);
        String isbn = data[6];
        return new Book(title,author,year,pages,publisher,isbn);
    }

    @Override
    public void exportData(Library library) {
        Publication[] publications= library.getPublications();
        try (var fw = new FileWriter(FILE_NAME);
        var bw = new BufferedWriter(fw)) {
            for (Publication p:publications) {
                bw.write(p.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku  "+ FILE_NAME);
        }
    }
}
