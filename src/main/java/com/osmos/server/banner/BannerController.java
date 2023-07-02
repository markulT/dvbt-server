package com.osmos.server.banner;

import com.osmos.server.banner.dto.BannerDto;
import com.osmos.server.banner.dto.UpdateBannerDto;
import com.osmos.server.responseDto.GetPage;
import com.osmos.server.responseDto.GetSingle;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetSingle<BannerDto>> createBanner(@RequestBody() BannerDto bannerDto) {
        return ResponseEntity.ok(GetSingle.<BannerDto>builder().item(bannerService.create(bannerDto)).build());
    }

    @GetMapping("/last")
    public ResponseEntity<GetSingle<BannerDto>> getLastBanner() {
        return ResponseEntity.ok(GetSingle.<BannerDto>builder().item(bannerService.last()).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteBanner(@PathVariable("id") String id) {
        bannerService.delete(id);
        return ResponseEntity.ok("");
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetSingle<BannerDto>> updateBanner(@RequestBody() UpdateBannerDto updateBannerDto) {
        return ResponseEntity.ok(GetSingle.<BannerDto>builder().item(bannerService.update(updateBannerDto.getId(), updateBannerDto.getFieldName(), updateBannerDto.getValue())).build());
    }

    @PutMapping("/setImage")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetSingle<String>> setBannerImage(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        System.out.println("request is here");
        return ResponseEntity.ok(
                GetSingle.<String>builder().item(bannerService.setBannerImage(file, id)).build());
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<?> getBannerImage(@PathVariable("name") String name) {
        InputStream inputStream = bannerService.getBannerImage(name);
        System.out.println(inputStream == null);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "filename.ext");
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK );
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetPage<BannerDto>> getBannerPage(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<BannerDto> bannerPage = bannerService.getBannerPage(pageNumber, pageSize);
        return ResponseEntity.ok(GetPage.<BannerDto>builder()
                        .length(bannerPage.getTotalElements())
                        .page(bannerPage.getContent())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSingle<BannerDto>> getBannerById(@PathVariable String id) {
        return ResponseEntity.ok(GetSingle.<BannerDto>builder().item(bannerService.getBannerById(id)).build());
    }

}
