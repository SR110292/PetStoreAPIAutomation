package api.test;

import api.endPoints.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
        response.then().log().body().assertThat().statusCode(200);
        logger.info("*************************************************");
    }

    @Test (priority = 2)
    public void testGetUser(){
        logger.info("************** Reading User ******************");
        Response response = UserEndpoints.readUser(userPayload.getUsername());
        response.then().log().all().assertThat().statusCode(200);
        logger.info("*************************************************");

    }

    @Test (priority = 3)
    public void testUpdateUser(){
        logger.info("************** Updating User ******************");

        String newName = faker.name().firstName();
        userPayload.setFirstName(newName);

        Response response = UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
        response.then().log().body().assertThat().statusCode(200);

        Response afterUpdateResponse = UserEndpoints.readUser(this.userPayload.getUsername());
        afterUpdateResponse.then().log().all().assertThat().statusCode(200);
        User afterUpdateUser= afterUpdateResponse.as(User.class);
        Assert.assertEquals(afterUpdateUser.getFirstName(),newName);

        logger.info("**************************************************");
    }

    @Test (priority = 4)
    public void testDeleteUser(){
        logger.info("********** Deleting created user *****************");
        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        response.then().log().all().assertThat().statusCode(200);
        logger.info("**************************************************");

    }

}
