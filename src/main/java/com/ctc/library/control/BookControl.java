package com.ctc.library.control;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.ctc.library.control.dto.BookDTO;
import com.ctc.library.entity.Book;
import com.ctc.library.service.BookServiceImpl;
import com.ctc.library.service.UnitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Book")
public class BookControl {
    @Autowired
    BookServiceImpl bookService;
    @Autowired
    UnitServiceImpl unitService;
    @GetMapping("/GetBookList")
    public HashMap<String, Object> GetBookList(@RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize,
                                               @RequestParam(defaultValue = "") String bookName,
                                               @RequestParam(defaultValue = "") String bookId,
                                               @RequestParam(defaultValue = "") String author,
                                               @RequestParam(defaultValue = "") String publisher){
        pageNum = (pageNum - 1) * pageSize;
        List<Book> data = bookService.GetAllBooks(pageNum,pageSize,bookName,bookId,author,publisher);
        Integer total = bookService.GetTotal(bookName,bookId,author,publisher);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
    @GetMapping("/GetPermissionBookList")
    public HashMap<String, Object> GetPermissionBookList(   @RequestParam Integer pageNum,
                                                            @RequestParam Integer pageSize,
                                                            @RequestParam(defaultValue = "") String bookName,
                                                            @RequestParam(defaultValue = "") String bookId,
                                                            @RequestParam(defaultValue = "") String author,
                                                            @RequestParam(defaultValue = "") String publisher,
                                                            @RequestParam(defaultValue = "") String unitName,
                                                            @RequestParam(defaultValue = "") String permission){
        pageNum = (pageNum - 1) * pageSize;
        List<Book> data = bookService.GetPermissionAllBooks(pageNum,pageSize,bookName,bookId,author,publisher,unitName,permission);
        Integer total = bookService.GetPermissionTotal(bookName,bookId,author,publisher,unitName,permission);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
    @PostMapping("/Update")
    public Book Update(@RequestBody BookDTO bookDTO) {
        int flag = bookService.update(bookDTO);
        if (flag == 1) {
            return bookService.GetBook(bookDTO);
        } else {
            return null;
        }
    }
    @PostMapping("/Delete")
    public Integer Delete(@RequestBody BookDTO bookDTO){
        return bookService.deleteBook(bookDTO);
    }
    @PostMapping("/AddBook")
    public Integer AddBook(@RequestBody BookDTO bookDTO) {
        //0:添加失败
        //1:添加成功
        if (StrUtil.isBlank(bookDTO.getUnitName())) {
            return 0;
        } else {
            if(bookService.update(bookDTO)>0) {
                return 1;
            }else{
                return 0;
            }
        }
    }
    @PostMapping("/Upload")
    public String Upload(@RequestParam MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = "E:/学习文件夹/Java Web技术/课程设计/课设/library/vue/src/assets/book/"+fileName;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile); // 将上传的文件保存到目标文件中
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "http://localhost:9090/book/" + fileName;
    }
    @PostMapping("/FindBook")
    public Book FindBook(@RequestBody BookDTO bookDTO){
        return bookService.GetBook(bookDTO);
    }
    @PostMapping("/Import")
    public Boolean Import(@RequestParam MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<BookDTO> bookList = reader.readAll(BookDTO.class);
        bookService.saveBatch(bookList);
        return true;
    }
    @GetMapping("/CountBook")
    public Integer CountBook(@RequestParam String unitName,
                             @RequestParam(defaultValue = "") String bookStatus){
        return bookService.countBook(unitName,bookStatus);
    }
    @PostMapping("/SearchBookByUnit")
    public List<Book> SearchBookByUnit(@RequestBody BookDTO bookDTO){
        return bookService.GetBookByUnit(bookDTO);
    }
}
