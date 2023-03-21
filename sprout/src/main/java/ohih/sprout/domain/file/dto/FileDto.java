package ohih.sprout.domain.file.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private Long id;
    private String oriFileName;
    private String savedFileName;
    private String ext;
    private Long size;

    private String dir;

    public FileDto(Long id, String oriFileName, String savedFileName, String ext, Long size) {
        this.id = id;
        this.oriFileName = oriFileName;
        this.savedFileName = savedFileName;
        this.ext = ext;
        this.size = size;
    }
}
