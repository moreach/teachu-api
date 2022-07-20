package ch.teachu.teachuapi.image;

import ch.teachu.teachuapi.image.dtos.ImageResponse;
import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Image")
@RequestMapping("/image")
@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(summary = "Get image")
    @GetMapping("/{imageId}")
    private ResponseEntity<Resource> getImage(@RequestHeader("access") String access, @PathVariable String imageId) {
        return imageService.getImage(access, imageId);
    }

    @Operation(summary = "Upload image")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<ImageResponse> createImage(@RequestHeader("access") String access, @RequestParam("file") MultipartFile image) {
        return imageService.createImage(access, image);
    }

    @Operation(summary = "Update image")
    @PutMapping(path = "/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<MessageResponse> updateImage(@RequestHeader("access") String access, @PathVariable String imageId, @RequestParam("file") MultipartFile image) {
        return imageService.updateImage(access, imageId, image);
    }
}
