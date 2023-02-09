package ru.skypro.socksstorage.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.socksstorage.mapper.SocksMapperImpl;
import ru.skypro.socksstorage.model.Socks;
import ru.skypro.socksstorage.repository.SocksRepository;
import ru.skypro.socksstorage.service.impl.SocksServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.skypro.socksstorage.Constants.*;

@WebMvcTest(controllers = SocksController.class)
class SocksControllerTest {

    Socks SOCKS1 = new Socks();
    Socks SOCKS2 = new Socks();
    Socks SOCKS3 = new Socks();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SocksRepository socksRepository;
    @SpyBean
    private SocksServiceImpl socksService;
    @SpyBean
    private SocksMapperImpl socksMapper;

    @BeforeEach
    void setUp() {

        SOCKS1.setId(ID1);
        SOCKS1.setColor(COLOR1);
        SOCKS1.setCottonPart(COTTONPART1);
        SOCKS1.setQuantity(QUANTITY1);

        SOCKS2.setId(ID1);
        SOCKS2.setColor(COLOR1);
        SOCKS2.setCottonPart(COTTONPART1);
        SOCKS2.setQuantity(QUANTITY3);

        SOCKS3.setId(ID1);
        SOCKS3.setColor(COLOR1);
        SOCKS3.setCottonPart(COTTONPART1);
        SOCKS3.setQuantity(QUANTITY4);

    }

    @Test
    void getQuantityOfSocksUseEqual() throws Exception {

        when(socksRepository.findAllByColorAndCottonPartEquals(anyString(), anyInt())).thenReturn(List.of(SOCKS1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/socks")
                        .queryParam("color", COLOR1)
                        .queryParam("operation", OPERATION1)
                        .queryParam("cottonPart", COTTONPART1.toString()))

                .andExpect(status().isOk())
                .andExpect(content().string(SOCKS1.getQuantity().toString()));
    }
    @Test
    void getQuantityOfSocksUseMoreThan() throws Exception {

        when(socksRepository.findAllByColorAndCottonPartEquals(anyString(), anyInt())).thenReturn(List.of(SOCKS1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/socks")
                        .queryParam("color", COLOR1)
                        .queryParam("operation", OPERATION3)
                        .queryParam("cottonPart", COTTONPART3.toString()))

                .andExpect(status().isOk())
                .andExpect(content().string(SOCKS1.getQuantity().toString()));
    }
    @Test
    void getQuantityOfSocksUseLessThan() throws Exception {

        when(socksRepository.findAllByColorAndCottonPartEquals(anyString(), anyInt())).thenReturn(List.of(SOCKS1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/socks")
                        .queryParam("color", COLOR1)
                        .queryParam("operation", OPERATION2)
                        .queryParam("cottonPart", COTTONPART2.toString()))

                .andExpect(status().isOk())
                .andExpect(content().string(SOCKS1.getQuantity().toString()));
    }
    @Test
    void getQuantityOfSocksByWrongOperation() throws Exception {

        when(socksRepository.findAllByColorAndCottonPartEquals(anyString(), anyInt())).thenReturn(List.of(SOCKS1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/socks")
                        .queryParam("color", COLOR1)
                        .queryParam("operation", OPERATIONWRONG)
                        .queryParam("cottonPart", COTTONPART1.toString()))

                .andExpect(status().isBadRequest());
    }
    @Test
    void getQuantityOfSocksByWrongCottonPart() throws Exception {

        when(socksRepository.findAllByColorAndCottonPartEquals(anyString(), anyInt())).thenReturn(List.of(SOCKS1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/socks")
                        .queryParam("color", COLOR1)
                        .queryParam("operation", OPERATION1)
                        .queryParam("cottonPart", COTTONPARTWRONG.toString()))

                .andExpect(status().isBadRequest());
    }
    @Test
    void getQuantityOfSocksByWrongColor() throws Exception {

        when(socksRepository.findAllByColorAndCottonPartEquals(anyString(), anyInt())).thenReturn(List.of(SOCKS1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/socks")
                        .queryParam("color", COLORWRONG)
                        .queryParam("operation", OPERATION1)
                        .queryParam("cottonPart", COTTONPART1.toString()))

                .andExpect(status().isBadRequest());
    }

    @Test
    void addSocks() throws Exception {
        JSONObject socksDtoObject = new JSONObject();
        socksDtoObject.put("color", COLOR1);
        socksDtoObject.put("cottonPart", COTTONPART1);
        socksDtoObject.put("quantity", QUANTITY2);

        when(socksRepository.findSocksByColorAndCottonPart(anyString(), anyInt())).thenReturn(SOCKS1);
        when(socksRepository.save(any(Socks.class))).thenReturn(SOCKS2);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/income")
                        .content(socksDtoObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.color").value(COLOR1))
                .andExpect(jsonPath("$.cottonPart").value(COTTONPART1))
                .andExpect(jsonPath("$.quantity").value(QUANTITY3));
    }
    @Test
    void addNewSocks() throws Exception {
        JSONObject socksDtoObject = new JSONObject();
        socksDtoObject.put("color", COLOR1);
        socksDtoObject.put("cottonPart", COTTONPART1);
        socksDtoObject.put("quantity", QUANTITY1);

        when(socksRepository.findSocksByColorAndCottonPart(anyString(), anyInt())).thenReturn(null);
        when(socksRepository.save(any(Socks.class))).thenReturn(SOCKS1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/income")
                        .content(socksDtoObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.color").value(COLOR1))
                .andExpect(jsonPath("$.cottonPart").value(COTTONPART1))
                .andExpect(jsonPath("$.quantity").value(QUANTITY1));
    }

    @Test
    void deleteSocks() throws Exception {
        JSONObject socksDtoObject = new JSONObject();
        socksDtoObject.put("color", COLOR1);
        socksDtoObject.put("cottonPart", COTTONPART1);
        socksDtoObject.put("quantity", QUANTITY2);

        when(socksRepository.findSocksByColorAndCottonPart(anyString(), anyInt())).thenReturn(SOCKS1);
        when(socksRepository.save(any(Socks.class))).thenReturn(SOCKS3);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(socksDtoObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.color").value(COLOR1))
                .andExpect(jsonPath("$.cottonPart").value(COTTONPART1))
                .andExpect(jsonPath("$.quantity").value(QUANTITY4));
    }
    @Test
    void deleteNotExistedSocks() throws Exception {
        JSONObject socksDtoObject = new JSONObject();
        socksDtoObject.put("color", COLOR1);
        socksDtoObject.put("cottonPart", COTTONPART1);
        socksDtoObject.put("quantity", QUANTITY1);

        when(socksRepository.findSocksByColorAndCottonPart(anyString(), anyInt())).thenReturn(null);
        when(socksRepository.save(any(Socks.class))).thenReturn(SOCKS3);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(socksDtoObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    void deleteMoreSocksThanExisted() throws Exception {
        JSONObject socksDtoObject = new JSONObject();
        socksDtoObject.put("color", COLOR1);
        socksDtoObject.put("cottonPart", COTTONPART1);
        socksDtoObject.put("quantity", QUANTITY1);

        when(socksRepository.findSocksByColorAndCottonPart(anyString(), anyInt())).thenReturn(SOCKS3);
        when(socksRepository.save(any(Socks.class))).thenReturn(SOCKS3);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(socksDtoObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}