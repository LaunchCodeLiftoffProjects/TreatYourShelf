package org.liftoff.BookApp.data;

import org.liftoff.BookApp.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findByIdIn(List<Integer> bookIds);
}