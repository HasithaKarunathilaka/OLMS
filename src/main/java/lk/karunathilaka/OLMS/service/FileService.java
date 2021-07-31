package lk.karunathilaka.OLMS.service;

import lk.karunathilaka.OLMS.bean.EbookBeen;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
//import org.apache.pdfbox.exceptions.CryptographyException;
//import org.apache.pdfbox.exceptions.InvalidPasswordException;

public class FileService {
    public boolean uploadEbook(Part filePart, EbookBeen ebookBeen){
        boolean result = false;
        try{
            String folderName = "ebooks";
            String uploadPath = ebookBeen.getPdfPath() + File.separator + folderName + File.separator + ebookBeen.getBookID(); // web_server_path/ebooks

//            File newName = new File(ebookBeen.getBookID() + ".pdf"); // set new file name to the pdf using bookID

            File file = new File(uploadPath);
            if(!file.exists()){
                file.mkdir();
            }

            // Open document
            InputStream inputStream = filePart.getInputStream();
//            Document pdfDocument = new Document(inputStream);
            PDDocument pdDocument = PDDocument.load(inputStream);
            System.out.println("Page Count is: " + pdDocument.getPages().getCount());
            ebookBeen.setPages(String.valueOf(pdDocument.getPages().getCount()));

            Splitter splitter = new Splitter();

            List<PDDocument> Page = splitter.split(pdDocument);

            Iterator<PDDocument> iteration = Page.listIterator();

// For page counter
            int pageCount = 1;

// Loop through all the pages
            while(iteration.hasNext()){
                PDDocument pd = iteration.next();
                pd.save(uploadPath + File.separator + pageCount + ".pdf");
                pageCount++;
//                System.out.println("while");
                result = true;
                pd.close();

            }
//            iteration.remove();
//            Page.clear();
            pdDocument.close();
//            for (PDPage pdfPage : pdDocument.getPages()) {
//                // Create a new document
//                Document newDocument = new Document();
//
//                // Add page to the document
//                newDocument.getPages().add((Iterable<Page>) pdfPage);
//
//                // Save as PDF
//                newDocument.save(uploadPath + File.separator + pageCount + ".pdf");
////                InputStream inputStream = filePart.getInputStream();
////                Files.copy(inputStream, Paths.get(uploadPath + File.separator + newName), StandardCopyOption.REPLACE_EXISTING);
//                pageCount++;
//                result =true;
//            }

//            ------ Make ebooks folder in web server if not exists ------

//            File file = new File(uploadPath);
//            if(!file.exists()){
//                file.mkdir();
//            }

//            String fileName = filePart.getSubmittedFileName();
//            String path = folderName + File.separator + newName; // ebooks/bookID.pdf
            ebookBeen.setPdfPath(folderName + File.separator + ebookBeen.getBookID());

//            InputStream inputStream = filePart.getInputStream();

//            if(!Files.exists(Paths.get(uploadPath + File.separator + newName))){
//                Files.copy(inputStream, Paths.get(uploadPath + File.separator + newName), StandardCopyOption.REPLACE_EXISTING);
//                result = true;

//            }else{
//                result = false;
//            }


        } catch (IOException e){
            System.out.println("Exception: " + e);
        }

        return result;
    }

    public boolean uploadImage(Part filePartImage, EbookBeen ebookBeen){
        boolean result = false;
        try{
            String folderName = "images";
            String uploadPath = ebookBeen.getImagePath() + File.separator + folderName; // web_server_path/images

            String fileName = filePartImage.getSubmittedFileName();
            String[] fileNameArray = fileName.split("\\.",0); // image.jpg, img.png, img.gif // test.exe


            File newName = new File(ebookBeen.getBookID() + "." + fileNameArray[1]); // set new file name to the image using bookID

//            ------ Make images folder in web server if not exists ------

            File file = new File(uploadPath);
            if(!file.exists()){
                file.mkdir();
            }
            file.delete();

//            String fileName = filePart.getSubmittedFileName();
            String path = folderName + File.separator + newName; // images/bookID.jpg
            ebookBeen.setImagePath(path);

            InputStream inputStream = filePartImage.getInputStream();

            if(!Files.exists(Paths.get(uploadPath + File.separator + newName))){
                Files.copy(inputStream, Paths.get(uploadPath + File.separator + newName), StandardCopyOption.REPLACE_EXISTING);
                result = true;

            }else{
                result = false;
            }
            newName.delete();


        } catch (IOException e){
            System.out.println("Exception: " + e);
        }

        return result;
    }
}
