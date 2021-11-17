package com.vuvankhiem.blogzine.Controller.user;

import com.vuvankhiem.blogzine.Common.Common;
import com.vuvankhiem.blogzine.Entity.Contact;
import com.vuvankhiem.blogzine.Service.user.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ContactController extends Common {

    @Autowired
    ContactService contactService;

    @GetMapping("lien-he")
    public String contactPage(@ModelAttribute Contact contact) {
        return "us/contact";
    }

    @PostMapping("/lien-he")
    public String saveContact(@Valid @ModelAttribute Contact contact,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "us/contact";
        }
        String currentDate = super.getCurrentDate(2);
        contact.setContactDate(currentDate);
        contactService.saveContact(contact);
        return "redirect:/";
    }

}
