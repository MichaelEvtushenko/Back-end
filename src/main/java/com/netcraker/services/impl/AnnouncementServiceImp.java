package com.netcraker.services.impl;

import com.netcraker.model.Announcement;
import com.netcraker.model.Page;
import com.netcraker.repositories.AnnouncementRepository;
import com.netcraker.services.AnnouncementService;
import com.netcraker.services.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@PropertySource({"classpath:view.properties"})
@RequiredArgsConstructor
public class AnnouncementServiceImp implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final PageService pageService;

    @Override
    public Page<Announcement> getAnnouncementsPagination(int page, int pageSize) {
        int total = announcementRepository.getCount();
        int pagesCount = pageService.getPagesCount(total, pageSize);
        int currentPage = pageService.getRestrictedPage(page, pagesCount);
        int offset = currentPage * pageSize;
        List<Announcement> list = announcementRepository.getAnnouncements(pageSize,offset);
        return new Page<>(currentPage, pagesCount, list);
    }
    @Override
    public Page<Announcement> getPublishAnnouncementsPagination(int page, int pageSize) {
        int totalPublish = announcementRepository.getPublishedCount();
        int pagesCount = pageService.getPagesCount(totalPublish, pageSize);
        int currentPage = pageService.getRestrictedPage(page, pagesCount);
        int offset = currentPage * pageSize;
        List<Announcement> list = announcementRepository.getPublishedAnnouncements(pageSize,offset);
        return new Page<>(currentPage, pagesCount, list);
    }

    @Override
    public Optional<Announcement> getAnnouncementById(int id) {
        return announcementRepository.getById(id);
    }

    @Override
    public Optional<Announcement> addAnnouncement(Announcement announcement) {
        return announcementRepository.insert(announcement);
    }

    @Override
    public Optional<Announcement> updateAnnouncement(Announcement announcement) {
        return announcementRepository.update(announcement);
    }

    @Override
    public boolean deleteAnnouncement(int id) {
        return announcementRepository.delete(id);
    }

    @Override
    public Page<Announcement> getPaginations(int page, int count) {
        return null;
    }
}
