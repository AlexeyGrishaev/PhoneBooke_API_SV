package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        int i = new Random().nextInt(1000) + 1000;
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("komi" + i + "@gmail.com").password("Qwerty1234!")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        AuthResponseDTO authResponseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        System.out.println(authResponseDTO.getToken());
    }

    @Test
    public void registrationWrongLogin() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("lockergmail.com")
                .password("Qwerty1234!")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getMessage().toString(),"{username=must be a well-formed email address}");
        Assert.assertEquals(errorDTO.getStatus(),400);
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/registration/usernamepassword");

    }
}
