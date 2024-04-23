package com.ctc.library.dao;

import com.ctc.library.control.dto.BorrowDTO;
import com.ctc.library.entity.Borrow;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BorrowDao {
    Borrow selectBorrowByNo(BorrowDTO borrowDTO);

    Integer insertBorrow(BorrowDTO borrowDTO);

    Integer updateAll(BorrowDTO borrowDTO);

    List<Borrow> selectAllBorrowsIn(Integer pageNum, Integer pageSize, String userName, String borrowStatus);

    Integer selectTotalIn(String userName, String borrowStatus);

    List<Borrow> selectAllBorrowsOut(Integer pageNum, Integer pageSize, String unitName, String borrowStatus);

    Integer selectTotalOut(String unitName, String borrowStatus);
    @MapKey("month")
    List<Map<String, Object>> getBorrowCountByMonth(String unitName);
}
