package lt.reviewapp.services.tag;

import lt.reviewapp.models.tag.TagDto;
import lt.reviewapp.models.tag.TagRequest;

import java.util.List;

public interface TagService {
    List<TagDto> findAll();

    TagDto findById(Integer id);

    Integer create(TagRequest tagRequest);

    void updateById(Integer id, TagRequest tagRequest);

    void deleteById(Integer id);
}
