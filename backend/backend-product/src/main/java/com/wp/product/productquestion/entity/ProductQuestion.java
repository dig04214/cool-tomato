package com.wp.product.productquestion.entity;

import com.wp.product.product.entity.Product;
import com.wp.product.productquestion.dto.request.ProductQuestionUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_question_board")
public class ProductQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productQuestionBoardId;
    private Long writerId;

    @Transient
    private String writerNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String questionContent;
    private String answerContent;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime questionRegisterDate;
    private LocalDateTime answerRegisterDate;

    public void change(ProductQuestionUpdateRequest productQuestionRequest) {
        this.answerContent = productQuestionRequest.getAnswerContent();;
        this.answerRegisterDate = LocalDateTime.now();
    }
}
