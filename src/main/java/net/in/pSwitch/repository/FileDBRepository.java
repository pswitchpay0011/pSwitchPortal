package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}