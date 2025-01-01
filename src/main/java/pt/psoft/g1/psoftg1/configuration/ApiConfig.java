package pt.psoft.g1.psoftg1.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import pt.psoft.g1.psoftg1.shared.services.FileStorageProperties;

/**
 *
 * @author pgsousa
 *
 */
@Configuration
@EnableConfigurationProperties({ FileStorageProperties.class })
public class ApiConfig {

    /*
     * Etags
     */
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    /*
     * OpenAPI
     */
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("ApiDePsoft").description("ApiDePsoft").version("v1.0")
                        .contact(new Contact().name("QualquerCoisa").email("email@something.noemail"))
                        .termsOfService("TOC").license(new License().name("MIT").url("#")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
    }

}
