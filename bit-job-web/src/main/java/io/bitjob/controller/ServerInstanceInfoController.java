package io.bitjob.controller;


import io.bitjob.dao.ServerInstanceInfoMapper;
import io.bitjob.domain.ServerInstanceInfo;
import io.bitjob.util.PageQuery;
import io.bitjob.util.PageUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * ServerInstanceInfo(服务器实例信息)的controller

 */
@Controller
@RequestMapping("/serverinstanceinfo")
public class ServerInstanceInfoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ServerInstanceInfoController.class);

    @Autowired
    private ServerInstanceInfoMapper serverinstanceinfoMapper;

    /**
     * 获取列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, ServerInstanceInfo serverinstanceinfo, Integer p, HttpServletRequest request) throws Exception {

        PageQuery<ServerInstanceInfo> pageQuery = new PageQuery<ServerInstanceInfo>();
        p = p == null ? 1 : p;
        pageQuery.setPageSize(20);
        pageQuery.setPageNo((p - 1) * 20);
        pageQuery.setQuery(serverinstanceinfo);
        if (serverinstanceinfo.getLastUpdate() == null) {
            Date date = DateUtils.addMinutes(new Date(), -5);
            serverinstanceinfo.setLastUpdate(date);
            model.addAttribute("lastUpdate", date);
        }
        List<ServerInstanceInfo> serverinstanceinfoList = serverinstanceinfoMapper.queryBySelectiveForPagination(pageQuery);
        int count = (int) serverinstanceinfoMapper.queryCountBySelective(pageQuery);
        model.addAttribute("count", count);
        model.addAttribute("list", serverinstanceinfoList);
        String joinPara = PageUtil.joinParameter(request, new String[]{"p"}, true);
        String pagestr = PageUtil.getPageStr(joinPara, 20, p, count);
        model.addAttribute("pagestr", pagestr);

        model.addAttribute("parameterMap", request.getParameterMap());

        return "/serverinstanceinfo/ServerInstanceInfoList";
    }


}