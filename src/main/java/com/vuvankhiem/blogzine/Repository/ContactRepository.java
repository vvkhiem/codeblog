package com.vuvankhiem.blogzine.Repository;

import com.vuvankhiem.blogzine.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByOrderByContactIDDesc();

    @Query("select c from Contact c where c.contactName like %:s% or c.contactTitle like %:s% or c.contactEmail like %:s% order by c.contactID desc ")
    List<Contact> findByTxtSearch(@Param("s") String txtSearch);

    List<Contact> findByContactStatusOrderByContactIDDesc(boolean contactStatus);

    List<Contact> findByContactReplyOrderByContactIDDesc(boolean contactReply);

    Contact findByContactID(int contactID);

    void deleteByContactID(int contactID);

    @Override
    void deleteAll();
}