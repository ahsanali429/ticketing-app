package com.task.callsign.controller.rest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.TimeZone;
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("file:src/test/resources/application-test.properties")
public abstract class BaseIntegrationTest {

    @Value("${local.server.port}")
    protected int port;

    @Autowired
    protected MockMvc mvc;

    @BeforeClass
    public static void configuration() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

