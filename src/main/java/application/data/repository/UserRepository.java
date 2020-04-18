package application.data.repository;

import application.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    @Query("select u from dbo_userdefinition u where u.email = :email")
    Iterable<User> findByEmail(@Param("email") String email);

    @Transactional(readOnly = true)
    @Query("select u from dbo_userdefinition u where u.userName = :username")
    Iterable<User> findByUsername(@Param("username") String userName);

    @Query("select u from dbo_userdefinition u where (:fullName IS NULL OR UPPER(u.fullName) LIKE CONCAT('%',UPPER(:fullName),'%'))")
    Page<User> getListAllUserByFullNameContaining(Pageable pageable, @Param("fullName") String fullName);

}
