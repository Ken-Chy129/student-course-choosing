package cn.ken.student.rubcourse.common.constant;

/**
 * <pre>
 * Github第三方登录相关常量
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/11 14:22
 */
public class GithubConstants {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String CALLBACK = "";

    /**
     * 获取code的url
     */
    public static final String CODE_URL = "https://github.com/login/oauth/authorize" +
            "?client_id=%s" +
            "&state=%s" +
            "&redirect_uri=%s";

    /**
     * 获取token的url
     */
    public static final String TOKEN_URL = "https://github.com/login/oauth/access_token" +
            "?client_id=%s" +
            "&client_secret=%s" +
            "&code=%s" +
            "&redirect_uri=%s";

    /**
     * 获取用户信息的url
     */
    public static final String USER_INFO_URL = "https://api.github.com/user/repos";
}
