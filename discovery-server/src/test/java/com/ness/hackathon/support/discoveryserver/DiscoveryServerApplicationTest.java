package com.ness.hackathon.support.discoveryserver;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ness.hackathon.support.tests.IntegrationTest;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Category(IntegrationTest.class)
public class DiscoveryServerApplicationTest {

    private static final String HEALTH_URL = "/actuator/health";
    private static final String INFO_URL = "/actuator/info";
    private static final String EUREKA_APPS = "/eureka/apps";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testIsCatalogLoading() {
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(EUREKA_APPS, Map.class);
        entity.getBody();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void testIsHealthUrlWorking() {
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(HEALTH_URL, Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void testIsInfoUrlWorking() {
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(INFO_URL, Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }
}
