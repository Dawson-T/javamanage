package com.cqgs.plus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqgs.plus.dto.BorrowRecordDTO;
import com.cqgs.plus.entity.Book;
import com.cqgs.plus.entity.BookCategory;
import com.cqgs.plus.entity.BorrowRecord;
import com.cqgs.plus.entity.Reader;

import java.util.List;
import java.util.Map;

public interface BookService {
    //查询所有书籍
    public List<Book> findBooks();

    //添加书籍
    public void addBookInfo(Book bookInfo);

    //根据ID删除单个书籍
    public void deleteBookInfo(Integer id);

    //修改书籍
    public void updateBookInfo(Book bookInfo);

    //根据ID获取书籍信息
    public Book findBookById(Integer id);

//    图书类型
    public List<BookCategory> findBookCategory();
    //    获取图书借阅数据
    public Map<String, Object> findHomeData();


//    借书
    public void borrowBook(String bookId, String idCard);
//    还书
    public void returnBook(String bookId, String idCard);

//    借书还书全部数据
    public IPage<BorrowRecordDTO> findBorrowRecord(int pageNum, int pageSize);
}
