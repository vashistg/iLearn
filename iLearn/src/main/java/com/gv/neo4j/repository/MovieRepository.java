package com.gv.neo4j.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.gv.neo4j.entities.Movie;

public interface MovieRepository extends GraphRepository<Movie>{

	 /* @Query("MATCH (m:Movie)<-[rating:RATED]-(user) WHERE id(m) = {movieId} RETURN rating")
	  Iterable<Rating> getRatings(Long movieId);*/

	  Collection<Movie> findByTitle(String name);
}
