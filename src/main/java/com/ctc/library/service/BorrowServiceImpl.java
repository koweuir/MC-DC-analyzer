package com.ctc.library.service;

import com.ctc.library.control.dto.BorrowDTO;
import com.ctc.library.dao.BorrowDao;
import com.ctc.library.entity.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl {
    @Autowired
    private BorrowDao borrowDao;

    public int update(BorrowDTO borrowDTO) {
        //1：成功
        //0：失败
        if(borrowDao.selectBorrowByNo(borrowDTO) == null){
            borrowDTO.setBorrowStatus("待审核");
            Integer flag = borrowDao.insertBorrow(borrowDTO);
            return flag>0?1:0;
        }
        else{
            Integer flag = borrowDao.updateAll(borrowDTO);
            return flag>0?1:0;
        }
    }

    public Borrow GetBorrow(BorrowDTO borrowDTO) {
        return borrowDao.selectBorrowByNo(borrowDTO);
    }

    public List<Borrow> GetAllBorrowsIn(Integer pageNum, Integer pageSize, String userName, String borrowStatus) {
        return borrowDao.selectAllBorrowsIn(pageNum,pageSize,userName,borrowStatus);
    }

    public Integer GetTotalIn(String userName, String borrowStatus) {
        return borrowDao.selectTotalIn(userName,borrowStatus);
    }

    public List<Borrow> GetAllBorrowsOut(Integer pageNum, Integer pageSize, String unitName, String borrowStatus) {
        return borrowDao.selectAllBorrowsOut(pageNum,pageSize,unitName,borrowStatus);
    }

    public Integer GetTotalOut(String unitName, String borrowStatus) {
        return borrowDao.selectTotalOut(unitName,borrowStatus);
    }

    public List<Map<String, Object>> getBorrowCountByMonth(String unitName) {
        return borrowDao.getBorrowCountByMonth(unitName);
    }
}
