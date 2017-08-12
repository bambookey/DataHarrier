package com.dh.util;

import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.PageListBean;
import com.dh.bean.config.StateBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 载入抓取配置工具类
 * <p>
 * Created by lixiangyu on 2017/8/12.
 */
public class CrawlerTemplateUtil {

    /**
     * 根据配置文件生成抓取模板
     *
     * @param filePath
     * @return
     */
    public static List<StateBean> getTemplate(String filePath) throws IOException {
        Map<String, StateBean> beanMap = new HashMap<String, StateBean>();
        List<String> olines = FileUtils.readLines(new File(filePath));
        String split = "##";
        for (String oline : olines) {
            if(oline.startsWith("#") || StringUtils.isEmpty(oline)) {
                continue;
            }
            String[] l = oline.split(split);
            String key = l[0];
            if (!beanMap.containsKey(key)) {
                beanMap.put(key, new StateBean());
            }
            StateBean opBean = beanMap.get(key);
            String part = l[1];
            if ("URL".equals(part)) {
                opBean.setUrlPattern(l[2]);
            }
            if ("KV".equals(part)) {
                PageKvBean kvBean = new PageKvBean();
                kvBean.setK(l[2]);
                kvBean.setXpath(l[3]);
                opBean.getKvs().add(kvBean);
            }
            if ("ARR".equals(part)) {
                PageListBean pageListBean = new PageListBean();
                pageListBean.setName(l[2]);
                pageListBean.setXpathOuter(l[3]);

                int start = (l[0] + l[1] + l[2] + l[3]).length() + split.length() * 4;
                String kvString = oline.substring(start);
                String[] kvStrings = kvString.split(split);

                List<PageKvBean> kvs = new ArrayList<PageKvBean>();
                for (int i = 0; i < kvStrings.length; i += 2) {
                    PageKvBean kvBean = new PageKvBean();
                    kvBean.setK(l[i]);
                    kvBean.setXpath(l[i + 1]);
                    kvs.add(kvBean);
                }
                pageListBean.setKvs(kvs);
                opBean.getArrs().add(pageListBean);
            }
        }

        List<StateBean> stateBeans = new ArrayList<StateBean>();
        for (String key : beanMap.keySet()) {
            stateBeans.add(beanMap.get(key));
        }
        return stateBeans;
    }
}
