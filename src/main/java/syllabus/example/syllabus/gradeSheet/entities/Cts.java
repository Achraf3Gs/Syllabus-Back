package syllabus.example.syllabus.gradeSheet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Dynamic CTS Class
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cts {

    @ElementCollection
    @CollectionTable(
            name = "cts_unsatisfactory_criteria",
            joinColumns = @JoinColumn(name = "maneuverItem_id")
    )
    @MapKeyColumn(name = "phase_name")
    @Column(name = "criteria")
    private Map<String, List<String>> unsatisfactoryCriteria = new HashMap<>();

    @ElementCollection
    @CollectionTable(
            name = "cts_fair_criteria",
            joinColumns = @JoinColumn(name = "maneuverItem_id")
    )
    @MapKeyColumn(name = "phase_name")
    @Column(name = "criteria")
    private Map<String, List<String>> fairCriteria = new HashMap<>();

    @ElementCollection
    @CollectionTable(
            name = "cts_good_criteria",
            joinColumns = @JoinColumn(name = "maneuverItem_id")
    )
    @MapKeyColumn(name = "phase_name")
    @Column(name = "criteria")
    private Map<String, List<String>> goodCriteria = new HashMap<>();

    @ElementCollection
    @CollectionTable(
            name = "cts_excellent_criteria",
            joinColumns = @JoinColumn(name = "maneuverItem_id")
    )
    @MapKeyColumn(name = "phase_name")
    @Column(name = "criteria")
    private Map<String, List<String>> excellentCriteria = new HashMap<>();

    @ElementCollection
    @CollectionTable(
            name = "cts_nograde_criteria",
            joinColumns = @JoinColumn(name = "maneuverItem_id")
    )
    @MapKeyColumn(name = "phase_name")
    @Column(name = "criteria")
    private Map<String, List<String>> noGradeCriteria = new HashMap<>();

    // Get criteria for specific phase and grade level
    public List<String> getCriteriaForPhaseAndLevel(String phase, String level) {
        if (phase == null || level == null) return null;

        Map<String, List<String>> criteriaMap = getCriteriaMapByLevel(level);
        return criteriaMap != null ? criteriaMap.get(phase) : null;
    }

    // Set criteria for specific phase and grade level
    public void setCriteriaForPhaseAndLevel(String phase, String level, List<String> criteria) {
        if (phase == null || level == null) return;

        Map<String, List<String>> criteriaMap = getCriteriaMapByLevel(level);
        if (criteriaMap != null) {
            criteriaMap.put(phase, criteria);
        }
    }

    // Get all criteria for a specific phase
    public CriteriaSet getCriteriaForPhase(String phase) {
        if (phase == null) return null;

        return CriteriaSet.builder()
                .unsatisfactory(unsatisfactoryCriteria.get(phase))
                .fair(fairCriteria.get(phase))
                .good(goodCriteria.get(phase))
                .excellent(excellentCriteria.get(phase))
                .noGrade(noGradeCriteria.get(phase))
                .build();
    }

    // Set all criteria for a specific phase
    public void setCriteriaForPhase(String phase, CriteriaSet criteriaSet) {
        if (phase == null || criteriaSet == null) return;

        if (criteriaSet.getUnsatisfactory() != null) {
            unsatisfactoryCriteria.put(phase, criteriaSet.getUnsatisfactory());
        }
        if (criteriaSet.getFair() != null) {
            fairCriteria.put(phase, criteriaSet.getFair());
        }
        if (criteriaSet.getGood() != null) {
            goodCriteria.put(phase, criteriaSet.getGood());
        }
        if (criteriaSet.getExcellent() != null) {
            excellentCriteria.put(phase, criteriaSet.getExcellent());
        }
        if (criteriaSet.getNoGrade() != null) {
            noGradeCriteria.put(phase, criteriaSet.getNoGrade());
        }
    }

    // Remove all criteria for a specific phase
    public void removePhase(String phase) {
        if (phase == null) return;

        unsatisfactoryCriteria.remove(phase);
        fairCriteria.remove(phase);
        goodCriteria.remove(phase);
        excellentCriteria.remove(phase);
        noGradeCriteria.remove(phase);
    }

    // Get all available phases
    public java.util.Set<String> getAvailablePhases() {
        java.util.Set<String> phases = new java.util.HashSet<>();
        phases.addAll(unsatisfactoryCriteria.keySet());
        phases.addAll(fairCriteria.keySet());
        phases.addAll(goodCriteria.keySet());
        phases.addAll(excellentCriteria.keySet());
        phases.addAll(noGradeCriteria.keySet());
        return phases;
    }

    // Check if phase exists
    public boolean hasPhase(String phase) {
        return unsatisfactoryCriteria.containsKey(phase) ||
                fairCriteria.containsKey(phase) ||
                goodCriteria.containsKey(phase) ||
                excellentCriteria.containsKey(phase) ||
                noGradeCriteria.containsKey(phase);
    }

    // Helper method to get the appropriate criteria map by level
    private Map<String, List<String>> getCriteriaMapByLevel(String level) {
        if (level == null) return null;

        switch (level.toLowerCase()) {
            case "unsatisfactory":
                return unsatisfactoryCriteria;
            case "fair":
                return fairCriteria;
            case "good":
                return goodCriteria;
            case "excellent":
                return excellentCriteria;
            case "nograde":
                return noGradeCriteria;
            default:
                return null;
        }
    }

    // Initialize maps if they are null
    @PostLoad
    @PostPersist
    private void initializeMaps() {
        if (unsatisfactoryCriteria == null) unsatisfactoryCriteria = new HashMap<>();
        if (fairCriteria == null) fairCriteria = new HashMap<>();
        if (goodCriteria == null) goodCriteria = new HashMap<>();
        if (excellentCriteria == null) excellentCriteria = new HashMap<>();
        if (noGradeCriteria == null) noGradeCriteria = new HashMap<>();
    }

    // Inner class to represent a complete set of criteria for a phase
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CriteriaSet {
        private List<String> unsatisfactory;
        private List<String> fair;
        private List<String> good;
        private List<String> excellent;
        private List<String> noGrade;
    }
}