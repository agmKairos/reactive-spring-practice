package es.kairosds.reactive.practice.blog.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import es.kairosds.reactive.practice.blog.domain.entity.Entry;
import reactor.core.publisher.Flux;

public interface EntryRepository extends ReactiveMongoRepository<Entry, String> {
	Flux<Entry> findByAuthorName(String authorName);
}