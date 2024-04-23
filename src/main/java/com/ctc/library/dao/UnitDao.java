package com.ctc.library.dao;

import com.ctc.library.control.dto.UnitDTO;
import com.ctc.library.entity.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitDao {
    List<Unit> selectAllUnitsByPage(Integer pageNum, Integer pageSize, String unitName);

    Integer selectTotal(String unitName);

    Unit selectUnitByName(UnitDTO unitDTO);

    Integer insertUnit(UnitDTO unitDTO);

    Integer updateAll(UnitDTO unitDTO);

    Integer deleteByUnitNo(UnitDTO unitDTO);

    List<Unit> selectAllUnits();
}
