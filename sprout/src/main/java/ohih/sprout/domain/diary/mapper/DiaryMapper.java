package ohih.sprout.domain.diary.mapper;

import ohih.sprout.domain.diary.dto.DiaryAccessAuthorityDto;
import ohih.sprout.domain.diary.dto.DiaryConfigUpdateDto;
import ohih.sprout.domain.diary.dto.DiaryListDto;
import ohih.sprout.domain.diary.dto.DiaryEditDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DiaryMapper {
    int diaryConfigInit(Long userId);

    int activateDiary(Long userId);

    int deactivateDiary(Long userId);

    int updateDiaryConfig(DiaryConfigUpdateDto diaryConfigUpdateDto);

    /*Long getTotalCountByOwner(Map map);

    List<DiaryListDto> getPostListByOwner(Map map);*/

    int writeDiary(DiaryEditDto diaryEditDto);

    DiaryAccessAuthorityDto getDiaryAccessAuthorityDto(Long postId);

    int deleteDiaryByDiaryId(Long diaryId);

    Boolean getDiaryActivationStatusByUserId(Long userId);

    Long getTotalDiaryCountByName(String owner);

    int getTodayDiaryCountByName(String owner);

    List<DiaryListDto> getDiaryListByName(Map map);

    Boolean getDiaryActivationStatus(String name);
}
