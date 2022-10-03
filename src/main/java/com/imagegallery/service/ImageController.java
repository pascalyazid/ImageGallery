package com.imagegallery.service;

import com.imagegallery.ImageGalleryApplication;
import com.imagegallery.data.DataHandler;
import com.imagegallery.model.Image;
import com.imagegallery.util.ImageNotFoundException;
import com.imagegallery.util.PathPatternConstraint;
import org.apache.commons.io.IOUtils;
import org.apache.juli.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/images")
public class ImageController {


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    List<Image> allImagesJSON() throws IOException {
        return DataHandler.getImages();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    Image get(@QueryParam("id") String id) throws IOException {
        return DataHandler.getImages().stream().filter(image -> image.getId().equals(id)).findFirst().orElseThrow(() -> new ImageNotFoundException(id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity<String> saveImageJSON(@Valid @BeanParam Image image) throws IOException {
        List<Image> images = DataHandler.getImages();
        if (!images.stream().anyMatch(image1 -> image1.getPath().equals(image.getPath()))) {
            images.add(image);
            DataHandler.writeImages(images);
            return new ResponseEntity<String>("Image created", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Image already exists", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity<String> replaceImageJSON(@Valid @BeanParam Image newImage, @QueryParam("id") String id) throws IOException {
        List<Image> images = DataHandler.getImages();

        if (images.stream().anyMatch(image1 -> image1.getId().equals(id))) {
            Image img1 = images.stream().filter(image1 -> image1.getId().equals(id)).findFirst().orElse(null);
            int index = images.indexOf(img1);

            Optional.ofNullable(newImage.getPath()).ifPresent(Objects.requireNonNull(images.get(index))::setPath);
            Optional.ofNullable(newImage.getDesc()).ifPresent(Objects.requireNonNull(images.get(index))::setDesc);
            Optional.ofNullable(newImage.getDate()).ifPresent(Objects.requireNonNull(images.get(index))::setDate);

            DataHandler.writeImages(images);
            return new ResponseEntity<String>("Image updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Image not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<String> deleteImageJSON(@QueryParam("id") String id) throws IOException {
        List<Image> images = DataHandler.getImages();
        if (images.stream().anyMatch(image -> image.getId().equals(id))) {
            Image image = images.stream().filter(image1 -> image1.getId().equals(id)).findFirst().orElse(null);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (images.remove(image) && Files.deleteIfExists(Paths.get(Config.getProperty("images") + "/" + image.getPath()))) {
                DataHandler.writeImages(images);
                return new ResponseEntity<String>("Image deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Image not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<String>("Image not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> uploadImageFile(
            @RequestParam("file") MultipartFile file,
            @Valid @BeanParam Image image) throws IOException {
        System.out.println(image);
        List<Image> images = DataHandler.getImages();

        if (!images.stream().anyMatch(image1 -> image1.getPath().equals(image.getPath())) || images.size() == 0) {
            images.add(image);
            DataHandler.writeImages(images);
            if (DataHandler.writeFile(file)) {
                return new ResponseEntity<String>("File saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("File couldn't be saved", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("Image already exists", HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(value = "/load", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] loadImageFile(@FormParam("id") String id) throws IOException {
        List<Image> images = DataHandler.getImages();
        if (images.stream().anyMatch(image -> image.getId().equals(id))) {
            Image image = images.stream().filter(image1 -> image1.getId().equals(id)).findFirst().orElseThrow(() -> new ImageNotFoundException(id));
            String path = Config.getProperty("images") + "/" + image.getPath();
            System.out.println(image.getPath());
            InputStream fis = new FileInputStream(image.getPath());

            return IOUtils.toByteArray(fis);

        } else {
            return new ResponseEntity<String>("Image not found", HttpStatus.NOT_FOUND).toString().getBytes();
        }
    }
}
