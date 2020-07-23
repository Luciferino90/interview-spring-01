package it.demo.interview.interview.persistence.sql.service;

import it.demo.interview.interview.enumerator.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DaoService<T> {

    T save(T toSave);
    Optional<T> findLatestDocumentIdForDocumentType(DocumentType documentType);
    Optional<T> findByDocumentIdAndDocumentType(Integer id, DocumentType documentType);
    Page<T> findByUsername(String username, Pageable pageable);

}
