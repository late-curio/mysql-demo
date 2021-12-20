package com.newrelic.mysqldemo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaContentRepository extends CrudRepository<Content, Integer> {
}
