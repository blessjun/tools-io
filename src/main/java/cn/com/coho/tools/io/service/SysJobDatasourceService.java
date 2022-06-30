
package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.core.exception.BizException;
import cn.com.coho.tools.io.core.util.BeanConvertUtils;
import cn.com.coho.tools.io.core.util.ResponseCode;
import cn.com.coho.tools.io.mapper.SysJobDatasourceMapper;
import cn.com.coho.tools.io.model.dto.JobDatasourceDto;
import cn.com.coho.tools.io.model.entity.SysJobDatasource;
import cn.com.coho.tools.io.model.vo.JobDatasourceVo;
import cn.com.coho.tools.io.model.vo.Result;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysJobDatasourceService extends ServiceImpl<SysJobDatasourceMapper, SysJobDatasource> {

    private static final Logger log = LoggerFactory.getLogger(SysJobDatasourceService.class);


    //删除
    @Transactional
    public Result<Boolean> del(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        List<SysJobDatasource> jobs = this.listByIds(ids);
        //删除任务对象
        boolean remove = this.removeByIds(ids);
        return Result.buildSuccess(remove);
    }

    //增加或修改
    @Transactional
    public Result<Boolean> addOrUpd(List<JobDatasourceDto> jobDatasourceDtoList) {
        //校验必填参数
//        if (jobDatasourceDto == null || jobDatasourceDto.getInterfaces() == null || dto.getInterfaces().isEmpty()) {
//            return Result.newInstance(ResponseCode.PARAM_FAIL);
//        }
//        List<SysJobInfo> infos = this.list();

        //校验编码重复
//        boolean present = infos.stream().anyMatch(m -> m.getCode().equals(dto.getCode()) && !m.getId().equals(dto.getId()));
//        if (present) {
//            return Result.newInstance(ResponseCode.CODE_REPEAT);
//        }
        //检验名称重复
//        present = infos.stream().anyMatch(m -> m.getName().equals(dto.getName()) && !m.getId().equals(dto.getId()));
//        if (present) {
//            return Result.newInstance(ResponseCode.NAME_REPEAT);
//        }
        boolean flag = true;
        for (JobDatasourceDto jobDatasourceDto : jobDatasourceDtoList) {
            SysJobDatasource info = this.getById(jobDatasourceDto.getId());
            //校验id 重复
            if (info == null) {
                info = new SysJobDatasource();
            }
            //复制参数
            BeanUtil.copyProperties(jobDatasourceDto, info);
            flag = this.saveOrUpdate(info);
        }


        if (!flag) {
            throw new BizException("数据源增加失败");
        }
        return Result.buildSuccess(flag);
    }


    //模糊查询任务
    public List<JobDatasourceVo> query(JobDatasourceDto jobDatasourceDto) {
        //校验参数
//        if (dto == null || dto.getId() == null || dto.getId() == 0L) {
//            return Result.newInstance(ResponseCode.PARAM_FAIL);
//        }
        LambdaQueryWrapper<SysJobDatasource> queryWrapper = new LambdaQueryWrapper<>();//不存在此任务
        queryWrapper.eq(SysJobDatasource::getJobCode, jobDatasourceDto.getJobCode());
        List<SysJobDatasource> sysJobDatasourceList = this.getBaseMapper().selectList(queryWrapper);
        //返回查询结果
        JobDatasourceVo vo = new JobDatasourceVo();
        List<JobDatasourceVo> list = new ArrayList<>();
        if (!sysJobDatasourceList.isEmpty()) {
            list = BeanConvertUtils.convertToList(sysJobDatasourceList, JobDatasourceVo::new);
        }
        BeanUtil.copyProperties(sysJobDatasourceList, vo);

        return list;
    }

    //根据任务编码查询数据源
    public List<JobDatasourceVo> queryByJobCode(String jobCode) {
        JobDatasourceDto jobDatasourceDto = new JobDatasourceDto();
        jobDatasourceDto.setJobCode(jobCode);
        return query(jobDatasourceDto);
    }
}

