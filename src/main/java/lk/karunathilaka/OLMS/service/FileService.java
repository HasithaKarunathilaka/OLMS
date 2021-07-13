package lk.karunathilaka.OLMS.service;

import lk.karunathilaka.OLMS.bean.EbookBeen;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileService {
    public boolean uploadEbook(Part filePart, EbookBeen ebookBeen){
        boolean result = false;
        try{
            String folderName = "ebooks";
            String uploadPath = ebookBeen.getPdfPath() + File.separator + folderName; // web_server_path/ebooks

            File newName = new File(ebookBeen.getBookID() + ".pdf"); // set new file name to the pdf using bookID

//            ------ Make ebooks folder in web server if not exists ------

            File file = new File(uploadPath);
            if(!file.exists()){
                file.mkdir();
            }

//            String fileName = filePart.getSubmittedFileName();
            String path = folderName + File.separator + newName; // ebooks/bookID.pdf
            ebookBeen.setPdfPath(path);

            InputStream inputStream = filePart.getInputStream();

            if(!Files.exists(Paths.get(uploadPath + File.separator + newName))){
                Files.copy(inputStream, Paths.get(uploadPath + File.separator + newName), StandardCopyOption.REPLACE_EXISTING);
                result = true;

            }else{
                result = false;
            }


        } catch (IOException e){
            System.out.println("Exception: " + e);
        }

        return result;
    }
}