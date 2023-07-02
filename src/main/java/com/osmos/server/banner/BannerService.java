package com.osmos.server.banner;

import com.osmos.server.banner.dto.BannerDto;
import com.osmos.server.banner.entities.Banner;
import com.osmos.server.banner.exceptions.BannerFieldDoesNotExist;
import com.osmos.server.minio.FileManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final FileManager fileManager;
    private final BannerRepo bannerRepo;
    private static final String serviceBucketName = "banner-image";

    public BannerDto create(BannerDto createBannerDto) {
        Banner banner = Banner.builder()
                .title(createBannerDto.getTitle())
                .content(createBannerDto.getContent())
                .imgName("")
                .detailsLink(createBannerDto.getDetailsLink())
                .build();
        System.out.println(banner.getTitle());
        var savedBanner = bannerRepo.save(banner);
        return new BannerDto(savedBanner.getTitle(), savedBanner.getContent(), savedBanner.getImgName(), savedBanner.getDetailsLink(), savedBanner.getId().toString());
    }

    public BannerDto last() {
        Banner banner = bannerRepo.findMostRecent();
        return new BannerDto(banner.getTitle(), banner.getContent(), banner.getImgName(), banner.getDetailsLink(), banner.getId().toString());
    }

    public void delete(String id) {
        Banner banner = bannerRepo.findById(UUID.fromString(id)).orElseThrow();
        fileManager.deleteFile(serviceBucketName, banner.getId().toString());
        bannerRepo.delete(banner);
    }

    public void delete(UUID uuid) {
        bannerRepo.deleteById(uuid);
    }

    public BannerDto update(String id,String fieldName, Object fieldValue) {
        Banner banner = bannerRepo.findById(UUID.fromString(id)).orElseThrow();
        Field field = ReflectionUtils.findField(Banner.class, fieldName);
        if (field == null) {
            throw new BannerFieldDoesNotExist("Such field does not exist");
        }
        field.setAccessible(true);
        ReflectionUtils.setField(field, banner, fieldValue);
        return new BannerDto(banner.getTitle(), banner.getContent(), banner.getImgName(), banner.getDetailsLink(), banner.getId().toString());
    }

    public String setBannerImage(MultipartFile file, String id) {
        Banner banner = bannerRepo.findById(UUID.fromString(id)).orElseThrow();
        fileManager.deleteFile(serviceBucketName, banner.getId().toString());
        var imageName = fileManager.uploadFile(file, serviceBucketName, banner.getId().toString());
        banner.setImgName(banner.getId().toString());
        bannerRepo.save(banner);
        return imageName;
    }

    public InputStream getBannerImage(String imgName) {
        return fileManager.downloadFile(serviceBucketName, imgName);
    }

    public Page<BannerDto> getBannerPage(int pageNumber, int pageSize) {
        if(pageSize > 50) pageSize = 50;
        if(pageSize<1) pageSize = 1;
        var page = bannerRepo.getBannersBy(PageRequest.of(pageNumber - 1, pageSize));
        System.out.println(page.getContent());
        return page;
    }

    public BannerDto getBannerById(String id) {
        return bannerRepo.findDTOById(UUID.fromString(id)).orElseThrow();
    }

}
