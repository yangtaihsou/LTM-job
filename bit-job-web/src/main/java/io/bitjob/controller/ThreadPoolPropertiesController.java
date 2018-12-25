package io.bitjob.controller;

import io.bitjob.dao.ThreadPoolPropertiesMapper;
import io.bitjob.domain.TaskServiceConfig;
import io.bitjob.domain.ThreadPoolProperties;
import io.bitjob.schedule.base.DataHandlerService;
import io.bitjob.service.cache.TaskServiceConfigCache;
import io.bitjob.service.cache.ThreadPoolPersistence;
import io.bitjob.util.DataResult;
import io.bitjob.util.PageQuery;
import io.bitjob.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolProperties(白拿任务线程池配置)的controller

 */
@Controller
@RequestMapping("/threadPoolProperties")
public class ThreadPoolPropertiesController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolPropertiesController.class);
    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;
    @Resource(name = "serviceMap")
    LinkedHashMap<String, DataHandlerService> serviceMap;
    @Resource(name = "threadPoolPersistence")
    ThreadPoolPersistence threadPoolPersistence;
    @Resource(name = "threadPoolPropertiesMapper")
    private ThreadPoolPropertiesMapper threadPoolPropertiesMapper;

    /**
     * 计算默认核心线程数量
     *
     * @return
     */
    private Integer defaultCoreSize(Integer taskType) {
        TaskServiceConfig taskServiceConfig = taskServiceConfigCache.getConfigByType(taskType);
        if (taskServiceConfig != null) {
            Integer perSize = taskServiceConfig.getTaskSizePerThread();
            Integer totalSize = taskServiceConfig.getTaskTotalSize();
            if (perSize != null && perSize > 0 && totalSize != null && totalSize > 0) {
                Integer coreSize = totalSize / perSize;
                return coreSize;
            }
        }
        return null;
    }

    /**
     * 获取列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String type, Integer p, Model model, ThreadPoolProperties threadPoolProperties, HttpServletRequest request) throws Exception {
        setDictionayToModel(model);
        PageQuery<ThreadPoolProperties> pageQuery = new PageQuery<ThreadPoolProperties>();
        p = p == null ? 1 : p;
        pageQuery.setPageSize(20);
        pageQuery.setPageNo((p - 1) * 20);
        pageQuery.setQuery(threadPoolProperties);
        List<ThreadPoolProperties> threadPoolPropertiesList = threadPoolPropertiesMapper.queryBySelectiveForPagination(pageQuery);
        int count = (int) threadPoolPropertiesMapper.queryCountBySelective(pageQuery);
        String joinPara = PageUtil.joinParameter(request, new String[]{"p"}, true);
        String pagestr = PageUtil.getPageStr(joinPara, 20, p, count);
        model.addAttribute("pagestr", pagestr);
        model.addAttribute("list", threadPoolPropertiesList);
        model.addAttribute("count", count);
        model.addAttribute("serviceMap", serviceMap);
        model.addAttribute("parameterMap", request.getParameterMap());
        ModelAndView mv = new ModelAndView("/threadPoolProperties/ThreadPoolPropertiesList");
        if (threadPoolPropertiesList != null && threadPoolPropertiesList.size() > 0) {
            for (ThreadPoolProperties poolProperties : threadPoolPropertiesList) {
                poolProperties.setWarnningCorePoolSize(defaultCoreSize(poolProperties.getTaskType()));
                ThreadPoolTaskExecutor threadPoolTaskExecutor = threadPoolPersistence.getThreadPoolMap().get(String.valueOf(poolProperties.getTaskType()));
                if (threadPoolTaskExecutor != null) {
                    ThreadPoolExecutor threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();
                    poolProperties.setTaskCount(threadPoolExecutor.getTaskCount());
                    poolProperties.setCompletedTaskCount(threadPoolExecutor.getCompletedTaskCount());
                    poolProperties.setLargestPoolSize(threadPoolExecutor.getLargestPoolSize());
                    poolProperties.setGetPoolSize(threadPoolExecutor.getPoolSize());
                    poolProperties.setActiveCount(threadPoolExecutor.getActiveCount());
                    poolProperties.setQueueSize(threadPoolExecutor.getQueue().size());
                    System.out.println("getActiveCount=" + threadPoolExecutor.getActiveCount() + "getMaximumPoolSize" + threadPoolExecutor.getMaximumPoolSize() + "getQueue" + threadPoolExecutor.getQueue().size());
                    int queueSize = threadPoolExecutor.getQueue().size();
                    if (queueSize > 0) {
                        System.out.println("getActiveCount=" + threadPoolExecutor.getActiveCount() + "getMaximumPoolSize" + threadPoolExecutor.getMaximumPoolSize() + "getQueue" + threadPoolExecutor.getQueue().size());
                    }
                }
            }
        }

        return mv;

    }

    @RequestMapping(value = "/toSave", method = RequestMethod.GET)
    public String toSave(Model model) {
        setDictionayToModel(model);
        return "/threadPoolProperties/ThreadPoolPropertiesAdd";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    //    @ParaValidator(validatorNames = {"threadPoolPropertiesValidator", "testValidator"})
    public DataResult save(ThreadPoolProperties threadPoolProperties, HttpServletRequest request) {
        DataResult result = new DataResult();
        result.setStatus(Boolean.TRUE);
        try {

            Long resultLong = threadPoolPropertiesMapper.save(threadPoolProperties);
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
        ThreadPoolProperties threadPoolProperties = threadPoolPropertiesMapper.queryByPrimaryKey(id);
        model.addAttribute("threadPoolProperties", threadPoolProperties);
        model.addAttribute("serviceMap", serviceMap);
        model.addAttribute("warnningCorePoolSize", defaultCoreSize(threadPoolProperties.getTaskType()));
        setDictionayToModel(model);
        return "/threadPoolProperties/ThreadPoolPropertiesEdit";
    }


    /**
     * 更新一个
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    // @ParaValidator(validatorNames = {"threadPoolPropertiesValidator", "testValidator"})
    public DataResult edit(ThreadPoolProperties threadPoolProperties, HttpServletRequest request) {
        DataResult result = new DataResult();
        try {

            int resultCode = threadPoolPropertiesMapper.updateByPrimaryKeySelective(threadPoolProperties);
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
    public DataResult updateStatus(Integer id, Integer status, HttpServletRequest request) {

        DataResult result = new DataResult();
        try {
            ThreadPoolProperties threadPoolProperties = new ThreadPoolProperties();
            threadPoolProperties.setId(id);
            int resultCode = threadPoolPropertiesMapper.updateByPrimaryKeySelective(threadPoolProperties);
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

            int resultCode = threadPoolPropertiesMapper.deleteByPrimaryKey(id);

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


    private void setDictionayToModel(Model model) {

        Map<Integer, String> taskTypeMap = new HashMap<Integer, String>();
        Map<Integer, TaskServiceConfig> taskServiceConfigMap = taskServiceConfigCache.taskServiceConfig_taskType_Map;
        for (Integer key : taskServiceConfigMap.keySet()) {
            taskTypeMap.put(key, taskServiceConfigMap.get(key).getTaskTypeDesc());
        }
        model.addAttribute("taskTypeMap", taskTypeMap);
    }

}