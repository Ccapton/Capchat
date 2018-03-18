package com.capton.rongim;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.capton.common.base.App;
import com.capton.common.base.JsonUtil;
import com.capton.common.user.LoginActivity;
import com.capton.common.user.User;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by capton on 2018/3/18.
 */

public class MyLoginActivity extends LoginActivity {

    public int TRY_TIME = 3;

    private User tempUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public void afterLoginSuccess(boolean islogined, User user) {
        Intent intent = new Intent();
        intent.putExtra(LOGINED_DATA, islogined);
        intent.putExtra(com.capton.common.base.SpConstant.USER, user);
        setResult(LOGIN_SUCCESS,intent);

        tempUser = user;
        getImToken(false,user);

    }

    private void getImToken(boolean isTokenError,User user){
        if(!isTokenError) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(SpConstant.IM_TOKEN))) {
                connect(SPUtils.getInstance().getString(SpConstant.IM_TOKEN));
            } else {
                if(user!=null)
                  getNetToken(user);
            }
        }else {
            if(user!=null)
                getNetToken(user);
        }
    }
    private void getNetToken(User user){
        String baseUrl = getString(com.capton.common.R.string.baseurl);
        String getImTokenUrl = baseUrl + getString(com.capton.common.R.string.getImToken) + user.getId() + "/" + user.getNick();
        OkGo.<String>get(getImTokenUrl)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            ImTokenResult result = (ImTokenResult) JsonUtil.strToObject(response.body(), ImTokenResult.class);
                            if (result.getCode() == 200) {
                                SPUtils.getInstance().put(SpConstant.IM_TOKEN, result.getToken());
                                System.out.println("MyLoginActivity.onSuccess 正在连接融云IM服务器");
                                connect(result.getToken());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在  init(context) 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     *
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        if (!App.isAppBackground(getApplicationContext())) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                        TRY_TIME --;
                        if (TRY_TIME >= 0)
                            getImToken(true,tempUser);
                        else
                            Toast.makeText(MyLoginActivity.this, "登录聊天服务器失败", Toast.LENGTH_SHORT).show();
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
                    startActivity(new Intent(MyLoginActivity.this, MainActivity.class));
                    finish();
                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Toast.makeText(MyLoginActivity.this, errorCode.getMessage()+" "
                            +errorCode.getValue(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
