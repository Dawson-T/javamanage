package com.cqgs.plus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqgs.plus.entity.Reader;
import com.cqgs.plus.service.ReaderService;
import com.cqgs.plus.util.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public HttpResult list(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           Reader readerParams) {
        System.out.println("查询读者列表");
        IPage<Reader> readerPage = readerService.findReaders(pageNum, pageSize, readerParams);
        return HttpResult.successResult(readerPage);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public HttpResult add(@RequestBody Reader reader) {
        System.out.println("添加读者信息");
        boolean success = readerService.addReader(reader);
        return success ? HttpResult.successResult("添加成功") : HttpResult.errorResult("添加失败");
    }

    @PostMapping(value = "update", consumes = "application/json")
    public HttpResult update(@RequestBody Reader reader) {
        System.out.println("更新读者信息");
        boolean success = readerService.updateReader(reader);
        return success ? HttpResult.successResult("更新成功") : HttpResult.errorResult("更新失败");
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public HttpResult delete(@RequestParam("id") Integer readerId) {
        System.out.println("删除读者 ID：" + readerId);
        boolean success = readerService.deleteReaderById(readerId);
        return success ? HttpResult.successResult("删除成功") : HttpResult.errorResult("删除失败");
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public HttpResult getById(@RequestParam("id") Integer readerId) {
        System.out.println("查询读者详情 ID：" + readerId);
        Reader reader = readerService.getReaderById(readerId);
        return reader != null ? HttpResult.successResult(reader) : HttpResult.errorResult("未找到读者信息");
    }

}
