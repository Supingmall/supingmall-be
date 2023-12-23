package com.github.shoppingmallproject.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewEntityJpaRepository extends JpaRepository<ReviewEntity, Integer> {
}
