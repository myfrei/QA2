package com.myfrei.qa.platform.recourse.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.myfrei.qa.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "users/role.yml",
        "users/users.yml",
        "users/reputation.yml",
        "users/editor.yml",
        "users/moderator.yml",
        "users/answer_u.yml",
        "users/question_u.yml",
        "users/votes_on_answer.yml",
        "users/votes_on_question.yml"}, cleanBefore = true, cleanAfter = true)
public class UserRecourseControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_User() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Админ"))
                .andExpect(jsonPath("$.email").value("admin@admin.ru"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void create_User_With_Id() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 2," +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле id должно принимать null значение при создании"));
    }

    @Test
    void create_User_No_Name() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": null," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле имя не должно быть Null при создании"));
    }

    @Test
    void create_User_Name_Blank() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"      \"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле имя должен начинаться с буквы"));
    }

    @Test
    void create_User_Invalid_Name() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"4Артем\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле имя должен начинаться с буквы"));
    }

    @Test
    void create_User_No_Email() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": null," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле Email не должно быть Null при создании"));
    }

    @Test
    void create_User_Email_Blank() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"       \"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email должен быть корректным"));
    }

    @Test
    void create_User_With_Invalid_Email() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@.ru\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email должен быть корректным"));
    }

    @Test
    void create_User_No_Password() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": null" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле password не должно быть Null при создании"));
    }

    @Test
    void create_User_With_Short_Password() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwert1\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле password должен быть не мение 8 символов."));
    }

    @Test
    void create_User_With_No_Number_Password() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwertyuior\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле password должен содержать 1 цифру, 1 заглавную букву."));
    }

    @Test
    void create_User_With_No_Capital_Letter_Password() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"qwertys123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле password должен содержать 1 цифру, 1 заглавную букву."));
    }

    @Test
    void create_User_With_Role() throws Exception {
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"fullName\": \"Админ\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Qwerty123\"," +
                        "\"role\": 1" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("'role' автоматически назначается при создании всем пользователям, " +
                        "явно указывать не нужно"));
    }

    @Test
    void update_User() throws Exception {
        this.mockMvc.perform(put("/api/user/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 3," +
                        "\"fullName\": \"Update\"," +
                        "\"email\": \"Update@email.com\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.fullName").value("Update"))
                .andExpect(jsonPath("$.email").value("Update@email.com"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_User_Invalid_Role() throws Exception {
        this.mockMvc.perform(put("/api/user/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 3," +
                        "\"fullName\": \"Update\"," +
                        "\"email\": \"Update@email.com\"," +
                        "\"password\": \"Qwerty123\"," +
                        "\"role\": \"Invalid\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Role с инянем Invalid не существует"));
    }

    @Test
    void update_User_No_Id() throws Exception {
        this.mockMvc.perform(put("/api/user/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": null," +
                        "\"fullName\": \"Василий update\"," +
                        "\"email\": \"vasiliy@email.com\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле id не должно принимать null значение при обновлении"));
    }

    @Test
    void update_User_Invalid_Id() throws Exception {
        this.mockMvc.perform(put("/api/user/{id}", 111L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 111," +
                        "\"fullName\": \"Василий\"," +
                        "\"email\": \"vasiliy@email.com\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 111 does not exist"));
    }

    @Test
    void update_User_Id_Blank() throws Exception {
        this.mockMvc.perform(put("/api/user/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": \"   \"," +
                        "\"fullName\": \"Василий update\"," +
                        "\"email\": \"vasiliy@email.com\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Поле id не должно принимать null значение при обновлении"));
    }

    @Test
    void update_User_Invalid_Id_Path_Variable() throws Exception {
        this.mockMvc.perform(put("/api/user/{id}", "ads")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 3," +
                        "\"fullName\": \"Василий update\"," +
                        "\"email\": \"vasiliy@email.com\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными," +
                        " только числовые!"));
    }

    @Test
    void update_User_Where_Id_Not_Equal_Id_Path_Variable() throws Exception {
        this.mockMvc.perform(put("/api/user/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 4," +
                        "\"fullName\": \"Василий update\"," +
                        "\"email\": \"vasiliy@email.com\"," +
                        "\"password\": \"Qwerty123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Specified ID: 3 does not match user ID: 4"));
    }

    @Test
    void find_User() throws Exception {
        this.mockMvc.perform(get("/api/user/{id}", 5L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.fullName").value("Иван"))
                .andExpect(jsonPath("$.email").value("ivan@email.com"))
                .andExpect(jsonPath("$.role").value("user"))
                .andExpect(jsonPath("$.about").value("description 2"))
                .andExpect(jsonPath("$.city").value("SPB"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void find_User_Invalid_Id() throws Exception {
        this.mockMvc.perform(get("/api/user/{id}", 115L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 115 does not exist"));
    }

    @Test
    void find_User_Invalid_Id_Path_Variable() throws Exception {
        this.mockMvc.perform(get("/api/user/{id}", "abc"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными, " +
                        "только числовые!"));
    }

    @Test
    void find_All_Users() throws Exception {
        this.mockMvc.perform(get("/api/user"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void get_List_New_Users_By_Reputation_For_Week() throws Exception {
        this.mockMvc.perform(get("/api/user/new/reputation?count=5&page=1&weeks=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key[0].id").value("2"))
                .andExpect(jsonPath("$.key[0].fullName").value("Igor"))
                .andExpect(jsonPath("$.value").value("3"));
    }

    @Test
    void get_List_New_Users_By_Reputation_Request_Parameter_Negative() throws Exception {
        this.mockMvc.perform(get("/api/user/new/reputation?count=5&page=1&weeks=-1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_New_Users_By_Reputation_Request_Parameter_Zero() throws Exception {
        this.mockMvc.perform(get("/api/user/new/reputation?count=0&page=1&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_New_Users_By_Reputation_Request_Parameter_Invalid() throws Exception {
        this.mockMvc.perform(get("/api/user/new/reputation?count=abc&page=1&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными," +
                        " только числовые!"));
    }

    @Test
    void get_List_Users_By_Creation_Date_For_Week() throws Exception {
        this.mockMvc.perform(get("/api/user/new?count=5&page=1&weeks=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key[0].id").value("1"))
                .andExpect(jsonPath("$.key[0].fullName").value("Артем"))
                .andExpect(jsonPath("$.value").value("3"));
    }

    @Test
    void get_List_Users_By_Creation_Date_Request_Parameter_Negative() throws Exception {
        this.mockMvc.perform(get("/api/user/new?count=-1&page=1&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Creation_Date_Request_Parameter_Zero() throws Exception {
        this.mockMvc.perform(get("/api/user/new?count=5&page=1&weeks=0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Creation_Date_Request_Parameter_Invalid() throws Exception {
        this.mockMvc.perform(get("/api/user/new?count=5&page=abc&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными, " +
                        "только числовые!"));

    }

    @Test
    void get_List_Users_By_Reputation_For_week() throws Exception {
        this.mockMvc.perform(get("/api/user/reputation?count=5&page=1&weeks=1"))
                .andDo(print())
                .andExpect(jsonPath("$.key[0].id").value("2"))
                .andExpect(jsonPath("$.key[0].fullName").value("Igor"))
                .andExpect(jsonPath("$.value").value("3"));
    }

    @Test
    void get_List_Users_By_Reputation_Request_Parameter_Negative() throws Exception {
        this.mockMvc.perform(get("/api/user/reputation?count=5&page=-1&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Reputation_Request_Parameter_Zero() throws Exception {
        this.mockMvc.perform(get("/api/user/reputation?count=0&page=1&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Reputation_Request_Parameter_Invalid() throws Exception {
        this.mockMvc.perform(get("/api/user/reputation?count=5&page=1&weeks=abc"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными, " +
                        "только числовые!"));
    }

    @Test
    void get_List_Users_By_Quantity_Edited_Text_For_Week() throws Exception {
        this.mockMvc.perform(get("/api/user/editor?count=5&page=1&weeks=1"))
                .andDo(print())
                .andExpect(jsonPath("$.key[0].id").value("1"))
                .andExpect(jsonPath("$.key[0].fullName").value("Артем"))
                .andExpect(jsonPath("$.value").value("3"));
    }

    @Test
    void get_List_Users_By_Quantity_Edited_Text_Request_Parameter_Negative() throws Exception {
        this.mockMvc.perform(get("/api/user/editor?count=-1&page=1&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Quantity_Edited_Text_Request_Parameter_Zero() throws Exception {
        this.mockMvc.perform(get("/api/user/editor?count=5&page=1&weeks=0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Quantity_Edited_Text_Request_Parameter_Invalid() throws Exception {
        this.mockMvc.perform(get("/api/user/editor?count=5&page=abc&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными, " +
                        "только числовые!"));
    }

    @Test
    void get_List_Users_By_Moderator() throws Exception {
        this.mockMvc.perform(get("/api/user/moderator"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void get_List_Users_By_Name_To_Search() throws Exception {
        this.mockMvc.perform(get("/api/user/find?count=5&page=1&weeks=12&name=Евгений"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key[0].id").value("3"))
                .andExpect(jsonPath("$.key[0].fullName").value("Евгений"))
                .andExpect(jsonPath("$.key[0].reputationCount").value("4"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void get_List_Users_By_Name_To_Search_Request_Parameter_Negative() throws Exception {
        this.mockMvc.perform(get("/api/user/find?count=5&page=-1&weeks=12&name=Андрей"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Name_To_Search_Request_Parameter_Zero() throws Exception {
        this.mockMvc.perform(get("/api/user/find?count=5&page=1&weeks=0&name=Андрей"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Name_To_Search_Request_Parameter_Invalid() throws Exception {
        this.mockMvc.perform(get("/api/user/find?count=abc&page=1&weeks=12&name=Андрей"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными, " +
                        "только числовые!"));
    }

    @Test
    void get_List_Users_By_Name_To_Search_Request_Parameter_Name_Not_String() throws Exception {
        this.mockMvc.perform(get("/api/user/find?count=5&page=1&weeks=12&name=4"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Параметр name должен начинаться с буквы"));
    }

    @Test
    void get_List_Users_By_Voice_For_Week() throws Exception {
        this.mockMvc.perform(get("/api/user/voice?count=5&page=1&weeks=1"))
                .andDo(print())
                .andExpect(jsonPath("$.key[0].id").value("1"))
                .andExpect(jsonPath("$.key[0].fullName").value("Артем"))
                .andExpect(jsonPath("$.value").value("3"));
    }

    @Test
    void get_List_Users_By_Voice_Request_Parameter_Negative() throws Exception {
        this.mockMvc.perform(get("/api/user/voice?count=-5&page=1&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Voice_Request_Parameter_Zero() throws Exception {
        this.mockMvc.perform(get("/api/user/voice?count=5&page=1&weeks=0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("must be greater than 0"));
    }

    @Test
    void get_List_Users_By_Voice_Request_Parameter_Invalid() throws Exception {
        this.mockMvc.perform(get("/api/user/voice?count=5&page=abc&weeks=12"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Значения не должны быть символьными, " +
                        "только числовые!"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testUserDtoAndAnswerPairFromUserStatisticVotesSorting() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=answer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.answerList.key").value("2"))
                .andExpect(jsonPath("$.answerList.value[0].id").value("5"))
                .andExpect(jsonPath("$.answerList.value[-1].id").value("2"))
                .andExpect(jsonPath("$.answerList.value.size()").value("2"));

    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testUserDtoAndAnswerPairFromUserStatisticDataSorting() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=answer&sort=newest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.answerList.key").value("2"))
                .andExpect(jsonPath("$.answerList.value[0].id").value("5"))
                .andExpect(jsonPath("$.answerList.value[-1].id").value("2"))
                .andExpect(jsonPath("$.answerList.value.size()").value("2"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testUserDtoAndAnswerPairFromUserStatisticSortingByViews() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=answer&sort=views"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.answerList.key").value("2"))
                .andExpect(jsonPath("$.answerList.value[0].id").value("2"))
                .andExpect(jsonPath("$.answerList.value[-1].id").value("5"))
                .andExpect(jsonPath("$.answerList.value.size()").value("2"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testQuestionPairFromUserStatisticSortingByVotes() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=question"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.questionDtoList.key").value("3"))
                .andExpect(jsonPath("$.questionDtoList.value[0].id").value("1"))
                .andExpect(jsonPath("$.questionDtoList.value[-1].id").value("5"))
                .andExpect(jsonPath("$.questionDtoList.value.size()").value("3"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testQuestionPairFromUserStatisticSortingByView() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=question&sort=views"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.questionDtoList.key").value("3"))
                .andExpect(jsonPath("$.questionDtoList.value[0].id").value("2"))
                .andExpect(jsonPath("$.questionDtoList.value[-1].id").value("5"))
                .andExpect(jsonPath("$.questionDtoList.value.size()").value("3"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testQuestionPairFromUserStatisticSortingByDate() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=question&sort=newest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.questionDtoList.key").value("3"))
                .andExpect(jsonPath("$.questionDtoList.value[0].id").value("5"))
                .andExpect(jsonPath("$.questionDtoList.value[-1].id").value("1"))
                .andExpect(jsonPath("$.questionDtoList.value.size()").value("3"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml",
            "userStatistics/userFavoriteQuestionStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testUserFavoriteQuestionPairFromUserStatisticSortingByVotes() throws Exception { //??
        this.mockMvc.perform(get("/api/user/1/Teat?tab=bookmarks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.userFavoriteQuestions.key").value("2"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value[0].id").value("3"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value[-1].id").value("8"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value.size()").value("2"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml",
            "userStatistics/userFavoriteQuestionStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testUserFavoriteQuestionPairFromUserStatisticSortingByDate() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=bookmarks&sort=newest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.userFavoriteQuestions.key").value("2"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value[0].id").value("8"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value[-1].id").value("3"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value.size()").value("2"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml",
            "userStatistics/userFavoriteQuestionStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testUserFavoriteQuestionPairFromUserStatisticSortingByViews() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=bookmarks&sort=views"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.userFavoriteQuestions.key").value("2"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value[0].id").value("3"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value[-1].id").value("8"))
                .andExpect(jsonPath("$.userFavoriteQuestions.value.size()").value("2"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testTagPairFromUserStatistic() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.tagDtoList.key").value("2"))
                .andExpect(jsonPath("$.tagDtoList.value[0].tagId").value("1"))
                .andExpect(jsonPath("$.tagDtoList.value[-1].tagId").value("2"))
                .andExpect(jsonPath("$.tagDtoList.value[0].countOfTag").value("3"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/badgesStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/user_badgesStatistics.yml"
    }, cleanBefore = true, cleanAfter = true)
    void testUserBadgesPairFromUserStatistic() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=badges"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.userBadges.key").value("2"))
                .andExpect(jsonPath("$.userBadges.value[0].id").value("1"))
                .andExpect(jsonPath("$.userBadges.value[0].badges").value("Helper"))
                .andExpect(jsonPath("$.userBadges.value[-1].id").value("2"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/badgesStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/user_badgesStatistics.yml"
    }, cleanBefore = true, cleanAfter = true)
    void testUserReputationFromUserStatistic() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=reputation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.userReputation.size()").value("2"))
                .andExpect(jsonPath("$.userReputation[0].count").value("30"))
                .andExpect(jsonPath("$.userReputation[1].count").value("100"));
    }

    @Test
    @DataSet(value = {"userStatistics/usersStatistics.yml",
            "userStatistics/roleStatistics.yml",
            "userStatistics/reputationStatistics.yml",
            "userStatistics/questionStatistics.yml",
            "userStatistics/answerStatistics.yml",
            "userStatistics/tagsStatistics.yml",
            "userStatistics/badgesStatistics.yml",
            "userStatistics/q_has_tagStatistics.yml",
            "userStatistics/userFavoriteQuestionStatistics.yml",
            "userStatistics/user_badgesStatistics.yml"}, cleanBefore = true, cleanAfter = true)
    void testUserStatisticsProfileTabFromUserStatisticSortingByViews() throws Exception {
        this.mockMvc.perform(get("/api/user/1/Teat?tab=profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUserReputation").value("130"))
                .andExpect(jsonPath("$.totalUserQuestions").value("3"))
                .andExpect(jsonPath("$.totalUserAnswers").value("2"))
                .andExpect(jsonPath("$.allViews").value("12"))
                .andExpect(jsonPath("$.userDto.id").value("1"))
                .andExpect(jsonPath("$.userDto.fullName").value("Teat"))
                .andExpect(jsonPath("$.userDto.persistDateTime").value("2020-05-28T13:58:56"))
                .andExpect(jsonPath("$.questionDtoList.key").value("3"))
                .andExpect(jsonPath("$.answerList.key").value("2"))
                .andExpect(jsonPath("$.tagDtoList.key").value("2"))
                .andExpect(jsonPath("$.userBadges.key").value("2"))
                .andExpect(jsonPath("$.userFavoriteQuestions.key").value("2"))
                .andExpect(jsonPath("$.userReputation.size()").value("2"));
    }
}



