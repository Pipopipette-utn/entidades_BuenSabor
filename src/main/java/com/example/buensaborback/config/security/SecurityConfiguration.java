package com.example.buensaborback.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${web.cors.allowed-origins}")
    private String corsAllowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors(withDefaults()) //por defecto spring va a buscar un bean con el nombre "corsConfigurationSource".
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                                // INSUMOS
                                    // al método get que no incluye eliminados pueden acceder todos
                                .requestMatchers(HttpMethod.GET, "/articulosInsumos/filtrar/{idSucursal}").permitAll()
                                    // Todos pueden acceder a los insumos directos (bebidas...)
                                .requestMatchers(HttpMethod.GET, "/articulosInsumos/paged/insumosDirectos").permitAll()
                                    // a todos los métodos get pueden acceder "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.GET, "/articulosInsumos/**").hasAnyAuthority("administrador", "cocinero")
                                    // pueden crear el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.POST, "/articulosInsumos/create").hasAnyAuthority("administrador", "cocinero")
                                // pueden actualizar el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.PUT, "/articulosInsumos/{id}").hasAnyAuthority("administrador", "cocinero")
                                    // puede eliminar el "administrador"
                                .requestMatchers(HttpMethod.DELETE, "/articulosInsumos/**").hasAuthority("administrador")
                                    // puede dar de alta el "administrador"
                                .requestMatchers(HttpMethod.PUT, "/articulosInsumos/alta/{id}}").hasAuthority("administrador")

                                // MANUFACTURADOS
                                    // al método get que no incluye eliminados pueden acceder todos
                                .requestMatchers(HttpMethod.GET, "/articulosManufacturados/filtrar/{idSucursal}").permitAll()
                                    // al método get paginado que no incluye eliminados pueden acceder todos
                                .requestMatchers("/articulosManufacturados/paged").permitAll()
                                    // a todos los métodos get pueden acceder "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.GET, "/articulosManufacturados/**").hasAnyAuthority("administrador", "cocinero")
                                    // pueden crear el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.POST, "/articulosManufacturados/create").hasAnyAuthority("administrador", "cocinero")
                                    // pueden actualizar el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.PUT, "/articulosManufacturados/{id}").hasAnyAuthority("administrador", "cocinero")
                                    // puede eliminar el "administrador"
                                .requestMatchers(HttpMethod.DELETE, "/articulosManufacturados/**").hasAuthority("administrador")
                                    // puede dar de alta el "administrador"
                                .requestMatchers(HttpMethod.PUT, "/articulosManufacturados/alta/{id}}").hasAuthority("administrador")

                                // PROMOCIONES
                                    // al método get que no incluye eliminados pueden acceder todos
                                .requestMatchers(HttpMethod.GET, "/promociones/porSucursal/{sucursalId}").permitAll()
                                    // al método get que no incluye eliminados pueden acceder todos
                                .requestMatchers(HttpMethod.GET, "/promociones").permitAll()
                                    // al método get paginado que no incluye eliminados pueden acceder todos
                                .requestMatchers("/promociones/paged").permitAll()
                                    // a todos los métodos get pueden acceder "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.GET, "/promociones/**").hasAnyAuthority("administrador", "cocinero")
                                    // pueden crear el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.POST, "/promociones").hasAnyAuthority("administrador", "cocinero")
                                    // pueden actualizar el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.PUT, "/promociones/{id}").hasAnyAuthority("administrador", "cocinero")
                                    // puede eliminar el "administrador"
                                .requestMatchers(HttpMethod.DELETE, "/promociones/**").hasAuthority("administrador")
                                    // puede dar de alta el "administrador"
                                .requestMatchers(HttpMethod.PUT, "/promociones/alta/{id}}").hasAuthority("administrador")

                                // UNIDADES DE MEDIDAS
                                    // a todos los métodos get pueden acceder "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.GET, "/unidadesMedidas/**").hasAnyAuthority("administrador", "cocinero")
                                    // pueden crear el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.POST, "/unidadesMedidas").hasAnyAuthority("administrador", "cocinero")
                                    // pueden actualizar el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.PUT, "/unidadesMedidas/{id}").hasAnyAuthority("administrador", "cocinero")
                                    // puede eliminar el "administrador"
                                .requestMatchers(HttpMethod.DELETE, "/unidadesMedidas/**").hasAuthority("administrador")
                                    // puede dar de alta el "administrador"
                                .requestMatchers(HttpMethod.PUT, "/unidadesMedidas/alta/{id}}").hasAuthority("administrador")

                                // CATEGORÍAS
                                    // Pueden acceder todos
                                .requestMatchers(HttpMethod.GET, "/sucursales/{id}/categorias").permitAll()
                                    // Pueden ver todas las categorías el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.GET, "/categorias/**").hasAnyAuthority("administrador", "cocinero")
                                    // pueden crear el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.POST, "/categorias").hasAnyAuthority("administrador", "cocinero")
                                    // pueden actualizar el "administrador" y "cocinero"
                                .requestMatchers(HttpMethod.PUT, "/categorias/{id}").hasAnyAuthority("administrador", "cocinero")
                                    // puede eliminar el "administrador"
                                .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasAuthority("administrador")
                                    // puede dar de alta el "administrador"
                                .requestMatchers(HttpMethod.PUT, "/categorias/alta/{id}}").hasAuthority("administrador")

                                // PEDIDOS
                                    // Pueden cambiar el estado el "administrador", "cocinero", "cajero" y "delivery"
                                .requestMatchers(HttpMethod.PUT, "/categorias/cambiarEstado/{id}").hasAnyAuthority("administrador", "cocinero", "cajero", "delivery")

                                // PAISES
                                    // Nadie puede crear
                                .requestMatchers(HttpMethod.POST, "/paises").denyAll()
                                    // Nadie puede actualizar
                                .requestMatchers(HttpMethod.PUT, "/paises/{id}").denyAll()
                                    // Nadie puede eliminar
                                .requestMatchers(HttpMethod.DELETE, "/paises/**").denyAll()

                                // PROVINCIAS
                                    // Nadie puede crear
                                .requestMatchers(HttpMethod.POST, "/provincias").denyAll()
                                    // Nadie puede actualizar
                                .requestMatchers(HttpMethod.PUT, "/provincias/{id}").denyAll()
                                    // Nadie puede eliminar
                                .requestMatchers(HttpMethod.DELETE, "/provincias/**").denyAll()

                                // LOCALIDADES
                                    // Nadie puede crear
                                .requestMatchers(HttpMethod.POST, "/localidades").denyAll()
                                    // Nadie puede actualizar
                                .requestMatchers(HttpMethod.PUT, "/localidades/{id}").denyAll()
                                    // Nadie puede eliminar
                                .requestMatchers(HttpMethod.DELETE, "/localidades/**").denyAll()


                                // DEFAULT
                                    // Para acceder a todas las otras rutas no se necesita nada
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(jwt ->
                                        jwt
                                                .decoder(jwtDecoder())
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                )
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(corsAllowedOrigins.split(",")));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("X-Get-Header"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        String audience = System.getenv("auth0.audience");
        converter.setAuthoritiesClaimName(audience+"/roles");
        converter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }
}