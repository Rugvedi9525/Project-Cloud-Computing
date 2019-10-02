package edu.neu.coe.csye6225.cloudnativeapp.controller;


import com.amazonaws.util.IOUtils;
import edu.neu.coe.csye6225.cloudnativeapp.domain.UserAccount;
import edu.neu.coe.csye6225.cloudnativeapp.service.SecurityServiceImpl;
import edu.neu.coe.csye6225.cloudnativeapp.service.UploadClient;
import edu.neu.coe.csye6225.cloudnativeapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;

@Controller
@Slf4j
public class ProfileController {


    @Autowired
    public UploadClient uploadClient;

    @Autowired
    UserService userService;

    @Autowired
    SecurityServiceImpl securityService;

    @PostMapping("/upload")
    public RedirectView uploadProfilePic(@RequestParam("profile-pic") MultipartFile file) {

        log.info("Inside upload profile pic");

        uploadClient.storeProfilePic(file);
        RedirectView rv = new RedirectView("/", true);

        log.info("Completed uploading profile pio");
        return rv;


    }


    @RequestMapping(value = "/profilePic", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getImageWithMediaType() throws IOException {
        InputStream is = uploadClient.getProfilePic();
        if (is != null) {
            return IOUtils.toByteArray(is);
        }

        ClassPathResource imageFile = new ClassPathResource("static/images/default-placeholder.png");
        return IOUtils.toByteArray(imageFile.getInputStream());

    }

    @RequestMapping(value = "/deletePic", method = RequestMethod.DELETE)
    public RedirectView deleteProfilePic() {
        uploadClient.deleteProfilePic();
        RedirectView rv = new RedirectView("/", true);
        return rv;

    }


    @PostMapping("/updateAboutMe")
    public RedirectView updateAboutMe(@ModelAttribute UserAccount user, RedirectAttributes redirectAttributes) {


        UserAccount userAccount = securityService.findLoggedInUsername();

        userAccount.setAboutMe(user.getAboutMe());

        userService.saveUserAccount(userAccount);
        RedirectView rv = new RedirectView("/", true);
        return rv;

    }


    @GetMapping("/offlineProfile/{userId}")
    public String viewOfflineProfile(@PathVariable Long userId, Model model) {


        UserAccount userAccount = userService.findById(userId);
        model.addAttribute("userAccount", userAccount);
        return "OfflineProfile";

    }


}
