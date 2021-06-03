package com.epam.jmp.elasticsearch.client;

import com.epam.jmp.elasticsearch.dto.Event;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Component
@AllArgsConstructor
@Slf4j
public class ElasticJavaApiDataProvider implements ElasticClient {

    private static final String EVENT_INDEX = "eventsv1";
    private RestHighLevelClient client;


    @SneakyThrows
    @Override
    public void createNewIndex() {
        CreateIndexRequest request = new CreateIndexRequest(EVENT_INDEX);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public void applyNewMapping() {
        PutMappingRequest request = new PutMappingRequest(EVENT_INDEX);
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("title");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("eventType");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
                builder.startObject("dateTime");
                {
                    builder.field("type", "date");
                }
                builder.endObject();
                builder.startObject("place");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("description");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("subTopics");
                {
                    builder.field("type", "nested");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.source(builder);
        log.info(request.source().utf8ToString());

        client.indices().putMapping(request, RequestOptions.DEFAULT);
    }

    @Override
    public void populateIndex() {
        List<Event> events = new LinkedList<>();
        Map<String, String> subTopics = new LinkedHashMap<>();
        subTopics.put("subTopic1", "SpringBoot");
        events.add(Event.builder().title("Epam conference").eventType("tech-talk").dateTime(now().minusDays(20))
                .place("Online").description("Spring tech-talk").subTopics(subTopics).build());

        Map<String, String> subTopics1 = new LinkedHashMap<>();
        subTopics1.put("subTopic1", "Java 16");
        subTopics1.put("subTopic2", "Kotlin");
        events.add(Event.builder().title("Community-Z event").eventType("workshop").dateTime(now().plusDays(20))
                .place("Teams").description("Java workshop").subTopics(subTopics1).build());

        Map<String, String> subTopics2 = new LinkedHashMap<>();
        subTopics2.put("subTopic1", "Selenium");
        subTopics2.put("subTopic2", "BDD Testing");
        subTopics2.put("subTopic3", "JBehave");
        events.add(Event.builder().title("Test automation").eventType("tech-talk").dateTime(now().plusDays(30))
                .place("Zoom").description("Java TA sandbox").subTopics(subTopics2).build());

        Map<String, String> subTopics3 = new LinkedHashMap<>();
        subTopics3.put("subTopic1", "Patterns overview");
        subTopics3.put("subTopic2", "Message-Driven pattern");
        subTopics3.put("subTopic3", "API Gateway");
        subTopics3.put("subTopic4", "Service Discovery");
        events.add(Event.builder().title("Microservices").eventType("workshop").dateTime(now().plusDays(30))
                .place("Skype").description("Microservices architecture with Spring").subTopics(subTopics3).build());

        events.forEach(this::saveEvent);
    }

    @SneakyThrows
    private void saveEvent(Event event) {
        IndexRequest request = new IndexRequest(EVENT_INDEX);
        request.source(
                "title", event.getTitle(),
                "eventType", event.getEventType(),
                "dateTime", event.getDateTime(),
                "place", event.getPlace(),
                "subTopics", event.getSubTopics()
        );
        client.index(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public List<Event> findAllEvents() {
        SearchRequest request = new SearchRequest(EVENT_INDEX);
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return mapResponseEventList(response);
    }

    private List<Event> mapResponseEventList(SearchResponse response) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'")
                .optionalStart()
                .appendPattern(".")
                .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
                .optionalEnd().toFormatter();
        return Arrays.stream(response.getHits().getHits()).map(hit -> {
            Map<String, Object> map = hit.getSourceAsMap();
            return Event.builder().title((String) map.get("title"))
                    .eventType((String) map.get("eventType"))
                    .dateTime(LocalDateTime.parse((String) map.get("dateTime"), dateTimeFormatter))
                    .place((String) map.get("place"))
                    .description((String) map.get("place"))
                    .subTopics((Map<String, String>) map.get("subTopics")).build();
        }).collect(Collectors.toList());
    }


    @SneakyThrows
    @Override
    public List<Event> findEventsByType(String type) {
        SearchRequest request = new SearchRequest(EVENT_INDEX);
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchQuery("eventType", type)));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return mapResponseEventList(response);
    }

    @SneakyThrows
    @Override
    public List<Event> findEventsByTitle(String title) {
        SearchRequest request = new SearchRequest(EVENT_INDEX);
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchQuery("title", title)));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return mapResponseEventList(response);
    }

    @SneakyThrows
    @Override
    public List<Event> findEventsByTitleAndDateGtThan(String title, LocalDateTime dateTime) {
        SearchRequest request = new SearchRequest(EVENT_INDEX);

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", title);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("dateTime").gt(dateTime);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchQueryBuilder).must(rangeQueryBuilder);

        request.source(new SearchSourceBuilder().query(boolQueryBuilder));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return mapResponseEventList(response);
    }
}
