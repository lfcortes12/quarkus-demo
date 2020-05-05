package com.gbt.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class DeveloperResourceTest {

    @Test
    public void testHelloEndpoint() {
        List<Developer> developers = given()
                .when().get("/developer")
                .as(new TypeRef<List<Developer>>() {
                });

        Assertions.assertEquals(4, developers.size());

    }

}