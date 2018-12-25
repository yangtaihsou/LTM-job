package io.bitjob.controller;

import io.bitjob.dao.TimerTaskConfigMapper;
import io.bitjob.domain.TimerTaskConfig;
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
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/timerTaskConfig")
public class TimerTaskConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TimerTaskConfigController.class);

    @Resource(name = "timerTypeConfig")
    LinkedHashMap<String, String> timerTypeConfig;

    @Resource(name = "timerTaskConfigMapper")
    private TimerTaskConfigMapper timerTaskConfigMapper;

    /**
     * 获取列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String type, Integer p, Model model, TimerTaskConfig timerTaskConfig, HttpServletRequest request) throws Exception {
        PageQuery<TimerTaskConfig> pageQuery = new PageQuery<TimerTaskConfig>();
        p = p == null ? 1 : p;
        pageQuery.setPageSize(20);
        pageQuery.setPageNo((p - 1) * 20);
        pageQuery.setQuery(timerTaskConfig);
        List<TimerTaskConfig> taskList = timerTaskConfigMapper.queryBySelectiveForPagination(pageQuery);
        int count = (int) timerTaskConfigMapper.queryCountBySelective(pageQuery);
        String joinPara = PageUtil.joinParameter(request, new String[]{"p"}, true);
        String pagestr = PageUtil.getPageStr(joinPara, 20, p, count);
        model.addAttribute("pagestr", pagestr);
        model.addAttribute("list", taskList);
        model.addAttribute("count", count);

        model.addAttribute("timerTypeConfig", timerTypeConfig);
        model.addAttribute("parameterMap", request.getParameterMap());
        ModelAndView mv = new ModelAndView("/timerTaskConfig/TimerTaskConfigList");
        return mv;

    }

    @RequestMapping(value = "/toSave", method = RequestMethod.GET)
    public String toSave(Model model) {
        model.addAttribute("timerTypeConfig", timerTypeConfig);
        return "/timerTaskConfig/TimerTaskConfigAdd";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    //@ParaValidator(validatorNames = {"timerTaskConfigValidator","testValidator"})
    public DataResult save(TimerTaskConfig timerTaskConfig, HttpServletRequest request) {
        DataResult result = new DataResult();
        result.setStatus(Boolean.TRUE);
        try {
            timerTaskConfig.setStatus(0);//初始化是停止的
            Long resultLong = timerTaskConfigMapper.save(timerTaskConfig);
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

        model.addAttribute("timerTypeConfig", timerTypeConfig);
        TimerTaskConfig timerTaskConfig = timerTaskConfigMapper.queryByPrimaryKey(id);
        model.addAttribute("timerTaskConfig", timerTaskConfig);
        return "/timerTaskConfig/TimerTaskConfigEdit";
    }


    /**
     * 更新一个
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    // @ParaValidator(validatorNames = {"timerTaskConfigValidator","testValidator"})
    public DataResult edit(TimerTaskConfig timerTaskConfig, HttpServletRequest request) {
        DataResult result = new DataResult();
        try {

            int resultCode = timerTaskConfigMapper.updateByPrimaryKeySelective(timerTaskConfig);
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
            TimerTaskConfig timerTaskConfig = new TimerTaskConfig();
            timerTaskConfig.setId(id);
            timerTaskConfig.setStatus(status);
            int resultCode = timerTaskConfigMapper.updateByPrimaryKeySelective(timerTaskConfig);
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

    @RequestMapping(value = "updateAllStatus", method = RequestMethod.POST)
    @ResponseBody
    public DataResult updateAllStatus(Integer status, HttpServletRequest request) {

        DataResult result = new DataResult();
        try {
            TimerTaskConfig timerTaskConfig = new TimerTaskConfig();
            timerTaskConfig.setStatus(status);
            int resultCode = timerTaskConfigMapper.update(timerTaskConfig);
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

            int resultCode = timerTaskConfigMapper.deleteByPrimaryKey(id);

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