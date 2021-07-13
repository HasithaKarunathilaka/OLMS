package lk.karunathilaka.OLMS.service;

import lk.karunathilaka.OLMS.bean.EbookBeen;

import javax.servlet.http.Part;

public class EbookService {
    public String setEbook(Part filePart, EbookBeen ebookBeen){
        FileService fileService = new FileService();
        boolean resultUploadEbook = fileService.uploadEbook(filePart, ebookBeen);

        if(resultUploadEbook){
            return "book upload to web server";

        }else{
            return "book already have";

        }

    }

}
