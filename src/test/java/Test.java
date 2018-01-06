//
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.Objects;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
//public class Test extends AbstractTransactionalJUnit4SpringContextTests{
//
//    private static Logger logger = LoggerFactory.getLogger(Test.class);
//    /*@Autowired
//    private UserService userService;*/
//
//    @org.junit.Test
//    public void testDemo(){
//        Map<String,String> stringMap = new HashMap<>();
//        stringMap.put("1","a");
//        stringMap.put(null,null);
//        Map<String,String> tableMap = new Hashtable<>();
//        tableMap.put(null,null);
//        String abc = null;
//        try {
//            Objects.requireNonNull(abc);
//        }catch (Exception e){
//            System.out.println(111);
//        }
//
//        logger.debug("=========="+stringMap.keySet());
//    }
//
//}
