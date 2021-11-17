package com.vuvankhiem.blogzine.Service.user.Impl;

import com.vuvankhiem.blogzine.Entity.Contact;
import com.vuvankhiem.blogzine.Repository.ContactRepository;
import com.vuvankhiem.blogzine.Service.user.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }
}
