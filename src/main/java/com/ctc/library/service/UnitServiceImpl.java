package com.ctc.library.service;

import com.ctc.library.control.dto.UnitDTO;
import com.ctc.library.dao.UnitDao;
import com.ctc.library.entity.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl {
    @Autowired
    UnitDao unitDao;

    public List<Unit> GetAllUnitsByPage(Integer pageNum, Integer pageSize, String unitName) {
        return unitDao.selectAllUnitsByPage(pageNum,pageSize,unitName);
    }

    public Integer GetTotal(String unitName) {
        return unitDao.selectTotal(unitName);
    }

    public int update(UnitDTO unitDTO) {
        //1：成功
        //0：失败
        if(unitDao.selectUnitByName(unitDTO) == null){
            Integer flag = unitDao.insertUnit(unitDTO);
            return flag>0?1:0;
        }
        else{
            Integer flag = unitDao.updateAll(unitDTO);
            return flag>0?1:0;
        }
    }

    public Unit GetUnit(UnitDTO unitDTO) {
        return unitDao.selectUnitByName(unitDTO);
    }

    public Integer deleteUnit(UnitDTO unitDTO) {
        return unitDao.deleteByUnitNo(unitDTO);
    }

    public List<Unit> GetAllUnits() {
        return unitDao.selectAllUnits();
    }
}
