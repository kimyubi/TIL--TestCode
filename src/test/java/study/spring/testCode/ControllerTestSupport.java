package study.spring.testCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.spring.testCode.api.controller.order.OrderController;
import study.spring.testCode.api.controller.product.ProductController;
import study.spring.testCode.api.service.order.OrderService;
import study.spring.testCode.api.service.product.ProductService;

@WebMvcTest(controllers = {
	OrderController.class,
	ProductController.class
})
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected OrderService orderService;

	@MockBean
	protected ProductService productService;

}
