package api.test;

import api.endPoints.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserTests {
    Faker faker;
    User userPayload;
    public Logger logger;
    @BeforeClass
    public void setupData(){
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        logger = LogManager.getLogger(this.getClass());
    }

    @Test (priority = 1)
    public void testPostUser(){
        logger.info("************** Create New User ******************");
        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("*************************************************");
    }

    @Test (priority = 2)
    public void testGetUser(){
        logger.info("************** Reading User ******************");
        Response response = UserEndpoints.readUser(userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("*************************************************");

    }

    @Test (priority = 3)
    public void testUpdateUser(){
        logger.info("************** Updating User ******************");

        String newName = faker.name().firstName();
        userPayload.setFirstName(newName);

        Response response = UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(),200);

        Response afterUpdateResponse = UserEndpoints.readUser(this.userPayload.getUsername());
        afterUpdateResponse.then().log().all();
        Assert.assertEquals(afterUpdateResponse.getStatusCode(),200);
        String responseBody= afterUpdateResponse.asString();
        JsonPath path=new JsonPath(responseBody);
        String afterUpdateName=path.getString("firstName");
        Assert.assertEquals(afterUpdateName,newName);
        logger.info("*************************************************");

    }

    @Test (priority = 4)
    public void testDeleteUser(){
        logger.info("********** Deleting created user *****************");
        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("**************************************************");

    }

}
