package com.producttask.product;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.producttask.product.controller.ProductController;
import com.producttask.product.model.Product;
import com.producttask.product.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;


    Product prod_1=new Product(1,"Lotte",12,200);

    Product prod_2=new Product(2,"Chocos",1,250);


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }


    @Test
    public void addProd_success() throws Exception{
        Product record= Product.builder()
                .productId(3)
                .productName("JimJam")
                .prodQuant(3)
                .price(150)
                .build();

        when(productService.addproduct(record)).thenReturn(record);

        String content=objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.post("/product/addprod")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.productName").value("JimJam"));
    }

    @Test
    public void getProd_success() throws Exception{
        List<Product> records=new ArrayList<>(Arrays.asList(prod_1,prod_2));

        when(productService.getprod()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/listallprod")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productName", is("Lotte")));

    }

    @Test
    public void deleteProd_success() throws Exception{
        willDoNothing().given(productService).deleteprod(prod_2.getProductId());
        ResultActions resultActions = mockMvc.perform(delete("/product/delete/2" + prod_2.getProductId()));
        resultActions.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void updateProd_success() throws Exception{
        Product updateRecord= Product.builder()
                .productId(1)
                .productName("Lotte Choco")
                .prodQuant(2)
                .price(150)
                .build();

        when(productService.updateProduct(prod_1.getProductId(),updateRecord)).thenReturn(updateRecord);

        String updatedContent= objectWriter.writeValueAsString(updateRecord);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder=MockMvcRequestBuilders.put("/product/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.productName").value("Lotte Choco"));


    }


}
