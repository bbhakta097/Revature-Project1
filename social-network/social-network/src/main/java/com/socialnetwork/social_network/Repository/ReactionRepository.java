package com.socialnetwork.social_network.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialnetwork.social_network.Model.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByPostIdAndUserId(Long postId, Long userId);
    List<Reaction> findAllByPostId(Long postId);
    long countByPostId(Long postId);
    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.post.id = :postId AND r.reactionType = :reactionType")
    long countByPostIdAndReactionType(@Param("postId") Long postId, @Param("reactionType") String reactionType);

}
