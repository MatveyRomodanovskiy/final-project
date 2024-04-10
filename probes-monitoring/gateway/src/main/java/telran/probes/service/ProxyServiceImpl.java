package telran.probes.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProxyServiceImpl implements ProxyService {

	@Value("#{${app.map.hosts.ports}}")
	Map<String, String> routingMap;
	@Override
	public ResponseEntity<byte[]> proxyRouting(ProxyExchange<byte[]> proxy, HttpServletRequest request,
			String httpMethod) {
		String routedUrl = getRoutedUrl(request);
		log.debug("routed Url is {}",routedUrl);
		ResponseEntity<byte[]> responseEntity = switch (httpMethod) {
		case "GET" -> proxy.uri(routedUrl).get();
		case "POST" -> proxy.uri(routedUrl).post();
		case "DELETE" -> proxy.uri(routedUrl).delete();
		case "PUT" -> proxy.uri(routedUrl).put();
		default -> throw new IllegalArgumentException("Unexpected value: " + httpMethod);
		};
		return responseEntity;
	}
	private String getRoutedUrl(HttpServletRequest request) {
		String resourceName = request.getRequestURI();
		log.debug("resource name: {}", resourceName);
		String firstPartString = resourceName.split("/")[1];
		log.debug("fst Part is {}",firstPartString);
		String hostPortString =routingMap.get(firstPartString);
		return String.format("http://%s%s", hostPortString, resourceName);
	}
@PostConstruct
	void logRoutingMap() {
	log.debug("routing map is {}", routingMap);
}
}
