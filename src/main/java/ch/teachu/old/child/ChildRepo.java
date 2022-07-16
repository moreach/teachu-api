//package ch.teachu.old.child;
//
//import ch.teachu.old.child.dto.OutlineChildResponse;
//import ch.teachu.old.parent.AbstractRepo;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//import static ch.teachu.teachuapi.generated.tables.ParentStudent.PARENT_STUDENT;
//import static ch.teachu.teachuapi.generated.tables.User.USER;
//
//@Repository
//public class ChildRepo extends AbstractRepo {
//
//    public List<OutlineChildResponse> findChildren(UUID parentId) {
//        return sql().select(USER.ID.as(OutlineChildResponse.ID),
//                USER.FIRST_NAME.as(OutlineChildResponse.FIRST_NAME),
//                USER.LAST_NAME.as(OutlineChildResponse.LAST_NAME))
//                .from(USER)
//                .join(PARENT_STUDENT)
//                .on(USER.ID.eq(PARENT_STUDENT.STUDENT_ID))
//                .where(PARENT_STUDENT.PARENT_ID.eq(parentId))
//                .fetchInto(OutlineChildResponse.class);
//    }
//}
