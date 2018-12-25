package io.bitjob.controller;

import io.bitjob.dao.JmqConfigMapper;
import io.bitjob.domain.JmqConfig;
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
import java.util.List;

@Controller
@RequestMapping("/jmqConfig")
public class JmqConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(JmqConfigController.class);


    @Resource(name = "jmqConfigMapper")
    private JmqConfigMapper jmqConfigMapper;

    /**
     * 获取列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String type, Integer p, Model model, JmqConfig jmqConfig, HttpServletRequest request) throws Exception {
        PageQuery<JmqConfig> pageQuery = new PageQuery<JmqConfig>();
        p = p == null ? 1 : p;
        pageQuery.setPageSize(20);
        pageQuery.setPageNo((p - 1) * 20);
        pageQuery.setQuery(jmqConfig);
        List<JmqConfig> jmqConfigList = jmqConfigMapper.queryBySelectiveForPagination(pageQuery);
        Long count = jmqConfigMapper.queryCountBySelective(jmqConfig);
        String joinPara = PageUtil.joinParameter(request, new String[]{"p"}, true);
        String pagestr = PageUtil.getPageStr(joinPara, 20, p, count.intValue());
        model.addAttribute("pagestr", pagestr);
        model.addAttribute("jmqConfigList", jmqConfigList);
        model.addAttribute("count", count);

        model.addAttribute("parameterMap", request.getParameterMap());
        ModelAndView mv = new ModelAndView("/jmqConfig/JmqConfigList");
        return mv;

    }

    @RequestMapping(value = "/toSave", method = RequestMethod.GET)
    public String toSave(Model model) {

        return "/jmqConfig/JmqConfigAdd";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    //@ParaValidator(validatorNames = {"jmqConfigValidator","testValidator"})
    public DataResult save(JmqConfig jmqConfig, HttpServletRequest request) {
        DataResult result = new DataResult();
        result.setStatus(Boolean.TRUE);
        try {
            jmqConfig.setStatus(0);//初始化是停止的
            Integer resultLong = jmqConfigMapper.save(jmqConfig);
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

        JmqConfig jmqConfig = jmqConfigMapper.queryByPrimaryKey(id);
        model.addAttribute("jmqConfig", jmqConfig);
        return "/jmqConfig/JmqConfigEdit";
    }


    /**
     * 更新一个
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    // @ParaValidator(validatorNames = {"jmqConfigValidator","testValidator"})
    public DataResult edit(JmqConfig jmqConfig, HttpServletRequest request) {
        DataResult result = new DataResult();
        try {

            int resultCode = jmqConfigMapper.updateByPrimaryKey(jmqConfig);
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
            JmqConfig jmqConfig = new JmqConfig();
            jmqConfig.setId(id);
            jmqConfig.setStatus(status);
            int resultCode = jmqConfigMapper.updateByPrimaryKey(jmqConfig);
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
            JmqConfig jmqConfig = new JmqConfig();
            jmqConfig.setStatus(status);
            int resultCode = jmqConfigMapper.updateByPrimaryKey(jmqConfig);
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


}