package com.osmos.server.banner;

import com.osmos.server.banner.dto.BannerDto;
import com.osmos.server.banner.entities.Banner;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;
public interface BannerRepo extends JpaRepository<Banner, UUID> {

    @Query("select b from Banner b order by b.created desc")
    Banner findMostRecent();
    @Query("select new com.osmos.server.banner.dto.BannerDto(b.title, b.content, b.imgName, b.detailsLink, b.id) from Banner b")
    Page<BannerDto> getBannersBy(Pageable pageable);

    @Query("select new com.osmos.server.banner.dto.BannerDto(b.title, b.content, b.imgName, b.detailsLink, b.id) from Banner b where b.id = :id")
    Optional<BannerDto> findDTOById(@Param("id") UUID id);

}
