package es.kairosds.reactive.practice.blog.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import es.kairosds.reactive.practice.blog.service.exception.PatchException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatchService {

	private final ObjectMapper mapper;

	  public PatchService(ObjectMapper mapper) {
	    this.mapper = mapper;
	  }

	  public <T> T applyPatch(T objectToPatch, Class<T> objectClass, JsonNode patch) {
	    try {
	      final JsonPatch jsonPatch = JsonPatch.fromJson(patch);
	      final JsonNode nodeSource = mapper.readValue(mapper.writeValueAsString(objectToPatch), JsonNode.class);

	      final JsonNode node = jsonPatch.apply(nodeSource);

	      return mapper.readValue(mapper.writeValueAsString(node), objectClass);
	      
	    } catch (NullPointerException | IOException | JsonPatchException ex) {
	      LOGGER.error("Error when apply patch exception ", ex);
	      throw new PatchException("Error when apply patch", ex);
	    }
	  }
	  
}
