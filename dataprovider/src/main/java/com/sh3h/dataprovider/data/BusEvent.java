package com.sh3h.dataprovider.data;

/**
 * Created by libao on 2018/9/12.
 */

public class BusEvent {
    public static class isLogin{
        public String message;
        public boolean isLogin;

      public   isLogin(String message,boolean flag){
            this.message=message;
            this.isLogin=flag;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public void setLogin(boolean login) {
            isLogin = login;
        }
    }
}
