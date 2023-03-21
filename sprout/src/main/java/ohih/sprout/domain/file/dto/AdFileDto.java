package ohih.sprout.domain.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdFileDto {

    private String provider;
    private String oriFileName;
    private String savedFileName;
    private String ext;
    private Long size;
    private String link;
}
