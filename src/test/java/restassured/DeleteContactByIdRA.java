package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdRA {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG9ja2VyQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIzOTc4NzE2LCJpYXQiOjE3MjMzNzg3MTZ9.D3JpvWdyEXRTp94juwzVu14fmP19GZA-hVVmbYc1pSM";

    String endPoint = "contacts";

    String id;

    @BeforeMethod
    private void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder().
                name("Alex" + i)
                .lastName("Gromov" + i)
                .phone("123456789" + i)
                .email("Makov" + i + "@gmail.com")
                .address("NY")
                .description("friend")
                .build();
        String message = given()
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .post(endPoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");
        String[] all = message.split(": ");
        id = all[1];

    }

    @Test
    public void deleteContactByIdSuccess(){

        given()
                .header("Authorization",token)
                .when()
                .delete(endPoint+"/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",containsString("Contact was deleted"))
                .assertThat().body("message",equalTo("Contact was deleted!"));




    }
    @Test
    public void deleteContactByIdWrongToken(){

        given()
                .header("Authorization","asasas")
                .when()
                .delete(endPoint+"/"+id)
                .then()
                .assertThat().statusCode(401);





    }
}
