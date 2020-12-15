package org.liftoff.BookApp.data;

import org.liftoff.BookApp.models.BookOwner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BookOwnerRepository extends CrudRepository<BookOwner, Integer> {

}