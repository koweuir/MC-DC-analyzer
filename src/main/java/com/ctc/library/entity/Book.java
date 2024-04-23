package com.ctc.library.entity;

import lombok.Data;

@Data
public class Book {
    String bookId;//图书编号(PK)
    String bookName;//图书名
    String publicationTime;//出版时间
    String author;//作者
    String publisher;//出版社
    String category;//图书分类
    Integer pageNumber;//页数
    float price;//价格
    String bookPicturePath;//图书图片存放路径
    String bookStatus;//图书借阅状态（借出 and 在库）
    String unitName;//图书所在单位
    String permission;//图书开放权限（开放 and 隐藏）
    String bookCirculation;//图书流通状态（流通 and 归还）
}
