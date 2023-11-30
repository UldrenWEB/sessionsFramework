package sessionFramework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "question"
)
public class Question {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "question_id_gen"
    )
    @SequenceGenerator(
        name = "question_id_gen",
        sequenceName = "answer_id_answer_seq",
        allocationSize = 1
    )
    @Column(
        name = "id_question",
        nullable = false
    )
    private Integer id;
    @Column(
        name = "de_question",
        nullable = false,
        length = 100
    )
    private String deQuestion;
    @Column(
        name = "res_question",
        length = 100
    )
    private String resQuestion;

    public Question() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getDeQuestion() {
        return this.deQuestion;
    }

    public String getResQuestion() {
        return this.resQuestion;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setDeQuestion(final String deQuestion) {
        this.deQuestion = deQuestion;
    }

    public void setResQuestion(final String resQuestion) {
        this.resQuestion = resQuestion;
    }
}