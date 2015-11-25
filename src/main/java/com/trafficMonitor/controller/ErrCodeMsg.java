package com.trafficMonitor.controller;

/**
 * Created by lizhengdong on 11/20/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 11/20/15.
 * Description: ErrCodeMsg
 */
public enum ErrCodeMsg {

    SUCC(0, "成功"),
    COMMON_FAIL(8888, "通用错误"),

    EMAIL_PASS_NOT_MATCH(101, "用户名或密码错误"),
    FOCUS_ACCOUNT_NOT_BIND(102, "未验证邮箱或手机号"),
    TOKEN_INVALID(103, "token无效"),
    TOKEN_EXPIRED(104, "token过期"),
    TOKEN_NOT_RELEASE(105, "其他设备在登录未登出账号！"),
    ACCOUNT_BLACK_LISTED(106, "黑名单用户，请联系管理员"),

    ARGS_MISSING(201, "参数不齐"),
    ARGS_ILLEGAL(202, "非法参数"),
    SIGN_INCORRECT(203, "签名错误"),

    NO_PERMISSION(301, "没有权限"),

    FILE_ERROR_TYPE(401, "文件格式不正确"),
    FILE_UPLOAD_ERROR(402, "文件上传失败"),
    FILE_UPLOAD_ERR_SIZE(403, "文件大小超过2M"),

    CODE_UTF8_ERROR(501, "上传文件编码格式非UTF8"),

    SYSTEM_ERROR(900, "系统错误"),

    SERVICE_TIMEOUT(901, "依赖后端接口超时"),

    SERVICE_RETURN_NULL(902, "依赖后端接口未返回"),

    MESSAGE_NOT_UPDATE(1000, "信息未更新");

    private int code;
    private String msg;

    ErrCodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
