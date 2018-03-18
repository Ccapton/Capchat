package com.capton.rongim;

/**
 * Created by capton on 2018/3/18.
 */

public class ImTokenResult {


    /**
     * code : 200
     * userId : 2
     * token : dnSknunkieHmS2kILfxCH3A6y7jm5VWWaoxhfBA6JkzMLUgAZwx4tHBX+QyNEWV7338jXx33V0U=
     */

    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
