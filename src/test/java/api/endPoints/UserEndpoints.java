package api.endPoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.*;

public class UserEndpoints {

    static ResourceBundle getURL(){
        ResourceBundle resource = ResourceBundle.getBundle("routes");
        return resource;
    }

    public static Response createUser(User payload){
        String user_post_url = getURL().getString("user_post_url");
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(user_post_url);
        return response;
    }

    public static Response readUser(String userName){
        String user_get_url = getURL().getString("user_get_url");
        Response response = given()
                .pathParam("username", userName)
                .when()
                .get(user_get_url);
        return response;
    }

    public static Response updateUser(String userName, User payload){
        String user_put_url = getURL().getString("user_put_url");
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", userName)
                .body(payload)
                .when()
                .put(user_put_url);
        return response;
    }

    public static Response deleteUser(String userName){
        String user_delete_url = getURL().getString("user_delete_url");
        Response response = given()
                .pathParam("username", userName)
                .when()
                .delete(user_delete_url);
        return response;
    }

}
