package com.elton.androidtouristapp.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/insertLocation")
    public Location insertLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }
@GetMapping("/getLocations")
    public List<Location> getAllLocations(){
    return locationRepository.findAll();
}
    @DeleteMapping("/deleteLocation/{id}")
    public void deleteLocation(@PathVariable int id) {

     locationRepository.deleteLocationById(id);
}
}
