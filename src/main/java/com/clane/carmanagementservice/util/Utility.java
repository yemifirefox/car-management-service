package com.clane.carmanagementservice.util;


import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class Utility {

    @Value("${HOME_DIR}")
    private String homeDir;
    private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

    private  String generate4Random(){
        Random random = new Random();
        String randomNumber = String.format("%04d", random.nextInt(1001));
        return randomNumber;
    }

    public  String createImage(String base64String){

        String path = "";
        String[] strings = base64String.split(",");
        if (strings.length > 0) {
            String extension;
            switch (strings[0]) {//check image's extension
                case "data:image/jpeg;base64":
                    extension = "jpeg";
                    break;
                case "data:image/png;base64":
                    extension = "png";
                    break;
                default:
                    extension = "jpg";
                    break;
            }
            String value = strings[1];
            String rootPath = System.getProperty(homeDir);
            File fileSaveDir = new File(rootPath + File.separator + "IMG");
            String pathName = fileSaveDir.getAbsolutePath() + File.separator;
            byte[] data = Base64.decodeBase64(value);
            path = pathName + String.valueOf(new Date().getTime()) +"." + extension;
            File file = new File(path);
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                outputStream.write(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Image path: " +path);
        return path;
    }

    public  Map<String, String> createImageAndAddPathToMap(String... base64Strings){

        Map<String, String> map = new HashMap<>();
        for (String base64String : base64Strings) {
            map.put(generate4Random(), createImage(base64String));;
        }

        return map;
    }
    public  Boolean deleteImage(String path) {
        boolean response = false;
        try
        {
           response =  Files.deleteIfExists(Paths.get(path));
        }
        catch(NoSuchFileException e)
        {
            LOGGER.info("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            LOGGER.info("Directory is not empty.");
        }
        catch(IOException e)
        {
            LOGGER.info("Invalid permissions.");
        }
        LOGGER.info("Deletion successful.");
        return response;
    }

    public static String convertImageToBase64(String filePath){
        String encodedString = null;
        String append = "";
        try{
            String[] array = filePath.split("\\.");

            if(array[1].equalsIgnoreCase("jpeg"))
                append = "data:image/jpeg;base64;";
            else if(array[1].equalsIgnoreCase("png"))
                append = "data:image/png;base64;";
            else
                append = "data:image/jpg;base64;";

            byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
            encodedString = Base64.encodeBase64String(fileContent);

        }catch (Exception e){
            e.printStackTrace();
        }
        return append + encodedString;

    }
}
