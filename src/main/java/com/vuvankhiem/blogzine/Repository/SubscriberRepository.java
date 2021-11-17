package com.vuvankhiem.blogzine.Repository;

import com.vuvankhiem.blogzine.Entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

    boolean existsSubscriberBySubscriberEmail(String subscriberEmail);

}