package restassured;

import com.jayway.restassured.RestAssured;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsTestRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG9ja2VyQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIzOTc4NzE2LCJpYXQiOjE3MjMzNzg3MTZ9.D3JpvWdyEXRTp94juwzVu14fmP19GZA-hVVmbYc1pSM";

    String endPoint = "contacts";

    @BeforeMethod
    private void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

    }

    @Test
    public void getAllContactsSuccess() throws IOException {
        GetAllContactsDTO contactsDTO = given()
                .header("Authorization",token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(GetAllContactsDTO.class);
        List<ContactDTO>list=contactsDTO.getContacts();
        for (ContactDTO contact:list){
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("=========================");
        }


    }
}
