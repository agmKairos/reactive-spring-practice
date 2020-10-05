package es.kairosds.reactive.practice.blog.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.kairosds.reactive.practice.blog.domain.entity.Entry;
import es.kairosds.reactive.practice.blog.domain.repository.EntryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EntryService {

    private EntryRepository entryRepository;

    EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Mono<Entry> saveEntry(Entry entry) {
        return entryRepository.save(entry);
    }

    public Flux<Entry> getEntrys(String authorName) {

        if (authorName == null) {
            return entryRepository.findAll();
        } else {
            return entryRepository.findByAuthorName(authorName);
        }
    }

    public Mono<Entry> getEntry(String id) {
        return entryRepository.findById(id)
        		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    public Mono<Entry> deleteEntry(String id) {
    	Mono<Entry> deletedEntry = entryRepository.findById(id);
    	
    	return deletedEntry.flatMap(du -> entryRepository.deleteById(id).then(Mono.just(du)));
    	
    }

	public Flux<Entry> getAll() {
		return entryRepository.findAll();
	}
	
}
