package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.QuestionAnswers;

import java.util.List;

@Repository
public interface QuestionAnswersRepository extends JpaRepository<QuestionAnswers, Integer>, JpaSpecificationExecutor<QuestionAnswers> {
    List<QuestionAnswers> findAllByOrderByQuestionId();
}