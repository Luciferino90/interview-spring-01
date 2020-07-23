package it.demo.interview.interview.controller;

import it.demo.interview.interview.enumerator.DocumentType;
import it.demo.interview.interview.persistence.sql.entity.Document;
import it.demo.interview.interview.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(path = "/upload")
    public ResponseEntity<Document> uploadDocument(MultipartFile file,
                                                   String username, DocumentType documentType){
        log.info("Received upload request by " + username);
        Document uploadedDocument = documentService.uploadDocument(file, username, documentType);
        return new ResponseEntity<>(uploadedDocument, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Page<Document>> findByUsername(@PathVariable("username") String username, Pageable pageable){
        log.info("Received search by username request by " + username);
        pageable = Optional.ofNullable(pageable)
                .orElse(PageRequest.of(0, 25));
        Page<Document> pagedResult = documentService.findByUsername(username, pageable);
        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }

    @GetMapping("/documenttype/{documenttype}/id/{documentid}")
    public ResponseEntity<Document> findByIdAndDocumentType(@PathVariable("documentid") Integer documentId, @PathVariable("documenttype") DocumentType documentType){
        log.info("Received search by id with id " + documentId);
        Document result = documentService.findByDocumentIdAndDocumentType(documentType, documentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
