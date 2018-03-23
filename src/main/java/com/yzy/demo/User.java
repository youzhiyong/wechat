package com.yzy.demo;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by youzhiyong on 2018/3/22.
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 6204222383226990020L;

    public static final User me = new User();

    public boolean save(String openId,String nickName,String unionid,String headimgurl,String country,String city,String province ,int sex){

        /**
         * 1、判断openId 是否存在
         *    如果存在就update
         *    如果不存在就保存
         */
        User user = findByOpenId(openId);
        if (user != null) {
            user.set("nickName", nickName);
            user.set("unionid", unionid);
            user.set("headimgurl", headimgurl);
            user.set("country", country);
            user.set("city", city);
            user.set("province", province);
            user.set("sex", sex);
            user.set("updateTime", new Date());
            return user.update();
        } else {
            if (StrKit.notBlank(openId)) {
                User me = new User();
                me.set("openId", openId);
                me.set("nickName", nickName);
                me.set("unionid", unionid);
                me.set("headimgurl", headimgurl);
                me.set("country", country);
                me.set("city", city);
                me.set("province", province);
                me.set("sex", sex);
                me.set("updateTime", new Date());
                return me.save();
            }
        }
        return false;
    }

    public List<User> getAll(){
        return me.find("select * from user");
    }

    public Page<User> paginate(int pageNumber, int pageSize) {
        return paginate(pageNumber, pageSize, "select *", "from user order by id asc");
    }

    public User findByOpenId(String openId){
        return this.findFirst("select * from user where openId=?", openId);
    }

    /**
     * 根据map参数查找
     * @param paras
     * @return
     */
    public List<User> findByMap(Map<String, Object> paras) {
        StringBuilder sql = new StringBuilder("select * from user ");
        if (paras.containsKey("order")) {
            sql.append(" ORDER BY ");
            sql.append(paras.get("order"));
            sql.append(" ");
        }
        if (paras.containsKey("limit")) {
            sql.append(" LIMIT ");
            sql.append(paras.get("limit"));
        }
        return this.find(sql.toString());
    }

}

