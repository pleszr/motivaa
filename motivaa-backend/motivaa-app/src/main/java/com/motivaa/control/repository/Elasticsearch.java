package com.motivaa.control.repository;

import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.motivaa.entity.Habit;
import com.motivaa.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class Elasticsearch {
        @Value("${elasticsearch.host}")
        String esHost;
        RestClient restClient;
        ElasticsearchTransport transport;
        co.elastic.clients.elasticsearch.ElasticsearchClient esClient;

        @PostConstruct
        public void init() {
                restClient = RestClient
                        .builder(HttpHost.create(esHost))
                        .build();
                transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
                esClient = new co.elastic.clients.elasticsearch.ElasticsearchClient(transport);
        }

        public void saveUser(User user) throws java.io.IOException{
                esClient.index(i -> i
                        .index("user")
                        .id(user.getUuid().toString())
                        .document(user));
        }

        public User findUserById(UUID uuid) throws java.io.IOException {
                GetResponse<User> getResponse = esClient.get(s -> s
                        .index("user")
                        .id(uuid.toString()),User.class);
                return getResponse.source();
        }

        public List<User> searchAllUsers() throws java.io.IOException {
                SearchResponse<User> searchResponse = esClient.search(s->s
                        .index("user"),User.class);
                List<Hit<User>> hits = searchResponse.hits().hits();
                log.info("ES search for all users: {}",hits);
                return hits.stream().map(Hit::source).toList();
        }

        public void saveHabit(Habit habit) throws java.io.IOException{
                esClient.index(i -> i
                        .index("habit")
                        .id(habit.getUuid().toString())
                        .document(habit));
        }

        public List<Habit> searchHabitByUserUuid(UUID userUuid) throws java.io.IOException {
                SearchResponse<Habit> searchResponse = esClient.search(s -> s
                        .index("habit")
                                .query(q -> q
                                                .match(t -> t
                                                                .field("userUuid")
                                                                .query(userUuid.toString())))
                        ,Habit.class);
                List<Hit<Habit>> hits = searchResponse.hits().hits();
                log.info("ES search for habits by userUuid: {}",hits);
                return hits.stream().map(Hit::source).toList();
        }




}
