package com.ctc.library.control;

import cn.hutool.core.util.StrUtil;
import com.ctc.library.control.dto.UnitDTO;
import com.ctc.library.entity.Unit;
import com.ctc.library.service.UnitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Unit")
public class UnitControl {
    @Autowired
    UnitServiceImpl unitService;
    @GetMapping("/GetUnitList")
    public HashMap<String, Object> GetUnitList(@RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize,
                                               @RequestParam(defaultValue = "") String UnitName){
        pageNum = (pageNum - 1) * pageSize;
        List<Unit> data = unitService.GetAllUnitsByPage(pageNum,pageSize,UnitName);
        Integer total = unitService.GetTotal(UnitName);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
    @PostMapping("/Update")
    public Unit Update(@RequestBody UnitDTO unitDTO){
        if (StrUtil.isBlank(unitDTO.getUnitName())) {
            return null;
        }
        else{
            if(unitService.update(unitDTO) == 1){
                return unitService.GetUnit(unitDTO);
            }
            else{
                return null;
            }
        }
    }
    @PostMapping("/Delete")
    public Integer Delete(@RequestBody UnitDTO unitDTO){
        return unitService.deleteUnit(unitDTO);
    }
    @PostMapping("/AddUnit")
    public Integer AddUnit(@RequestBody UnitDTO unitDTO) {
        //0:添加失败
        //1:添加成功
        //2:单位名已经存在
        if (StrUtil.isBlank(unitDTO.getUnitName())) {
            return 0;
        } else {
            Unit unit = unitService.GetUnit(unitDTO);
            if (unit != null) {
                return 2;
            }
            else{
                if(unitService.update(unitDTO)>0) {
                    return 1;
                }else{
                    return 0;
                }
            }
        }
    }
    @PostMapping("/GetAllUnits")
    public List<Unit> GetAllUnits(){
        return  unitService.GetAllUnits();
    }
}
