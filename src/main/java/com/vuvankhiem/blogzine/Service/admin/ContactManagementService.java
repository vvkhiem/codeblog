package com.vuvankhiem.blogzine.Service.admin;

import com.vuvankhiem.blogzine.Entity.Contact;

import java.util.List;

public interface ContactManagementService {

    List<Contact> getAllContactsOrderByIdDesc();

    List<Contact> getContactsByTxtSearch(String txtSearch);

    List<Contact> getContactsByContactStatus(boolean status);

    List<Contact> getContactsByContactReply();

    List<Contact> getContactsByPage(List<Contact> contacts, int index);

    void deleteByContactID(int id);

    void deleteAllContacts();

    Contact getContactByID(int id);

    void updateContact(Contact contact);

}
