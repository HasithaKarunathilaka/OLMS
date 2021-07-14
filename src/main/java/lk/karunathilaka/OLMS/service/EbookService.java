package lk.karunathilaka.OLMS.service;

import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.repository.EbookRepository;

import javax.servlet.http.Part;

public class EbookService {
    public String setEbook(Part filePart, Part filePartImage, EbookBeen ebookBeen){
        FileService fileService = new FileService();
        boolean resultUploadEbook = fileService.uploadEbook(filePart, ebookBeen);

        if(resultUploadEbook){
            boolean resultUploadImage = fileService.uploadImage(filePartImage, ebookBeen);

            if(resultUploadImage){
                boolean result = EbookRepository.setEbook(ebookBeen);
                if(result){
                    return "success";

                }else{
                    return "error while set ebook repo";
                }

            }else{
                return "error while uploading image";

            }

        }else{
            return "error while uploading pdf";

        }

    }

}
