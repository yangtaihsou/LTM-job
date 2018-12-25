package io.bitjob.controller;

import io.bitjob.domain.Result;
import io.bitjob.service.SchedulerService;
import io.bitjob.service.listener.SchedulerSignalEnum;
import io.bitjob.service.listener.SchedulerZookConfig;
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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/scheduler/")
public class SchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);
    @Resource(name = "schedulerService")
    private SchedulerService schedulerService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView listScheduler(Model model) {
        List<Result> schedulerResultList = schedulerService.scheduerDetailList();
        ModelAndView mv = new ModelAndView("/SchedulerManager");
        model.addAttribute("schedulerResultList", schedulerResultList);
        return mv;
    }


    @RequestMapping(value = "stopScheduler", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> stopScheduler(HttpServletRequest request, HttpServletResponse response) {
        String schedulerName = request.getParameter("schedulerName");

        SchedulerZookConfig zookConfig = new SchedulerZookConfig();
        zookConfig.setSignalType(SchedulerSignalEnum.STOP_SINGLE_TYPE.type());
        zookConfig.setSchedulerName(schedulerName);
        Result result = schedulerService.notifySchedulerCluster(zookConfig);

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("status", result.getStatus());
        map.put("reason", result.getName());

        return map;
    }

    @RequestMapping(value = "startScheduler", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> startScheduler(HttpServletRequest request, HttpServletResponse response) {
        String schedulerName = request.getParameter("schedulerName");

        SchedulerZookConfig zookConfig = new SchedulerZookConfig();
        zookConfig.setSignalType(SchedulerSignalEnum.START_SINGLE_TYPE.type());
        zookConfig.setSchedulerName(schedulerName);
        Result result = schedulerService.notifySchedulerCluster(zookConfig);

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("status", result.getStatus());
        map.put("reason", result.getName());

        return map;
    }

    @RequestMapping(value = "stopAllScheduler", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> stopAllScheduler(HttpServletRequest request, HttpServletResponse response) {
        SchedulerZookConfig zookConfig = new SchedulerZookConfig();
        zookConfig.setSignalType(SchedulerSignalEnum.STOP_ALL_TYPE.type());
        Result result = schedulerService.notifySchedulerCluster(zookConfig);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("status", result.getStatus());
        map.put("reason", result.getName());
        return map;
    }

    @RequestMapping(value = "startAllScheduler", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> startAllScheduler(HttpServletRequest request, HttpServletResponse response) {
        SchedulerZookConfig zookConfig = new SchedulerZookConfig();
        zookConfig.setSignalType(SchedulerSignalEnum.START_ALL_TYPE.type());
        Result result = schedulerService.notifySchedulerCluster(zookConfig);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("status", result.getStatus());
        map.put("reason", result.getName());
        return map;
    }

    @RequestMapping(value = "updateSchedulerRepeatInterval", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> updateSchedulerRepeatInterval(HttpServletRequest request, HttpServletResponse response) {
        logger.info("updateTriggerRepeatInterval");
        String triggerName = request.getParameter("triggerName");
        String schedulerName = request.getParameter("schedulerName");
        String repeatInterval = request.getParameter("repeatInterval");
        SchedulerZookConfig zookConfig = new SchedulerZookConfig();
        zookConfig.setSignalType(SchedulerSignalEnum.UPDATE_REPEAT_TYPE.type());
        zookConfig.setSchedulerName(schedulerName);
        zookConfig.setTriggerName(triggerName);
        zookConfig.setRepeatInterval(repeatInterval);
        Result result = schedulerService.notifySchedulerCluster(zookConfig);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("status", result.getStatus());
        map.put("reason", result.getName());
        return map;
    }
}