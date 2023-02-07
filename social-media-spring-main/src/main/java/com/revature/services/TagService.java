package com.revature.services;

import com.revature.models.Tag;
import com.revature.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagService {

    private TagRepository tagRepository;

    @Autowired

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findById(int tagId)
    {
        return tagRepository.findById(tagId).orElse(null);
    }
}
