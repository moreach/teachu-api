//package ch.teachu.old.grade;
//
//import ch.teachu.old.enums.UserRole;
//import ch.teachu.old.grade.dto.*;
//import ch.teachu.old.parent.AbstractService;
//import ch.teachu.teachuapi.grade.dto.*;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class GradeService extends AbstractService {
//
//    private final GradeRepo gradeRepo;
//
//    public ResponseEntity<SemestersGradesResponse> loadGrades(String auth, UUID studentId) {
//        authMinRole(auth, UserRole.PARENT);
//        List<SemesterGradesResponse> semesters = gradeRepo.loadGrades(studentId);
//        calculateAverages(semesters);
//        return ResponseEntity.ok(new SemestersGradesResponse(semesters));
//    }
//
//    public void calculateAverages(List<SemesterGradesResponse> semesters) {
//        for (SemesterGradesResponse semester : semesters) {
//            for (SchoolClassGradesResponse schoolClass : semester.getSchoolClasses()) {
//                calculateAverages(schoolClass);
//            }
//        }
//    }
//
//    private void calculateAverages(SchoolClassGradesResponse schoolClass) {
//        double sum = 0;
//        double weight = 0;
//        for (SubjectGradesResponse subject : schoolClass.getSubjects()) {
//            calculateAverages(subject);
//            sum += subject.getAverageMark() * subject.getWeight();
//            weight += subject.getWeight();
//        }
//        schoolClass.setAverageMark(round2DecimalPlaces(sum / weight));
//    }
//
//    private void calculateAverages(SubjectGradesResponse subject) {
//        double sum = 0;
//        double weight = 0;
//        for (GradeResponse exam : subject.getGrades()) {
//            sum += exam.getMark() * exam.getWeight();
//            weight += exam.getWeight();
//        }
//        subject.setAverageMark(round2DecimalPlaces(sum / weight));
//    }
//
//    private double round2DecimalPlaces(double d) {
//        return Math.round(d * 100.0) / 100.0;
//    }
//}
