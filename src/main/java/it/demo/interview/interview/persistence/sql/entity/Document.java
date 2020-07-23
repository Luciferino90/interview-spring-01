package it.demo.interview.interview.persistence.sql.entity;

import it.demo.interview.interview.enumerator.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"document_type", "document_id"})})
public class Document {

    private static final long serialVersionUID = 20200502_1423L;

    @Id
    private String id;

    @Column(name = "document_id", unique = true)
    private Integer documentId;
    private String username;
    @Column(name = "doc_filename")
    private String docFileName;
    @Column(name = "doc_hash")
    private String docHash;
    @Column(name = "document_type")
    private DocumentType documentType;
    @Column(name = "upload_date")
    private Date uploadDate;

}
