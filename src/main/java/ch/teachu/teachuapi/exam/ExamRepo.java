package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.exam.dto.*;
import ch.teachu.teachuapi.generated.tables.records.ExamRecord;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.teachuapi.util.DateUtil;

import org.jooq.Field;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;
import static ch.teachu.teachuapi.generated.tables.Grade.GRADE;
import static ch.teachu.teachuapi.generated.tables.SchoolClass.SCHOOL_CLASS;
import static ch.teachu.teachuapi.generated.tables.SchoolClassSemester.SCHOOL_CLASS_SEMESTER;
import static ch.teachu.teachuapi.generated.tables.SchoolClassSubject.SCHOOL_CLASS_SUBJECT;
import static ch.teachu.teachuapi.generated.tables.Semester.SEMESTER;
import static ch.teachu.teachuapi.generated.tables.Subject.SUBJECT;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.val;

@Repository
public class ExamRepo extends AbstractRepo {

    public List<SemesterExamsDTO> loadExams(UUID teacherId) {
        List<SemesterExamsDTO> exams = new ArrayList<>();

        ObjectIdHolder<SemesterExamsDTO> currentSemester = new ObjectIdHolder<>();
        ObjectIdHolder<SchoolClassExamsDTO> currentSchoolClass = new ObjectIdHolder<>();
        ObjectIdHolder<SubjectExamsDTO> currentSubject = new ObjectIdHolder<>();

        sql().select(EXAM.ID,
                        EXAM.NAME,
                        EXAM.DESCRIPTION,
                        EXAM.WEIGHT,
                        EXAM.DATE,
                        EXAM.VIEW_DATE,
                        SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID,
                        SCHOOL_CLASS_SUBJECT.SUBJECT_ID,
                        EXAM.SEMESTER_ID,
                        SEMESTER.NAME,
                        SCHOOL_CLASS.ID,
                        SCHOOL_CLASS.NAME,
                        SUBJECT.ID,
                        SUBJECT.NAME)
                .from(EXAM)
                .join(SCHOOL_CLASS_SUBJECT).on(EXAM.SCHOOL_CLASS_SUBJECT_ID.eq(SCHOOL_CLASS_SUBJECT.ID))
                .rightJoin(SUBJECT).on(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(SUBJECT.ID))
                .join(SCHOOL_CLASS).on(SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(SCHOOL_CLASS.ID))
                .join(SEMESTER).on(EXAM.SEMESTER_ID.eq(SEMESTER.ID))
                .where(SCHOOL_CLASS_SUBJECT.TEACHER_ID.eq(teacherId))
                .stream().forEach(record -> {
                    ExamDTO examDTO = new ExamDTO(record.value1(), record.value2(), record.value3(), record.value4(),
                            DateUtil.toDate(record.value5()), DateUtil.toDate(record.value6()));

                    if (currentSemester.isNewId(record.value9())) {
                        SemesterExamsDTO semesterExamsDTO = new SemesterExamsDTO(record.value10(), new ArrayList<>());
                        exams.add(semesterExamsDTO);
                        currentSemester.newObject(semesterExamsDTO, record.value9());
                        currentSchoolClass.forceNew();
                        currentSubject.forceNew();
                    }

                    if (currentSchoolClass.isNewId(record.value11())) {
                        SchoolClassExamsDTO schoolClassDTO = new SchoolClassExamsDTO(record.value12(), new ArrayList<>());
                        currentSchoolClass.newObject(schoolClassDTO, record.value11());
                        currentSemester.get().getSchoolClasses().add(currentSchoolClass.get());
                    }

                    if (currentSubject.isNewId(record.value13())) {
                        SubjectExamsDTO subjectDTO = new SubjectExamsDTO(record.value14(), new ArrayList<>());
                        currentSubject.newObject(subjectDTO, record.value13());
                        currentSchoolClass.get().getSubjects().add(currentSubject.get());
                    }

                    currentSubject.get().getExams().add(examDTO);
                });

        return exams;
    }

    public void createExam(CreateExamRequest request) {
        ExamRecord examRecord = sql().newRecord(EXAM);
        examRecord.setId(UUID.randomUUID());
        examRecord.setName(request.getName());
        examRecord.setDescription(request.getDescription());
        examRecord.setWeight(request.getWeight());
        examRecord.setDate(DateUtil.toLocalDate(request.getDate()));
        examRecord.setViewDate(DateUtil.toLocalDate(request.getViewDate()));
        examRecord.setSemesterId(request.getSemesterId());
        examRecord.setSchoolClassSubjectId(computeSchoolClassSubjectId(request.getSubjectId(), request.getSchoolClassId()));
        examRecord.store();
    }

    private UUID computeSchoolClassSubjectId(UUID subjectId, UUID schoolClassId) {
        return sql().select(SCHOOL_CLASS_SUBJECT.ID)
                .from(SCHOOL_CLASS_SUBJECT)
                .where(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(subjectId),
                        SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(schoolClassId))
                .fetchOneInto(UUID.class);
    }

    public boolean hasSchoolClassSubjectAndSemester(UUID schoolClassId, UUID subjectId, UUID semesterId) {
        return sql().fetchExists(sql().select().from(SCHOOL_CLASS)
                .join(SCHOOL_CLASS_SEMESTER).on(SCHOOL_CLASS.ID.eq(SCHOOL_CLASS_SEMESTER.SCHOOL_CLASS_ID))
                .join(SCHOOL_CLASS_SUBJECT).on(SCHOOL_CLASS.ID.eq(SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID))
                .where(SCHOOL_CLASS.ID.eq(schoolClassId), SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(subjectId), SCHOOL_CLASS_SEMESTER.SEMESTER_ID.eq(semesterId)));
    }

    public boolean examUsed(UUID examId) {
        return sql().fetchExists(sql().selectFrom(GRADE).where(GRADE.EXAM_ID.eq(examId)));
    }

    public void changeExam(ChangeExamRequest request) {
        int result = sql().update(EXAM)
                .set(EXAM.NAME, request.getName())
                .set(EXAM.DESCRIPTION, request.getDescription())
                .set(EXAM.WEIGHT, request.getWeight())
                .set(EXAM.DATE, DateUtil.toLocalDate(request.getDate()))
                .set(EXAM.VIEW_DATE, DateUtil.toLocalDate(request.getViewDate()))
                .where(EXAM.ID.eq(request.getId()))
                .execute();

        if (result == 0) {
            throw new NotFoundException("Exam");
        }
    }
}
