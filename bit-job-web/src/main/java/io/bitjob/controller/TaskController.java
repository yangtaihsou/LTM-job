package io.bitjob.controller;

import io.bitjob.dao.TaskMapper;
import io.bitjob.domain.Task;
import io.bitjob.domain.TaskEnum;
import io.bitjob.service.cache.TaskServiceConfigCache;
import io.bitjob.util.DataResult;
import io.bitjob.util.PageQuery;
import io.bitjob.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;

/**

 * Date: 14-8-6
 * Time: 下午8:18
 */

@Controller
@RequestMapping("/task/")
public class TaskController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Resource(name = "taskMapper")
    protected TaskMapper taskMapper;

    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView listTask(String type, Integer p, Model model, Task task, HttpServletRequest request) throws Exception {
        PageQuery<Task> taskPageQuery = new PageQuery<Task>();
        p = p == null ? 1 : p;
        taskPageQuery.setPageSize(20);
        taskPageQuery.setPageNo((p - 1) * 20);
        taskPageQuery.setQuery(task);
        List<Task> taskList = taskMapper.queryBySelectiveForPagination(taskPageQuery);
        int taskCount = (int) taskMapper.queryCountBySelective(taskPageQuery);
        String joinPara = PageUtil.joinParameter(request, new String[]{"p"}, true);
        String pagestr = PageUtil.getPageStr(joinPara, 20, p, taskCount);
        model.addAttribute("pagestr", pagestr);
        model.addAttribute("taskList", taskList);
        model.addAttribute("taskCount", taskCount);

        model.addAttribute("taskTypeConfig", taskServiceConfigCache.taskServiceConfig_taskType_Map);
        if (type != null && type.equals("query")) {
            ModelAndView mv = new ModelAndView("/subtask/SubTaskResult");
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("/subtask/SubTaskList");
            return mv;
        }
    }

    @RequestMapping(value = "resetTask", method = RequestMethod.POST)
    @ResponseBody
    public DataResult resetTask(Long taskId, HttpServletRequest request) throws Exception {
        Task task = new Task();
        task.setId(taskId);
        DataResult result = new DataResult();
        try {
            task.setTaskStatus(TaskEnum.TaskStatus.init.status());
            int resultCode = taskMapper.resetTaskById(task);

            if (resultCode == 0) {

                result.setStatus(Boolean.FALSE);
                return result;
            }
            result.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            logger.error(request.getRequestURI(), e);
            result.setReason("重发失败");
        }
        return result;
    }

    @RequestMapping(value = "restTaskByQuery", method = RequestMethod.GET)
    public ModelAndView restTaskByQuery(String type, Integer p, Model model, Task task, HttpServletRequest request) throws Exception {
        Integer status = task.getTaskStatus();
        if (task == null || task.getTaskType() == null || task.getCreateTime() == null || task.getLastUpdate() == null || status == null) {
            model.addAttribute("resetCount", "。重置任务0条,请填写任务类型、区间时间、选择锁定或者完成状态");
        } else {
            if (status == TaskEnum.TaskStatus.init.status()) {
                model.addAttribute("resetCount", "。重置任务0条,选择状态为锁定或者完成");
            } else {
                PageQuery<Task> taskPageQuery = new PageQuery<Task>();
                if (!StringUtils.isEmpty(type)) {
                    if (type.equals("init")) {
                        taskPageQuery.setPageNo(TaskEnum.TaskStatus.init.status());
                        taskPageQuery.setQuery(task);
                        int resetCount = taskMapper.resetTaskByQuery(taskPageQuery);
                        model.addAttribute("resetCount", "。重置任务" + resetCount + "条");
                    } else if (type.equals("stop")) {
                        taskPageQuery.setPageNo(TaskEnum.TaskStatus.stop.status());
                        taskPageQuery.setQuery(task);
                        int resetCount = taskMapper.resetTaskByQuery(taskPageQuery);
                        model.addAttribute("resetCount", "。暂停任务" + resetCount + "条");
                    }
                }
            }
        }
        PageQuery<Task> taskPageQuery = new PageQuery<Task>();

        p = p == null ? 1 : p;
        taskPageQuery.setPageSize(20);
        taskPageQuery.setPageNo((p - 1) * 20);
        task.setTaskStatus(status);
        taskPageQuery.setQuery(task);
        List<Task> taskList = taskMapper.queryBySelectiveForPagination(taskPageQuery);
        int taskCount = (int) taskMapper.queryCountBySelective(taskPageQuery);
        String joinPara = PageUtil.joinParameter(request, new String[]{"p"}, true);
        String pagestr = PageUtil.getPageStr(joinPara, 20, p, taskCount);
        model.addAttribute("pagestr", pagestr);
        model.addAttribute("taskList", taskList);
        model.addAttribute("taskCount", taskCount);

        model.addAttribute("taskTypeConfig", taskServiceConfigCache.taskServiceConfig_taskType_Map);

        ModelAndView mv = new ModelAndView("/subtask/SubTaskResult");
        return mv;

    }

}
