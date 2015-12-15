package com.trafficMonitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.trafficMonitor.model.ErrCodeMsg;

/**
 * Created by lizhengdong on 11/20/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/20/15.
 * Description: BaseController
 */
public class BaseController {
    public String badResult(ErrCodeMsg err) {
        JSONObject json = new JSONObject();
        json.put("errorCode", err.code());
        json.put("errorMessage", err.msg());
        return "@" + json.toJSONString();
    }

    public String badResult(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("errorCode", code);
        json.put("errorMessage", msg);
        return "@" + json.toJSONString();
    }

    public String badResult(Object data) {
        JSONObject res = new JSONObject();
        res.put("errorCode", 9999);
        res.put("errorMessage", "通用失败信息");
        if (data instanceof String) {
            res.put("data", data);
        } else {
            res.put("data", JSONObject.toJSON(data));
        }
        return "@" + res.toJSONString();
    }

    public String succResult(Object data) {
        JSONObject res = new JSONObject();
        res.put("errorCode", 0);
        res.put("errorMessage", "成功");
        if (data instanceof String) {
            res.put("data", data);
        } else {
            res.put("data", JSONObject.toJSON(data));
        }
        return "@" + res.toJSONString();
    }

    public String succResult() {
        JSONObject res = new JSONObject();
        res.put("errorCode", 0);
        res.put("errorMessage", "成功");
        return "@" + res.toJSONString();
    }
}
