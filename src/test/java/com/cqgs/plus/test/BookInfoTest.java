package com.cqgs.plus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqgs.plus.CqgsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest(classes = CqgsApplication.class)
public class BookInfoTest {
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Test
    public void testList(){
        //查询所有的图书
        List<BookInfo> bookInfoList=bookInfoMapper.selectList(null);
        for (BookInfo bookInfo :bookInfoList){
            System.out.println(bookInfo.getBookid()+"\t"+bookInfo.getBookname());
        }
    }

    //测试添加的方法
    @Test
    public void testAdd(){
        BookInfo bookInfo=new BookInfo();
        bookInfo.setAuthor("小河");
        bookInfo.setBookname("如何变得强大");
        bookInfo.setDatetime("2021-11-12");
        bookInfo.setGuige("呵呵");
        bookInfo.setKeyword("强大，生存");
        bookInfo.setLocation("学会强大，变得强大");
        bookInfo.setType("武侠");
        bookInfo.setNum(30);
        bookInfo.setPages(400);
        bookInfo.setPrice(20);
        //调用添加的方法
        bookInfoMapper.insert(bookInfo);
    }

    //测试查询单个的根据ID
    @Test
    public void testOne(){
        BookInfo bookInfo=bookInfoMapper.selectById(8);
        if (bookInfo==null){
            System.out.println("此图书不存在");
        }else {
            System.out.println(bookInfo.getBookid()+"\t"+bookInfo.getBookname());
        }
    }

    //根据ID,删除
    @Test
    public void testDelete(){
        bookInfoMapper.deleteById(16);
    }

    //修改
    @Test
    public void testupdate(){
        BookInfo bookInfo=new BookInfo();
        bookInfo.setBookid(16);//修改的时候必须提供ID的值
        bookInfo.setAuthor("小河");
        bookInfo.setBookname("如何变得强大");
        bookInfo.setDatetime("2021-11-12");
        bookInfo.setGuige("哈哈");
        bookInfo.setKeyword("强大，生存");
        bookInfo.setLocation("学会强大，变得强大");
        bookInfo.setType("武侠");
        bookInfo.setNum(30);
        bookInfo.setPages(400);
        bookInfo.setPrice(20);
        //调用修改的方法
        bookInfoMapper.updateById(bookInfo);
    }

    //条件查询
    @Test
    public void testlistbypram(){
        QueryWrapper<BookInfo> queryWrapper=new QueryWrapper();
        //QueryWrapper用于封装查询的条件（等值判断 = eq，模糊判断 like，范围判断 ge,le）
        queryWrapper.eq("type","武侠");//类型等于武侠
        queryWrapper.like("location","人民");
        queryWrapper.ge("num",60);

        List<BookInfo> bookInfoList = bookInfoMapper.selectList(queryWrapper);
        for (BookInfo bookInfo :bookInfoList){
            System.out.println(bookInfo.getBookid()+"\t"+bookInfo.getBookname()+"\t"+bookInfo.getPrice());
        }
    }

    //动态条件判断 动态条件的使用：根据值去决定where子句中条件的多少
    @Test
    public void testlistbypram2(){
        //如果使用一个对象封装了所有的条件 有可能多有可能少
        BookInfo bookInfo = new BookInfo();
        //bookInfo.setBookname("天龙八部");
        //bookInfo.setAuthor("吴承恩");
        //bookInfo.setLocation("人民");
        //bookInfo.setPrice(10);
        bookInfo.setType("武侠");

        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper();
        //QueryWrapper用于封装查询的条件（等值判断 = eq，模糊判断 like，范围判断 ge,le）
        queryWrapper.eq(StringUtils.isNotEmpty(bookInfo.getType()),"type",bookInfo.getType());//类型等于武侠
        queryWrapper.like(StringUtils.isNotEmpty(bookInfo.getLocation()),"location",bookInfo.getLocation());
        queryWrapper.ge(bookInfo.getPrice()!=null,"price",bookInfo.getPrice());
        queryWrapper.eq(StringUtils.isNotEmpty(bookInfo.getBookname()),"bookname",bookInfo.getBookname());

        List<BookInfo> bookInfoList = bookInfoMapper.selectList(queryWrapper);
        for (BookInfo tbookInfo :bookInfoList){
            System.out.println(tbookInfo.getBookid()+"\t"+tbookInfo.getBookname()+"\t"+tbookInfo.getPrice());
        }
    }

    //测试分页（不带条件的分页）
    @Test
    public void testPage(){
        //测试分页的方法Page构造函数的两个参数，第一个是页码，第二个是每页现实的容量
        IPage page = new Page(3,2);
        //调用分页的方法
        IPage bookPage = bookInfoMapper.selectPage(page,null);
        //分页page对象中返回的结果包括
        List<BookInfo> list = bookPage.getRecords();
        long pages = bookPage.getPages();//一共有多少页
        long total = bookPage.getTotal();//一共有多少条记录
        long current = bookPage.getCurrent();//当前页是多少
        System.out.println("共："+total+"条，"+pages+"页"+",当前页:"+current);

        for (BookInfo tbookInfo :list){
            System.out.println(tbookInfo.getBookid()+"\t"+tbookInfo.getBookname()+"\t"+tbookInfo.getPrice());
        }
    }
}
