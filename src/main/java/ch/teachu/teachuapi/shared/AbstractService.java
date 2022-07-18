package ch.teachu.teachuapi.shared;

import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.errorhandlig.NotFoundException;
import ch.teachu.teachuapi.shared.errorhandlig.UnauthorizedException;
import ch.teachu.teachuapi.sql.SQL;

public abstract class AbstractService {

    protected SharedDAO authMinRole(String access, UserRole minRole) {
        SharedDAO sharedDAO = loadUser(access);
        UserRole userRole = UserRole.valueOf(sharedDAO.getRole());

        if (userRole.getLevel() < minRole.getLevel()) {
            throw new UnauthorizedException("Not permitted to perform this action. Required at least role: " + minRole + ". Your role: " + userRole);
        }

        return sharedDAO;
    }

    protected SharedDAO authExactRole(String access, UserRole requiredRole) {
        SharedDAO sharedDAO = loadUser(access);
        UserRole userRole = UserRole.valueOf(sharedDAO.getRole());

        if (userRole != requiredRole) {
            throw new UnauthorizedException("Not permitted to perform this action. Required role: " + requiredRole + ". Your role: " + userRole);
        }

        return sharedDAO;
    }

    protected SharedDAO authStudentId(String access, String studentId) {
        if (studentId == null) {
            return authExactRole(access, UserRole.student);
        }

        SharedDAO sharedDAO = authExactRole(access, UserRole.parent);
        sharedDAO.setStudentId(studentId);

        SQL.select("" +
                        "SELECT EXISTS(SELECT 1 " +
                        "              FROM   parent_student ps " +
                        "              INNER JOIN user u ON ps.student_id = u.id " +
                        "              WHERE  ps.parent_id = UUID_TO_BIN(-userId) " +
                        "              AND    ps.student_id = UUID_TO_BIN(-studentId) " +
                        "              AND    u.active IS TRUE )" +
                        "INTO   :isParent",
                sharedDAO,
                sharedDAO);

        if (!sharedDAO.getIsParent()) {
            throw new NotFoundException("Student with this parent");
        }

        sharedDAO.setUserId(sharedDAO.getStudentId());

        return sharedDAO;
    }

    private SharedDAO loadUser(String access) {
        SharedDAO sharedDAO = new SharedDAO();
        sharedDAO.setAccess(access);

        SQL.select("" +
                        "SELECT	BIN_TO_UUID(u.id), " +
                        "       u.role " +
                        "FROM 	user u " +
                        "JOIN 	token t ON u.id = t.user_id " +
                        "WHERE 	t.access = -access " +
                        "AND 	t.access_expires > NOW() " +
                        "AND 	u.active IS TRUE " +
                        "INTO 	:userId, " +
                        "       :role",
                sharedDAO,
                sharedDAO);

        return sharedDAO;
    }
}
