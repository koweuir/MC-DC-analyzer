package com.ctc.library.service;

import com.ctc.library.control.dto.BookDTO;
import com.ctc.library.dao.BookDao;
import com.ctc.library.dao.UnitDao;
import com.ctc.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl {
    @Autowired
    BookDao bookDao;
    @Autowired
    UnitDao unitDao;
    public List<Book> GetAllBooks(Integer pageNum,
                                  Integer pageSize,
                                  String bookName,
                                  String bookId,
                                  String author,
                                  String publisher) {
        return bookDao.selectAllBooks(pageNum,pageSize,bookName,bookId,author,publisher);
    }

    public Integer GetTotal(String bookName,
                            String bookId,
                            String author,
                            String publisher) {
        return bookDao.selectTotal(bookName,bookId,author,publisher);
    }

    public int update(BookDTO bookDTO) {
        //1：成功
        //0：失败
        if(bookDao.selectBookByNo(bookDTO) == null){
            bookDTO.setPermission("开放");
            bookDTO.setBookStatus("在库");
            Integer flag = bookDao.insertBook(bookDTO);
            return flag>0?1:0;
        }
        else{
            Integer flag = bookDao.updateAll(bookDTO);
            return flag>0?1:0;
        }
    }

    public Book GetBook(BookDTO bookDTO) {
        return bookDao.selectBookByNo(bookDTO);
    }

    public Integer deleteBook(BookDTO bookDTO) {
        return bookDao.deleteByBookNo(bookDTO);
    }

    public List<Book> GetPermissionAllBooks(Integer pageNum, Integer pageSize, String bookName, String bookId, String author, String publisher, String unitName, String permission) {
        return bookDao.selectPermissionAllBooks(pageNum,pageSize,bookName,bookId,author,publisher,unitName,permission);
    }

    public Integer GetPermissionTotal(String bookName, String bookId, String author, String publisher, String unitName, String permission) {
        return bookDao.selectPermissionTotal(bookName,bookId,author,publisher,unitName,permission);
    }

    public void saveBatch(List<BookDTO> bookList) {
        for(BookDTO bookDTO:bookList){
            bookDao.insertBook(bookDTO);
        }
    }

    public Integer countBook(String unitName, String bookStatus) {
        return bookDao.countBook(unitName,bookStatus);
    }

    public List<Book> GetBookByUnit(BookDTO bookDTO) {
        return bookDao.getBookByUnit(bookDTO);
    }
}
