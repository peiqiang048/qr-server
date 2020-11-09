package com.cnc.qr.api.csmborder;

import com.cnc.qr.QrServerApp;
import com.cnc.qr.api.config.QrTestConfig;
import com.cnc.qr.api.config.ReplacementCsvDataSetLoader;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.ExceptionTranslator;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {QrServerApp.class, QrTestConfig.class})
@DbUnitConfiguration(dataSetLoader = ReplacementCsvDataSetLoader.class, databaseConnection = "postgresDataSource")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@Transactional
public class MobileScreenControllerIntTest {

    // Spring MVCのモック
    private MockMvc mockMvc;

//    @Autowired
//    MobileScreenController mobileScreenController;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Before
    public void setUp() {
        // Spring MVCのモックを設定する
//        mockMvc = MockMvcBuilders.standaloneSetup(mobileScreenController)
//                .setMessageConverters(jacksonMessageConverter)
//                .setControllerAdvice(exceptionTranslator)
//            .build();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 正常：返却値確認
     *
     */
    @DatabaseSetup(value = "classpath:dbunit/com/cnc/qr/api/csmborder/MobileScreenControllerTest/test01/", type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = "classpath:dbunit/com/cnc/qr/api/csmborder/MobileScreenControllerTest/Expected/test01/", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void test01() throws Exception {
//        MvcResult result = mockMvc.perform(get(UrlConstants.CSMB_ORDER_API_GET_STORE_INFO +
//            "?tableCd=777&weid=11&bussinessid=1&storeid=53")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // 結果確認
//        String resultData = result.getResponse().getContentAsString();
//        Assert.assertEquals( "{\"message\":\"API正常完了\",\"ok\":true}", resultData);
    }
}
