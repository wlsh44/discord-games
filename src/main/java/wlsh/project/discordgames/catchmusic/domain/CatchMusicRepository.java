package wlsh.project.discordgames.catchmusic.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatchMusicRepository extends JpaRepository<CatchMusic, Long> {
    Optional<CatchMusic> findByGuildId(String guildId);
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select c from CatchMusic c where c.guildId = :guildId")
    Optional<CatchMusic> findByGuildIdForLock(@Param("guildId") String guildId);
}
