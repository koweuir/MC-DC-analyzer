package com.ctc.library.dao;

import com.ctc.library.control.dto.BookDTO;
import com.ctc.library.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookDao {

    Integer selectTotal(String bookName);

    Book selectBookByNo(BookDTO bookDTO);

    Integer insertBook(BookDTO bookDTO);

    Integer updateAll(BookDTO bookDTO);

    Integer deleteByBookNo(BookDTO bookDTO);

    List<Book> selectAllBooks(Integer pageNum, Integer pageSize, String bookName, String bookId, String author, String publisher);

    Integer selectTotal(String bookName, String bookId, String author, String publisher);

    List<Book> selectPermissionAllBooks(Integer pageNum, Integer pageSize, String bookName, String bookId, String author, String publisher, String unitName, String permission);

    Integer selectPermissionTotal(String bookName, String bookId, String author, String publisher, String unitName, String permission);

    Integer countBook(String unitName, String bookStatus);

    List<Book> getBookByUnit(BookDTO bookDTO);
}
