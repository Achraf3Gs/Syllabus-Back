package syllabus.example.syllabus.gradeSheet.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.gradeSheet.entities.Phase;
import syllabus.example.syllabus.gradeSheet.entities.SortiesTypes;
import syllabus.example.syllabus.gradeSheet.repository.PhaseRepository;
import syllabus.example.syllabus.gradeSheet.repository.SortiesTypesRepository;

import java.util.List;



@RestController
@RequestMapping({"/sortiesTypes"})
@CrossOrigin(origins="*")
public class SortiesTypesController {

    @Autowired
    private SortiesTypesRepository sortiesTypesRepository;

    @GetMapping("/list")
    public List<SortiesTypes> getAllSortiesTypes() {

        return (List<SortiesTypes>) sortiesTypesRepository.findAll();
    }


    @PostMapping("/add")
    public SortiesTypes createSortiesTypes(@Valid @RequestBody SortiesTypes sortiesTypes) {
        return this.sortiesTypesRepository.save(sortiesTypes);
    }

    @DeleteMapping("/delete/{sortiesTypesId}")
    public ResponseEntity<?> SortiesTypes(@PathVariable (value = "sortiesTypesId") Long sortiesTypesId) {
        return sortiesTypesRepository.findById(sortiesTypesId).map(sortiesTypes -> {
            sortiesTypesRepository.delete(sortiesTypes);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("SortiesTypes not found with id " + sortiesTypesId));
    }
    @PutMapping("/update/{sortiesTypesId}")
    public  SortiesTypes updateSortiesTypes(@PathVariable Long  sortiesTypesId, @Valid  @RequestBody  SortiesTypes  sortiesTypesRequest) {
        return  sortiesTypesRepository.findById( sortiesTypesId).map( sortiesTypes -> {
            sortiesTypes.setSortiesTypeName( sortiesTypesRequest.getSortiesTypeName());

            return this. sortiesTypesRepository.save( sortiesTypes);
        }).orElseThrow(() -> new IllegalArgumentException(" sortiesTypesId " +  sortiesTypesId + " not found"));
    }
}

