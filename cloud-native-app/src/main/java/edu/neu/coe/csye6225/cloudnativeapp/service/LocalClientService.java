package edu.neu.coe.csye6225.cloudnativeapp.service;


import edu.neu.coe.csye6225.cloudnativeapp.domain.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
@Profile("!dev")
@Slf4j
public class LocalClientService implements UploadClient {


    @Autowired
    private SecurityServiceImpl securityService;

    private static final String FILE_NAME_PRE = "profile_pic_";

    private static final String DOT = ".";

    private static final String PROFILE_PIC_DIR = "/Profile_Pics/";


    public void storeProfilePic(MultipartFile file) {



        deleteProfilePic();
        UserAccount loggedInUsername = securityService.findLoggedInUsername();
        String[] split = file.getOriginalFilename().split("\\.");
        String contentType = Arrays.asList(split).get(1);
        String id = loggedInUsername.getId().toString();
        String fileName = FILE_NAME_PRE + id + DOT + contentType;

        String profileDir = System.getProperty("user.dir") + PROFILE_PIC_DIR;

        if (!Files.exists(Paths.get(profileDir))) {

            new File(profileDir).mkdir();

        }
        File tempFile = new File(profileDir + fileName);
        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            File profileImage = convertMultiPartToFile(file, fileName);
//            awsClient.uploadFile(profileImage, fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//
//        try {
//            // read and write the file to the selected location-
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(tempDirectory + "profile_pic_" + id);
//            Files.write(path, bytes);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }


//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//
//        File convFile = new File(fileName);
//        file.transferTo(convFile);
//        convFile.createNewFile();
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }

    public void deleteProfilePic() {


        UserAccount loggedInUsername = securityService.findLoggedInUsername();
        String id = loggedInUsername.getId().toString();

        String fileName = FILE_NAME_PRE + id;
        String folderName = System.getProperty("user.dir") + "/Profile_Pics";
        File folder = new File(folderName);

        File file = Arrays.asList(folder.listFiles()).stream()
                .filter(o -> o.getName().contains(fileName))
                .map(o -> o.getAbsoluteFile())
                .findAny().orElse(null);

        if (file != null) {

            file.delete();
        }


    }

    public InputStream getProfilePic() {

        UserAccount loggedInUsername = securityService.findLoggedInUsername();
        String id = loggedInUsername.getId().toString();

        String fileName = FILE_NAME_PRE + id;
        String folderName = System.getProperty("user.dir") + "/Profile_Pics";

        log.info("Folder Name is {} ", folderName);


        File folder = new File(folderName);


        if(folder == null || folder.listFiles() == null){

            return null;
        }

        File file = Arrays.asList(folder.listFiles()).stream()
                .filter(o -> o.getName().contains(fileName))
                .map(o -> o.getAbsoluteFile())
                .findAny().orElse(null);


        if (file == null) {

            return null;
        }


        try {
            InputStream is = new FileInputStream(file);
            return is;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        return null;
    }
}
