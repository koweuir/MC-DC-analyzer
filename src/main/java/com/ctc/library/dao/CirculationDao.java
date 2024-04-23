package com.ctc.library.dao;

import com.ctc.library.control.dto.CirculationDTO;
import com.ctc.library.entity.Circulation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CirculationDao {
    List<Circulation> selectAllCirculationsIn(Integer pageNum, Integer pageSize, String unitTo, String circulationStatus);

    Integer selectTotalIn(String unitTo, String circulationStatus);

    List<Circulation> selectAllCirculationsOut(Integer pageNum, Integer pageSize, String unitFrom, String circulationStatus);

    Integer selectTotalOut(String unitFrom, String circulationStatus);

    Circulation selectCirculationByNo(CirculationDTO circulationDTO);

    Integer insertCirculation(CirculationDTO circulationDTO);

    Integer updateAll(CirculationDTO circulationDTO);

    Integer countCirculation(String unitTo, String unitFrom);
}
