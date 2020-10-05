package es.kairosds.reactive.practice.blog.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import es.kairosds.reactive.practice.blog.api.dto.CommentDTO;
import es.kairosds.reactive.practice.blog.api.dto.EntryDTO;
import es.kairosds.reactive.practice.blog.domain.entity.Comment;
import es.kairosds.reactive.practice.blog.domain.entity.Entry;
import es.kairosds.reactive.practice.blog.service.EntryService;
import es.kairosds.reactive.practice.blog.service.PatchService;
import es.kairosds.reactive.practice.blog.service.exception.PatchException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/entries/")
public class EntryController {

	
    private EntryService entryService;
    
    private PatchService patchService;

    @Autowired
    EntryController(EntryService entryService, PatchService patchService) {
        this.entryService = entryService;
        this.patchService = patchService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EntryDTO> createEntry(@RequestBody Mono<EntryDTO> entry) {
    	
    	return entry
    		.map(this::toEntry)
    		.flatMap(entryService::saveEntry)
    		.map(this::toEntryDTO);
    }

    @GetMapping("/")
    public Flux<EntryDTO> getEntrys() {
       return entryService.getAll().map(this::toEntryDTO);
    }

    @GetMapping("/{id}")
    public Mono<EntryDTO> getEntry(@PathVariable String id) {
    	return entryService.getEntry(id).map(this::toEntryDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<EntryDTO> deleteEntry(@PathVariable String id) {
        return entryService.deleteEntry(id).map(this::toEntryDTO);
    }
    
    @PatchMapping("/{id}")
    public Mono<EntryDTO> patchEntry(@PathVariable String id, @RequestBody JsonNode operations) {
        return entryService.getEntry(id)
        		.map(this::toEntryDTO)
        		.map(entry -> applyPatch(entry, operations))
        		.map(this::toEntry)
        		.flatMap(patchedEntry -> entryService.saveEntry(patchedEntry))
        		.map(this::toEntryDTO);
    }
    
    @PostMapping("/{entryId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EntryDTO> createEntryComment(@PathVariable String entryId, @RequestBody Mono<CommentDTO> commentDTO) {
    	
    	return entryService.getEntry(entryId)
    			.zipWith(commentDTO)
    			.map(EntryAndComment -> addCommentToEntry(EntryAndComment.getT1(), toComment(EntryAndComment.getT2())))
    			.flatMap(entryService::saveEntry)
    			.map(this::toEntryDTO);
    	
    }
    
    @DeleteMapping("/{entryId}/comment/{commentId}")
    public Mono<EntryDTO> deleteEntry(@PathVariable String entryId, @PathVariable String commentId) {
        return entryService.getEntry(entryId)
        		.map(entry -> removeCommentOfEntry(entry, commentId))
        		.flatMap(entryService::saveEntry)
        		.map(this::toEntryDTO);
    }
    
    private Entry toEntry(EntryDTO entryDTO) {
    	
    	List<Comment> comments = null;
    	
    	if(entryDTO.getComments() != null) {
    		comments = entryDTO.getComments().stream().map(commentDTO -> toComment(commentDTO)).collect(Collectors.toList());    		
    	}
    	
        return new Entry(entryDTO.getId(), entryDTO.getAuthorName(), entryDTO.getTitle(), entryDTO.getText(), comments);
    }
    
    private Comment toComment(CommentDTO commentDTO) {
    	
    	if(commentDTO.getDate() == null) {
    		commentDTO.setDate(new Date());
    	}
    	
        return new Comment(commentDTO.getAuthorName(), commentDTO.getText(), commentDTO.getDate());
    }

    private EntryDTO toEntryDTO(Entry entry) {
    	List<CommentDTO> commentsDTO = null;
    	
    	if(entry.getComments() != null) {
    		commentsDTO = entry.getComments().stream().map(comment -> toCommentDTO(comment)).collect(Collectors.toList());    		
    	}
    	
        return new EntryDTO(entry.getId(), entry.getAuthorName(), entry.getTitle(), entry.getText(), commentsDTO);
    }
    
    private CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getAuthorName(), comment.getText(), comment.getDate());
    }
    
    private Entry addCommentToEntry(Entry entry, Comment comment) {
    	if (entry != null) {
        	if (entry.getComments() != null) {
        		entry.getComments().add(comment);
        		return entry;
        	} else {
        		entry.setComments(new ArrayList<>());
        		entry.getComments().add(comment);
        		return entry;
        	}
    	} else {
    		return entry;
    	}
    	
    }
    
    private Entry removeCommentOfEntry(Entry entry, String commentId) {
    	if (entry != null) {
        	if (entry.getComments() != null) {
        		Map<String, Comment> commentsMap = entry.getComments().stream()
        			      .collect(Collectors.toMap(Comment::getId, comment -> comment));
        		
        		commentsMap.remove(commentId);
        		
        		entry.setComments(new ArrayList<>(commentsMap.values()));
        		
        		return entry;
        	} else {
        		
        		return entry;
        	}
    	} else {
    		return entry;
    	}
    	
    }
    
    
    /**
     * Applies the patch operation to the specific object
     *
     * @param entryDTO object to modify by the patch operation
     * @param objectPatch        patch operations to be made
     * @return EntryDTO object modified by the patch operation
     * @throws PatchException when is not possible apply the patch operations
     */
    private EntryDTO applyPatch(EntryDTO entryDTO, JsonNode objectPatch) {

      final EntryDTO resultDTO =
              patchService.applyPatch(entryDTO, EntryDTO.class, objectPatch);

      return resultDTO;
    }

}
