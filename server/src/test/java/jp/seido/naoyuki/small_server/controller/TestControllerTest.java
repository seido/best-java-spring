package jp.seido.naoyuki.small_server.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jp.seido.naoyuki.lib.entity.User;
import jp.seido.naoyuki.lib.service.UserStore;

@WebMvcTest(controllers = { TestController.class })
public class TestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserStore userStore;

    @Test
    @DisplayName("put呼び出しで登録されたユーザー情報が返却される")
    public void testPutAndGet() throws Exception {
        doReturn(User.builder().id("id").username("naoyuki").password("password").build()).when(userStore)
                .register("naoyuki", "password");
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"naoyuki\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("naoyuki"))
                .andExpect(jsonPath("$.user.password").value("password"))
                .andExpect(jsonPath("$.user.id").isNotEmpty());
    }

    @Test
    @DisplayName("get呼び出しで登録されたユーザー情報が返却される")
    public void testGet() throws Exception {
        doReturn(User.builder().id("testid").username("testname").password("testpassword").build()).when(userStore)
                .getUserById("testid");
        mockMvc.perform(get("/test?id=testid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("testname"))
                .andExpect(jsonPath("$.user.password").value("testpassword"))
                .andExpect(jsonPath("$.user.id").value("testid"));
    }

    @Test
    @DisplayName("getで存在しないユーザーの場合はnullが返却される")
    public void testGetNotFound() throws Exception {
        doReturn(null).when(userStore).getUserById("notfound");
        mockMvc.perform(get("/test?id=notfound"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").value(IsNull.nullValue()));
    }

    @Test
    @DisplayName("putでusernameが空の場合はエラー")
    public void testPutErrorUsernameEmpty() throws Exception {
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"\", \"password\":\"password\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("putでpasswordが空の場合はエラー")
    public void testPutErrorPasswordEmpty() throws Exception {
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"naoyuki\", \"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("putでjson異常の場合はエラー")
    public void testPutErrorJson() throws Exception {
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"naoyuki\", \"password\":\"password\""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("passwordに改行文字が含まれる場合はエラー")
    public void testPutErrorPasswordNewline() throws Exception {
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"naoyuki\", \"password\":\"password\\n\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("passwordに制御文字が含まれる場合はエラー")
    public void testPutErrorPasswordControl() throws Exception {
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"naoyuki\", \"password\":\"password\\u0000\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("passwordにタブが含まれる場合はエラー")
    public void testPutErrorPasswordTab() throws Exception {
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"naoyuki\", \"password\":\"password\\t\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("passwordにスペースとバックスラッシュ、&、ダブルクォーテーションを含めてもエラーにならない")
    public void testPutErrorPasswordSpaceBackslashAnd() throws Exception {
        mockMvc.perform(put("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"naoyuki\", \"password\":\" \\&\\\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
