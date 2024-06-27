package com.motivaa.control;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.motivaa.entity.User;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ESClient {
        RestClient restClient = RestClient
                .builder(HttpHost.create("http://localhost:9200"))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        public void saveUser(User user) {
                try {
                        IndexResponse response = esClient.index(i -> i
                                .index("user")
                                .id(user.getUuid())
                                .document(user));
                } catch (Exception e) {
                        log.error(e.getStackTrace());
                }
        }

                public List<User> searchUserByFirstName(String firstName) throws java.io.IOException {
                        SearchResponse<User> searchResponse = esClient.search(s->s
                                .index("user")
                                .query(q->q
                                        .match(t->t
                                                .field("firstName")
                                                .query(firstName))),User.class);
                        List<Hit<User>> hits = searchResponse.hits().hits();
                        return hits.stream().map(Hit::source).toList();
                }


}
