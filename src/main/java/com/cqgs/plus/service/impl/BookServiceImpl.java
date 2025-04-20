package com.cqgs.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqgs.plus.entity.Book;
import com.cqgs.plus.entity.BookCategory;
import com.cqgs.plus.entity.BorrowRecord;
import com.cqgs.plus.entity.Reader;
import com.cqgs.plus.mapper.BookCategoryMapper;
import com.cqgs.plus.mapper.BookMapper;
import com.cqgs.plus.mapper.BorrowRecordMapper;
import com.cqgs.plus.mapper.ReaderMapper;
import com.cqgs.plus.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;
    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    @Autowired
    private ReaderMapper readerMapper;
//    @Override
    public List<Book> findBooks(){
        return bookMapper.selectList(null);
    }

    //添加书籍
    public void addBookInfo(Book bookInfo){

        bookMapper.insert(bookInfo);
    }

    //删除书籍
    public void deleteBookInfo(Integer id){
        System.out.println("业务方法的具体执行");
        Book bookInfo=bookMapper.selectById(id);
        //同时完成该信息的备份
        bookMapper.deleteById(id);
    }

    //修改书籍
    public void updateBookInfo(Book bookInfo){

        bookMapper.updateById(bookInfo);
    }

    //根据ID获取书籍信息
    public Book findBookById(Integer id){

        return bookMapper.selectById(id);
    }

    public  List<BookCategory>  findBookCategory(){
        return bookCategoryMapper.selectList(null);
    }

    public Map<String, Object> findHomeData() {
        Map<String, Object> result = new HashMap<>();
        System.out.println("查首页数据");

        // 获取今天的日期和最近五天的日期
        LocalDate today = LocalDate.now();
        List<LocalDate> lastFiveDays = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            lastFiveDays.add(today.minusDays(i));
        }

        // 格式化日期为字符串（yyyy-MM-dd）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 查询所有借阅记录
        List<BorrowRecord> allRecords = borrowRecordMapper.selectList(null);
        int totalCount = allRecords.size();
        Map<Integer, Integer> borrowCountMap = new HashMap<>();
        Map<String, Integer> dailyBorrowCount = new HashMap<>();

        // 统计借阅书籍数量及每天借阅次数
        for (BorrowRecord record : allRecords) {
            if (record.getBookId() != null) {
                Integer bookId = record.getBookId();
                borrowCountMap.put(bookId, borrowCountMap.getOrDefault(bookId, 0) + 1);

                // 获取借阅时间并格式化
                if (record.getBorrowTime() != null) {
                    String borrowDate = null;

                    // 判断 borrowTime 类型并转换为 LocalDate
                    if (record.getBorrowTime() instanceof java.sql.Timestamp) {
                        borrowDate = ((java.sql.Timestamp) record.getBorrowTime()).toLocalDateTime().toLocalDate().format(formatter);
                    } else {
                        borrowDate = ((java.util.Date) record.getBorrowTime()).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate().format(formatter);
                    }

                    // 如果是最近五天中的某一天，增加借阅次数
                    if (lastFiveDays.contains(LocalDate.parse(borrowDate))) {
                        dailyBorrowCount.put(borrowDate, dailyBorrowCount.getOrDefault(borrowDate, 0) + 1);
                    }
                }
            }
        }

        // 获取借阅次数最多的前5本书
        List<Map.Entry<Integer, Integer>> topBorrowedBooks = borrowCountMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // 按借阅次数降序
                .limit(5)
                .collect(Collectors.toList());

        // 转成更容易前端使用的结构，例如 List<Map<String, Object>>
        List<Map<String, Object>> top5List = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : topBorrowedBooks) {
            Integer bookId = entry.getKey();
            Book book = bookMapper.selectById(bookId);  // 查书名
            Map<String, Object> bookInfo = new HashMap<>();
            bookInfo.put("bookName", book != null ? book.getTitle() : "未知书名");

            BookCategory category = bookCategoryMapper.selectById(book != null ? book.getBookid() : 0);
            bookInfo.put("category", category != null ? category.getName() : "未知类别");
            bookInfo.put("bookId", entry.getKey());
            bookInfo.put("borrowCount", entry.getValue());
            top5List.add(bookInfo);
        }

        // 统计最近五天的借阅数据
        List<Map<String, Object>> dailyBorrowList = new ArrayList<>();
        for (LocalDate day : lastFiveDays) {
            Map<String, Object> dailyData = new HashMap<>();
            String dayString = day.format(formatter);
            dailyData.put("date", dayString);
            dailyData.put("borrowCount", dailyBorrowCount.getOrDefault(dayString, 0));
            dailyBorrowList.add(dailyData);
        }

        // 统计 status 分组的数量
        Map<Integer, Integer> statusCountMap = new HashMap<>();
        for (BorrowRecord record : allRecords) {
            Integer status = record.getStatus();
            if (status != null) {
                statusCountMap.put(status, statusCountMap.getOrDefault(status, 0) + 1);
            }
        }

        List<Map<String, Object>> statusStats = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : statusCountMap.entrySet()) {
            Integer status = entry.getKey();
            Integer count = entry.getValue();
            double percent = totalCount > 0 ? count * 100.0 / totalCount : 0;

            Map<String, Object> statusInfo = new HashMap<>();
            statusInfo.put("status", status);
            statusInfo.put("label", getStatusLabel(status)); // 显示中文标签
            statusInfo.put("count", count);
            statusInfo.put("percentage", String.format("%.1f", percent));

            statusStats.add(statusInfo);
        }
        Map<Integer, Integer> categoryCountMap = new HashMap<>();
