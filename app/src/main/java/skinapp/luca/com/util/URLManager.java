package skinapp.luca.com.util;

/**
 * Created by Leif on 12/1/2017.
 */

public class URLManager {

//    private static final String HTTP_SERVER = "http://192.168.5.144/";
    private static final String SERVER_PREFIX = "tfs_skin/";
    private static final String HTTP_SERVER = "http://218.245.1.214/";

    public static String getSignInURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/Login.php";
    }

    public static String getSignUpURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/SignUp.php";
    }

    public static String getImageURL() {
        return HTTP_SERVER + SERVER_PREFIX + "/trunk/admin/webroot/upload/";
    }

    public static String getProductsURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/GetProduct.php";
    }

    public static String getAnalysisURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/GetAnalysis.php";
    }

    public static String getHistoryURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/GetHistory.php";
    }

    public static String getOpenIDURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/GetOpenId.php";
    }

    public static String getConfirmOpenIDURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/ConfirmOpenID.php";
    }

    public static String getCheckDeviceIDURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/CheckDeviceID.php";
    }

    public static String getUploadQRURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/UploadQR.php";
    }

    public static String getDeviceQRURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/GetDeviceQR.php";
    }

    public static String getQRURL() {
        return "http://test.91tfs.com/Home/Tfsapi";
    }
}
