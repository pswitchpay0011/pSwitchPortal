package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {

}