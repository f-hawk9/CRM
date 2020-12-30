import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class log4jTest {
    @Test
    public void test(){
        Logger logger = LogManager.getLogger("MyLogger");
        logger.debug("这是调试信息");
        logger.info("这是普通信息");
        logger.warn("这是警告信息");
        logger.error("这是错误信息");
        logger.fatal("这是严重错误信息");
    }
}
