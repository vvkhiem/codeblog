package com.vuvankhiem.blogzine.Controller.admin;

import com.vuvankhiem.blogzine.Entity.Contact;
import com.vuvankhiem.blogzine.Service.admin.ContactManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/lien-lac")
public class ContactManagementController {

    static List<Contact> st_contacts;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ContactManagementService contactManagementService;

    @GetMapping("/")
    public String contact(Model model) {
        st_contacts = contactManagementService.getAllContactsOrderByIdDesc();
        model.addAttribute("opt", "tat-ca");
        model.addAttribute("active", 3);
        model.addAttribute("contacts", contactManagementService.getContactsByPage(st_contacts, 5));
        return "ad/contact-management";
    }

    @GetMapping("/{option}")
    public String filterContact(@PathVariable String option, Model model) {
        if (option.equals("da-xem"))
            st_contacts = contactManagementService.getContactsByContactStatus(true);
        if (option.equals("chua-xem"))
            st_contacts = contactManagementService.getContactsByContactStatus(false);
        if (option.equals("tra-loi"))
            st_contacts = contactManagementService.getContactsByContactReply();
        model.addAttribute("opt", option);
        model.addAttribute("active", 3);
        model.addAttribute("contacts", contactManagementService.getContactsByPage(st_contacts, 5));
        return "ad/contact-management";
    }

    @GetMapping("/tim-kiem")
    public String searchContact(@RequestParam String s, Model model) {
        st_contacts = contactManagementService.getContactsByTxtSearch(s);
        model.addAttribute("s", s);
        model.addAttribute("opt", "tat-ca");
        model.addAttribute("active", 3);
        model.addAttribute("contacts", contactManagementService.getContactsByPage(st_contacts, 5));
        return "ad/contact-management";
    }

    @ResponseBody
    @DeleteMapping("/xoa/{id}")
    public boolean deleteContact(@PathVariable int id) {
        contactManagementService.deleteByContactID(id);
        return true;
    }

    @GetMapping("/xoa-tat-ca")
    public String deleteAllContacts() {
        contactManagementService.deleteAllContacts();
        return "redirect:/admin/lien-lac/";
    }

    @ResponseBody
    @GetMapping("/load-more-contact/{index}")
    public List<Contact> loadMoreContacts(@PathVariable int index) {
        return contactManagementService.getContactsByPage(st_contacts, index);
    }

    @GetMapping("/xem-chi-tiet/{id}")
    public String viewDetails (@PathVariable int id ,Model model) {
        Contact contact = contactManagementService.getContactByID(id);
        contact.setContactStatus(true);
        contactManagementService.updateContact(contact);
        model.addAttribute("active", 3);
        model.addAttribute("contact", contact);
        model.addAttribute("opt","tat-ca");
        return "ad/contact-detail";
    }

    @PostMapping("/tra-loi")
    public String replyContact(@RequestParam int ctID,
                               @RequestParam String title,
                               @RequestParam String message,
                               Model model) {
        Contact contact = contactManagementService.getContactByID(ctID);
        message =  contact.getContactSubject() + "\n\nBlogzine trả lời tin nhắn : " + message;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(contact.getContactEmail());
        mailMessage.setSubject(title);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
        contact.setContactReply(true);
        contactManagementService.updateContact(contact);
        return "redirect:/admin/lien-lac/";
    }
}
