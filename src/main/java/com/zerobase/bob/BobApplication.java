package com.zerobase.bob;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.put_index_template.IndexTemplateMapping;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.zerobase.bob.entity.Recipe;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;

import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.transport.Header;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class BobApplication {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        SpringApplication.run(BobApplication.class, args);

//          2. 조회하기 > 실패 : Error deserializing co.elastic.clients.elasticsearch.core.search.Hit
//        String indexName = "my_index";
//
//        SearchResponse<String> response = client.search(s -> s
//                .index(indexName)
//                .query(q -> q
//                        .match(t -> t
//                                .field("name")
//                                .query("Lee")
//                        )
//                ),
//                String.class
//            );
//
//        System.out.println(response);
//          3. document 추가하기
//        IndexRequest<Recipe> indexRequest = IndexRequest.of(builder -> builder
//        .index("recipe")
//        .document(recipe));
//
//        IndexResponse response = client.index(indexRequest);
//        System.out.println(response.result());
//
//        System.out.println("---------------- end !");
//
//        transport.close();
//        restClient.close();

    }
}

//        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http"))
//                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)).build();
//
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//
//        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(transport);
//
//        try {
//            CreateIndexResponse indexRequest = elasticsearchClient.indices().create(createIndexBuilder -> createIndexBuilder
//                    .index("my_index")
//                    .aliases("name", aliasBuilder -> aliasBuilder
//                            .isWriteIndex(true)
//                    )
//            );
//            boolean acknowledged = indexRequest.acknowledged();
//            System.out.println("Index document successfully! " + acknowledged);
//
//            DeleteIndexResponse deleteResponse = elasticsearchClient.indices().delete(createIndexBuilder -> createIndexBuilder
//                    .index("my_index")
//            );
//            System.out.println("Delete document successfully! \n" + deleteResponse.toString());
//
//            IndicesResponse indicesResponse = elasticsearchClient.cat().indices();
//            indicesResponse.valueBody().forEach(info -> System.out.println(info.health() + "\t"+  info.status() + "\t" + info.index() + "\t" + info.uuid() +"\t" + info.pri() + "\t" + info.rep()));
//
//
//            transport.close();
//            restClient.close();
//        } catch (IOException ioException) {
//            // Handle exceptions.
//        }
//
//    }
//
//}
