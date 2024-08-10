package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;

import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp {

    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG9ja2VyQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIzOTEwNzU2LCJpYXQiOjE3MjMzMTA3NTZ9.GPdV3K_tgnyGXe-3E_T4alRoVnqtfQqiG1ZemkvS-20
    @Test
    public void loginSuccess() throws IOException {


        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("locker@gmail.com")
                .password("Qwerty1234!")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }

    @Test
    public void loginWrongEmail() throws IOException {


        AuthRequestDTO auth = AuthRequestDTO.builder()
                .password("Qwerty1234!")
                .username("lockergmail.com")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 401);
        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
        Assert.assertEquals(errorDTO.getPath(), "/v1/user/login/usernamepassword");
    }

    @Test
    public void loginWrongPassword() throws IOException {


        AuthRequestDTO auth = AuthRequestDTO.builder()
                .password("qw234512")
                .username("locker@gmail.com")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 401);
        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
        Assert.assertEquals(errorDTO.getPath(), "/v1/user/login/usernamepassword");
    }



    @Test
    public void loginWrongUnregistred() throws IOException {


        AuthRequestDTO auth = AuthRequestDTO.builder()
                .password("Qwerty1234!")
                .username("locker1234567890@gmail.com")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 401);
        System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getPath(), "/v1/user/login/usernamepassword");
    }
}
