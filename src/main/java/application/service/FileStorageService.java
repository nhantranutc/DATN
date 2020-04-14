package application.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

@Service
public class FileStorageService {

    Logger log = LogManager.getLogger(FileStorageService.class);

    @Value("./uploaded")
    private Path rootLocation;

    public String store(MultipartFile file) {
        long unixTimestamp = Instant.now().getEpochSecond();
        try{
            String newFileName ="" + unixTimestamp + "-" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName));
            return newFileName;
        }catch (Exception e) {
            throw  new RuntimeException("Lỗi");
        }
    }

    public Resource loadFile (String fileName) {
        try {
            Path file =rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw  new RuntimeException("Lỗi");
            }
        } catch (MalformedURLException e) {
            throw  new RuntimeException("Lỗi");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively((rootLocation.toFile()));
    }

    public  void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw  new RuntimeException("Không thể khởi tạo lưu trữ");

        }
    }

}
