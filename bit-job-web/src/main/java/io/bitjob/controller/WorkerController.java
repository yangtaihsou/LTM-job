package io.bitjob.controller;

import io.bitjob.domain.Result;
import io.bitjob.domain.TriggerResult;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Deprecated
/*@Controller
@RequestMapping("/work/")*/
public class WorkerController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
    private final static int START_ALL_TYPE = 1;
    private final static int STOP_ALL_TYPE = 2;
    private final static int SINGLE_SCHEDULER_TYPE = 3;
    private final static int IS_START_TYPE = 4;
    List<Result> schedulerResultList;
    //@Resource(name="bizSchedulerMap")
    private Map<String, Scheduler> bizSchedulerMap;
    // @Resource(name="bizSchedulerNameMap")
    private Map<String, String> bizschedulerNameMap;
    // @Resource(name="baseSchedulerMap")
    private Map<String, Scheduler> baseSchedulerMap;
    //  @Resource(name="baseSchedulerNameMap")
    private Map<String, String> baseSchedulerNameMap;
    @Resource(name = "schedulerMap")
    private Map<String, Scheduler> schedulerMap = new HashMap<String, Scheduler>();
    @Resource(name = "schedulerNameMap")
    private Map<String, String> schedulerNameMap = new HashMap<String, String>();

    @Bean(name = "schedulerMap")
    private Map<String, Scheduler> schedulerMap() {
        schedulerMap.putAll(baseSchedulerMap);
        schedulerMap.putAll(bizSchedulerMap);
        return bizSchedulerMap;
    }

    @Bean(name = "schedulerNameMap")
    private Map<String, String> schedulerNameMap() {
        schedulerNameMap.putAll(baseSchedulerNameMap);
        schedulerNameMap.putAll(bizschedulerNameMap);
        return bizschedulerNameMap;
    }

    @RequestMapping(value = "baseList", method = RequestMethod.GET)
    public ModelAndView listBaseTask(Model model) {
        schedulerMap = baseSchedulerMap;
        schedulerNameMap = baseSchedulerNameMap;
        schedulerResultList = doExecute(IS_START_TYPE, "isStarted", "");
        ModelAndView mv = new ModelAndView("/QuartzManager");
        model.addAttribute("workerType", "base");
        model.addAttribute("schedulerResultList", schedulerResultList);
        return mv;
    }

    @RequestMapping(value = "bizList", method = RequestMethod.GET)
    public ModelAndView listBizTask(Model model) {
        schedulerMap = bizSchedulerMap;
        schedulerNameMap = bizschedulerNameMap;
        schedulerResultList = doExecute(IS_START_TYPE, "isStarted", "");
        ModelAndView mv = new ModelAndView("/QuartzManager");
        model.addAttribute("workerType", "biz");
        model.addAttribute("schedulerResultList", schedulerResultList);
        return mv;
    }


    @RequestMapping(value = "stop", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> stop(HttpServletRequest request, HttpServletResponse response) {
        String schedulerName = request.getParameter("schedulerName");
        List<Result> listResult = doExecute(SINGLE_SCHEDULER_TYPE, schedulerName, "stop");
        HashMap<String, String> map = new HashMap<String, String>();
        if (listResult != null) {
            map.put("status", "true");
            map.put("reason", "");
        } else {
            map.put("status", "false");
        }
        return map;
    }

    @RequestMapping(value = "start", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> start(HttpServletRequest request, HttpServletResponse response) {
        String schedulerName = request.getParameter("schedulerName");
        List<Result> listResult = doExecute(SINGLE_SCHEDULER_TYPE, schedulerName, "start");
        HashMap<String, String> map = new HashMap<String, String>();
        if (listResult != null) {
            map.put("status", "true");
            map.put("reason", "");
        } else {
            map.put("status", "false");
        }
        return map;
    }

    @RequestMapping(value = "stopBaseAll", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> stopBaseAll(HttpServletRequest request, HttpServletResponse response) {
        schedulerMap = baseSchedulerMap;
        schedulerNameMap = baseSchedulerNameMap;
        List<Result> listResult = doExecute(STOP_ALL_TYPE, "doPauseAll", "");
        HashMap<String, String> map = new HashMap<String, String>();
        if (listResult != null) {
            map.put("status", "true");
            map.put("reason", "");
        } else {
            map.put("status", "false");
        }
        return map;
    }

    @RequestMapping(value = "startBaseAll", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> startBaseAll(HttpServletRequest request, HttpServletResponse response) {
        schedulerMap = baseSchedulerMap;
        schedulerNameMap = baseSchedulerNameMap;
        List<Result> listResult = doExecute(START_ALL_TYPE, "doStartAll", "");
        HashMap<String, String> map = new HashMap<String, String>();
        if (listResult != null) {
            map.put("status", "true");
            map.put("reason", "");
        } else {
            map.put("status", "false");
        }
        return map;
    }

    @RequestMapping(value = "stopBizAll", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> stopBizAll(HttpServletRequest request, HttpServletResponse response) {
        schedulerMap = bizSchedulerMap;
        schedulerNameMap = bizschedulerNameMap;
        List<Result> listResult = doExecute(STOP_ALL_TYPE, "doPauseAll", "");
        HashMap<String, String> map = new HashMap<String, String>();
        if (listResult != null) {
            map.put("status", "true");
            map.put("reason", "");
        } else {
            map.put("status", "false");
        }
        return map;
    }

    @RequestMapping(value = "startBizAll", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> startBizAll(HttpServletRequest request, HttpServletResponse response) {
        schedulerMap = bizSchedulerMap;
        schedulerNameMap = bizschedulerNameMap;
        List<Result> listResult = doExecute(START_ALL_TYPE, "doStartAll", "");
        HashMap<String, String> map = new HashMap<String, String>();
        if (listResult != null) {
            map.put("status", "true");
            map.put("reason", "");
        } else {
            map.put("status", "false");
        }
        return map;
    }

    @RequestMapping(value = "updateTriggerRepeatInterval", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> updateTriggerRepeatInterval(HttpServletRequest request, HttpServletResponse response) throws SchedulerException {
        logger.info("updateTriggerRepeatInterval");
        String triggerName = request.getParameter("triggerName");
        String schedulerName = request.getParameter("schedulerName");
        String repeatInterval = request.getParameter("repeatInterval");
        boolean flag = false;
        Iterator<String> it = schedulerMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals(schedulerName)) {
                Scheduler scheduler = schedulerMap.get(key);
                String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
                for (String triggerNameVar : triggerNames) {
                    if (triggerNameVar.equals(triggerName)) {
                        SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP);
                        trigger.setRepeatInterval(Long.parseLong(repeatInterval));
                        scheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP, trigger);
                        flag = true;
                        break;
                    }
                }
            }
        }
        HashMap<String, String> map = new HashMap<String, String>();
        if (flag) {
            map.put("status", "true");
            map.put("reason", "");
        } else {
            map.put("status", "false");
        }
        return map;
    }

    private List<Result> doExecute(int type, String schedulerName, String status) {
        List<Result> listResult = new ArrayList<Result>();

        if (schedulerMap == null || schedulerMap.size() <= 0) {
            return null;
        }
        Iterator<String> it = schedulerNameMap.keySet().iterator();
        Scheduler scheduler = null;
        try {
            while (it.hasNext()) {
                Result result = new Result();
                String key = it.next();
                result.setKey(key);
                result.setName(schedulerNameMap.get(key));
                if (type == START_ALL_TYPE) {
                    // 启动所有的时间程序
                    scheduler = schedulerMap.get(key);
                    scheduler.start();
                    result.setStatus("1");
                } else if (type == STOP_ALL_TYPE) {
                    // 关闭所有的时间程序
                    scheduler = schedulerMap.get(key);
                    scheduler.standby();
                    result.setStatus("0");
                } else if (type == SINGLE_SCHEDULER_TYPE) {
                    // 启动一个指定的时间程序
                    if (key.equals(schedulerName)) {
                        scheduler = schedulerMap.get(key);
                        if (status.equals("start")) {
                            scheduler.start();
                            result.setStatus("1");
                        } else if (status.equals("stop")) {
                            scheduler.standby();
                            result.setStatus("0");
                        }
                    }
                } else if (type == IS_START_TYPE) {
                    scheduler = schedulerMap.get(key);
                    String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
                    List<TriggerResult> triggerResultList = new ArrayList<TriggerResult>();
                    for (String triggerName : triggerNames) {

                        SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP);
                        //执行频率
                        long repeatInterval = trigger.getRepeatInterval();
                        TriggerResult triggerResult = new TriggerResult();
                        triggerResult.setTriggerName(triggerName);
                        triggerResult.setRepeatInterval(repeatInterval);
                        triggerResultList.add(triggerResult);
                    }
                    result.setTriggerResultList(triggerResultList);
                    if (scheduler.isStarted()) {
                        result.setStatus("1");
                        if (scheduler.isInStandbyMode()) {
                            result.setStatus("0");
                        }
                    } else {
                        result.setStatus("0");
                    }
                }
                listResult.add(result);
            }
            logger.info("Method:" + schedulerName + "--->success");
        } catch (SchedulerException e) {

            logger.error("Method:" + schedulerName + "--->error", e);
            return null;
        }
        return listResult;
    }


}