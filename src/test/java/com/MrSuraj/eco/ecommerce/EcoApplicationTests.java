package com.MrSuraj.eco.ecommerce;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.MrSuraj.eco.ecommerce.config.JwtProvider;
import com.MrSuraj.eco.ecommerce.entity.Order;
import com.MrSuraj.eco.ecommerce.entity.OrderStatus;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.repo.OrderRepository;
import com.MrSuraj.eco.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class EcoApplicationTests {
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Test
	void shouldReturnConsistentUnauthorizedErrorForProtectedRoute() throws Exception {
		mockMvc.perform(get("/api/admin/orders/"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.error").value("UNAUTHORIZED"))
				.andExpect(jsonPath("$.path").value("/api/admin/orders/"));
	}

	@Test
	void shouldRejectCustomerRoleFromAdminRoute() throws Exception {
		mockMvc.perform(get("/api/admin/orders/")
						.header("Authorization", bearerToken("ROLE_CUSTOMER")))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.error").value("FORBIDDEN"));
	}

	@Test
	void shouldAllowAdminRoleOnAdminRoute() throws Exception {
		mockMvc.perform(get("/api/admin/orders/")
						.header("Authorization", bearerToken("ROLE_ADMIN")))
				.andExpect(status().isAccepted());
	}

		    @Test
		    @Transactional
		    void shouldReflectAdminOrderStatusChangeForTheOwningCustomer() throws Exception {
			User customer = saveUser("customer-order@example.com", "CUSTOMER");
			User anotherCustomer = saveUser("another-customer@example.com", "CUSTOMER");
			Order order = new Order();
			order.setUser(customer);
			order.setOrderStatus(OrderStatus.PLACED);
			order = orderRepository.save(order);

			mockMvc.perform(get("/api/orders/{orderId}", order.getId())
					.header("Authorization", bearerToken(customer.getEmail(), "ROLE_CUSTOMER")))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.orderStatus").value("PLACED"));

			mockMvc.perform(put("/api/admin/orders/{orderId}/ship", order.getId())
					.header("Authorization", bearerToken("admin@example.com", "ROLE_ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.orderStatus").value("SHIPPED"));

			mockMvc.perform(get("/api/orders/{orderId}", order.getId())
					.header("Authorization", bearerToken(customer.getEmail(), "ROLE_CUSTOMER")))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.orderStatus").value("SHIPPED"));

			Order otherOrder = new Order();
			otherOrder.setUser(anotherCustomer);
			otherOrder.setOrderStatus(OrderStatus.PLACED);
			otherOrder = orderRepository.save(otherOrder);
			mockMvc.perform(get("/api/orders/{orderId}", otherOrder.getId())
					.header("Authorization", bearerToken(customer.getEmail(), "ROLE_CUSTOMER")))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.error").value("FORBIDDEN"));
		    }

	@Test
	void shouldKeepSwaggerDocumentPublic() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.components.securitySchemes.bearerAuth.scheme").value("bearer"));
	}

	@Test
	void shouldReturnFieldErrorsForInvalidLoginPayload() throws Exception {
		mockMvc.perform(post("/auth/signin")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.validationErrors.email").exists())
				.andExpect(jsonPath("$.validationErrors.password").exists());
	}

	@Test
	void shouldIgnoreRequestedAdminRoleDuringCustomerSignup() throws Exception {
		mockMvc.perform(post("/auth/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"firstName":"Taylor","lastName":"Test","email":"signup-role@example.com","password":"example-password","role":"ADMIN"}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.role").value("CUSTOMER"));
	}

	private String bearerToken(String role) {
		return bearerToken("test@example.com", role);
	}

	private String bearerToken(String email, String role) {
		var authentication = new UsernamePasswordAuthenticationToken(
				email, null, List.of(new SimpleGrantedAuthority(role)));
		return "Bearer " + jwtProvider.generateToken(authentication);
	}

	private User saveUser(String email, String role) {
		User user = new User();
		user.setEmail(email);
		user.setPassword("encoded-test-password");
		user.setRole(role);
		user.setFirstName("Test");
		user.setLastName("Customer");
		return userRepository.save(user);
	}

}
