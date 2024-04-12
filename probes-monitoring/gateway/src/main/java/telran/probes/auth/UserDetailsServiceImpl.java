package telran.probes.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.core.joran.conditional.IfAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.AccountDto;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	@Value("${app.accounts.provider.host}")
	String host;
	@Value("${app.accounts.provider.port}")
	int port;
	@Value("${app.accounts.provider.url}")
	String url;
@Value("${app.root.password}")
String rootPassword;
@Value("${app.root.username:root@root.com}")
String rootUserName;
final RestTemplate restTemplate;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = null;
		if(!email.equals(rootUserName)) {
			String fullUrl = String.format("http://%s:%d%s/%s", host, port, url, email);
			ResponseEntity<?> response = restTemplate.exchange(fullUrl, HttpMethod.GET, null, AccountDto.class);
			
			if(response.getStatusCode().is4xxClientError()) {
				throw new UsernameNotFoundException(email);
			}		
			AccountDto account = (AccountDto) response.getBody();
			String[] roleStrings = Arrays.stream(account.roles()).map(r -> "ROLE_" + r).toArray(String[] :: new);
			user = new User(email, account.password(), AuthorityUtils.createAuthorityList(roleStrings));
			log.warn("user: {}", user);
		}else {
			user = new User(rootUserName, rootPassword, AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN", "ROLE_ADMIN_NOTIFIER"));
			log.warn("establish root user");
		}
		return user;
				}
}
