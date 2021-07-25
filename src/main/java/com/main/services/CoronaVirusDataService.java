package com.main.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.main.models.LocalStats;

@Service
public class CoronaVirusDataService {
	
	private static String URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private List<LocalStats> allStats = new ArrayList<>();
	
	
	
	public List<LocalStats> getAllStats() {
		return allStats;
	}



	public void setAllStats(List<LocalStats> allStats) {
		this.allStats = allStats;
	}



	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusData() throws IOException {
		List<LocalStats> newStats = new ArrayList<>();
		HttpClient http = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		HttpResponse<String> response = null;
		try {
			response = http.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		StringReader csvBodyReader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		try {
			for (CSVRecord record : records) {
				LocalStats localStats = new LocalStats();
			    localStats.setState(record.get("Province/State"));
				localStats.setCountry(record.get("Country/Region"));
				localStats.setLatestCases(Long.parseLong(record.get(record.size()-1)));
				newStats.add(localStats);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.allStats = newStats;
		     
	}
}
