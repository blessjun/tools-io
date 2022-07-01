
package com.sunfujun.tools.io.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunfujun.tools.io.core.exception.BizException;
import com.sunfujun.tools.io.core.util.BeanConvertUtils;
import com.sunfujun.tools.io.core.util.ResponseCode;
import com.sunfujun.tools.io.mapper.SysJobInfoMapper;
import com.sunfujun.tools.io.model.dto.IdDto;
import com.sunfujun.tools.io.model.dto.JobDatasourceDto;
import com.sunfujun.tools.io.model.dto.JobDto;
import com.sunfujun.tools.io.model.entity.SysJobInfo;
import com.sunfujun.tools.io.model.vo.JobDatasourceVo;
import com.sunfujun.tools.io.model.vo.JobInfoVo;
import com.sunfujun.tools.io.model.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SysJobInfoService extends ServiceImpl<SysJobInfoMapper, SysJobInfo> {

    private static final Logger log = LoggerFactory.getLogger(SysJobInfoService.class);


    @Autowired
    private SysJobDatasourceService sysJobDatasourceService;


    //删除任务
    @Transactional
    public Result<Boolean> del(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        List<SysJobInfo> jobs = this.listByIds(ids);

        //删除任务对象
        boolean remove = this.removeByIds(ids);
        //删除数据源
        for (SysJobInfo job : jobs) {
            List<JobDatasourceVo> jobDatasourceVos = sysJobDatasourceService.queryByJobCode(job.getCode());
            List<Long> longList = jobDatasourceVos.stream().map((JobDatasourceVo vo) -> {
                        return vo.getId();
                    }
            ).collect(Collectors.toList());
            sysJobDatasourceService.del(longList);
        }
        return Result.buildSuccess(remove);
    }

    //增加或修改任务
    @Transactional
    public Result<Boolean> addOrUpd(JobDto dto) {
        //校验必填参数
        if (dto == null || dto.getJobDatasourceList() == null || dto.getJobDatasourceList().isEmpty()) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        List<SysJobInfo> infos = this.list();

        //校验编码重复
        boolean present = infos.stream().anyMatch(m -> m.getCode().equals(dto.getCode()) && !m.getId().equals(dto.getId()));
        if (present) {
            return Result.newInstance(ResponseCode.CODE_REPEAT);
        }
        //检验名称重复
        present = infos.stream().anyMatch(m -> m.getName().equals(dto.getName()) && !m.getId().equals(dto.getId()));
        if (present) {
            return Result.newInstance(ResponseCode.NAME_REPEAT);
        }

        SysJobInfo info = this.getById(dto.getId());
        //校验id 重复
        if (info == null) {
            info = new SysJobInfo();
        }
        //复制参数
        BeanUtil.copyProperties(dto, info);
        dto.setId(info.getId()); //保留新增的id

        Boolean flag = this.saveOrUpdate(info);
        if (!flag) {
            throw new BizException("增加任务失败");
        }
        List<JobDatasourceDto> SysJobDatasourceList = BeanConvertUtils.convertToList(dto.getJobDatasourceList(), JobDatasourceDto::new);
        //填入任务id
        SysJobDatasourceList = SysJobDatasourceList.stream().map((JobDatasourceDto dtov) -> {
            dtov.setJobCode(dto.getCode());
            return dtov;
        }).collect(Collectors.toList());
        //先删除数据源
        List<JobDatasourceVo> jobDatasourceVos = sysJobDatasourceService.queryByJobCode(info.getCode());
        //过滤出ids
        if (jobDatasourceVos != null && !jobDatasourceVos.isEmpty()) {
            List<Long> longList = jobDatasourceVos.stream().map((JobDatasourceVo vo) -> {
                        return vo.getId();
                    }
            ).collect(Collectors.toList());
            sysJobDatasourceService.del(longList);
        }
        //增加数据源
        flag = sysJobDatasourceService.addOrUpd(SysJobDatasourceList).getData();

        if (!flag) {
            throw new BizException("增加数据源失败");
        }
        return Result.buildSuccess(flag);
    }


    //根据id查询任务
    public JobInfoVo queryById(Long id) {
        IdDto idDto = new IdDto();
        idDto.setId(id);
        return query(idDto).getData();
    }

    //根据ids查询任务
    public List<JobInfoVo> queryByIds(List<Long> ids) {
        List<JobInfoVo> list = new ArrayList<>();
        for (Long id : ids) {
            IdDto idDto = new IdDto();
            idDto.setId(id);
            JobInfoVo data = query(idDto).getData();
            if (data != null) {
                list.add(data);
            }
        }

        return list;
    }

    //根据id查询任务
    public Result<JobInfoVo> query(IdDto dto) {
        //校验参数
        if (dto == null || dto.getId() == null || dto.getId() == 0L) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }

        SysJobInfo info = this.getById(dto.getId());
        //不存在此任务
        if (info == null) {
            return Result.newInstance(ResponseCode.NOT_FOUND);
        }
        //返回查询结果
        JobInfoVo vo = new JobInfoVo();
        BeanUtil.copyProperties(info, vo);
        List<JobDatasourceVo> jobDatasourceVos = sysJobDatasourceService.queryByJobCode(vo.getCode());
        //不需要转换
//        List<JobInterfaceRuleVo> list = new ArrayList<>();
//        if (!jobDatasourceVos.isEmpty()) {
//            list = BeanConvertUtils.convertToList(jobDatasourceVos, JobDatasourceVo::new);
//        }
        vo.setJobDatasourceList(jobDatasourceVos);
        return Result.buildSuccess(vo);
    }

    /*排序*/
    public void sort(List<JobInfoVo> jobInfoVoList) {
        jobInfoVoList.sort((o1, o2) -> o2.getSort() - o1.getSort());
    }
}

