package lt.reviewapp.services.tag;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.entities.Tag;
import lt.reviewapp.models.tag.TagDto;
import lt.reviewapp.models.tag.TagRequest;
import lt.reviewapp.repositories.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto findById(Integer id) {
        Tag tag =
                tagRepository.findById(id).orElseThrow(() -> createEntityNotFoundException(id));

        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public Integer create(TagRequest tagRequest) {
        if (tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())) {
            throw new BadRequestException("Tag already exist with title: " + tagRequest.getTitle());
        }

        Tag tag = modelMapper.map(tagRequest, Tag.class);
        return tagRepository.save(tag).getId();
    }

    @Transactional
    @Override
    public void updateById(Integer id, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> createEntityNotFoundException(id));

        if (!tag.getTitle().equalsIgnoreCase(tagRequest.getTitle()) && tagRepository.existsByTitleIgnoreCase(tagRequest.getTitle())) {
            throw new BadRequestException("Tag already exist with title: " + tagRequest.getTitle());
        }

        tagRepository.updateTitleById(id, tagRequest.getTitle());
    }

    @Override
    public void deleteById(Integer id) {
        if (!tagRepository.existsById(id)) {
            throw createEntityNotFoundException(id);
        }

        tagRepository.deleteById(id);
    }

    private EntityNotFoundException createEntityNotFoundException(Integer id) {
        return new EntityNotFoundException("Tag not found by id: " + id);
    }
}
