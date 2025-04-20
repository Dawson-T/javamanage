package com.cqgs.plus.controller;

import com.cqgs.plus.entity.Book;
import com.cqgs.plus.entity.BookCategory;
import com.cqgs.plus.service.BookService;
import com.cqgs.plus.util.HttpResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookService bookService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public HttpResult list(){
        System.out.println("查询所有书籍信息");
        List<Book> bookInfoList = bookService.findBooks();
        return HttpResult.successResult(bookInfoList);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public HttpResult add(@RequestBody Book bookInfo){
        System.out.println("添加书籍信息");
        System.out.println(bookInfo);
        bookService.addBookInfo(bookInfo);
        return HttpResult.successResult("success");
    }

    @RequestMapping (value = "delete", method = RequestMethod.POST)
    public HttpResult delete(Integer id){
        System.out.println("根据ID删除书籍："+id);
        bookService.deleteBookInfo(id);
        return HttpResult.successResult("success");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody Book bookInfo){
        System.out.println("修改书籍信息");
        System.out.println(bookInfo);
        bookService.updateBookInfo(bookInfo);
        return HttpResult.successResult("success");
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public HttpResult getBookById(@RequestParam("bookid") Integer id) {
        System.out.println("根据ID查询书籍信息");
        Book bookInfo = bookService.findBookById(id); // 调用findBookById方法
        if (bookInfo == null) {
            return HttpResult.errorResult("未找到书籍信息");
        }
        return HttpResult.successResult(bookInfo);
    }

    @RequestMapping(value = "category", method = RequestMethod.GET)
    public  HttpResult findBookCategory(){
        List<BookCategory> res = bookService.findBookCategory();
        return HttpResult.successResult(res);
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public  HttpResult getHomeData(){
        System.out.println("home data");
        Map<String,Object> map = bookService.findHomeData();
        System.out.println(map);
        return HttpResult.successResult(map);
    }


    @RequestMapping(value = "borrow", method = RequestMethod.POST)
    public HttpResult borrow(@RequestParam("readerId") String readerId, @RequestParam("bookId") String bookId) {
        bookService.borrowBook(bookId,readerId);
        return HttpResult.successResult("success");
    }
}