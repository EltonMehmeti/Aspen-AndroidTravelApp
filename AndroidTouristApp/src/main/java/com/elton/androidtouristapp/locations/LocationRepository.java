package com.elton.androidtouristapp.locations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    // Find location by ID
    Location findById(int id);


    // Find locations by name (can be used for search functionality)
    List<Location> findByNameContainingIgnoreCase(String name);

    List<Location> findAll();



    void deleteLocationById(int id);




}
