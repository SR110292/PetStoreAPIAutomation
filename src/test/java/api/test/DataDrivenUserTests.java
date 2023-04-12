package api.test;

import api.endPoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenUserTests {

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String userID,String userName, String firstName, String lastName, String email,
                             String password, String phone){
        User userPayload = new User();
        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(firstName);
        userPayload.setLastName(lastName);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        userPayload.setPhone(phone);

        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().body().assertThat().statusCode(200);
    }

    @Test (priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUser(String userName){
        Response response = UserEndpoints.deleteUser(userName);
        response.then().log().all().assertThat().statusCode(200);
    }

}
