package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.exam.dto.ChangeExamRequest;
import ch.teachu.teachuapi.exam.dto.CreateExamRequest;
import ch.teachu.teachuapi.exam.dto.ExamDTO;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.teachuapi.util.DateUtil;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;
import static ch.teachu.teachuapi.generated.tables.Grade.GRADE;
import static ch.teachu.teachuapi.generated.tables.SchoolClassSubject.SCHOOL_CLASS_SUBJECT;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.val;

@Repository
public class ExamRepo extends AbstractRepo {

    public List<ExamDTO> loadExams(UUID teacherId) {
        return sql().select(EXAM.ID,
                        EXAM.NAME,
                        EXAM.DESCRIPTION,
                        EXAM.WEIGHT,
                        EXAM.DATE,
                        EXAM.VIEW_DATE,
                        SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID,
                        SCHOOL_CLASS_SUBJECT.SUBJECT_ID,
                        EXAM.SEMESTER_ID)
                .from(EXAM)
                .join(SCHOOL_CLASS_SUBJECT).on(SCHOOL_CLASS_SUBJECT.ID.eq(EXAM.SCHOOL_CLASS_SUBJECT_ID))
                .where(SCHOOL_CLASS_SUBJECT.TEACHER_ID.eq(teacherId))
                .stream().map(record -> new ExamDTO(record.value1(), record.value2(), record.value3(), record.value4(),
                        DateUtil.toDate(record.value5()), DateUtil.toDate(record.value6()), record.value7(), record.value8(), record.value9()))
                .collect(Collectors.toList());
    }

    public void createExam(CreateExamRequest request) {
        sql().insertInto(EXAM,
                EXAM.NAME,
                EXAM.DESCRIPTION,
                EXAM.WEIGHT,
                EXAM.DATE,
                EXAM.VIEW_DATE,
                EXAM.SCHOOL_CLASS_SUBJECT_ID,
                EXAM.SEMESTER_ID)
                .values(val(request.getName()),
                        val(request.getDescription()),
                        val(request.getWeight()),
                        val(DateUtil.toLocalDate(request.getDate())),
                        val(DateUtil.toLocalDate(request.getViewDate())),
                        schoolClassSubjectField(request.getSubjectId(), request.getSchoolClassId()),
                        val(request.getSemesterId()))
                .execute();
    }

    public boolean examUsed(UUID examId) {
        return sql().select(count()).from(GRADE).where(GRADE.EXAM_ID.eq(examId)).fetchSingle().value1() != 0;
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

    private Field<UUID> schoolClassSubjectField(UUID subjectId, UUID schoolClassId) {
        return sql().select(SCHOOL_CLASS_SUBJECT.ID)
                .from(SCHOOL_CLASS_SUBJECT)
                .where(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(subjectId),
                        SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(schoolClassId))
                .asField();
    }
}
