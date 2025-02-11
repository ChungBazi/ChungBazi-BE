package chungbazi.chungbazi_be.domain.document.entity;


import chungbazi.chungbazi_be.domain.cart.entity.Cart;
import chungbazi.chungbazi_be.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CalendarDocument extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_document_id")
    private Long id;

    @NotNull
    @Setter
    @Builder.Default
    private boolean isChecked = false;

    @NotNull
    private String document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;


    public CalendarDocument(String doc, Cart cart) {
        this.document = doc;
        this.cart = cart;
    }


    public void updateCheck(boolean check) {
        this.isChecked = check;
    }
}
