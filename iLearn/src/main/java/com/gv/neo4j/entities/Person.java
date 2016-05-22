package com.gv.neo4j.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Person {
   @GraphId Long id;
   
   private String name;
   private String born;
}
