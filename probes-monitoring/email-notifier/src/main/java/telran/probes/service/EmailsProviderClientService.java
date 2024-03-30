package telran.probes.service;

public interface EmailsProviderClientService {
	String[] DEFAULT_EMAILS = {"email1", "email2"};
	
	String[] getMails(long sensorId);
}