//        前五分类的书籍类型
        List<Book> books=  bookMapper.selectList(null);
        for (Book book : books) {
            if (book.getCategoryid() != null) {
                int categoryId = book.getCategoryid();
                categoryCountMap.put(categoryId, categoryCountMap.getOrDefault(categoryId, 0) + 1);
            }
        }
        List<Integer> top5CategoryIds = categoryCountMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Map<String, Object>> temp = new ArrayList<>();
        for (Integer categoryId : top5CategoryIds) {
            BookCategory category = bookCategoryMapper.selectById(categoryId);

            // 从所有书中找出该分类的第一本书（也可以再查一次）
            Book representativeBook = books.stream()
                    .filter(book -> categoryId.equals(book.getCategoryid()))
                    .findFirst()
                    .orElse(null);

            Map<String, Object> item = new HashMap<>();
            item.put("categoryId", categoryId);
            item.put("categoryName", category != null ? category.getName() : "未知分类");
            item.put("bookCount", categoryCountMap.get(categoryId));
            temp.add(item);
        }

        result.put("top5BorrowedBooks", top5List);
        result.put("dailyBorrowData", dailyBorrowList);
        result.put("statusStats", statusStats);
        result.put("categoryCountMap", temp);
        return result;
    }

    private String getStatusLabel(int status) {
        switch (status) {
            case 1: return "借阅中";
            case 2: return "已归还";
            case 3: return "已逾期";
            case 4: return "已赔付";
            default: return "未知状态";
        }
    }

    public enum BookStatus {
        BORROWING(1, "借阅中"),
        RETURNED(2, "已归还"),
        OVERDUE(3, "已逾期"),
        COMPENSATED(4, "已赔付"),
        UNKNOWN(0, "未知状态");

        private final int code;
        private final String description;

        BookStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static BookStatus fromCode(int code) {
            for (BookStatus status : BookStatus.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return UNKNOWN;
        }
    }

    //  借书
    public void borrowBook(String bookId, String idCard) {
    System.out.println("用户id " + idCard + "图书id" + bookId);
    // 1. 查询读者
    Reader reader = readerMapper.selectOne(new QueryWrapper<Reader>().eq("id_card", idCard));
    if (reader == null) {
        throw new RuntimeException("未找到该读者");
    }

    // 2. 查询图书
    Book book = bookMapper.selectById(bookId);
    if (book == null) {
        throw new RuntimeException("未找到该图书");
    }

    //  判断是否有已经借了没还的
        BorrowRecord temp = borrowRecordMapper.selectOne(
                new QueryWrapper<BorrowRecord>()
                        .eq("reader_id", reader.getReaderId())
                        .eq("book_id", bookId)
                        .isNull("return_time")  // 未归还
        );

        if (temp != null) {
            throw new RuntimeException("您已借阅此书请勿重复借阅");
        }



    // 3. 判断图书是否可借
    if (book.getStatus() != 2) {
        throw new RuntimeException(BookStatus.fromCode(book.getStatus()).getDescription());
    }

    // 4. 更新图书状态
    book.setStatus(1);
    bookMapper.update(book, new QueryWrapper<Book>().eq("bookid", bookId));  // 根据bookId更新状态
    // 5. 添加借书记录
    BorrowRecord record = new BorrowRecord();
    System.out.println("书id："+ reader.getReaderId());
    record.setReaderId(reader.getReaderId());
    record.setBookId(Integer.valueOf(bookId));
    record.setBorrowTime(Timestamp.valueOf(LocalDateTime.now()));

    // 设置应还时间为借书时间 + 30天
    record.setDueTime(Timestamp.valueOf(LocalDateTime.now().plusDays(30)));

    borrowRecordMapper.insert(record);
}

    // 还书
    public void returnBook(String bookId, String idCard) {
        System.out.println("用户id " + idCard + " 归还图书id " + bookId);

        // 1. 查询读者
        Reader reader = readerMapper.selectOne(new QueryWrapper<Reader>().eq("id_card", idCard));
        if (reader == null) {
            throw new RuntimeException("未找到该读者");
        }

        // 2. 查询图书
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            throw new RuntimeException("未找到该图书");
        }

        // 3. 查询借书记录（未归还的）
        BorrowRecord record = borrowRecordMapper.selectOne(
                new QueryWrapper<BorrowRecord>()
                        .eq("reader_id", reader.getReaderId())
                        .eq("book_id", bookId)
                        .isNull("return_time")  // 未归还
        );


        if (record == null) {
            throw new RuntimeException("未找到对应的借书记录，可能已归还或未借阅");
        }

        // 4. 设置归还时间为当前时间
        record.setReturnTime(Timestamp.valueOf(LocalDateTime.now()));
        record.setStatus(2);
        borrowRecordMapper.updateById(record);

        // 5. 更新图书状态为“可借”（状态码为2）
        book.setStatus(2);
        bookMapper.update(book, new QueryWrapper<Book>().eq("bookid", bookId));
    }
}


