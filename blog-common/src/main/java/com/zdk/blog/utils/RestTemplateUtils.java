package com.zdk.blog.utils;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/7 13:34
 * RestTemplate工具类
 */
@Lazy
@Component
public class RestTemplateUtils {

    @Resource
    private RestTemplate restTemplate;

    public String urlConvert(String url, Map<String, ?> uriVariables) {
        url = url + "?";

        Map.Entry map;
        for(Iterator var3 = uriVariables.entrySet().iterator(); var3.hasNext(); url = url + (String)map.getKey() + "={" + (String)map.getKey() + "}&") {
            map = (Map.Entry)var3.next();
        }

        url = url.substring(0, url.length() - 1);
        return url;
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType) throws RestClientException {
        return this.restTemplate.getForEntity(url, responseType, new Object[0]);
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.restTemplate.getForEntity(url, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        url = this.urlConvert(url, uriVariables);
        return this.restTemplate.getForEntity(url, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.get(url, httpHeaders, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<?> requestEntity = new HttpEntity(headers);
        return this.exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.get(url, httpHeaders, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<?> requestEntity = new HttpEntity(headers);
        return this.exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, Class<T> responseType) throws RestClientException {
        return this.restTemplate.postForEntity(url, HttpEntity.EMPTY, responseType, new Object[0]);
    }

    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType) throws RestClientException {
        return this.restTemplate.postForEntity(url, requestBody, responseType, new Object[0]);
    }

    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return this.restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody, headers);
        return this.post(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody, headers);
        return this.post(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.put(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.put(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody, headers);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.put(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody, headers);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.delete(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return this.delete(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody, headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity(requestBody, headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return this.restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }
}
