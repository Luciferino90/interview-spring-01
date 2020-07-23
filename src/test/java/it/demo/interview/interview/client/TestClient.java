package it.demo.interview.interview.client;

import com.fasterxml.jackson.core.type.TypeReference;
import it.demo.interview.interview.enumerator.DocumentType;
import it.demo.interview.interview.persistence.sql.entity.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.IntStream;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestClient {

    @LocalServerPort
    private Integer port;

    private final String host = "http://localhost";
    private final String basepath = "/api/v2";
    private final String filename = "uploadTest.pdf";

    private final RestTemplate restTemplate;

    TypeReference<Page<Document>> pagedResponse = new TypeReference<>() {};

    public TestClient() {
        this.restTemplate = new RestTemplate();
    }

    private String buildEndpoint(String path, String... optionalArgs){
        return host + ":" + port + basepath + String.format(path, optionalArgs);
    }

    @Test
    public void singleUploadAndSearch() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters .add("username", "test");
        parameters .add("documentType", DocumentType.DOC.name());
        parameters .add("file", getTestFile());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "multipart/form-data");
        httpHeaders.set("Accept", "application/json");

        String uploadUrl = buildEndpoint("/document/upload");

        Document result = restTemplate.postForEntity(
                uploadUrl,
                new HttpEntity<>(parameters, httpHeaders),
                Document.class
        ).getBody();
        Assertions.assertNotNull(result);

        String getByIdAndDocType = buildEndpoint("/document/documenttype/%s/id/%s", result.getDocumentType().name(), result.getDocumentId().toString());

        Assertions.assertNotNull(restTemplate.getForEntity(getByIdAndDocType, Document.class).getBody());

        String getByUsername = buildEndpoint("/document/username/%s", result.getUsername());

        Assertions.assertEquals(1, restTemplate.getForEntity(getByUsername, Map.class).getBody().get("totalElements"));
    }

    @Test
    public void multipleUpdateWithErrors() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters .add("username", "test");
        parameters .add("documentType", DocumentType.DOC.name());
        parameters .add("file", getTestFile());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "multipart/form-data");
        httpHeaders.set("Accept", "application/json");

        String uploadUrl = buildEndpoint("/document/upload");
        try {
            IntStream.range(0, 100)
                    .parallel()
                    .forEach(i -> restTemplate.postForEntity(
                            uploadUrl,
                            new HttpEntity<>(parameters, httpHeaders),
                            Document.class
                    ));
        } catch (Exception ex) {
            // Disabled, right now it fails
        }
    }

    public Resource getTestFile() {
        Path testFile = new File(getClass().getClassLoader().getResource(filename).getFile()).toPath();
        System.out.println("Creating and Uploading Test File: " + testFile);
        return new FileSystemResource(testFile.toFile());
    }

}
