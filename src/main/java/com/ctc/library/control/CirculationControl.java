package com.ctc.library.control;

import cn.hutool.core.util.StrUtil;
import com.ctc.library.control.dto.CirculationDTO;
import com.ctc.library.entity.Circulation;
import com.ctc.library.service.CirculationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Circulation")
public class CirculationControl {
    @Autowired
    CirculationServiceImpl circulationService;
    @PostMapping("/Update")
    public Circulation Update(@RequestBody CirculationDTO circulationDTO){
        if (StrUtil.isBlank(circulationDTO.getBookId())) {
            return null;
        }
        else{
            int flag = circulationService.update(circulationDTO);
            if(flag == 1){
                return circulationService.GetCirculation(circulationDTO);
            }
            else{
                return null;
            }
        }
    }
    @PostMapping("/IsComeBack")
    public Integer IsComeBack(@RequestBody CirculationDTO circulationDTO){
        //0:还未归还图书，不能进行流通
        //1:已归还图书，可以进行流通
        if (StrUtil.isBlank(circulationDTO.getBookId())) {
            return 0;
        }
        else{
            Circulation circulation = circulationService.GetCirculation(circulationDTO);
            if(circulation == null){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
    @GetMapping("/GetInList")//流入列表
    public HashMap<String, Object> GetInList(@RequestParam Integer pageNum,
                                             @RequestParam Integer pageSize,
                                             @RequestParam(defaultValue = "") String unitTo,
                                             @RequestParam(defaultValue = "") String circulationStatus){
        pageNum = (pageNum - 1) * pageSize;
        List<Circulation> data = circulationService.GetAllCirculationsIn(pageNum,pageSize,unitTo,circulationStatus);
        Integer total = circulationService.GetTotalIn(unitTo,circulationStatus);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
    @GetMapping("/GetOutList")//流出列表
    public HashMap<String, Object> GetOutList(@RequestParam Integer pageNum,
                                             @RequestParam Integer pageSize,
                                             @RequestParam(defaultValue = "") String unitFrom,
                                             @RequestParam(defaultValue = "") String circulationStatus){
        pageNum = (pageNum - 1) * pageSize;
        List<Circulation> data = circulationService.GetAllCirculationsOut(pageNum,pageSize,unitFrom,circulationStatus);
        Integer total = circulationService.GetTotalOut(unitFrom,circulationStatus);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
    @GetMapping("/CountCirculation")
    public Integer CountCirculation(@RequestParam(defaultValue = "") String unitTo,
                                    @RequestParam(defaultValue = "") String unitFrom){
        return circulationService.countCirculation(unitTo,unitFrom);
    }
}
