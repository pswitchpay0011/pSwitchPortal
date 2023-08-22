package net.in.pSwitch.repository;

import net.in.pSwitch.model.LoginInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginTrackerRepository extends JpaRepository<LoginInfo, Long> {

    @Query("SELECT login FROM LoginInfo login where login.userId = ?1 order by login.modifiedDate DESC ")
    Page<LoginInfo> findAllByUserId(Integer userId, Pageable pageable);

//    @Query("SELECT login FROM LoginInfo login where login.userId = ?1 order by login.modifiedDate DESC")
//    LoginInfo findLastByUserId(Integer userId, Pageable pageable);

}
