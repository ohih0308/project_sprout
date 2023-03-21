package ohih.sprout.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Paging {
    private Long totalCount;
    private Integer totalPages;

    private Integer startPage;
    private Integer endPage;

    public Integer presentPage;

    private Long firstItemIndex;

    private Integer listSize;

    private Integer pages = 10;


    public Paging() {
        this.presentPage = 0;
    }

    public Paging(Integer presentPage) {
        this.presentPage = presentPage;
    }
}
