package ch.teachu.teachuapi.shared.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocsConfig {
    
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TeachU Api")
                        .description("" +
                                "<table>" +
                                "   <tr>" +
                                "       <th>Email</th>" +
                                "       <th>Password</th>" +
                                "       <th>Role</th>" +
                                "   </tr>" +
                                "   <tr>" +
                                "       <td>teacher@test.ch</td>" +
                                "       <td>test</td>" +
                                "       <td>teacher</td>" +
                                "   </tr>" +
                                "   <tr>" +
                                "       <td>student@test.ch</td>" +
                                "       <td>test</td>" +
                                "       <td>student</td>" +
                                "   </tr>" +
                                "   <tr>" +
                                "       <td>student2@test.ch</td>" +
                                "       <td>test</td>" +
                                "       <td>student</td>" +
                                "   </tr>" +
                                "   <tr>" +
                                "       <td>parent@test.ch</td>" +
                                "       <td>test</td>" +
                                "       <td>parent</td>" +
                                "   </tr>" +
                                "   <tr>" +
                                "       <td>parent2@test.ch</td>" +
                                "       <td>test</td>" +
                                "       <td>parent</td>" +
                                "   </tr>" +
                                "</table>")
                        .version("v0.1"));
    }
}
