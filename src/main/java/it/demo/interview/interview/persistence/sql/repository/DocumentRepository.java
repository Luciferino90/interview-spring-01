package it.demo.interview.interview.persistence.sql.repository;

import it.demo.interview.interview.enumerator.DocumentType;
import it.demo.interview.interview.persistence.sql.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, String> {

    @Query("select d from Document d where d.documentType = :documentType order by d.uploadDate desc")
    Page<Document> findLatestIdByDocumentType(DocumentType documentType, Pageable page);

    Page<Document> findByUsername(String username, Pageable pageable);

    Optional<Document> findByDocumentIdAndDocumentType(Integer id, DocumentType documentType);

}
