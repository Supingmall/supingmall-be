package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJpa;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ProductJpa productJpa;
    private final UserJpa userJpa;
    private final PlatformTransactionManager transactionManager;
    private final EntityManager em;

//    @Transactional(transactionManager = "tm")
    public void setupOldProduct() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        try{
        List<ProductEntity> productEntities = productJpa.findAll();
        List<ProductEntity> oldProducts = productEntities.stream()
                .filter(pe->pe.getProductStatus().equals("판매중"))
                .filter(pe->pe.getFinishAt()!=null)
                .filter(pe->pe.getFinishAt().isBefore(LocalDateTime.now()))
                .toList();
        for(ProductEntity old: oldProducts){
            old.setProductStatus("판매종료");
            em.merge(old);
            em.flush();
        }
        transactionManager.commit(transactionStatus);
        }catch (Exception e){
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
        }
    }

    //탈퇴한지 7일 이상된 계정 정보 자동 삭제 (하루에 한번씩 로직 실행됨)
    @Transactional(transactionManager = "tm")
    public void cleanupOldWithdrawnUser() {
        List<UserEntity> userEntities = userJpa.findAll();
        List<UserEntity> oldWithdrawnUser = userEntities.stream().filter(ue->ue.getDeletionDate()!=null)
                .filter(ue-> ChronoUnit.DAYS.between(ue.getDeletionDate(), LocalDateTime.now())>=7).toList();
        if (oldWithdrawnUser.isEmpty()) return;
        for(UserEntity old:oldWithdrawnUser){
            List<ReviewEntity> reviewEntities = old.getReviewEntities();
            for(ReviewEntity r:reviewEntities){
                r.setUserEntity(null);
            }
        }
        userJpa.deleteAll(oldWithdrawnUser);
    }
}
