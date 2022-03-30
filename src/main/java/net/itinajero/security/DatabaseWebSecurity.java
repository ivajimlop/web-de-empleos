package net.itinajero.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override //para leer de mysql los usuarios y roles
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
		.authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up " + 
		"inner join Usuarios u on u.id = up.idUsuario " + 
		"inner join Perfiles p on p.id = up.idPerfil " +
		"where u.username=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		//recursos estáticos
		.antMatchers("/bootstrap/**","/images/**","/tinymce/**","/logos/**").permitAll()
		
		//vistas para todos los usuarios
		.antMatchers("/","/login","/signup","/search","/Bcrypt/**","/about","/vacantes/view/**").permitAll()
		
		//acceso por roles de usuarios
		.antMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("solicitudes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		
		//vistas restringidas
		.anyRequest().authenticated()
		
		//formulario sin protección para registrarse
		.and().formLogin().loginPage("/login").permitAll()
		.and().logout().permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

}
