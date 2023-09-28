package com.flyingpig.controller;

import com.flyingpig.dataobject.entity.SupervisionTask;
import com.flyingpig.dataobject.vo.SupervisionTaskAddVO;
import com.flyingpig.pojo.PageBean;
import com.flyingpig.pojo.Result;
import com.flyingpig.service.SupervisionTaskServie;
import com.flyingpig.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/supervisiontasks")
public class SupervisionTaskController {
    @Autowired
    private SupervisionTaskServie supervisionTaskServie;
    @GetMapping("")
    private Result getSupervisonTaskPageBySupervisonId(@RequestParam(defaultValue = "1")Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize,@RequestHeader String Authorization){
        //记录日志
        log.info("分页查询，参数：{}，{}",pageNo,pageSize);
        //调用业务层分页查询功能
        Claims claims= JwtUtil.parseJwt(Authorization);
        String id=claims.getSubject();
        Integer userId=Integer.parseInt(id);
        PageBean pageBean= supervisionTaskServie.page(pageNo,pageSize,userId);
        //响应
        return Result.success(pageBean);
    }
    @PostMapping("")
    public Result addSupervisonTaskByTeaUserIdAndSupervisionTaskAddVO(@RequestHeader String Authorization,@RequestBody SupervisionTaskAddVO supervisionTaskAddVO){
        Claims claims= JwtUtil.parseJwt(Authorization);
        String teaUserid=claims.getSubject();
        supervisionTaskServie.addSupervisonTaskByTeaUserIdAndSupervisionTaskAddVO(teaUserid,supervisionTaskAddVO);
        return Result.success();
    }
    @DeleteMapping("")
    public Result deleteSupervisonTaskByTeaUserIdAndSupervisionTaskAddVO(@RequestHeader String Authorization,@RequestBody SupervisionTaskAddVO supervisionTaskAddVO){
        Claims claims= JwtUtil.parseJwt(Authorization);
        String teaUserid=claims.getSubject();
        supervisionTaskServie.deleteSupervisonTaskByTeaUserIdAndSupervisionTaskAddVO(teaUserid,supervisionTaskAddVO);
        return Result.success();
    }
}
