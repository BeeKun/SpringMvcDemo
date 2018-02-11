import com.lk.util.MD5Util;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author likun
 * @version V1.0
 * @Title: PACKAGE_NAME
 * @date 2018/2/7 14:25
 */
public class MD5Test {
    @Test
    public void test(){
        String password = "E07E5E40F0C03D6A8D0249BADD9C5E5E";
        boolean flag = MD5Util.validatePassword("E07E5E40F0C03D6A8D0249BADD9C5E5E","Li123456");
        Assert.assertEquals(flag,true);

    }

}
