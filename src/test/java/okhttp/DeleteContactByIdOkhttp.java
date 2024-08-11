package okhttp;


import com.google.gson.Gson;

import dto.ContactDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdOkhttp {


    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG9ja2VyQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIzOTEwNzU2LCJpYXQiOjE3MjMzMTA3NTZ9.GPdV3K_tgnyGXe-3E_T4alRoVnqtfQqiG1ZemkvS-20";

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    public String id;
    @BeforeMethod
    private void preCondition() throws IOException {

        int i = new Random().nextInt(1000)+1000;
        ContactDTO contact = ContactDTO.builder()
                .name("Alex"+i)
                .lastName("Gromov"+i)
                .phone("123456789"+i)
                .email("Makov"+i+"@gmail.com")
                .address("NY")
                .description("friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contact),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        MessageDTO messageDTO = gson.fromJson(response.body().string(),MessageDTO.class);

        String[] str = messageDTO.getMessage().split(": ");
        id = str[1];
        System.out.println(id);


    }


    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();
        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        Assert.assertEquals(response.code(),200);
        Assert.assertEquals(dto.getMessage(),"Contact was deleted!");


    }
}
