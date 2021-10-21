import com.atguigu.crowd.CrowdMainClass;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.vo.DetailProjectVO;
import com.atguigu.crowd.vo.PortalProjectVO;
import com.atguigu.crowd.vo.PortalTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = CrowdMainClass.class)
public class MySqlTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Test
    public void testLoadDetailProjectData() {

        DetailProjectVO detailProjectVOS = projectPOMapper.selectDetailProjectVO(3);
        log.info(detailProjectVOS.toString());
    }

    @Test
    public void testMapper() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String source = "123123";
        String encode = passwordEncoder.encode(source);
        MemberPO memberPO = new MemberPO(null, "jack", encode, " 杰 克 ", "jack@qq.com", 1, 1, "杰克", "123123", 2);
        memberPOMapper.insert(memberPO);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.debug(connection.toString());
    }

    @Test
    public void testLoadTypeData() {
        List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();
        for (PortalTypeVO portalTypeVO : portalTypeVOList) {
            String name = portalTypeVO.getName();
            String remark = portalTypeVO.getRemark();
            log.info("name="+name+",remark="+remark);

            List<PortalProjectVO> portalProjectVOList = portalTypeVO.getPortalProjectVOList();
            for (PortalProjectVO portalProjectVO : portalProjectVOList) {
                if(portalProjectVO == null){
                    continue;
                }
                log.info(portalProjectVO.toString());
            }


        }
    }
}
