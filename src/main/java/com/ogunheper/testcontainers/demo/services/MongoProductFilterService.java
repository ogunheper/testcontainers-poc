package com.ogunheper.testcontainers.demo.services;

import com.google.common.io.CharStreams;
import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Builder
public class MongoProductFilterService implements ProductFilterService, InitializingBean {

    private final MongoTemplate mongoTemplate;

    private String dateAvailabilityPredicates;

    @Override
    public void afterPropertiesSet() {
        this.dateAvailabilityPredicates = this.getDateAvailabilityJs();
    }

    public List<Product> filter(
            Date availableAt
    ) {
        final Query query = this.anEmptyQuery();

        query.addCriteria(this.getDateCriteria(availableAt).get());

        return mongoTemplate.find(query, Product.class);
    }

    private Query anEmptyQuery() {
        return new Query();
    }

    private Optional<Criteria> getDateCriteria(Date availableAt) {
        if (Objects.isNull(availableAt)) {
            return Optional.empty();
        }

        final String js = this.getDateAvailabilityJs(availableAt);
        log.debug("Using function: {}", js);

        return Optional.of(Criteria.where("$where").is(js));
    }

    private String getDateAvailabilityJs(Date availableAt) {
        return this.dateAvailabilityPredicates.replace("__QUERY__", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(availableAt));
    }

    @SneakyThrows
    private String getDateAvailabilityJs() {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dateAvailabilityPredicates.js");
        return CharStreams.toString(new InputStreamReader(inputStream));
        // final URL url = Resources.getResource("dateAvailabilityPredicates.js");
        // final String js = Resources.toString(url, Charsets.UTF_8);
    }
}
