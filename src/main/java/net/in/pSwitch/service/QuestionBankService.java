package net.in.pSwitch.service;

import net.in.pSwitch.model.Category;
import net.in.pSwitch.model.QuestionAnswers;

import java.util.List;

public interface QuestionBankService {

    List<Category> fetchAllCategories();

    List<QuestionAnswers> fetchAllQuestionsAndAnswers();

    void saveCategory(Category category);

    void saveQuestion(QuestionAnswers question);

    void deleteQuestion(String questionId);

    void deleteCategory(String categoryId);

    Category fetchCategory(String categoryId);

    QuestionAnswers fetchQuestionAnswer(String questionId);
}
