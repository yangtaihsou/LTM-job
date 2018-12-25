package io.bitjob.controller;

import io.bitjob.service.cache.TaskServiceConfigCache;
import io.bitjob.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**

 * Date: 14-8-11ß
 * Time: 下午5:51
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(TimerTaskConfigController.class);
    @Resource(name = "taskServiceConfigCache")
    TaskServiceConfigCache taskServiceConfigCache;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        // logger.info("----ping", LoginContext.getLoginContext().getPin());
        ModelAndView mv = new ModelAndView("/index");
        return mv;
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> json() {
        HashMap<String, String> map = new HashMap<String, String>();
        return taskServiceConfigCache.valid_taskTypeList;
    }

    /**
     * 上传附件*
     */
    @RequestMapping(value = "/file/createMigrateTask", method = RequestMethod.POST)
    @ResponseBody
    public void createMigrateTask(@RequestParam MultipartFile fileName, Integer taskType, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        HashMap<String, String> map = new HashMap<String, String>();

        response.getOutputStream().print(GsonUtils.toJson(map));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView upload() {
        ModelAndView mv = new ModelAndView("/upload");
        return mv;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Connection → keep-alive
        //response.setHeader("Connection","close");
        /*try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Enumeration headers = request.getHeaderNames();
        Date date = new Date();
        date.setTime(date.getTime() + 360000000l);
        response.setDateHeader("Expires", System.currentTimeMillis() + 1000 * 1000);
        response.setHeader("Cache-Control", "max-age=920");
//

        //  response.setDateHeader("Last-Modified", System.currentTimeMillis());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("test", "test1221");
        response.getOutputStream().print(GsonUtils.toJson(map));

        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
    }


}
