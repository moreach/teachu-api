package ch.teachu.teachuapi.image.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDAO {
    private String id;
    private InputStream inputImage;
    private byte[] outputImage;
}