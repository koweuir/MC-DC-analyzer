package com.ctc.library.service;

import com.ctc.library.control.dto.CirculationDTO;
import com.ctc.library.dao.CirculationDao;
import com.ctc.library.entity.Circulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CirculationServiceImpl {
    @Autowired
    private CirculationDao circulationDao;

    public List<Circulation> GetAllCirculationsIn(Integer pageNum, Integer pageSize, String unitTo, String circulationStatus) {
        return circulationDao.selectAllCirculationsIn(pageNum,pageSize,unitTo,circulationStatus);
    }
    public Integer GetTotalIn(String unitTo, String circulationStatus) {
        return circulationDao.selectTotalIn(unitTo,circulationStatus);
    }

    public List<Circulation> GetAllCirculationsOut(Integer pageNum, Integer pageSize, String unitFrom, String circulationStatus) {
        return circulationDao.selectAllCirculationsOut(pageNum,pageSize,unitFrom,circulationStatus);
    }

    public Integer GetTotalOut(String unitFrom, String circulationStatus) {
        return circulationDao.selectTotalOut(unitFrom,circulationStatus);
    }

    public int update(CirculationDTO circulationDTO) {
        //1：成功
        //0：失败
        if(circulationDao.selectCirculationByNo(circulationDTO) == null){
            circulationDTO.setCirculationStatus("待审核");
            Integer flag = circulationDao.insertCirculation(circulationDTO);
            return flag>0?1:0;
        }
        else{
            Integer flag = circulationDao.updateAll(circulationDTO);
            return flag>0?1:0;
        }
    }

    public Circulation GetCirculation(CirculationDTO circulationDTO) {
        return circulationDao.selectCirculationByNo(circulationDTO);
    }

    public Integer countCirculation(String unitTo, String unitFrom) {
        return circulationDao.countCirculation(unitTo,unitFrom);
    }
}
