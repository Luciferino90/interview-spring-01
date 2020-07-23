package it.demo.interview.interview.config;

import it.demo.interview.interview.enumerator.DocumentType;
import it.demo.interview.interview.persistence.sql.entity.Document;
import it.demo.interview.interview.persistence.sql.service.DocumentDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.IntStream;

import static it.demo.interview.interview.enumerator.DocumentType.*;
import static it.demo.interview.interview.enumerator.DocumentType.REG;

@Profile("!test")
@Service
@RequiredArgsConstructor
public class PopulatorService {

    private final DocumentDaoService documentDaoService;

    @PostConstruct
    public Disposable randomize(){
        List<String> usernames = List.of("tyler_durden", "marla_singer", "angel_face", "robert_paulson", "richard_chesler");
        List<DocumentType> documentTypes = List.of(DOC, FE, PEC, SAN, LOT, REG);

        Random random = new Random();

        Function<List<String>, String> randomUser = genList -> genList.get(random.nextInt(genList.size()));
        Function<List<DocumentType>, DocumentType> randomDocumentType = genList -> genList.get(random.nextInt(genList.size()));

        return Flux.fromStream(IntStream.range(0, 100000).boxed())
                .parallel()
                .map(documentId -> {
                    Document document = Document.builder()
                            .id(UUID.randomUUID().toString())
                            .docFileName(UUID.randomUUID().toString())
                            .docHash(UUID.randomUUID().toString())
                            .documentId(documentId)
                            .uploadDate(new Date())
                            .username(randomUser.apply(usernames))
                            .documentType(randomDocumentType.apply(documentTypes))
                            .build();
                    return documentDaoService.save(document);
                })
                .subscribe();
    }

}
