package lt.reviewapp.services.tag;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.entities.Tag;
import lt.reviewapp.models.tag.TagDto;
import lt.reviewapp.models.tag.TagRequest;
import lt.reviewapp.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void givenTags_whenFindAll_thenReturnTags() {
        Tag tag = new Tag();
        TagDto tagDto = new TagDto();

        given(tagRepository.findAll()).willReturn(List.of(tag));
        given(modelMapper.map(tag, TagDto.class)).willReturn(tagDto);

        List<TagDto> tags = tagService.findAll();

        assertThat(tags).containsExactly(tagDto);
    }

    @Test
    void givenTag_whenFindById_thenReturnTag() {
        Tag tag = Tag.builder().id(1).build();
        TagDto tagDto = new TagDto();

        given(tagRepository.findById(1)).willReturn(Optional.of(tag));
        given(modelMapper.map(tag, TagDto.class)).willReturn(tagDto);

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
        TagRequest tagRequest = new TagRequest();

        given(tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())).willReturn(true);

        assertThrows(BadRequestException.class, () -> tagService.create(tagRequest));
    }

    @Test
    void givenTagRequestWithNotExistingTitle_whenCreate_thenSaveNewUser() {
        TagRequest tagRequest = new TagRequest();
        Tag tag = new Tag();
        Tag savedTag = Tag.builder().id(1).build();

        given(tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())).willReturn(false);
        given(modelMapper.map(tagRequest, Tag.class)).willReturn(tag);
        given(tagRepository.save(tag)).willReturn(savedTag);

        Integer tagId = tagService.create(tagRequest);

        assertThat(tagId).isEqualTo(savedTag.getId());
    }

    @Test
    void givenTagWithNotExistingTitle_whenUpdateById_thenUpdateTag() {
        Tag tag = Tag.builder().id(1).title("warning").build();
        TagRequest tagRequest = new TagRequest("error");

        given(tagRepository.findById(1)).willReturn(Optional.of(tag));
        given(tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())).willReturn(false);

        tagService.updateById(1, tagRequest);

        then(tagRepository).should().updateTitleById(1, tagRequest.getTitle());
    }

    @Test
    void givenNoTag_whenUpdateById_thenThrowEntityNotFoundException() {
        given(tagRepository.findById(anyInt())).willReturn(Optional.empty());

        TagRequest tagRequest = new TagRequest();

        assertThrows(EntityNotFoundException.class, () -> tagService.updateById(anyInt(), tagRequest));
    }

    @Test
    void givenTagWithExistingTitle_whenUpdateById_thenThrowBadRequestException() {
        Tag tag = Tag.builder().id(1).title("warning").build();
        TagRequest tagRequest = new TagRequest("error");

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