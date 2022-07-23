package ch.teachu.teachuapi.image;

import ch.teachu.teachuapi.image.dtos.ImageDAO;
import ch.teachu.teachuapi.image.dtos.ImageResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.errorhandlig.NotFoundException;
import ch.teachu.teachuapi.shared.util.ValidationUtil;
import ch.teachu.teachuapi.sql.SQL;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService extends AbstractService {

    public Resource getImage(String access, String imageId) {
        authMinRole(access, UserRole.parent);

        ImageDAO imageDAO = new ImageDAO();
        imageDAO.setId(imageId);

        SQL.select("" +
                        "SELECT image " +
                        "FROM   image " +
                        "WHERE  id = UUID_TO_BIN(-id) " +
                        "INTO   :outputImage ",
                imageDAO,
                imageDAO);

        if (imageDAO.getOutputImage() == null) {
            throw new NotFoundException("Image with id " + imageId);
        }

        return new ByteArrayResource(imageDAO.getOutputImage());
    }

    public ImageResponse createImage(String access, MultipartFile image) {
        authMinRole(access, UserRole.parent);

        ValidationUtil.checkIfImageIsValid(image);

        ImageDAO imageDAO = new ImageDAO();
        imageDAO.setId(UUID.randomUUID().toString());

        try {
            imageDAO.setInputImage(image.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image");
        }

        int count = SQL.insert("" +
                        "INSERT INTO image ( " +
                        "       id, " +
                        "       image) " +
                        "VALUES (" +
                        "       UUID_TO_BIN(-id), " +
                        "       -inputImage) ",
                imageDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to save image");
        }

        return new ImageResponse(imageDAO.getId());
    }

    public MessageResponse updateImage(String access, String imageId, MultipartFile image) {
        authMinRole(access, UserRole.parent);

        ValidationUtil.checkIfImageIsValid(image);

        ImageDAO imageDAO = new ImageDAO();
        imageDAO.setId(imageId);

        try {
            imageDAO.setInputImage(image.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image");
        }

        int count = SQL.update("" +
                        "UPDATE image " +
                        "SET    image = -inputImage " +
                        "WHERE  id = UUID_TO_BIN(-id) ",
                imageDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update image");
        }

        return new MessageResponse("Successfully updated image");
    }
}
