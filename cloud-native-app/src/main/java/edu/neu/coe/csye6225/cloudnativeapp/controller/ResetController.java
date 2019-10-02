package edu.neu.coe.csye6225.cloudnativeapp.controller;

import edu.neu.coe.csye6225.cloudnativeapp.User.ResetForm;
import edu.neu.coe.csye6225.cloudnativeapp.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class ResetController {


    @Autowired
    ResetPasswordService resetPasswordService;

    @RequestMapping("/resetPassword")
    public String getResetPasswordPage(Model model) {

        model.addAttribute("resetForm", new ResetForm());
        return "resetPassword";

    }

    @PostMapping("/resetPassword")
    public String generateResetToken(@ModelAttribute ResetForm resetForm) {


        System.out.println(resetForm.getEmailAddress());
        try {
            resetPasswordService.sendMessage(resetForm.getEmailAddress());
        } catch (Exception e) {
            e.printStackTrace();
            return  "resetPassword";
        }

        return "resetPassword";


    }


}
