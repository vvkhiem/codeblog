package com.vuvankhiem.blogzine.Service.admin.Impl;

import com.vuvankhiem.blogzine.Entity.Contact;
import com.vuvankhiem.blogzine.Repository.ContactRepository;
import com.vuvankhiem.blogzine.Service.admin.ContactManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<Contact> getAllContactsOrderByIdDesc() {
        return contactRepository.findByOrderByContactIDDesc();
    }

    @Override
    public List<Contact> getContactsByTxtSearch(String txtSearch) {
        return contactRepository.findByTxtSearch(txtSearch);
    }

    @Override
    public List<Contact> getContactsByContactStatus(boolean status) {
        return contactRepository.findByContactStatusOrderByContactIDDesc(status);
    }

    @Override
    public List<Contact> getContactsByContactReply() {
        return contactRepository.findByContactReplyOrderByContactIDDesc(true);
    }

    @Override
    public List<Contact> getContactsByPage(List<Contact> contacts, int index) {
        List<Contact> contacts_ = new ArrayList<Contact>();
        for (int i = index - 5; i < index; i++) {
            if (i <= contacts.size() - 1) {
                contacts_.add(contacts.get(i));
            } else {
                return contacts_;
            }
        }
        return contacts_;
    }

    @Override
    public void deleteByContactID(int id) {
        contactRepository.deleteByContactID(id);
    }

    @Override
    public void deleteAllContacts() {
        contactRepository.deleteAll();
    }

    @Override
    public Contact getContactByID(int id) {
        return contactRepository.findByContactID(id);
    }

    @Override
    public void updateContact(Contact contact) {
        contactRepository.save(contact);
    }
}
