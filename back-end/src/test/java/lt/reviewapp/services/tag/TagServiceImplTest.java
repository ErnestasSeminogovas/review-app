package lt.reviewapp.services.tag;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.entities.Tag;
import lt.reviewapp.models.tag.TagDto;
import lt.reviewapp.models.tag.TagRequest;
import lt.reviewapp.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;

    private Tag tag;
    private TagDto tagDto;
    private TagRequest tagRequest;

    @BeforeEach
    void setUp() {
        LocalDateTime dateTime = LocalDateTime.now();
        tag = Tag.builder().id(1).title("warning").createdAt(dateTime).updatedAt(dateTime).build();
        tagDto = TagDto.builder().id(1).title("warning").createdAt(dateTime).updatedAt(dateTime).build();
        tagRequest = TagRequest.builder().title("error").build();
    }

    @Test
    void givenTags_whenFindAll_thenReturnTags() {
        given(tagRepository.findAll()).willReturn(List.of(tag));

        List<TagDto> tags = tagService.findAll();

        assertThat(tags).containsExactly(tagDto);
    }

    @Test
    void givenTag_whenFindById_thenReturnTag() {
        given(tagRepository.findById(1)).willReturn(Optional.of(tag));

        TagDto foundTagDto = tagService.findById(1);

        assertThat(foundTagDto).isEqualTo(tagDto);
    }

    @Test
    void givenNoTag_whenFindById_thenThrowEntityNotFoundException() {
        given(tagRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tagService.findById(anyInt()));
    }

    @Test
    void givenTagRequestWithExistingTitle_whenCreate_thenThrowBadRequestException() {
        given(tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())).willReturn(true);

        assertThrows(BadRequestException.class, () -> tagService.create(tagRequest));
    }

    @Test
    void givenTagRequestWithNotExistingTitle_whenCreate_thenSaveNewUser() {
        given(tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())).willReturn(false);
        given(tagRepository.save(any())).willReturn(tag);

        Integer tagId = tagService.create(tagRequest);

        assertThat(tagId).isEqualTo(tag.getId());
    }

    @Test
    void givenTagWithNotExistingTitle_whenUpdateById_thenUpdateTag() {
        given(tagRepository.findById(1)).willReturn(Optional.of(tag));
        given(tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())).willReturn(false);

        tagService.updateById(1, tagRequest);

        then(tagRepository).should().updateTitleById(1, tagRequest.getTitle());
    }

    @Test
    void givenNoTag_whenUpdateById_thenThrowEntityNotFoundException() {
        given(tagRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tagService.updateById(anyInt(), tagRequest));
    }

    @Test
    void givenTagWithExistingTitle_whenUpdateById_thenThrowBadRequestException() {
        given(tagRepository.findById(1)).willReturn(Optional.of(tag));
        given(tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())).willReturn(true);

        assertThrows(BadRequestException.class, () -> tagService.updateById(1, tagRequest));
    }

    @Test
    void givenTag_whenDeleteById_thenDeleteById() {
        given(tagRepository.existsById(anyInt())).willReturn(true);

        tagService.deleteById(anyInt());

        then(tagRepository).should().deleteById(anyInt());
    }

    @Test
    void givenNoTag_whenDeleteById_thenEntityNotFoundThrown() {
        given(tagRepository.existsById(anyInt())).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> tagService.deleteById(anyInt()));
    }
}