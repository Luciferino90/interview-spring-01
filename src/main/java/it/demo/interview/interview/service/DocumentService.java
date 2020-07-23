package it.demo.interview.interview.service;

import it.demo.interview.interview.enumerator.DocumentType;
import it.demo.interview.interview.persistence.sql.entity.Document;
import it.demo.interview.interview.persistence.sql.service.DocumentDaoService;
import it.demo.interview.interview.utils.HashService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.IntStream;

import static it.demo.interview.interview.enumerator.DocumentType.*;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentDaoService documentDaoService;

    public Document uploadDocument(MultipartFile file, String username, DocumentType documentType){
        Integer documentId = documentDaoService.findLatestDocumentIdForDocumentType(documentType)
                .map(Document::getDocumentId)
                .map(id -> id + 1)
                .orElse(0);
        String hash;
        try {
            hash = HashService.getFileHash(file);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot evaluate hash for file " + file.getOriginalFilename());
        }

        Document document = Document.builder()
                .id(UUID.randomUUID().toString())
                .docFileName(file.getOriginalFilename())
                .docHash(hash)
                .documentId(documentId)
                .documentType(documentType)
                .uploadDate(new Date())
                .username(username)
                .build();
        return documentDaoService.save(document);
    }

    public Page<Document> findByUsername(String username, Pageable pageable){
        return documentDaoService.findByUsername(username, pageable);
    }

    public Document findByDocumentIdAndDocumentType (DocumentType documentType, Integer documentId){
        return documentDaoService.findByDocumentIdAndDocumentType(documentId, documentType)
                .orElseThrow(() -> new RuntimeException("No document found for id " + documentId));
    }

}
