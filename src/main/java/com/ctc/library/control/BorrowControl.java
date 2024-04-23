package com.ctc.library.control;

import cn.hutool.core.util.StrUtil;
import com.ctc.library.control.dto.BorrowDTO;
import com.ctc.library.entity.Borrow;
import com.ctc.library.service.BorrowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Borrow")
public class BorrowControl {
    @Autowired
    BorrowServiceImpl borrowService;
    @PostMapping("/Update")
    public Borrow Update(@RequestBody BorrowDTO borrowDTO){
        if (StrUtil.isBlank(borrowDTO.getBookId())) {
            return null;
        }
        else{
            int flag = borrowService.update(borrowDTO);
            if(flag == 1){
                return borrowService.GetBorrow(borrowDTO);
            }
            else{
                return null;
            }
        }
    }
    @PostMapping("/IsComeBack")
    public Integer IsComeBack(@RequestBody BorrowDTO borrowDTO){
        //0:还未归还图书，不能进行借阅
        //1:已归还图书，可以进行借阅
        if (StrUtil.isBlank(borrowDTO.getBookId())) {
            return 0;
        }
        else{
            Borrow borrow = borrowService.GetBorrow(borrowDTO);
            if(borrow == null){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
    @GetMapping("/GetInList")//借阅列表
    public HashMap<String, Object> GetInList(@RequestParam Integer pageNum,
                                             @RequestParam Integer pageSize,
                                             @RequestParam(defaultValue = "") String userName,
                                             @RequestParam(defaultValue = "") String borrowStatus){
        pageNum = (pageNum - 1) * pageSize;
        List<Borrow> data = borrowService.GetAllBorrowsIn(pageNum,pageSize,userName,borrowStatus);
        Integer total = borrowService.GetTotalIn(userName,borrowStatus);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
    @GetMapping("/GetOutList")//借出列表
    public HashMap<String, Object> GetOutList(@RequestParam Integer pageNum,
                                              @RequestParam Integer pageSize,
                                              @RequestParam(defaultValue = "") String unitName,
                                              @RequestParam(defaultValue = "") String borrowStatus){
        pageNum = (pageNum - 1) * pageSize;
        List<Borrow> data = borrowService.GetAllBorrowsOut(pageNum,pageSize,unitName,borrowStatus);
        Integer total = borrowService.GetTotalOut(unitName,borrowStatus);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    @GetMapping("/GetCountByMonth")
    public List<Map<String, Object>> GetCountByMonth(@RequestParam String unitName) {
        return borrowService.getBorrowCountByMonth(unitName);
    }
}
