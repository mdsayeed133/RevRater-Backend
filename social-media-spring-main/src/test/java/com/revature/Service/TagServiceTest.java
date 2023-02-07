package com.revature.Service;

import com.revature.models.Tag;
import com.revature.repositories.TagRepository;
import com.revature.services.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    void testGetTagById() {
        Tag mockTag = new Tag(1, "TestTag");
        when(tagRepository.findById(mockTag.getId())).thenReturn(Optional.of(mockTag));

        Tag result = tagService.findById(mockTag.getId());

        assertThat(mockTag, equalTo(result));
    }

}