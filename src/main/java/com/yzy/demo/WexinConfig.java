package com.yzy.demo;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;

/**
 * Created by youzhiyong on 2018/3/21.
 */
public class WexinConfig extends JFinalConfig{

    public void configConstant(Constants constants) {
        PropKit.use("config_dev.txt");
        constants.setDevMode(PropKit.getBoolean("devmode", false));
        constants.setViewType(ViewType.JSP);//设置当前的视图类型

        //constants.setBaseViewPath("/WEB-INF/view");
    }

    public void configRoute(Routes routes) {
        routes.add("/msg", WeixinMsgController.class, "/view");
        routes.add("/api", WeixinApiController.class, "/view");
    }


    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置ActiveRecord插件  数据库插件
        DruidPlugin druidPlugin = createDruidPlugin();
        me.add(druidPlugin);

        // 配置ActiveRecord插件   对象-表映射插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.addMapping("user", "id", User.class);
        arp.setShowSql(PropKit.getBoolean("devMode", true));
        me.add(arp);

        // ehcahce插件配置  缓存插件
        //me.add(new EhCachePlugin());
    }

    public static DruidPlugin createDruidPlugin() {
        String jdbcUrl = PropKit.get("jdbcUrl");
        String user = PropKit.get("user");
        String password = PropKit.get("password");
        System.out.println(jdbcUrl + " " + user + " " + password);
        // 配置druid数据连接池插件
        DruidPlugin dp = new DruidPlugin(jdbcUrl, user, password);
        // 配置druid监控
        dp.addFilter(new StatFilter());
        WallFilter wall = new WallFilter();
        wall.setDbType("mysql");
        dp.addFilter(wall);
        return dp;
    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {

    }

    public static void main(String[] args) {
        JFinal.start("src/main/web", 8080, "/", 5);
    }
}
