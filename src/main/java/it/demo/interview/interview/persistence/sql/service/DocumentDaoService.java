package it.demo.interview.interview.persistence.sql.service;

import it.demo.interview.interview.enumerator.DocumentType;
import it.demo.interview.interview.persistence.sql.entity.Document;
import it.demo.interview.interview.persistence.sql.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentDaoService implements DaoService<Document> {

    private final DocumentRepository documentRepository;

    @Override
    public Document save(Document toSave) {
        return documentRepository.save(toSave);
    }

    @Override
    public Optional<Document> findLatestDocumentIdForDocumentType(DocumentType documentType) {
        return documentRepository.findLatestIdByDocumentType(documentType, PageRequest.of(0, 1)).stream().findFirst();
    }

    @Override
    public Optional<Document> findByDocumentIdAndDocumentType(Integer id, DocumentType documentType) {
        return documentRepository.findByDocumentIdAndDocumentType(id, documentType);
    }

    @Override
    public Page<Document> findByUsername(String username, Pageable pageable) {
        return documentRepository.findByUsername(username, pageable);
    }

}
