package com.gv.neo4j.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Movie {

  @GraphId Long id;

  String title;

  @Relationship(type="DIRECTED", direction = "INCOMING")
  Person director;

  @Relationship(type="ACTED_IN", direction = "INCOMING")
  Set<Person> actors = new HashSet<Person>();
}