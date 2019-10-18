package com.ogunheper.testcontainers.demo.services;

import com.ogunheper.testcontainers.demo.model.ContinuousProductSalesDates;
import com.ogunheper.testcontainers.demo.model.DiscreteProductSalesDates;
import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import com.ogunheper.testcontainers.demo.persistence.repository.ProductRepository;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = MongoProductFilterServiceIT.ContextInitializer.class)
public class MongoProductFilterServiceIT {

    private static final int MONGODB_PORT = 27017;
    private static final Duration MONGODB_STARTUP_TIMEOUT = Duration.ofSeconds(15);

    static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            final TestPropertyValues testPropertyValues = TestPropertyValues.of(
                    getPropertyString("spring.data.mongodb.host", mongo.getContainerIpAddress()),
                    getPropertyString("spring.data.mongodb.port", mongo.getMappedPort(MONGODB_PORT))
            );
            testPropertyValues.applyTo(applicationContext);
        }
    }

    @ClassRule
    public static GenericContainer mongo = new GenericContainer<>("mongo:4.0.13")
            .withExposedPorts(MONGODB_PORT)
            .waitingFor(Wait.forLogMessage(".*waiting for connections on port 27017.*", 1))
            .withStartupTimeout(MONGODB_STARTUP_TIMEOUT);

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MongoProductFilterService productFilterService;
    //private LocalProductFilterService productFilterService;

    @Before
    public void setUp() throws Exception {
        productRepository.deleteAll();

        productRepository.save(
                Product.builder()
                        .name("discrete_1")
                        .productSalesDates(
                                DiscreteProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2019-01-01T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("discrete_2")
                        .productSalesDates(
                                DiscreteProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2019-02-01T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("discrete_3")
                        .productSalesDates(
                                DiscreteProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2019-03-01T00:00:00Z"),
                                                dateFormat.parse("2019-03-15T00:00:00Z"),
                                                dateFormat.parse("2019-03-30T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("discrete_4_1")
                        .productSalesDates(
                                DiscreteProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2019-04-01T00:00:00Z"),
                                                dateFormat.parse("2019-04-15T00:00:00Z"),
                                                dateFormat.parse("2019-04-30T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("discrete_4_2")
                        .productSalesDates(
                                DiscreteProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2019-04-01T00:00:00Z"),
                                                dateFormat.parse("2019-04-15T00:00:00Z"),
                                                dateFormat.parse("2019-04-30T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("continuous_1")
                        .productSalesDates(
                                ContinuousProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2020-01-01T00:00:00Z"),
                                                dateFormat.parse("2020-03-31T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("continuous_2")
                        .productSalesDates(
                                ContinuousProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2020-04-01T00:00:00Z"),
                                                dateFormat.parse("2020-06-30T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("continuous_3_1")
                        .productSalesDates(
                                ContinuousProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2020-07-01T00:00:00Z"),
                                                dateFormat.parse("2020-12-31T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("continuous_3_2")
                        .productSalesDates(
                                ContinuousProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2020-08-01T00:00:00Z"),
                                                dateFormat.parse("2020-08-31T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("both_1")
                        .productSalesDates(
                                DiscreteProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2021-09-01T00:00:00Z"),
                                                dateFormat.parse("2021-09-15T00:00:00Z"),
                                                dateFormat.parse("2021-09-30T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("both_2")
                        .productSalesDates(
                                ContinuousProductSalesDates.builder()
                                        .dates(ImmutableList.of(
                                                dateFormat.parse("2021-09-01T00:00:00Z"),
                                                dateFormat.parse("2021-09-30T00:00:00Z")
                                        ))
                                        .build()
                        )
                        .build()
        );
    }

    @Test
    @SneakyThrows
    public void shouldNotFilterMissing() {
        final Date date = dateFormat.parse("2019-12-31T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should not contain any item.", products.size(), Matchers.is(0));
    }

    @Test
    @SneakyThrows
    public void shouldFilterDiscreteCorrectly_01() {
        final Date date = dateFormat.parse("2019-01-01T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(1));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("discrete_1"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterDiscreteCorrectly_02() {
        final Date date = dateFormat.parse("2019-02-01T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(1));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("discrete_2"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterDiscreteCorrectly_03() {
        final Date date = dateFormat.parse("2019-03-01T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(1));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("discrete_3"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterDiscreteCorrectly_04() {
        final Date date = dateFormat.parse("2019-04-15T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(2));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("discrete_4_1"));
        Assert.assertThat("Product name should be correct.", products.get(1).getName(), Matchers.is("discrete_4_2"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterContinuousCorrectly_01() {
        final Date date = dateFormat.parse("2020-02-01T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(1));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("continuous_1"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterContinuousCorrectly_02() {
        final Date date = dateFormat.parse("2020-05-01T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(1));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("continuous_2"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterContinuousCorrectly_03() {
        final Date date = dateFormat.parse("2020-08-15T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(2));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("continuous_3_1"));
        Assert.assertThat("Product name should be correct.", products.get(1).getName(), Matchers.is("continuous_3_2"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterBothCorrectly_01() {
        final Date date = dateFormat.parse("2021-09-15T00:00:00Z");
        final List<Product> products = productFilterService.filter(date);

        Assert.assertThat("Filtered products should contain one item.", products.size(), Matchers.is(2));
        Assert.assertThat("Product name should be correct.", products.get(0).getName(), Matchers.is("both_1"));
        Assert.assertThat("Product name should be correct.", products.get(1).getName(), Matchers.is("both_2"));
    }

    private static String getPropertyString(String name, Object value) {
        return name + "=" + value;
    }
}
