package com.imagegallery.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imagegallery.model.Image;
import com.imagegallery.service.Config;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class DataHandler {

    public DataHandler() throws IOException {}



    public static List<Image> getImages() throws IOException {
        List<Image> images;
        try {
            Type listType = new TypeToken<List<Image>>() {
            }.getType();

            InputStream fis = new FileInputStream(Config.getProperty("imageJSON"));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(isr);

            images = new Gson().fromJson(reader, listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return images;
    }

    public static void writeImages(List<Image> images) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(String.valueOf(Paths.get(Config.getProperty("imageJSON")))), images);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Boolean writeFile(MultipartFile file) throws IOException {
        Path filePath = Paths.get(Config.getProperty("images"), file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
