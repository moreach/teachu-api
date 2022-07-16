//package ch.teachu.old.util;
//
//import ch.teachu.old.errorhandling.InvalidException;
//import ch.teachu.old.errorhandling.NotFoundException;
//import ch.teachu.old.properties.FileProperties;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Service
//public class LocalStorageAccessService implements IStorageAccessService {
//
//    private final FileProperties fileProperties;
//    private Path root;
//
//    public LocalStorageAccessService(FileProperties fileProperties) {
//        this.fileProperties = fileProperties;
//    }
//
//    @Override
//    public void init() {
//        try {
//            if (root == null) {
//                root = Paths.get(fileProperties.getUploadDirectory());
//            }
//
//            if(!Files.exists(root)) {
//                Files.createDirectory(root);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void save(MultipartFile file, String folder, String fileName) {
//        Path folderPathToSave = root.resolve(folder);
//        Path filePathToSave = folderPathToSave.resolve(fileName);
//
//        try {
//            Files.deleteIfExists(filePathToSave);
//            if(!Files.exists(folderPathToSave)) {
//                Files.createDirectories(folderPathToSave);
//            }
//
//            Files.copy(file.getInputStream(), filePathToSave);
//        } catch (IOException e) {
//            throw new InvalidException(e.getMessage());
//        }
//    }
//
//    @Override
//    public Resource load(String file, String folder) {
//        try {
//            Path filePath = root.resolve(file).resolve(folder);
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            }
//            else {
//                throw new NotFoundException("File " + file);
//            }
//        } catch (MalformedURLException e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void delete(String file, String folder) {
//        try {
//            Files.deleteIfExists(root.resolve(file).resolve(folder));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//
