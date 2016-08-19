package com.lip.core.utils;

public class ConstantUtils {
    //前台购买
    public static final int FT_PURCHASE=1;
    //后台定向配售
    public static final int BG_PLACEMENT = 2;
    //配售中
    public static final int PLACING=0;
    //配售成功
    public static final int PLACING_SUCCESS=1;
    //配售失败
    public static final int PLACING_FAIL=2;

    //购买中
    public static final int PURCHASING=0;
    //购买成功
    public static final int PURCHASING_SUCCESS=1;
    //购买失败
    public static final int PURCHASING_FAIL=2;

    //提货失败
    public static final int PRODUCT_WITHDRAW_FAIL = 3;
    //提货
    public static final int PRODUCT_WITHDRAW = 4;
    //提货取消
    public static final int PRODUCT_WITHDRAW_CANCEL = 3;
    //提货
    public static final int PRODUCT_DEPOSIT = 5;

    //上市成功
    public static final int LIST_SUCCESS=6;
    //上市失败
    public static final int LIST_FAIL=7;
    //发行项目
    public static final int IPO_ING=9;

    //入金
    public static final int MONEY_CHARGE=11;

    //提现
    public static final int MONEY_WITHDRAW=13;
    //提现成功
    public static final int MONEY_WITHDRAW_SUCCESS=14;
    //提现
    public static final int MONEY_WITHDRAW_REFUSE=12;
    //提现失败退回
    public static final int MONEY_WITHDRAW_FAIL=15;

    public static final int MONEY_ACCOUNT=20;

    public static final int MONEY_ADJUST_FROM_BANK=25;

    public static final int CHARGE_MONEY_ADJUST_FROM_ACCOUNT=30;
    
    public static final int WITHDRAW_MONEY_ADJUST_FROM_ACCOUNT=31;


    public static final String SUCCESS = "success";
    public static final String MSG = "msg";
    
    
    /**
     * 交易相关
     */
    public static final int MONEY_TRADING = 1;
}
