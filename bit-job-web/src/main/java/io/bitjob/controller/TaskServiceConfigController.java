package io.bitjob.controller;

import io.bitjob.dao.TaskServiceConfigMapper;
import io.bitjob.domain.TaskServiceConfig;
import io.bitjob.schedule.base.DataHandlerService;
import io.bitjob.service.cache.TaskServiceConfigCache;
import io.bitjob.util.DataResult;
import io.bitjob.util.PageQuery;
import io.bitjob.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/taskServiceConfig")
public class TaskServiceConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceConfigController.class);
    @Resource(name = "serviceMap")
    LinkedHashMap<String, DataHandlerService> serviceMap;
    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;
    @Resource(name = "taskServiceConfigMapper")
    private TaskServiceConfigMapper taskServiceConfigMapper;

    /**
     * 获取列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String type, Integer p, Model model, TaskServiceConfig taskServiceConfig, HttpServletRequest request) throws Exception {
        PageQuery<TaskServiceConfig> pageQuery = new PageQuery<TaskServiceConfig>();
        p = p == null ? 1 : p;
        pageQuery.setPageSize(20);
        pageQuery.setPageNo((p - 1) * 20);
        pageQuery.setQuery(taskServiceConfig);
        List<TaskServiceConfig> taskList = taskServiceConfigMapper.queryBySelectiveForPagination(pageQuery);
        int count = (int) taskServiceConfigMapper.queryCountBySelective(pageQuery);
        String joinPara = PageUtil.joinParameter(request, new String[]{"p"}, true);
        String pagestr = PageUtil.getPageStr(joinPara, 20, p, count);
        model.addAttribute("pagestr", pagestr);
        model.addAttribute("list", taskList);
        model.addAttribute("count", count);
        model.addAttribute("serviceMap", serviceMap);
        model.addAttribute("taskServiceConfigMap", taskServiceConfigCache.taskServiceConfig_taskParentType_Map);
        model.addAttribute("parameterMap", request.getParameterMap());
        ModelAndView mv = new ModelAndView("/taskServiceConfig/TaskServiceConfigList");
        //catalinaHome=/export/servers/tomcat6.0.33,catalinaBase=/export/Domains/job.com/server1
        String catalinaHome = System.getProperty("catalina.home");
        String catalinaBase = System.getProperty("catalina.base");
        InetAddress addr = InetAddress.getLocalHost();
        String localip = addr.getHostAddress();
        logger.info("catalinaHome=" + catalinaHome + ",catalinaBase=" + catalinaBase + ",localip=" + localip);
        return mv;

    }

    @RequestMapping(value = "/toSave", method = RequestMethod.GET)
    public String toSave(Model model) {
        model.addAttribute("serviceMap", serviceMap);
        return "/taskServiceConfig/TaskServiceConfigAdd";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    //@ParaValidator(validatorNames = {"taskServiceConfigValidator","testValidator"})
    public DataResult save(TaskServiceConfig taskServiceConfig, HttpServletRequest request) {
        DataResult result = new DataResult();
        result.setStatus(Boolean.TRUE);
        try {
            if (taskServiceConfig.getTaskParentType() != null) {
                String taskDesc = taskServiceConfigCache.taskServiceConfig_taskType_Map.get(taskServiceConfig.getTaskParentType()).getTaskTypeDesc();
                taskServiceConfig.setTaskParentTypeDesc(taskDesc);
            }
            taskServiceConfig.setStatus(0);//初始化是停止的
            Long resultLong = taskServiceConfigMapper.save(taskServiceConfig);
            if (resultLong == 0) {

                result.setStatus(Boolean.FALSE);
            }
        } catch (Exception e) {
            logger.error(request.getRequestURI(), e);
            result.setStatus(Boolean.FALSE);

            result.setReason("保存失败");
        }
        return result;
    }

    @RequestMapping(value = "/toEdit", method = RequestMethod.GET)
    public String toEdit(Model model, Long id, HttpServletRequest request) throws Exception {
        TaskServiceConfig taskServiceConfig = taskServiceConfigMapper.queryByPrimaryKey(id);
        model.addAttribute("taskServiceConfig", taskServiceConfig);
        model.addAttribute("serviceMap", serviceMap);

        model.addAttribute("taskServiceConfigMap", taskServiceConfigCache.taskServiceConfig_taskType_Map);
        return "/taskServiceConfig/TaskServiceConfigEdit";
    }


    /**
     * 更新一个
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    //@ParaValidator(validatorNames = {"taskServiceConfigValidator","testValidator"})
    public DataResult edit(TaskServiceConfig taskServiceConfig, HttpServletRequest request) {
        DataResult result = new DataResult();
        try {
            if (taskServiceConfig.getTaskParentType() != null) {
                String taskDesc = taskServiceConfigCache.taskServiceConfig_taskType_Map.get(taskServiceConfig.getTaskParentType()).getTaskTypeDesc();
                taskServiceConfig.setTaskParentTypeDesc(taskDesc);
            }
            int resultCode = taskServiceConfigMapper.updateByPrimaryKeySelective(taskServiceConfig);
            if (resultCode == 0) {

                result.setStatus(Boolean.FALSE);
                result.setReason("更新失败:没有找到此条数据");
                return result;
            }
            result.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            logger.error(request.getRequestURI(), e);
            result.setStatus(Boolean.FALSE);

            result.setReason("更新失败");
        }
        return result;
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public DataResult updateStatus(Long id, Integer status, HttpServletRequest request) {

        DataResult result = new DataResult();
        try {
            TaskServiceConfig taskServiceConfig = new TaskServiceConfig();
            taskServiceConfig.setId(id);
            taskServiceConfig.setStatus(status);
            int resultCode = taskServiceConfigMapper.updateByPrimaryKeySelective(taskServiceConfig);
            if (resultCode == 0) {

                result.setStatus(Boolean.FALSE);
                return result;
            }
            result.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            logger.error(request.getRequestURI(), e);
            result.setStatus(Boolean.FALSE);

            result.setReason("更新状态失败");

        }
        return result;
    }

    /**
     * @param status
     * @return
     */
    @RequestMapping(value = "updateAllStatus", method = RequestMethod.POST)
    @ResponseBody
    public DataResult updateAllStatus(Integer status, HttpServletRequest request) {

        DataResult result = new DataResult();
        try {
            TaskServiceConfig taskServiceConfig = new TaskServiceConfig();
            taskServiceConfig.setStatus(status);
            int resultCode = taskServiceConfigMapper.update(taskServiceConfig);
            if (resultCode == 0) {

                result.setStatus(Boolean.FALSE);
                return result;
            }
            result.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            logger.error(request.getRequestURI(), e);
            result.setStatus(Boolean.FALSE);

            result.setReason("更新状态失败");

        }
        return result;
    }

    /**
     * 删除一个
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public DataResult delete(Long id, HttpServletRequest request) {

        DataResult result = new DataResult();
        try {

            int resultCode = taskServiceConfigMapper.deleteByPrimaryKey(id);

            if (resultCode == 0) {

                result.setStatus(Boolean.FALSE);
                return result;
            }
            result.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            logger.error(request.getRequestURI(), e);
            result.setReason("删除失败");
        }
        return result;
    }


}