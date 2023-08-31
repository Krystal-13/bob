package com.zerobase.bob.filesave;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
public class FileSave {

    public static String getNewSaveFile(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String baseLocalPath = "/Users/krystal/IdeaProjects/bob/files";
        String baseUrlPath = "/files";

        LocalDate now = LocalDate.now();
        String[] dirs = {
                String.format("%s/%d/", baseLocalPath
                        , now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath
                        , now.getYear()
                        , now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath
                        , now.getYear()
                        , now.getMonthValue()
                        , now.getDayOfMonth())
        };

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath
                , now.getYear()
                , now.getMonthValue()
                , now.getDayOfMonth());

        for(String dir : dirs) {
            File newFile = new File(dir);
            if (!newFile.isDirectory()) {
                newFile.mkdirs();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String saveFilename = String.format("%s%s", dirs[2], uuid);
        String urlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            saveFilename += "." + fileExtension;
            urlFilename += "." + fileExtension;
        }

        try {
            File newFile = new File(saveFilename);
            FileCopyUtils.copy(file.getInputStream()
                    , new FileOutputStream(newFile));
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        return urlFilename;
    }
}
