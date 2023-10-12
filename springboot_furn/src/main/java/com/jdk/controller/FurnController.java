package com.jdk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdk.bean.Furn;
import com.jdk.util.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.jdk.service.FurnService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FurnController {
    @Resource
    private FurnService furnService;

    /**1.如果前端请求是以json数据请求的，那么后端接收的参数必须加@RequestBody
     * 2.如果前端请求是以表单数据请求的，那么后端接收的参数不用加@RequestBody
     * 3.@RequestBody:将前端传递的json数据转换成java对象,即JavaBean
     * 4.@Validated对提交的数据进行校验
     */
    @PostMapping("/save")
    public Result save(@Validated @RequestBody Furn furn, Errors errors){
//        boolean save = furnService.save(furn);
//        if (save){
//            log.info("添加成功");
//            return Result.success();
//        }
//        log.info("添加失败");
//        return Result.error("500","添加失败");
        HashMap<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        if (map.isEmpty()){
            furnService.save(furn);
            return Result.success();
        }else {
            return Result.error("500","后端校验失败");
        }
    }
    //返回所有家居信息。
    @GetMapping("/furns")
    public Result list(){
        log.info("查询所有家居信息");
        List<Furn> furns = furnService.list();
        return Result.success(furns);
    }
    //修改更新家居信息
    @PutMapping("/update")
    public Result update(@RequestBody Furn furn){
        boolean update = furnService.updateById(furn);
        if (update){
            log.info("修改成功");
            return Result.success();
        }
        log.info("修改失败");
        return Result.error("500","修改失败");
    }
    //删除家居信息
    @DeleteMapping("/deleteById/id={id}")
    public Result deleteById(@PathVariable Integer id){//@PathVariableb:获取路径上的参数,即{id}里的id
        boolean delete = furnService.removeById(id);
        if (delete){
            log.info("删除成功");
            return Result.success();
        }
        log.info("删除失败");
        return Result.error("500","删除失败");
    }
    //根据id查询家居信息
    @GetMapping("/getById/id={id}")
    public Result getById(@PathVariable Integer id){
        Furn furn = furnService.getById(id);
        if (furn!=null){
            log.info("查询成功");
            return Result.success(furn);
        }
        log.info("查询失败");
        return Result.error("500","查询失败");
    }

    //分页的方法
    @GetMapping("/furnsByPage")
    public Result furnsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize){
        Page<Furn> page = furnService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }
    //待条件的分页查询
    @GetMapping("/furnsByPageAndCondition")
    public Result furnsByPageAndCondition(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                          @RequestParam(defaultValue = "") String search){
        if (search==null|| search.isEmpty()){
            return furnsByPage(pageNum,pageSize);
        }
        QueryWrapper<Furn> queryWrapper = Wrappers.query();
        queryWrapper.like("name",search);
        Page<Furn> page = furnService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }
}
