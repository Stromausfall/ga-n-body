package net.matthiasauer.ganbody.common.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestRunResult {
    @Id
    private String id;
}
